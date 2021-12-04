package com.hero.instadog.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hero.instadog.database.model.Breed
import kotlinx.coroutines.flow.Flow

@Dao
interface BreedDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBreeds(breeds: List<Breed>)

    @Query("SELECT * FROM breed")
    fun getBreedsWithFlow(): Flow<List<Breed>>
}