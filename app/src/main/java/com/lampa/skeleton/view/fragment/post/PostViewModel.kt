package com.lampa.skeleton.view.fragment.post

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lampa.skeleton.data.domain.post.Post
import com.lampa.skeleton.repository.PostRepository
import com.lampa.skeleton.repository.TestRepository
import com.lampa.skeleton.util.DataState
import com.lampa.skeleton.util.UiState
import com.lampa.skeleton.view.base.BaseViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import okhttp3.Dispatcher
import timber.log.Timber
import kotlin.system.measureTimeMillis

class PostViewModel @ViewModelInject constructor(
    private val postRepository: PostRepository,
    private val testRepository: TestRepository
) : BaseViewModel() {

    //TODO: connection handler

    val postList = MutableLiveData<List<Post>>()
    private var repeatableJob: Job? = null
    private var repeatCounter = 0

    val requestText = MutableLiveData<String>()
    init {
//        singleRequest()
//        handleParallelRequest()
//        sequentialRequest()
    }

    private fun singleRequest() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = UiState.Loading(true)
            postRepository.getPostList().collect { state ->
                when (state) {
                    is DataState.Success -> {
                        repeatCounter += 1
                        requestText.postValue("Repeat count: $repeatCounter")
//                        postList.postValue(state.data)
                    }
                    is DataState.Error -> _uiState.value = UiState.Error(state.exception)
                }
            }
            _uiState.value = UiState.Loading(false)
        }
    }

    private fun handleParallelRequest() {
        viewModelScope.launch() {
            _uiState.value = UiState.Loading(true)
            val time = measureTimeMillis {
                val flowJob = async {
                    delay(4000)
                    var result: String? = null
                    testRepository.getTestFlow().collect { state ->
                        when (state) {
                            is DataState.Success -> result = state.data
                            is DataState.Error -> cancel()
                        }
                    }
                    result
                }
                //TODO: handle cancelable
                val throwableJob = async {
                    delay(2000)
                    cancel()
                    "Exception"
                }
                val simpleJob = async { testRepository.getTestResult() }
                var simpleData: String? = null
                when (val simple = simpleJob.await()) {
                    is DataState.Success -> simpleData = simple.data
                    is DataState.Error -> simple.exception
                }
                Timber.d("Flow = ${flowJob.await()}")
                Timber.d("Simple = $simpleData")

                Timber.d("Result = ${flowJob.await()}, $simpleData")
            }
            Timber.d("Total time: $time")
            _uiState.value = UiState.Loading(false)
        }
    }

    private fun sequentialRequest() {
        viewModelScope.launch() {
            _uiState.value = UiState.Loading(true)
            val time = measureTimeMillis {
                withContext(Dispatchers.IO) {
                    delay(3000)
                    testRepository.getTestFlow().collect { state ->
                        when (state) {
                            is DataState.Success -> state.data
                            is DataState.Error -> cancel()
                        }
                    }
                }
                Timber.d("flowJob1 finished")
                withContext(Dispatchers.IO) {
                    delay(3000)
                    testRepository.getTestFlow().collect { state ->
                        when (state) {
                            is DataState.Success -> state.data
                            is DataState.Error -> cancel()
                        }
                    }
                }
                Timber.d("flowJob2 finished")
            }
            Timber.d("Total time: $time")
        }
    }

    fun repeatableRequest(delay: Long) {
        repeatableJob = viewModelScope.launch (Dispatchers.IO) {
            repeat(10) {
                delay(delay)
                singleRequest()
            }
        }
    }

    fun stopRepeating() {
        repeatableJob?.cancel()
    }
}