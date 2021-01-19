package com.lampa.skeleton.view.fragment.post

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lampa.skeleton.data.model.domain.post.Post
import com.lampa.skeleton.data.repository.PostRepository
import com.lampa.skeleton.util.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PostViewModel @ViewModelInject constructor(
    private val postRepository: PostRepository
) : ViewModel() {

    //TODO: connection handler

    val postState = MutableLiveData<DataState<List<Post>>>()

    init {
        getPostList()
    }

    private fun getPostList() {
        viewModelScope.launch(Dispatchers.IO) {
            postRepository.getPostList().collect { state ->
                postState.postValue(state)
            }
        }
    }
}