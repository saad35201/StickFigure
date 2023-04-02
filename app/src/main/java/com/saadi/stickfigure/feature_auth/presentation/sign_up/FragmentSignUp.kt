package com.saadi.stickfigure.feature_auth.presentation.sign_up

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.github.dhaval2404.imagepicker.ImagePicker
import com.saadi.stickfigure.databinding.FragmentSignUpBinding
import com.saadi.stickfigure.feature_auth.domain.model.sign_in.SignInResponse
import com.saadi.stickfigure.feature_auth.domain.model.sign_up.SignUpRequest
import com.saadi.stickfigure.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentSignUp : Fragment() {

    private var profileImageUri: Uri? = null
    private val mSignUpVm by viewModels<VmSignUp>()
    private lateinit var mProgressDialog: Dialog
    private lateinit var mBinding: FragmentSignUpBinding

    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            when (resultCode) {
                Activity.RESULT_OK -> {
                    val fileUri = data?.data!!
                    //setting uri to imageview
                    mBinding.ivProfile.setImageURI(fileUri)
                    profileImageUri = fileUri
                }
                ImagePicker.RESULT_ERROR -> {
                    view?.showSnackBar(ImagePicker.getError(data = data),2000)
                }
                else -> {
                    view?.showSnackBar("Task Cancelled",2000)
                }
            }
        }



    override fun onAttach(context: Context) {
        super.onAttach(context)
        mProgressDialog = context.progressDialog()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentSignUpBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //attaching phone edittext with ccp
        mBinding.ccp.registerCarrierNumberEditText(mBinding.etPhone)
        mBinding.ccp.isValidFullNumber

        //image picker click listener
        mBinding.ivProfile.setOnClickListener {
            activity?.imagePicker(startForProfileImageResult)
        }

        //btn signUp click listener
        mBinding.btnSignUp.setOnClickListener {
            mSignUpVm.signUp(
                SignUpRequest(
                    profilePic = context?.let { it1 -> profileImageUri?.toMultipartFile(it1.contentResolver,"profile") },
                    username = mBinding.etUsername.text.toString(),
                    firstName = mBinding.etFirstName.text.toString(),
                    lastName = mBinding.etLastName.text.toString(),
                    email = mBinding.etEmail.text.toString(),
                    phoneNumber = mBinding.ccp.fullNumberWithPlus,
                    password = mBinding.etPassword.text.toString(),
                    passwordConfirmation = mBinding.etConfirmPassword.text.toString()
                )
            )
        }

        mBinding.tvAlreadyHaveAccount.setOnClickListener {
            findNavController().navigateUp()
        }

        //observing sign in LiveData
        observe(mSignUpVm.signUpLiveData, ::handleSignUp)

    }

    private fun handleSignUp(networkResult: NetworkResult<SignInResponse>) {
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

}