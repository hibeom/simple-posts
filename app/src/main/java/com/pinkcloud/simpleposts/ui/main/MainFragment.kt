package com.pinkcloud.simpleposts.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.pinkcloud.data.model.Post
import com.pinkcloud.simpleposts.databinding.MainFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: MainFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater, container, false)

        val adapter = PostsAdapter(object : PostClickListener {
            override fun onClick(postId: Int) {
                findNavController().navigate(
                    MainFragmentDirections.actionMainFragmentToDetailFragment(postId)
                )
            }
        })

        binding.swipeRefreshLayout.setOnRefreshListener {
            adapter.refresh()
        }

        binding.bindPosts(
            adapter = adapter,
            pagingData = viewModel.postPagingFlow
        )
        return binding.root
    }

    private fun MainFragmentBinding.bindPosts(
        adapter: PostsAdapter,
        pagingData: Flow<PagingData<Post>>
    ) {
        recyclerView.adapter = adapter

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    pagingData.collectLatest {
                        adapter.submitData(it)
                    }
                }
                launch {
                    adapter.loadStateFlow.collectLatest { loadState ->
                        val isListEmpty = loadState.source.refresh is LoadState.NotLoading && loadState.mediator?.refresh is LoadState.NotLoading  && adapter.itemCount == 0
                        textEmpty.isVisible = isListEmpty
                        recyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading || loadState.mediator?.refresh is LoadState.NotLoading
                        textError.isVisible = loadState.mediator?.refresh is LoadState.Error && adapter.itemCount == 0
                        swipeRefreshLayout.isRefreshing = loadState.mediator?.refresh is LoadState.Loading
                    }
                }
            }
        }
    }
}