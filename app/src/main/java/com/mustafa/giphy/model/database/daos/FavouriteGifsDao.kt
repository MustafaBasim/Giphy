package com.mustafa.giphy.model.database.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mustafa.giphy.model.data_models.responses.DataId
import com.mustafa.giphy.model.database.entities.FavouriteGifsEntity


@Dao
interface FavouriteGifsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favouriteGif: FavouriteGifsEntity): Long

    @Update()
    suspend fun update(favouriteGif: FavouriteGifsEntity)

    @Query("SELECT * FROM favourite_gifs_table WHERE id = :id")
    suspend fun selectById(id: String): FavouriteGifsEntity

    @Query("SELECT * FROM favourite_gifs_table WHERE download_id = :downloadId")
    suspend fun selectByDownloadId(downloadId: Long): FavouriteGifsEntity?

    @Delete
    suspend fun delete(favouriteGif: FavouriteGifsEntity)

//    TODO Future feature to add pagination either manually or by using jetpack paging 3 library
//    @Query("SELECT * FROM favourite_gifs_table ORDER BY created_at DESC LIMIT :pageSize OFFSET :pageIndex ")
//    fun select(pageSize: Int, pageIndex: Int): Flow<List<FavouriteGifsEntity>>

    @Query("SELECT * FROM favourite_gifs_table ORDER BY created_at DESC")
    fun selectAll(): LiveData<List<FavouriteGifsEntity>>

    @Query("SELECT id FROM favourite_gifs_table ORDER BY created_at DESC")
    fun selectIdsOnly(): List<DataId>

    @Query("SELECT COUNT(*) FROM favourite_gifs_table")
    suspend fun getFavouriteGifsCount(): Int
}