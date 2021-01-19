package com.lampa.skeleton.view.fragment.post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.lampa.skeleton.R
import com.lampa.skeleton.data.model.domain.post.Post
import com.lampa.skeleton.data.network.PostApi
import com.lampa.skeleton.databinding.FragmentPostBinding
import com.lampa.skeleton.util.DataState
import com.lampa.skeleton.view.adapter.PostAdapter
import com.lampa.skeleton.view.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PostFragment : BaseFragment() {

    private lateinit var binding: FragmentPostBinding
    private val viewModel: PostViewModel by viewModels()

    @Inject
    lateinit var postAdapter: PostAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_post, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUi()
        loadData()
        setupViewModelCallbacks()
    }

    private fun setupUi() {
        setupRecycler()
    }

    private fun setupRecycler() {
        val layoutManager = LinearLayoutManager(requireContext())
        binding.postRecycler.apply {
            this.layoutManager = layoutManager
            this.adapter = postAdapter
        }
    }

    private fun loadData() {

    }

    private fun setupViewModelCallbacks() {
        viewModel.apply {
            postState.observe(viewLifecycleOwner, { state ->
                when (state) {
                    is DataState.Loading -> {
                        binding.progressPlaceholder.root.isVisible = state.inProgress
                    }
                    is DataState.Success<List<Post>> -> {
                        postAdapter.submitList(state.data)
                    }
                    is DataState.Error -> {
                        displayError(state.exception)
                    }
                }
            })
        }
    }
}