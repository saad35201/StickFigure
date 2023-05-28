package com.saadi.stickfigure.feature_blog.presentation.blog_detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.saadi.stickfigure.R

class FragmentBlogDetail : Fragment() {

    //binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blog_detail, container, false)
    }

}