package com.rezzavinola.dogapplication.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.rezzavinola.dogapplication.data.model.entity.DogsEntity
import retrofit2.http.Query

@Dao
interface DogsDao {

    @Query("select * from dogs")
    fun getAllDogs(): List<DogsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllDogs(dogs: List<DogsEntity>)

}