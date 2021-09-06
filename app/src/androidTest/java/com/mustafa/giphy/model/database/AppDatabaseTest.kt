package com.mustafa.giphy.model.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.mustafa.giphy.model.data_models.responses.DataId
import com.mustafa.giphy.model.database.daos.FavouriteGifsDao
import com.mustafa.giphy.model.database.entities.FavouriteGifsEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.io.IOException
import java.util.*

/**
 * Created by: Mustafa Basim
 * E-mail: 96.mustafa.basim@gmail.com
 * Project name: Giphy
 * Package: com.mustafa.giphy.model.database
 * Date: 8/25/2021
 */

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class AppDatabaseTest {


    private lateinit var db: AppDatabase
    private lateinit var favouriteGifsDao: FavouriteGifsDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()
        favouriteGifsDao = db.favouriteGifsDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndReadFromDatabase() {
        runBlocking {
            val favouriteGifsEntity = FavouriteGifsEntity(
                id = "12345",
                title = "Title",
                createdAt = Date().time,
                username = "Username",
                originalUrl = "url_link",
                previewUrl = "url_link",
                isAvailableOffline = false,
                downloadId = 0
            )

            favouriteGifsDao.insert(favouriteGifsEntity)
            val favouriteGif = favouriteGifsDao.selectById(favouriteGifsEntity.id)

            assertEquals(favouriteGifsEntity, favouriteGif)
        }
    }

    @Test
    @Throws(Exception::class)
    fun readFavouriteGifsIds() {
        runBlocking {
            val favouriteGif = FavouriteGifsEntity(
                id = "1",
                title = "Title",
                createdAt = Date().time,
                username = "Username",
                originalUrl = "url_link",
                previewUrl = "url_link",
                isAvailableOffline = false,
                downloadId = 0
            )
            val expectedResult = listOf(DataId("1"), DataId("2"), DataId("3"))

            expectedResult.forEach {
                favouriteGif.id = it.id
                favouriteGifsDao.insert(favouriteGif)
            }

            val favouriteGifsIds = favouriteGifsDao.selectIdsOnly()

            assertEquals(expectedResult, favouriteGifsIds)
        }
    }

    @Test
    @Throws(Exception::class)
    fun deleteFavouriteGif() {
        runBlocking {
            val favouriteGif = FavouriteGifsEntity(
                id = "1",
                title = "Title",
                createdAt = Date().time,
                username = "Username",
                originalUrl = "url_link",
                previewUrl = "url_link",
                isAvailableOffline = false,
                downloadId = 0
            )
            favouriteGifsDao.insert(favouriteGif)

            val expectedResult = null

            favouriteGifsDao.delete(favouriteGif)

            val favouriteGifsIds = favouriteGifsDao.selectById(favouriteGif.id)

            assertEquals(expectedResult, favouriteGifsIds)
        }
    }
}