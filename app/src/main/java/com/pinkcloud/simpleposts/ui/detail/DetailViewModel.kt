package com.pinkcloud.simpleposts.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pinkcloud.domain.model.Comment
import com.pinkcloud.domain.model.Post
import com.pinkcloud.domain.usecase.DeletePostUseCase
import com.pinkcloud.domain.usecase.GetCommentsUseCase
import com.pinkcloud.domain.usecase.GetPostFlowUseCase
import com.pinkcloud.domain.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getPostFlowUseCase: GetPostFlowUseCase,
    private val getCommentsUseCase: GetCommentsUseCase,
    private val deletePostUseCase: DeletePostUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val postId = savedStateHandle.get<Int>("postId")

    val post = postId?.let {
        getPostFlowUseCase(it)
    }
    private val _comments = MutableStateFlow<Result<List<Comment>>>(Result.Loading())
    val comments: StateFlow<Result<List<Comment>>>
        get() = _comments
    private val _isDeleted = MutableStateFlow(false)
    val isDeleted: StateFlow<Boolean>
        get() = _isDeleted
    private val _isEditClicked = MutableStateFlow(false)
    val isEditClicked: StateFlow<Boolean>
        get() = _isEditClicked

    init {
        postId?.let {
            viewModelScope.launch {
                _comments.value = getCommentsUseCase(postId)
            }
        }
    }

    fun deletePost(post: Post) {
        viewModelScope.launch {
            deletePostUseCase(post).also { result ->
                if (result is Result.Success) _isDeleted.value = true
            }
        }
    }

    fun onClickEdit() {
        _isEditClicked.value = true
    }

    fun editShown() {
        _isEditClicked.value = false
    }
}