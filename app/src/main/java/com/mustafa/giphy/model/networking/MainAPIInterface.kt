package com.mustafa.giphy.model.networking

import com.mustafa.giphy.model.data_models.responses.DataResponse
import com.mustafa.giphy.utilities.Constants
import com.mustafa.giphy.utilities.Urls
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * Created by: Mustafa Basim
 * E-mail: 96.mustafa.basim@gmail.com
 * Project name: FreshWorks Giphy
 * Package: com.mustafa.giphy.model.networking
 * Date: 8/21/2021
 */

interface MainAPIInterface {

    @GET(Urls.TRENDING)
    suspend fun trending(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int = Constants.PAGE_LIMIT,
        @Query("api_key") apiKey: String = Constants.API_KEY
    ): Response<DataResponse>

    @GET(Urls.SEARCH)
    suspend fun search(
        @Query("q") query: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int = Constants.PAGE_LIMIT,
        @Query("api_key") apiKey: String = Constants.API_KEY
    ): Response<DataResponse>

//    @POST(Urls.POST_LOGIN)
//    suspend fun login(@Body loginBody: LoginBody): Response<UserRegisterLoginResponse>
//
//    @POST(Urls.POST_REGISTER)
//    suspend fun register(@Body registerBody: RegisterBody): Response<UserRegisterLoginResponse>
//
//    @POST(Urls.POST_UPDATE_USER)
//    suspend fun updateUser(@Body updateUserBody: UpdateUserBody): Response<UserRegisterLoginResponse>
//
//    @Multipart
//    @POST(Urls.POST_UPDATE_PUBLISHER)
//    suspend fun updatePublisher(
//        @Part("name") name: RequestBody,
//        @Part("email") email: RequestBody,
//        @Part("phone") phone: RequestBody,
//        @Part("description") description: RequestBody,
//        @Part("latitude") latitude: Double?,
//        @Part("longitude") longitude: Double?,
//        @Part avatar: MultipartBody.Part?,
//        @Part cover: MultipartBody.Part?
//    ): Response<UserRegisterLoginResponse>
//
//    @GET(Urls.GET_CATEGORIES)
//    suspend fun categories(): Response<CategoriesResponse>
//
//    @GET(Urls.GET_SUB_CATEGORIES)
//    suspend fun subCategories(): Response<SubCategoriesResponse>
//
//    @GET(Urls.GET_ABOUT)
//    suspend fun about(): Response<AboutResponse>
//
//    @GET(Urls.GET_PUBLISHERS)
//    suspend fun publishers(@Query("page") pageNumber: Int): Response<PublishersResponse>
//
//    @GET("${Urls.GET_PUBLISHER}/{publisherId}")
//    suspend fun publisher(@Path("publisherId") publisherId: Int): Response<PublisherResponse>
//
//    @Multipart
//    @POST(Urls.POST_NEW_POST)
//    suspend fun newPost(
//        @Part("title") model: RequestBody,
//        @Part("description") description: RequestBody,
//        @Part("category_id") category_id: Int,
//        @Part images: ArrayList<MultipartBody.Part>?
//    ): Response<MessageResponse>
//
//    @PUT("${Urls.POST_UPDATE_POST}/{postId}")
//    suspend fun updatePost(
//        @Path("postId") postId: Int,
//        @Body updatePostBody: UpdatePostBody
//    ): Response<UpdatePostResponse>
//
//    @Multipart
//    @POST("${Urls.POST_ADD_IMAGES}/{postId}/images")
//    suspend fun addImages(@Path("postId") postId: Int, @Part images: ArrayList<MultipartBody.Part>?): Response<UpdatePostResponse>
//
//    @POST("${Urls.POST_FOLLOW_PUBLISHER}/{publisherId}")
//    suspend fun followPublisher(@Path("publisherId") publisherId: Int): Response<MessageResponse>
//
//    @DELETE("${Urls.DELETE_UNFOLLOW_PUBLISHER}/{publisherId}")
//    suspend fun unfollowPublisher(@Path("publisherId") publisherId: Int): Response<MessageResponse>
//
//    @GET(Urls.GET_POSTS)
//    suspend fun posts(@Query("search") search: String, @Query("category_id") categoryId: String, @Query("page") pageNumber: Int): Response<PostsResponse>
//
//    @GET(Urls.GET_SAVED_POSTS)
//    suspend fun savedPosts(@Query("page") pageNumber: Int): Response<PostsResponse>
//
//    @GET(Urls.GET_POSTS)
//    suspend fun posts(@Query("created_by") publisherId: Int, @Query("page") pageNumber: Int): Response<PostsResponse>
//
//    @GET("${Urls.GET_POST}/{postId}")
//    suspend fun getPost(@Path("postId") postId: Int): Response<PostResponse>
//
//    @POST("${Urls.POST_LIKE_POST}/{postId}")
//    suspend fun likePost(@Path("postId") postId: Int): Response<MessageResponse>
//
//    @POST("${Urls.POST_SAVE_POST}/{postId}")
//    suspend fun savePost(@Path("postId") postId: Int): Response<MessageResponse>
//
//    @POST("${Urls.POST_CALL_POST}/{postId}")
//    suspend fun callPost(@Path("postId") postId: Int): Response<MessageResponse>
//
//    @POST("${Urls.POST_CALL_PUBLISHER}/{publisherId}")
//    suspend fun callPublisher(@Path("publisherId") postId: Int): Response<MessageResponse>
//
//    @DELETE("${Urls.DELETE_POST_DELETE}/{postId}")
//    suspend fun deletePost(@Path("postId") postId: Int): Response<MessageResponse>
//
//    @DELETE("${Urls.DELETE_IMAGE}/{postId}/images/{imageId}")
//    suspend fun deleteImage(@Path("postId") postId: Int, @Path("imageId") imageId: String): Response<UpdatePostResponse>
//
//    @GET(Urls.GET_HOME)
//    suspend fun home(@Query("page") pageNumber: Int): Response<PostsResponse>
//
//    @POST(Urls.POST_FOLLOW_CATEGORIES)
//    suspend fun followCategories(@Body followCategoriesBody: FollowCategoriesBody): Response<MessageResponse>

}