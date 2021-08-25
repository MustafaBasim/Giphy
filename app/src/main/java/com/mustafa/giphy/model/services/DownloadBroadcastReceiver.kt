package com.mustafa.giphy.model.services

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.mustafa.giphy.model.repository.DatabaseRepository
import com.mustafa.giphy.utilities.Hilt_DownloadBroadcastReceiver
import com.mustafa.giphy.utilities.ObjectsMapper.toGifData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


/**
 * Created by: Mustafa Basim
 * E-mail: 96.mustafa.basim@gmail.com
 * Project name: FreshWorks Giphy
 * Package: com.mustafa.giphy.model.services
 * Date: 8/25/2021
 */

@AndroidEntryPoint(BroadcastReceiver::class)
class DownloadBroadcastReceiver : Hilt_DownloadBroadcastReceiver(), CoroutineScope {

    @Inject
    lateinit var databaseRepository: DatabaseRepository

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)

        val downloadId = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1) ?: -1

        launch {
            val data = databaseRepository.getFavouriteGifByDownloadId(downloadId)?.toGifData()
            data?.isAvailableOffline = true
            data?.let { databaseRepository.update(it) }
        }
    }
}