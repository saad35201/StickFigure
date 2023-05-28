package com.saadi.stickfigure.feature_auth.presentation.forgot_password

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.saadi.stickfigure.databinding.FragmentForgotPasswordBinding
import com.saadi.stickfigure.feature_auth.domain.model.sign_up.SignUpResponse
import com.saadi.stickfigure.utils.Constants
import com.saadi.stickfigure.utils.NetworkResult
import com.saadi.stickfigure.utils.observe
import com.saadi.stickfigure.utils.progressDialog
import com.saadi.stickfigure.utils.snackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentForgotPassword : Fragment() {

    private lateinit var mBinding: FragmentForgotPasswordBinding
    private val mForgotPasswordVm by viewModels<VmForgotPassword>()
    private lateinit var mProgressDialog: Dialog

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mProgressDialog = context.progressDialog()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentForgotPasswordBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //test
        mBinding.etEmail.setText("stickfiguretest@gmail.com")

        mBinding.btnGetCode.setOnClickListener {
            mForgotPasswordVm.forgotPassword(mBinding.etEmail.text.toString())
        }

        //observing sign in LiveData
        observe(mForgotPasswordVm.forgotPasswordLiveData, ::handleForgotPassword)

    }

    private fun handleForgotPassword(networkResult: NetworkResult<SignUpResponse>) {
        mProgressDialog.dismiss()
        when (networkResult) {
            is NetworkResult.Error -> {
                networkResult.message?.let {
                    mBinding.root.snackBar(message = it, 3000)
                }
            }

            is NetworkResult.Loading -> {
                mProgressDialog.show()
            }

            is NetworkResult.Success -> {
                networkResult.data?.let {
                    it.message?.let { it1 -> mBinding.root.snackBar(message = it1, 3000) }
                    Handler(Looper.getMainLooper()).postDelayed({
                        findNavController().navigateUp()
                    }, Constants.SPLASH_DELAY.toLong())
                }
            }
        }
    }

}