package com.pinkcloud.domain.usecase

import com.pinkcloud.domain.repository.PostRepository
import javax.inject.Inject

class GetPostPagingStreamUseCase @Inject constructor(
    private val postRepository: PostRepository
) {
    operator fun invoke() = postRepository.getPostPagingFlow()
}