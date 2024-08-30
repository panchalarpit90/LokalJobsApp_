package com.example.lokaljobs.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.lokaljobs.network.model.Result


@Database(entities = [Result::class, ResultRemoteKey::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class JobsDatabase : RoomDatabase() {
    companion object {
        fun getInstance(context: Context): JobsDatabase {
            return Room.databaseBuilder(context, JobsDatabase::class.java, "jobs").build()
        }
    }

    abstract fun getJobDao(): JobsDao

}