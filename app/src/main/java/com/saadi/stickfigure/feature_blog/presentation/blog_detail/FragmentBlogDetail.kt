package com.saadi.stickfigure.feature_blog.presentation.blog_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.saadi.stickfigure.databinding.FragmentBlogDetailBinding
import com.saadi.stickfigure.utils.loadImage

class FragmentBlogDetail : Fragment() {

    //binding
    private lateinit var mBinding: FragmentBlogDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentBlogDetailBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //getting argument object
        val args: FragmentBlogDetailArgs by navArgs()
        val blog = args.blogObj

        blog?.let { // Safe call operator to check for null
            // Setting data
            with(mBinding) {
                ivBlog.loadImage(blog.banner!!)
                tvDate.text = blog.date
                tvTitle.text = blog.title
                tvDescription.text = blog.description
            }
        } ?: run {
            // Handle the case when the blog object is null
        }

    }

}