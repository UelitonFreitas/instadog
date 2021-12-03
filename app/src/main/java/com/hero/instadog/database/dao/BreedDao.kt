package com.hero.instadog.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.hero.instadog.database.model.Breed
import com.hero.instadog.database.model.BreedWithSubBreeds
import com.hero.instadog.database.model.SubBreed
import kotlinx.coroutines.flow.Flow

@Dao
interface BreedDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(breed: Breed)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBreeds(repositories: List<Breed>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSubBreeds(repositories: List<SubBreed>)

    @Query("SELECT * FROM breed")
    fun load(): LiveData<List<Breed>>

    @Query("SELECT * FROM breed WHERE name = :name")
    fun loadBreed(name: String): LiveData<Breed>

    @Transaction
    @Query("SELECT * FROM breed WHERE name = :name")
    fun getBreedWithSubBreeds(name: String): LiveData<BreedWithSubBreeds>

    @Query("SELECT * FROM breed")
    fun getBreedsWithFlow(): Flow<List<Breed>>
}