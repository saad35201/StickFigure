package com.saadi.stickfigure.feature_blog.domain.usecase

import com.saadi.stickfigure.feature_blog.domain.model.ModelBlog
import com.saadi.stickfigure.feature_blog.domain.repository.BlogRepository

class GetBlogs(
    private val repository: BlogRepository
) {
    suspend operator fun invoke(): List<ModelBlog> {
        return repository.getBlogs()
    }
}