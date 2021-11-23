package com.hero.instadog.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hero.instadog.database.model.Breed

@Dao
interface BreedDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(breed: Breed)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBreeds(repositories: List<Breed>)

    @Query("SELECT * FROM breed")
    abstract fun load(): LiveData<List<Breed>>
}