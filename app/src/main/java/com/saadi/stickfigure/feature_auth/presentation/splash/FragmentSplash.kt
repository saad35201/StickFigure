package com.saadi.stickfigure.feature_auth.presentation.splash

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.saadi.stickfigure.Home
import com.saadi.stickfigure.R
import com.saadi.stickfigure.utils.Constants
import com.saadi.stickfigure.utils.observe
import com.saadi.stickfigure.utils.showSnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentSplash : Fragment() {

    private lateinit var mHandler: Handler
    private val mSplashVm by viewModels<VmSplash>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //full screen
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            requireActivity().window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            requireActivity().window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        //getting session
        mSplashVm.getSession()

        //observing sign in LiveData
        viewLifecycleOwner.observe(mSplashVm.sessionLiveData) {
            if (it){
                requireView().showSnackBar("Remembered and logged in",3000)
                //Splash timer
                mHandler = Handler(Looper.getMainLooper())
                mHandler.postDelayed({
                    startActivity(Intent(activity,Home::class.java))
                    activity?.finish()
                }, Constants.SPLASH_DELAY.toLong())
            }else{
                //Splash timer
                mHandler = Handler(Looper.getMainLooper())
                mHandler.postDelayed({
                    findNavController().navigate(R.id.action_fragmentSplash_to_fragmentSignIn)
                }, Constants.SPLASH_DELAY.toLong())
            }
        }
    }


    override fun onDetach() {
        super.onDetach()

        //removing full screen
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            requireActivity().window.insetsController?.show(WindowInsets.Type.statusBars())
        } else {
            requireActivity().window.clearFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

    }

}