package com.lampa.skeleton.view.fragment.post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.lampa.skeleton.R
import com.lampa.skeleton.databinding.FragmentPostBinding
import com.lampa.skeleton.util.UiState
import com.lampa.skeleton.view.adapter.PostAdapter
import com.lampa.skeleton.view.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
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
        setupState()
        setupViewModelCallbacks()
    }

    private fun setupState() {
        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect { state ->
                when (state) {
                    is UiState.Loading -> /** binding.progressPlaceholder.isVisible = **/ state.inProgress
                    is UiState.Error -> /** showToast() **/ state.exception.localizedMessage
                }
            }
        }
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
            postList.observe(viewLifecycleOwner, {
                postAdapter.submitList(it)
            })
        }
    }
}