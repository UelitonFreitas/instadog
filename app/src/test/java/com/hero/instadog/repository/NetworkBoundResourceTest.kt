package com.hero.instadog.repository


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.hero.instadog.api.ApiResponse
import com.hero.instadog.repository.model.Resource
import com.hero.instadog.util.CountingAppExecutors
import com.hero.instadog.util.InstantAppExecutors
import com.hero.instadog.util.mock
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.mockito.Mockito.reset
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions
import retrofit2.Response
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicReference

@RunWith(Parameterized::class)
class NetworkBoundResourceTest(private val useRealExecutors: Boolean) {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var handleSaveCallResult: (AnyClass) -> Unit

    private lateinit var handleShouldMatch: (AnyClass?) -> Boolean

    private lateinit var handleCreateCall: () -> LiveData<ApiResponse<AnyClass>>

    private val dbData = MutableLiveData<AnyClass>()

    private lateinit var networkBoundResource: NetworkBoundResource<AnyClass, AnyClass>

    private val fetchedOnce = AtomicBoolean(false)
    private lateinit var countingAppExecutors: CountingAppExecutors

    init {
        if (useRealExecutors) {
            countingAppExecutors = CountingAppExecutors()
        }
    }

    @Before
    fun init() {

        val appExecutors = when (useRealExecutors) {
            true -> countingAppExecutors.appExecutors
            false -> InstantAppExecutors()
        }

        networkBoundResource = object : NetworkBoundResource<AnyClass, AnyClass>(appExecutors) {
            override fun saveCallResult(item: AnyClass) {
                handleSaveCallResult(item)
            }

            override fun shouldFetch(data: AnyClass?): Boolean {
                // since test methods don't handle repetitive fetching, call it only once
                return handleShouldMatch(data) && fetchedOnce.compareAndSet(false, true)
            }

            override fun loadFromDb(): LiveData<AnyClass> {
                return dbData
            }

            override fun createCall(): LiveData<ApiResponse<AnyClass>> {
                return handleCreateCall()
            }
        }
    }

    private fun executeTask() {
        if (!useRealExecutors) {
            return
        }
        try {
            countingAppExecutors.drainTasks(1, TimeUnit.SECONDS)
        } catch (t: Throwable) {
            throw AssertionError(t)
        }

    }

    @Test
    fun shouldLoadDataFromNetwork() {
        val dataToBeSaved = AtomicReference<AnyClass>()

        handleShouldMatch = { it == null }

        val fetchedDbValue = AnyClass(1)

        handleSaveCallResult = { foo ->
            dataToBeSaved.set(foo)
            dbData.setValue(fetchedDbValue)
        }

        val networkResult = AnyClass(1)
        handleCreateCall = { ApiUtil.createCall(Response.success(networkResult)) }

        val observer = mock<Observer<Resource<AnyClass>>>()
        networkBoundResource.asLiveData().observeForever(observer)

        executeTask()
        verify(observer).onChanged(Resource.loading(null))

        reset(observer)
        dbData.value = null

        executeTask()

        assertThat(dataToBeSaved.get(), `is`(networkResult))
        verify(observer).onChanged(Resource.success(fetchedDbValue))
    }

    @Test
    fun shouldNotifyAnErrorWhenThereWasAnNetworkError() {
        val dataToBeSaved = AtomicBoolean(false)

        handleShouldMatch = { it == null }

        handleSaveCallResult = {
            dataToBeSaved.set(true)
        }

        val body = ResponseBody.create(MediaType.parse("text/html"), "error")
        handleCreateCall = { ApiUtil.createCall(Response.error<AnyClass>(500, body)) }
        val observer = mock<Observer<Resource<AnyClass>>>()
        networkBoundResource.asLiveData().observeForever(observer)

        executeTask()

        verify(observer).onChanged(Resource.loading(null))
        reset(observer)

        dbData.value = null

        executeTask()

        assertThat(dataToBeSaved.get(), `is`(false))

        verify(observer).onChanged(Resource.error("error", null))

        verifyNoMoreInteractions(observer)
    }

    @Test
    fun shouldLoadDataFromDatabase() {

        val dataToBeSaved = AtomicBoolean(false)

        handleShouldMatch = { it == null }
        handleSaveCallResult = {
            dataToBeSaved.set(true)
        }

        val netWorkBoundObserver = mock<Observer<Resource<AnyClass>>>()
        networkBoundResource.asLiveData().observeForever(netWorkBoundObserver)

        executeTask()

        verify(netWorkBoundObserver).onChanged(Resource.loading(null))
        reset(netWorkBoundObserver)

        val dbFoo = AnyClass(1)
        dbData.value = dbFoo

        executeTask()

        verify(netWorkBoundObserver).onChanged(Resource.success(dbFoo))
        assertThat(dataToBeSaved.get(), `is`(false))

        val dbFoo2 = AnyClass(2)
        dbData.value = dbFoo2

        executeTask()

        verify(netWorkBoundObserver).onChanged(Resource.success(dbFoo2))
        verifyNoMoreInteractions(netWorkBoundObserver)
    }

    @Test
    fun shouldLoadFromDatabaseWhenThereWasANetworkError() {
        val dataOnDatabase = AnyClass(1)

        val dataToBeSaved = AtomicBoolean(false)

        handleShouldMatch = { foo -> foo === dataOnDatabase }
        handleSaveCallResult = {
            dataToBeSaved.set(true)
        }
        val body = ResponseBody.create(MediaType.parse("text/html"), "error")
        val apiResponseLiveData = MutableLiveData<ApiResponse<AnyClass>>()
        handleCreateCall = { apiResponseLiveData }

        val observer = mock<Observer<Resource<AnyClass>>>()
        networkBoundResource.asLiveData().observeForever(observer)

        executeTask()

        verify(observer).onChanged(Resource.loading(null))
        reset(observer)

        dbData.value = dataOnDatabase
        executeTask()
        verify(observer).onChanged(Resource.loading(dataOnDatabase))

        apiResponseLiveData.value = ApiResponse.create(Response.error<AnyClass>(400, body))

        executeTask()

        assertThat(dataToBeSaved.get(), `is`(false))
        verify(observer).onChanged(Resource.error("error", dataOnDatabase))

        val dbValue2 = AnyClass(2)
        dbData.value = dbValue2

        executeTask()

        verify(observer).onChanged(Resource.error("error", dbValue2))
        verifyNoMoreInteractions(observer)
    }

    @Test
    fun shouldLoadDataFromNetworkAndSaveItOnDb() {
        val dataToBeSaved1 = AnyClass(1)
        val dataToBeSaved2 = AnyClass(2)

        val saved = AtomicReference<AnyClass>()

        handleShouldMatch = { foo -> foo === dataToBeSaved1 }

        handleSaveCallResult = { foo ->
            saved.set(foo)
            dbData.setValue(dataToBeSaved2)
        }

        val apiResponseLiveData = MutableLiveData<ApiResponse<AnyClass>>()
        handleCreateCall = { apiResponseLiveData }

        val observer = mock<Observer<Resource<AnyClass>>>()
        networkBoundResource.asLiveData().observeForever(observer)

        executeTask()

        verify(observer).onChanged(Resource.loading(null))
        reset(observer)

        dbData.value = dataToBeSaved1
        executeTask()

        val networkResult = AnyClass(1)
        verify(observer).onChanged(Resource.loading(dataToBeSaved1))

        apiResponseLiveData.value = ApiResponse.create(Response.success(networkResult))
        executeTask()

        assertThat(saved.get(), `is`(networkResult))

        verify(observer).onChanged(Resource.success(dataToBeSaved2))
        verifyNoMoreInteractions(observer)
    }

    private data class AnyClass(var value: Int)

    companion object {
        @Parameterized.Parameters
        @JvmStatic
        fun param(): List<Boolean> {
            return arrayListOf(true, false)
        }
    }
}