package com.pinkcloud.domain.usecase

import com.pinkcloud.domain.repository.PostRepository
import javax.inject.Inject

class GetCommentsUseCase @Inject constructor(
    private val postRepository: PostRepository
) {
    suspend operator fun invoke(postId: Int) = postRepository.getComments(postId)
}