package com.saadi.stickfigure.feature_auth.presentation.sign_in

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.saadi.stickfigure.R
import com.saadi.stickfigure.databinding.FragmentSignInBinding
import com.saadi.stickfigure.feature_auth.domain.model.sign_in.SignInRequest
import com.saadi.stickfigure.feature_auth.domain.model.sign_in.SignInResponse
import com.saadi.stickfigure.utils.NetworkResult
import com.saadi.stickfigure.utils.observe
import com.saadi.stickfigure.utils.progressDialog
import com.saadi.stickfigure.utils.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FragmentSignIn @Inject constructor() : Fragment() {

    private val mSignInVm by viewModels<VmSignIn>()
    private lateinit var mBinding: FragmentSignInBinding
    private lateinit var mProgressDialog: Dialog

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mProgressDialog = context.progressDialog()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentSignInBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.tvForgotPassword.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentSignIn_to_fragmentForgotPassword)
        }

        mBinding.btnSignIn.setOnClickListener {
            val request = SignInRequest(
                mBinding.etPassword.text.toString(),
                mBinding.etEmailOrUsername.text.toString()
            )
            mSignInVm.signIn(request)
        }

        mBinding.tvSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentSignIn_to_fragmentSignUp)
        }

        //observing sign in LiveData
        observe(mSignInVm.signInLiveData, ::handleSignIn)

    }

    private fun handleSignIn(networkResult: NetworkResult<SignInResponse>) {
        mProgressDialog.dismiss()
        when (networkResult) {
            is NetworkResult.Error -> {
                networkResult.message?.let {
                    mBinding.root.showSnackBar(message = it, 3000)
                }
            }
            is NetworkResult.Loading -> {
                mProgressDialog.show()
            }
            is NetworkResult.Success -> {
                mBinding.root.showSnackBar(message = "saving data", 3000)
            }
        }

    }

    override fun onPause() {
        super.onPause()
        mSignInVm.clear()
    }

}