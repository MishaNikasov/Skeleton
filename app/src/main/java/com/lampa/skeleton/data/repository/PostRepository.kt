package com.lampa.skeleton.data.repository

import com.lampa.skeleton.data.mapper.NetworkMapper
import com.lampa.skeleton.data.model.domain.post.Post
import com.lampa.skeleton.data.model.network.post.PostNetworkEntity
import com.lampa.skeleton.data.network.PostApi
import com.lampa.skeleton.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class PostRepository @Inject constructor(
    private val postApi: PostApi,
    private val networkMapper: NetworkMapper
) {
    suspend fun getPostList(): Flow<DataState<List<Post>>> = flow {
        emit(DataState.Loading(true))
        try {
            val postList = postApi.getPostList()
            emit(DataState.Loading(false))
            if (postList.isSuccessful) {
                postList.body()?.let { list ->
                    emit(DataState.Success(networkMapper.mapFromEntityList(list)))
                }
            } else {
                emit(DataState.Error(Exception()))
            }
        } catch (e: Exception) {
            emit(DataState.Loading(false))
            emit(DataState.Error(e))
        }
    }
}