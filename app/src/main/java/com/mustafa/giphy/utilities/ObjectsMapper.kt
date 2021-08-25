package com.mustafa.giphy.utilities

import com.mustafa.giphy.model.data_models.responses.Data
import com.mustafa.giphy.model.data_models.responses.Images
import com.mustafa.giphy.model.data_models.responses.Original
import com.mustafa.giphy.model.data_models.responses.PreviewGif
import com.mustafa.giphy.model.database.entities.FavouriteGifsEntity
import java.util.*

/**
 * Created by: Mustafa Basim
 * E-mail: 96.mustafa.basim@gmail.com
 * Project name: FreshWorks Giphy
 * Package: com.mustafa.giphy.utilities
 * Date: 8/21/2021
 */

object ObjectsMapper {

    fun Data.toFavouriteEntity(): FavouriteGifsEntity = FavouriteGifsEntity(
        id = id ?: UUID.randomUUID().toString(),
        title = title,
        createdAt = Date().time,
        username = username,
        originalUrl = images?.original?.url,
        previewUrl = images?.previewGif?.url
    )

    fun FavouriteGifsEntity.toGifData(): Data = Data(
        id = id,
        title = title,
        username = username,
        images = Images(
            original = Original(url = originalUrl),
            previewGif = PreviewGif(url = previewUrl)
        ),
        isFavourite = true
    )

}
