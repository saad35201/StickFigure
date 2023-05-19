package com.saadi.stickfigure.feature_profile.presentation.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.saadi.stickfigure.R
import com.saadi.stickfigure.databinding.FragmentEditProfileBinding
import com.saadi.stickfigure.databinding.FragmentProfileBinding


class FragmentProfile : Fragment() {

    private lateinit var mBinding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentProfileBinding.inflate(inflater,container,false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        mBinding.tv.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentProfile_to_fragmentEditProfile)
        }

    }

}