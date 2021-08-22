package com.mustafa.giphy.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mustafa.giphy.model.database.daos.FavouriteGifsDao
import com.mustafa.giphy.model.database.entities.FavouriteGifsEntity


@Database(entities = [FavouriteGifsEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun favouriteGifsDao(): FavouriteGifsDao

    companion object {

        private lateinit var INSTANCE: AppDatabase

        fun init(context: Context): AppDatabase {
            if (!::INSTANCE.isInitialized) {
                synchronized(RoomDatabase::class.java) {
                    if (!::INSTANCE.isInitialized) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java,
                            "favourite_database"
                        )
                            .build()
                    }
                }
            }
            return INSTANCE
        }

    }
}