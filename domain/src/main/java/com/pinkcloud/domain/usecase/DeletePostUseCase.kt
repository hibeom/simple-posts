package com.pinkcloud.domain.usecase

import com.pinkcloud.domain.repository.PostRepository
import com.pinkcloud.domain.model.Post
import javax.inject.Inject

class DeletePostUseCase @Inject constructor(
    private val postRepository: PostRepository
) {
    suspend operator fun invoke(post: Post) = postRepository.deletePost(post)
}