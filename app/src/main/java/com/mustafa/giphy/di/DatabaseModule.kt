package com.mustafa.giphy.di

/**
 * Created by: Mustafa Basim
 * E-mail: 96.mustafa.basim@gmail.com
 * Project name: Giphy
 * Package: com.mustafa.giphy.di
 * Date: 8/22/2021
 */

import android.content.Context
import com.mustafa.giphy.model.database.AppDatabase
import com.mustafa.giphy.model.database.daos.FavouriteGifsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.init(context)
    }

    @Provides
    fun provideFavouriteGifsDao(database: AppDatabase): FavouriteGifsDao {
        return database.favouriteGifsDao()
    }
}