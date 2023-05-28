package com.saadi.stickfigure.feature_blog.presentation.blogs

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.saadi.stickfigure.databinding.FragmentBlogBinding
import com.saadi.stickfigure.feature_blog.domain.model.ModelBlog
import com.saadi.stickfigure.utils.Constants.TAG
import com.saadi.stickfigure.utils.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentBlog : Fragment() {

    //binding
    private lateinit var mBinding: FragmentBlogBinding
    private val mBlogVm by viewModels<VmBlog>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentBlogBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //getting blog list
        mBlogVm.getBlogList()
        observe(mBlogVm.blogLiveData, ::handleBlogData)

    }

    private fun handleBlogData(blogs: List<ModelBlog>) {

        mBinding.rvBlog.apply {
            adapter = BlogAdapter(blogs)
        }

    }

}