package com.mustafa.giphy.model.repository

import androidx.lifecycle.LiveData
import com.mustafa.giphy.model.data_models.responses.Data
import com.mustafa.giphy.model.data_models.responses.DataId
import com.mustafa.giphy.model.database.daos.FavouriteGifsDao
import com.mustafa.giphy.model.database.entities.FavouriteGifsEntity
import com.mustafa.giphy.utilities.ObjectsMapper.toFavouriteEntity
import javax.inject.Inject

/**
 * Created by: Mustafa Basim
 * E-mail: 96.mustafa.basim@gmail.com
 * Project name: FreshWorks Giphy
 * Package: com.mustafa.giphy.model.repository
 * Date: 8/21/2021
 */

class DatabaseRepository @Inject constructor() {

    @Inject
    lateinit var favouriteGifsDao: FavouriteGifsDao

//    suspend fun getFavouriteGifs(pageSize: Int, pageIndex: Int): List<FavouriteGifsEntity> = favouriteGifsDao.select(pageSize, pageIndex)

    fun getFavouriteGifs(): LiveData<List<FavouriteGifsEntity>> = favouriteGifsDao.selectAll()

    fun getFavouriteGifsIds(): List<DataId> = favouriteGifsDao.selectIdsOnly()

    suspend fun addToFavourite(data: Data) {
        favouriteGifsDao.insert(data.toFavouriteEntity())
    }

    suspend fun getFavouriteGifByDownloadId(downloadId: Long): FavouriteGifsEntity? = favouriteGifsDao.selectByDownloadId(downloadId)

    suspend fun update(data: Data) {
        favouriteGifsDao.update(data.toFavouriteEntity())
    }

    suspend fun removeFromFavourite(data: Data) {
        favouriteGifsDao.delete(data.toFavouriteEntity())
    }

}