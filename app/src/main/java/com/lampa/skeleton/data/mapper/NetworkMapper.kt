package com.lampa.skeleton.data.mapper

import com.lampa.skeleton.data.model.domain.post.Post
import com.lampa.skeleton.data.model.network.post.PostNetworkEntity
import javax.inject.Inject

class NetworkMapper
@Inject
constructor(): EntityMapper<PostNetworkEntity, Post>{
    override fun mapFromEntity(entity: PostNetworkEntity): Post {
        return Post(
            id = entity.id,
            body = entity.body,
            title = entity.title,
            userId = entity.userId,
        )
    }
    override fun mapToEntity(domainModel: Post): PostNetworkEntity {
        return PostNetworkEntity(
            id = domainModel.id,
            body = domainModel.body,
            title = domainModel.title,
            userId = domainModel.userId,
        )
    }
}