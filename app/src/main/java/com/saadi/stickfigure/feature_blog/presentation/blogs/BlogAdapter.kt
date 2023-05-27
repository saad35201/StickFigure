package com.saadi.stickfigure.feature_blog.presentation.blogs

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.saadi.stickfigure.databinding.RvItemBlogBinding
import com.saadi.stickfigure.feature_blog.domain.model.ModelBlog
import com.saadi.stickfigure.utils.loadImage

class BlogAdapter(private val blogs: List<ModelBlog>) :
    RecyclerView.Adapter<BlogAdapter.BlogViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlogViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RvItemBlogBinding.inflate(inflater, parent, false)
        return BlogViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BlogViewHolder, position: Int) {
        val blog = blogs[position]
        holder.bind(blog)
    }

    override fun getItemCount(): Int {
        return blogs.size
    }

    inner class BlogViewHolder(private val binding: RvItemBlogBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(blog: ModelBlog) {
            binding.ivBlog.loadImage(blog.banner!!)
            binding.tvDate.text = blog.date
            binding.tvTitle.text = blog.title
            binding.tvDescription.text = blog.description
        }
    }
}