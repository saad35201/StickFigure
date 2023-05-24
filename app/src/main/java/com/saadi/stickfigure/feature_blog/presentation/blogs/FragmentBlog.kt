package com.saadi.stickfigure.feature_blog.presentation.blogs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.saadi.stickfigure.R
import com.saadi.stickfigure.databinding.FragmentBlogBinding

class FragmentBlog : Fragment() {

    //binding
    private lateinit var mBinding: FragmentBlogBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentBlogBinding.inflate(inflater,container,false)
        return mBinding.root
    }

}