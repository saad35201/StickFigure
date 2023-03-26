package com.saadi.stickfigure.feature_auth.presentation.sign_in

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.saadi.stickfigure.R
import com.saadi.stickfigure.databinding.FragmentSignInBinding

class FragmentSignIn : Fragment() {

    private lateinit var mBinding: FragmentSignInBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentSignInBinding.inflate(inflater,container,false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //click listeners
        mBinding.tvForgotPassword.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentSignIn_to_fragmentForgotPassword)
        }

        mBinding.btnSignIn.setOnClickListener {

        }

        mBinding.tvSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentSignIn_to_fragmentSignUp)
        }

    }

}