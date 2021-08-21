package com.mustafa.giphy.model.repository

import android.content.Context
import androidx.lifecycle.LiveData

/**
 * Created by: Mustafa Basim
 * E-mail: 96.mustafa.basim@gmail.com
 * Project name: FreshWorks Giphy
 * Package: com.mustafa.giphy.model.repository
 * Date: 8/21/2021
 */

//class DatabaseRepository(context: Context) {
//
//    companion object {
//        fun instance(context: Context) = DatabaseRepository(context)
//    }
//
//    private val appDatabase = AppDatabase.invoke(context)
//
//    private val episodeDao = appDatabase.episodesDao()
//
//    private val subtitlesDao = appDatabase.subtitlesDao()
//
////
////    suspend fun checkIfExistInDataBase(hash: String, type: String): Download? {
////        return if (type == "movie") movieDao.get(hash)?.toDownload()
////        else episodeDao.get(hash)?.toDownload()
////    }
////
//
//
//    suspend fun getDownloads(): ArrayList<Download>? {
//        val downloads = arrayListOf<Download>()
//        (episodeDao.getUnfinished() as ArrayList).map { downloads.add(it.toDownload()) }
//        (episodeDao.getAllFinishedGrouped() as ArrayList).map { downloads.add(it.toDownload()) }
//        return downloads
//    }
//
//    fun getUnfinishedLive(): LiveData<List<EpisodeEntity>> {
//        return episodeDao.getUnfinishedLive()
//    }
//
//    fun getAllFinishedGroupedLive(): LiveData<List<EpisodeEntity>> {
//        return episodeDao.getAllFinishedGroupedLive()
//    }
//
//    suspend fun getFinishedShowEpisodes(showId: Int): ArrayList<Download>? {
//        val downloads = arrayListOf<Download>()
//        (episodeDao.getFinishedShowEpisodes(showId) as ArrayList).map { downloads.add(it.toDownload()) }
//        return downloads
//    }
//
//    suspend fun getShowEpisodes(showId: Int): ArrayList<Download>? {
//        val downloads = arrayListOf<Download>()
//        (episodeDao.getShowEpisodes(showId) as ArrayList).map { downloads.add(it.toDownload()) }
//        return downloads
//    }
//
//    suspend fun getUnfinishedDownloadsWithNoErrors(): ArrayList<Download>? {
//        val downloads = arrayListOf<Download>()
//        (episodeDao.getUnfinishedWithNoErrors() as ArrayList).map { downloads.add(it.toDownload()) }
//        return downloads
//    }
//
//    suspend fun getUnfinishedDownloads(): ArrayList<Download>? {
//        val downloads = arrayListOf<Download>()
//        (episodeDao.getUnfinished() as ArrayList).map { downloads.add(it.toDownload()) }
//        return downloads
//    }
//
//    suspend fun setAllErrorsToPause() {
//        episodeDao.setAllErrorsToPause()
//    }
//
//    suspend fun <T> addToDb(model: T) {
//        when (model) {
//            is EpisodeEntity -> episodeDao.add(model)
//            is SubtitleEntity -> subtitlesDao.add(model)
//        }
//    }
//
//    suspend fun <T> updateDb(model: T) {
//        when (model) {
//            is EpisodeEntity -> episodeDao.update(model)
//            is SubtitleEntity -> subtitlesDao.update(model)
//        }
//    }
//
//    suspend fun getEpisodeById(id: Int): EpisodeEntity? {
//        return episodeDao.get(id)
//    }
//
//    suspend fun getSubtitlesByEpisodeId(episodeId: Int): ArrayList<SubtitleEntity>? {
//        val subtitles = subtitlesDao.getByEpisodeId(episodeId)
//        return if (subtitles != null) ArrayList(subtitles) else null
//    }
//
//    suspend fun getSeriesByShowId(showId: Int): Download? {
//        return episodeDao.getSeriesByShowId(showId)?.toDownload()
//    }
//
//    suspend fun <T> removeFromDb(model: T) {
//        when (model) {
//            is EpisodeEntity -> episodeDao.remove(model)
//            is SubtitleEntity -> subtitlesDao.remove(model)
//        }
//    }
//
//}