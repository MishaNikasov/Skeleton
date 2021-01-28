package com.lampa.skeleton.data.network

import com.lampa.skeleton.data.network.NetworkUrls.GET_POST
import com.lampa.skeleton.data.network.NetworkUrls.GET_POST_LIST
import com.lampa.skeleton.data.network.model.post.PostNetworkEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PostApi {

    @GET(GET_POST_LIST)
    suspend fun getPostList(): Response<List<PostNetworkEntity>?>

    @GET(GET_POST)
    suspend fun getPost(@Path("id") id: Int?): Response<PostNetworkEntity?>

}