package com.saadi.stickfigure.feature_blog.data.repository

import com.saadi.stickfigure.core.base.BaseRepository
import com.saadi.stickfigure.feature_blog.domain.model.ModelBlog
import com.saadi.stickfigure.feature_blog.domain.repository.BlogRepository

class BlogRepositoryImp : BlogRepository, BaseRepository() {

    override suspend fun getBlogs(): List<ModelBlog> {

        //dummy list

        return listOf(
            ModelBlog(
                getRandomImageUrl(),
                "22-05-2023",
                "This is dummy blog title"
            ),
            ModelBlog(
                getRandomImageUrl(),
                "22-05-2023",
                "This is dummy blog title"
            ),
            ModelBlog(
                getRandomImageUrl(),
                "22-05-2023",
                "This is dummy blog title"
            ),
            ModelBlog(
                getRandomImageUrl(),
                "22-05-2023",
                "This is dummy blog title"
            ),
            ModelBlog(
                getRandomImageUrl(),
                "22-05-2023",
                "This is dummy blog title"
            ),
            ModelBlog(
                getRandomImageUrl(),
                "22-05-2023",
                "This is dummy blog title"
            ),
            ModelBlog(
                getRandomImageUrl(),
                "22-05-2023",
                "This is dummy blog title"
            ), ModelBlog(
                getRandomImageUrl(),
                "22-05-2023",
                "This is dummy blog title"
            ),
            ModelBlog(
                getRandomImageUrl(),
                "22-05-2023",
                "This is dummy blog title"
            ), ModelBlog(
                getRandomImageUrl(),
                "22-05-2023",
                "This is dummy blog title"
            ),
            ModelBlog(
                getRandomImageUrl(),
                "22-05-2023",
                "This is dummy blog title"
            )


        )
    }

    private fun getRandomImageUrl(): String {
        val width = 1080 // Specify the desired width of the image
        val height = 400 // Specify the desired height of the image
        val id = (1..1000).random() // Generate a random ID between 1 and 1000

        return "https://picsum.photos/$width/$height?random=$id"
    }

}