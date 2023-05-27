package com.saadi.stickfigure.feature_blog.domain.repository

import com.saadi.stickfigure.feature_blog.domain.model.ModelBlog

interface BlogRepository {

    suspend fun getBlogs(): List<ModelBlog>

}