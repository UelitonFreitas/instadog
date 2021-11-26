package com.hero.instadog.di

import android.app.Application
import androidx.room.Room
import com.hero.instadog.api.ApiBuilder
import com.hero.instadog.api.BreedsService
import com.hero.instadog.database.BreedsDatabase
import com.hero.instadog.database.dao.BreedDao
import dagger.Module
import dagger.Provides
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {


    @Singleton
    @Provides
    fun provideBreedsService(
        apiBuilder: ApiBuilder
    ): BreedsService {
        return apiBuilder.buildBreedService(
            baseUrl = "https://dog.ceo/api/",
            loggingLevel = HttpLoggingInterceptor.Level.BODY
        )
    }

    @Singleton
    @Provides
    fun provideDb(app: Application): BreedsDatabase {
        return Room
            .databaseBuilder(app, BreedsDatabase::class.java, "breeds.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideBreedDao(db: BreedsDatabase): BreedDao {
        return db.breedDao()
    }
}