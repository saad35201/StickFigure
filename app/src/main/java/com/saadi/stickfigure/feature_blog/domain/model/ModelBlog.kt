package com.saadi.stickfigure.feature_blog.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ModelBlog(
    val banner: String? = null,
    val date: String? = null,
    val title: String? = null,
    val description: String? = null,
) : Parcelable
