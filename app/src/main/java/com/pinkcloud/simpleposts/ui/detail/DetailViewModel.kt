package com.pinkcloud.simpleposts.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pinkcloud.data.source.PostRepository
import com.pinkcloud.data.model.Comment
import com.pinkcloud.data.model.Post
import com.pinkcloud.data.remote.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: PostRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val postId = savedStateHandle.get<Int>("postId")

    val post = postId?.let {
        repository.getPostFlow(it)
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
                _comments.value = repository.getComments(postId)
            }
        }
    }

    fun deletePost(post: Post) {
        viewModelScope.launch {
            repository.deletePost(post).also { result ->
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