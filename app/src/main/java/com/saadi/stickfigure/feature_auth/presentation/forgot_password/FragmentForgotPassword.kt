package com.saadi.stickfigure.feature_auth.presentation.forgot_password

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.saadi.stickfigure.R
import com.saadi.stickfigure.databinding.FragmentForgotPasswordBinding


class FragmentForgotPassword : Fragment() {

    private lateinit var mBinding: FragmentForgotPasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentForgotPasswordBinding.inflate(inflater,container,false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.btnGetCode.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentForgotPassword_to_fragmentResetPassword)
        }

    }

}