package com.lampa.skeleton.data.network.model.post

data class PostNetworkEntity(
    var id: Int,
    var body: String,
    var title: String,
    var userId: Int
)