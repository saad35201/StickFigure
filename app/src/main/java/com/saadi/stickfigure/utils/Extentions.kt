package com.saadi.stickfigure.utils

import android.app.Activity
import android.app.Dialog
import android.app.Service
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.text.Editable
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.DrawableRes
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.snackbar.Snackbar
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.saadi.stickfigure.R
import com.saadi.stickfigure.databinding.ProgressDialogBinding
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONException
import org.json.JSONObject
import java.io.File
import java.io.IOException
import java.util.regex.Pattern


fun View.showKeyboard() {
    (this.context.getSystemService(Service.INPUT_METHOD_SERVICE) as? InputMethodManager)
        ?.showSoftInput(this, 0)
}

fun View.hideKeyboard() {
    (this.context.getSystemService(Service.INPUT_METHOD_SERVICE) as? InputMethodManager)
        ?.hideSoftInputFromWindow(this.windowToken, 0)
}

fun View.toVisible() {
    this.visibility = View.VISIBLE
}

fun View.toGone() {
    this.visibility = View.GONE
}

fun View.toInvisible() {
    this.visibility = View.GONE
}


/**
 * Transforms static java function SnackBar.make() to an extension function on View.
 */
fun View.showSnackBar(message: String, timeLength: Int) {
    Snackbar.make(this, message, timeLength).show()
}

/**
 * Triggers a snackBar message when the value contained by snackBarTaskMessageLiveEvent is modified.
 */
fun View.setupSnackBar(
    lifecycleOwner: LifecycleOwner,
    snackBarEvent: LiveData<SingleEvent<Any>>,
    timeLength: Int
) {
    snackBarEvent.observe(lifecycleOwner, Observer { event ->
        event.getContentIfNotHandled()?.let {
            when (it) {
                is String -> {
                    hideKeyboard()
                    showSnackBar(it, timeLength)
                }
                is Int -> {
                    hideKeyboard()
                    showSnackBar(this.context.getString(it), timeLength)
                }
                else -> {
                }
            }

        }
    })
}

fun View.showToast(
    lifecycleOwner: LifecycleOwner,
    ToastEvent: LiveData<SingleEvent<Any>>,
    timeLength: Int
) {
    ToastEvent.observe(lifecycleOwner, Observer { event ->
        event.getContentIfNotHandled()?.let {
            when (it) {
                is String -> Toast.makeText(this.context, it, timeLength).show()
                is Int -> Toast.makeText(this.context, this.context.getString(it), timeLength)
                    .show()
                else -> {
                }
            }
        }
    })
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}

fun ImageView.loadImage(@DrawableRes resId: Int) =
    Glide.with(this).load(resId).transition(DrawableTransitionOptions.withCrossFade())
        .placeholder(R.drawable.ic_avatar).error(R.drawable.ic_avatar).into(this)

fun ImageView.loadImage(path: String) =
    Glide.with(this).load(path).transition(DrawableTransitionOptions.withCrossFade())
        .placeholder(R.drawable.ic_avatar).error(R.drawable.ic_avatar).into(this)

fun ImageView.loadImageProfile(fileName: String) =
    Glide.with(this).load(Constants.BASE_URL + fileName).transition(DrawableTransitionOptions.withCrossFade())
        .placeholder(R.drawable.ic_avatar).error(R.drawable.ic_avatar).into(this)


fun Context.progressDialog(): Dialog {
    val binding = ProgressDialogBinding.inflate(LayoutInflater.from(this))
    val dialog = Dialog(this)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    dialog.setCancelable(false)
    dialog.setContentView(binding.root)
    return dialog
}

fun Activity.imagePicker(startImageResult: ActivityResultLauncher<Intent>){
    ImagePicker.with(this)
        .crop()
        .maxResultSize(1080, 1080)
        .createIntent { intent ->
            startImageResult.launch(intent)
        }
}

fun String.isValidEmailAddress(): Boolean{
    val emailPattern = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )

    return emailPattern.matcher(this).matches()
}

fun String.isValidName(): Boolean{
    val regex = Regex("^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*\$")
    return regex.matches(this)
}

fun String.isValidNumber(): Boolean{
    val phoneNumberUtil = PhoneNumberUtil.getInstance()
    return try {
        val phoneNumber = phoneNumberUtil.parse(this, null)
        phoneNumberUtil.isValidNumber(phoneNumber)
    } catch (e: Exception) {
        false
    }
}

fun Uri.toMultipartData(contentResolver: ContentResolver,name: String): MultipartBody.Part {
    val file = File(this.path)
    val requestFile = RequestBody.create(
        contentResolver.getType(this)?.toMediaTypeOrNull(),
        file
    )
    return MultipartBody.Part.createFormData(name, file.name, requestFile)
}
