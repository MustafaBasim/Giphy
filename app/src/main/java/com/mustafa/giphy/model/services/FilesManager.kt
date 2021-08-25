package com.mustafa.giphy.model.services

import android.app.DownloadManager
import android.content.Context
import android.content.Context.DOWNLOAD_SERVICE
import android.net.Uri
import com.mustafa.giphy.R
import com.mustafa.giphy.model.data_models.responses.Data
import com.mustafa.giphy.model.repository.DatabaseRepository
import com.mustafa.giphy.utilities.getGifsLocalPath
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject


/**
 * Created by: Mustafa Basim
 * E-mail: 96.mustafa.basim@gmail.com
 * Project name: FreshWorks Giphy
 * Package: com.mustafa.giphy.model.services
 * Date: 8/25/2021
 */

class FilesManager @Inject constructor(@ApplicationContext val context: Context) {

    @Inject
    lateinit var databaseRepository: DatabaseRepository

    suspend fun downloadFile(data: Data) {
        val file = File(context.getGifsLocalPath(data.id ?: ""))
        val request = DownloadManager.Request(Uri.parse(data.images?.original?.url))
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
            .setDestinationUri(Uri.fromFile(file))
            .setTitle(data.title)
            .setDescription(context.getString(R.string.downloading_message))

        val downloadManager = context.getSystemService(DOWNLOAD_SERVICE) as DownloadManager?
        val downloadId: Long? = downloadManager?.enqueue(request)
        data.downloadId = downloadId
        databaseRepository.update(data)
    }

    fun deleteFile(data: Data) {
        data.downloadId?.let { downloadId ->
            val downloadManager = context.getSystemService(DOWNLOAD_SERVICE) as DownloadManager?
            downloadManager?.remove(downloadId)
        }

        data.id?.let { File(context.getGifsLocalPath(data.id)).delete() }
    }

}