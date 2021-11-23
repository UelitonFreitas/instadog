package com.hero.instadog.database;

import androidx.room.Database;
import androidx.room.RoomDatabase
import com.hero.instadog.database.dao.BreedDao
import com.hero.instadog.database.model.Breed

@Database(
    entities = [
        Breed::class
    ],
    version = 1,
    exportSchema = false
)
abstract class BreedsDatabase : RoomDatabase() {
        abstract fun breedDao(): BreedDao
}
