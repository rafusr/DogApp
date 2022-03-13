package com.rezzavinola.dogapplication.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rezzavinola.dogapplication.data.model.entity.DogsEntity


@Database(entities = [DogsEntity::class], version = 1)
abstract class DogsDatabase : RoomDatabase() {

    abstract fun dogs(): DogsDao

    companion object {
        private var database: DogsDatabase? = null

        fun instance(context: Context): DogsDatabase {
            if (database == null) {
                synchronized(DogsDatabase::class) {
                    database = Room.databaseBuilder(
                        context.applicationContext,
                        DogsDatabase::class.java,
                        "dog_database.db"
                    ).build()
                }
            }
            return database!!
        }

        fun destroyInstance() {
            database = null
        }
    }
}