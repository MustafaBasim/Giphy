package com.mustafa.giphy.utilities

import com.mustafa.giphy.model.data_models.responses.Data
import com.mustafa.giphy.model.data_models.responses.Images
import com.mustafa.giphy.model.data_models.responses.Original
import com.mustafa.giphy.model.data_models.responses.PreviewGif
import com.mustafa.giphy.model.database.entities.FavouriteGifsEntity
import com.mustafa.giphy.utilities.ObjectsMapper.toFavouriteEntity
import com.mustafa.giphy.utilities.ObjectsMapper.toGifData
import org.junit.Test

import org.junit.Assert.*
import java.util.*

/**
 * Created by: Mustafa Basim
 * E-mail: 96.mustafa.basim@gmail.com
 * Project name: FreshWorks Giphy
 * Package: com.mustafa.giphy.utilities
 * Date: 8/25/2021
 */

class ObjectsMapperTest {

    private val createdAt = Date().time

    private val data = Data(
        id = "12345",
        title = "Title",
        username = "Username",
        images = Images(
            original = Original(url = "url_link"),
            previewGif = PreviewGif(url = "url_link")
        ),
        isFavourite = true,
        downloadId = 0
    )

    private val favouriteGifsEntity = FavouriteGifsEntity(
        id = "12345",
        title = "Title",
        createdAt = createdAt,
        username = "Username",
        originalUrl = "url_link",
        previewUrl = "url_link",
        isAvailableOffline = false,
        downloadId = 0
    )

    @Test
    fun `from Data to FavouriteGifsEntity`() {
        val result = data.toFavouriteEntity().also { it.createdAt = createdAt }

        assertEquals(result, favouriteGifsEntity)
    }

    @Test
    fun `from FavouriteGifsEntity to Data`() {
        val result = favouriteGifsEntity.toGifData()

        assertEquals(result, data)
    }
}