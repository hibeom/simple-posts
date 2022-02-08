package com.pinkcloud.simpleposts.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pinkcloud.domain.model.Post
import com.pinkcloud.domain.usecase.GetPostUseCase
import com.pinkcloud.domain.usecase.UpdatePostUseCase
import com.pinkcloud.domain.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(
    private val getPostUseCase: GetPostUseCase,
    private val updatePostUseCase: UpdatePostUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val postId = savedStateHandle.get<Int>("postId")
    private val _post = MutableStateFlow<Post?>(null)
    val post: StateFlow<Post?>
        get() = _post
    private val _isDone = MutableStateFlow(false)
    val isDone: StateFlow<Boolean>
        get() = _isDone
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?>
        get() = _errorMessage

    init {
        postId?.let {
            viewModelScope.launch {
                _post.value = getPostUseCase(postId).let { result ->
                    if (result is Result.Success) result.data
                    else null
                }
            }
        }
    }

    fun updatePost(title: String, body: String) {
        viewModelScope.launch {
            val newPost = Post(
                id = post.value!!.id,
                userId = post.value!!.userId,
                title = title,
                body = body
            )
            updatePostUseCase(newPost).also { result ->
                if (result is Result.Success) {
                    _isDone.value = true
                } else {
                    _errorMessage.value = result.message
                }
            }
        }
    }
}