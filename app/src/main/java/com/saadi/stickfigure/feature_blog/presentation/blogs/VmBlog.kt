package com.saadi.stickfigure.feature_blog.presentation.blogs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saadi.stickfigure.feature_blog.domain.model.ModelBlog
import com.saadi.stickfigure.feature_blog.domain.usecase.BlogUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VmBlog @Inject constructor(
    private val useCases: BlogUseCases
) : ViewModel() {

    private val mBlogListLiveData: MutableLiveData<List<ModelBlog>> by lazy { MutableLiveData() }
    val blogLiveData: LiveData<List<ModelBlog>> get() = mBlogListLiveData

    fun getBlogList() {
        viewModelScope.launch {
            mBlogListLiveData.postValue(useCases.blogs())
        }
    }

}