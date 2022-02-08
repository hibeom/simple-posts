package com.pinkcloud.simpleposts.ui.main

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.pinkcloud.domain.usecase.GetPostPagingStreamUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getPostPagingStreamUseCase: GetPostPagingStreamUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val postPagingFlow = getPostPagingStreamUseCase().cachedIn(viewModelScope)
}