package com.rezzavinola.dogapplication.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rezzavinola.dogapplication.data.model.entity.DogsEntity

@Dao
interface DogsDao {

    @Query("SELECT * FROM dogs")
    fun getAllDogs(): List<DogsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllDogs(dogs: List<DogsEntity>)

}