package com.saadi.stickfigure.utils

import android.app.Activity
import android.app.Dialog
import android.app.DownloadManager
import android.app.NotificationManager
import android.app.Service
import android.content.ClipData
import android.content.ClipboardManager
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.Insets
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.util.DisplayMetrics
import android.util.Log
import android.util.Size
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowInsets
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.snackbar.Snackbar
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.saadi.stickfigure.R
import com.saadi.stickfigure.databinding.ProgressDialogBinding
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.regex.Pattern


fun View.showKeyboard() {
    (this.context.getSystemService(Service.INPUT_METHOD_SERVICE) as? InputMethodManager)
        ?.showSoftInput(this, 0)
}

fun View.hideKeyboard() {
    (this.context.getSystemService(Service.INPUT_METHOD_SERVICE) as? InputMethodManager)
        ?.hideSoftInputFromWindow(this.windowToken, 0)
}

fun View.gone() = run { visibility = View.GONE }

fun View.visible() = run { visibility = View.VISIBLE }

fun View.invisible() = run { visibility = View.INVISIBLE }

infix fun View.visibleIf(condition: Boolean) =
    run { visibility = if (condition) View.VISIBLE else View.GONE }

infix fun View.goneIf(condition: Boolean) =
    run { visibility = if (condition) View.GONE else View.VISIBLE }

infix fun View.invisibleIf(condition: Boolean) =
    run { visibility = if (condition) View.INVISIBLE else View.VISIBLE }


/**
 * Transforms static java function SnackBar.make() to an extension function on View.
 */
fun View.snackBar(message: String, duration: Int = Snackbar.LENGTH_LONG) {
    Snackbar.make(this, message, duration).show()
}

fun View.snackBar(@StringRes message: Int, duration: Int = Snackbar.LENGTH_LONG) {
    Snackbar.make(this, message, duration).show()
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
                    snackBar(it, timeLength)
                }

                is Int -> {
                    hideKeyboard()
                    snackBar(this.context.getString(it), timeLength)
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

fun Fragment.toast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
}

fun Fragment.toast(@StringRes message: Int) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
}

fun Activity.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Activity.toast(@StringRes message: Int) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
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

fun ImageView.loadImage(@DrawableRes resId: Int) {
    Glide.with(this)
        .load(resId)
        .transition(DrawableTransitionOptions.withCrossFade())
        .placeholder(R.drawable.img_placeholder)
        .error(R.drawable.img_placeholder)
        .into(this)
}

fun ImageView.loadImage(path: String) {
    val requestOptions = RequestOptions()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .priority(Priority.HIGH)
        .placeholder(R.drawable.img_placeholder)
        .error(R.drawable.img_placeholder)

    val crossFadeFactory = DrawableCrossFadeFactory.Builder()
        .setCrossFadeEnabled(true)
        .build()

    val thumbnailRequest = Glide.with(this)
        .load(path)
        .override(250) // Set the desired thumbnail size here

    Glide.with(this)
        .load(path)
        .apply(requestOptions)
        .transition(DrawableTransitionOptions.withCrossFade(crossFadeFactory))
        .thumbnail(thumbnailRequest)
        .into(this)
}

fun ImageView.loadImageProfile(fileName: String) =
    Glide.with(this).load(Constants.BASE_URL + fileName)
        .transition(DrawableTransitionOptions.withCrossFade())
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

fun Activity.imagePicker(startImageResult: ActivityResultLauncher<Intent>) {
    ImagePicker.with(this)
        .crop()
        .maxResultSize(1080, 1080)
        .createIntent { intent ->
            startImageResult.launch(intent)
        }
}

fun String.isValidEmailAddress(): Boolean {
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

fun String.isValidName(): Boolean {
    val regex = Regex("^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*\$")
    return regex.matches(this)
}

fun String.isValidNumber(): Boolean {
    val phoneNumberUtil = PhoneNumberUtil.getInstance()
    return try {
        val phoneNumber = phoneNumberUtil.parse(this, null)
        phoneNumberUtil.isValidNumber(phoneNumber)
    } catch (e: Exception) {
        false
    }
}

val String.isDigitOnly: Boolean
    get() = matches(Regex("^\\d*\$"))

val String.isAlphabeticOnly: Boolean
    get() = matches(Regex("^[a-zA-Z]*\$"))

val String.isAlphanumericOnly: Boolean
    get() = matches(Regex("^[a-zA-Z\\d]*\$"))

val Any?.isNull get() = this == null

fun Any?.ifNull(block: () -> Unit) = run {
    if (this == null) {
        block()
    }
}

fun Context.isNetworkAvailable(): Boolean {
    val manager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val capabilities = manager.getNetworkCapabilities(manager.activeNetwork)
    return if (capabilities != null) {
        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
    } else false
}

fun Context.isPermissionGranted(permission: String) = run {
    ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
}


fun Uri.toMultipartData(contentResolver: ContentResolver, name: String): MultipartBody.Part {
    val file = File(this.path)
    val requestFile = file
        .asRequestBody(contentResolver.getType(this)?.toMediaTypeOrNull())
    return MultipartBody.Part.createFormData(name, file.name, requestFile)
}

fun String.toDate(format: String = "yyyy-MM-dd HH:mm:ss"): Date? {
    val dateFormatter = SimpleDateFormat(format, Locale.getDefault())
    return dateFormatter.parse(this)
}

fun Date.toStringFormat(format: String = "yyyy-MM-dd HH:mm:ss"): String {
    val dateFormatter = SimpleDateFormat(format, Locale.getDefault())
    return dateFormatter.format(this)
}

fun String.copyToClipboard(context: Context) {
    val clipboardManager = context.clipboardManager
    val clip = ClipData.newPlainText("clipboard", this)
    clipboardManager?.setPrimaryClip(clip)
}

fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

fun Any?.printToLog(tag: String = "DEBUG_LOG") {
    Log.e(tag, toString())
}

/*etName.text = "First name".toEditable()*/
val EditText.value
    get() = text?.toString() ?: ""


/*startActivity(MainActivity::class.java) // Without Intent modification
startActivity(MainActivity::class.java) {
    // You can access the intent object in this block
    putExtra("key", "value")
}*/
fun Activity.startActivity(
    cls: Class<*>,
    finishCallingActivity: Boolean = true,
    block: (Intent.() -> Unit)? = null
) {
    val intent = Intent(this, cls)
    block?.invoke(intent)
    startActivity(intent)
    if (finishCallingActivity) finish()
}

/*val size = screenSize
val deviceHeight = size.height
val deviceWidth = size.width*/
val Context.screenSize: Size
    get() {
        val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager

        val size = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val metrics = windowManager.currentWindowMetrics
            val windowInsets = metrics.windowInsets
            val insets: Insets = windowInsets.getInsetsIgnoringVisibility(
                WindowInsets.Type.navigationBars()
                        or WindowInsets.Type.displayCutout()
            )

            val insetsWidth: Int = insets.right + insets.left
            val insetsHeight: Int = insets.top + insets.bottom
            val bounds: Rect = metrics.bounds
            Size(
                bounds.width() - insetsWidth,
                bounds.height() - insetsHeight
            )
        } else {
            val displayMetrics = DisplayMetrics()
            windowManager.defaultDisplay?.getMetrics(displayMetrics)
            val height = displayMetrics.heightPixels
            val width = displayMetrics.widthPixels
            Size(width, height)
        }
        return size
    }


/*val manager = downloadManager // In Activity
val manager = requireContext().downloadManager// In Fragment*/
val Context.windowManager
    get() = ContextCompat.getSystemService(this, WindowManager::class.java)

val Context.connectivityManager
    get() = ContextCompat.getSystemService(this, ConnectivityManager::class.java)

val Context.notificationManager
    get() = ContextCompat.getSystemService(this, NotificationManager::class.java)

val Context.downloadManager
    get() = ContextCompat.getSystemService(this, DownloadManager::class.java)

val Context.clipboardManager
    get() = ContextCompat.getSystemService(
        this,
        ClipboardManager::class.java
    )