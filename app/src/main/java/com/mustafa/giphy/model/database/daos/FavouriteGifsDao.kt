package com.mustafa.giphy.model.database.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mustafa.giphy.model.data_models.responses.DataId
import com.mustafa.giphy.model.database.entities.FavouriteGifsEntity


@Dao
interface FavouriteGifsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favouriteGif: FavouriteGifsEntity): Long

    @Query("SELECT * FROM favourite_gifs_table WHERE id = :id")
    suspend fun selectById(id: String): FavouriteGifsEntity

    @Delete
    suspend fun delete(favouriteGif: FavouriteGifsEntity)

//    @Query("SELECT * FROM favourite_gifs_table ORDER BY created_at DESC LIMIT :pageSize OFFSET :pageIndex ")
//    suspend fun select(pageSize: Int, pageIndex: Int): List<FavouriteGifsEntity>

    @Query("SELECT * FROM favourite_gifs_table ORDER BY created_at DESC LIMIT :pageSize OFFSET :pageIndex ")
    fun select(pageSize: Int, pageIndex: Int): LiveData<List<FavouriteGifsEntity>>

    @Query("SELECT id FROM favourite_gifs_table ORDER BY created_at DESC")
    fun selectIdsOnly(): List<DataId>

    @Query("SELECT COUNT(*) FROM favourite_gifs_table")
    suspend fun getFavouriteGifsCount(): Int
}