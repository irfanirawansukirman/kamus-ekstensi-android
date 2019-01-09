package id.ac.unpad.profolio.util.ext

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.PopupMenu
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import timber.log.Timber
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.util.*


/**
 * Using it for replacement fragment
 *
 * @param fragment => your fragment class
 * @param frameId => your container layout id
 */
fun AppCompatActivity.replaceFragmentInActivity(
    fragment: Fragment,
    frameId: Int
) {
    supportFragmentManager.transact {
        replace(frameId, fragment)
    }

    //=========== How to using it ===========
    // override fun onYourMethod() {
    //      replaceFragmentInActivity(Fragment.newInstance(), R.id.frame_container)
    // }
    //=======================================
}

/**
 * Using it for replacement fragment and add it into backstack
 *
 * @param fragment => your fragment class
 * @param frameId => your container layout id
 * @param TAG => your TAG usually the name of activity class
 */
fun AppCompatActivity.replaceFragmentInActivityWithBackStack(
    fragment: Fragment,
    frameId: Int,
    TAG: String?
) {
    supportFragmentManager.transact {
        replace(frameId, fragment)
        addToBackStack(TAG)
    }

    //=========== How to using it ===========
    // override fun onYourMethod() {
    //      replaceFragmentInActivityWithBackStack(Fragment.newInstance(), R.id.frame_container, TAG)
    // }
    //=======================================
}

/**
 * Using it for showing toast in activity level
 *
 * @param activityContext => state app
 * @param message => text info
 */
fun AppCompatActivity.showToast(
    activityContext: Context,
    message: String
) {
    Toast.makeText(
        activityContext, if (TextUtils.isEmpty(message))
            Const.SERVER_ERROR_MESSAGE_DEFAULT else message, Toast.LENGTH_SHORT
    ).show()

    //=========== How to using it ===========
    //override fun onYourMethod(errorMessage: String) {
    //    showToast(this, errorMessage)
    //}
    //=======================================
}

private inline fun FragmentManager.transact(
    action: FragmentTransaction.() -> Unit
) {
    beginTransaction().apply {
        action()
    }.commit()
}

/**
 * Using it for making your status bar is transparent
 *
 * @param decorView => your global view in activity
 */
@SuppressLint("ObsoleteSdkInt")
fun AppCompatActivity.transparentStatusBar(
    decorView: View
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
    }

    //=========== How to using it ===========
    //override fun onCreate(savedInstanceState: Bundle?) {
    //    super.onCreate(savedInstanceState)
    //    setContentView(resLayout)
    //    transparentStatusBar(window.decorView)
    //}
    //=======================================
}

/**
 * Using it for move to another page without param
 */
inline fun <reified T : AppCompatActivity> AppCompatActivity.navigator(

) {
    val intent = Intent(this, T::class.java)
    startActivity(intent)

    //=========== How to using it ===========
    // navigator<YourActivity>()
    //=======================================
}

/**
 * Using it for move to another page with a single param
 * If you want sand data using object, model, etc convert it into string using Gson
 *
 * @param param => param with string type
 */
inline fun <reified T : AppCompatActivity> AppCompatActivity.navigator(
    param: String
) {
    val intent = Intent(this, T::class.java)
    intent.putExtra("param", param)
    startActivity(intent)

    //=========== How to using it ===========
    // navigator<YourActivity>(yourParamObj)
    //=======================================
}

/**
 * Using it for moving to another page with activity package name (usually modular package)
 *
 * @param activityPackage => exp : id.co.gits.feature_home_detail.HomeDetailActivity
 * @param activityContext => your state app
 */
fun AppCompatActivity.navigatorImplicit(
    activityContext: Context,
    activityPackage: String
) {
    val intent = Intent()
    try {
        intent.setClass(
            activityContext,
            Class.forName(activityPackage)
        )
        startActivity(intent)
    } catch (e: Exception) {
        e.printStackTrace()
    }

    //=========== How to using it ===========
    // navigatorImplicit(context, yourActivityPackageName)
    //=======================================
}

/**
 * Using it for loggin debug data using Timber
 *
 * @param TAG => your TAG usually the name of activity class
 * @param message => text info
 */
fun AppCompatActivity.logD(
    TAG: AppCompatActivity,
    message: String
) {
    Timber.d("${TAG::class.java.simpleName} $message")

    //=========== How to using it ===========
    // logD(YourActivity(), yourMessage)
    //=======================================
}

/**
 * Using it for logging verbose data using Timber
 *
 * @param TAG => your TAG usually the name of activity class
 * @param message => text info
 */
fun AppCompatActivity.logV(
    TAG: AppCompatActivity,
    message: String
) {
    Timber.v("${TAG::class.java.simpleName} $message")

    //=========== How to using it ===========
    // logV(YourActivity(), yourMessage)
    //=======================================
}

/**
 * Using it for logging error data using Timber
 *
 * @param TAG => your TAG usually the name of activity class
 * @param message => text info
 */
fun AppCompatActivity.logE(
    TAG: AppCompatActivity,
    message: String
) {
    Timber.e("${TAG::class.java.simpleName} $message")

    //=========== How to using it ===========
    // logE(YourActivity(), yourMessage)
    //=======================================
}

/**
 * Using it for saving bitmap from imageview to image file
 *
 * @param context => state app
 * @param imageBitmap => bitmap from your imageview
 * @param directoryName => for naming your directory for saving image view
 * @param showMessageStatus => message status if you failed or success creating image file
 */
@SuppressLint("ObsoleteSdkInt")
fun AppCompatActivity.saveBitmapToLocalFile(
    context: Context,
    imageBitmap: Bitmap,
    directoryName: String?,
    showMessageStatus: Boolean
) {
    val root = Environment.getExternalStorageDirectory().toString()

    val directoryNameDefault = if (TextUtils.isEmpty(directoryName)) {
        Const.APP_FOLDER_DEFAULT
    } else {
        directoryName
    }

    val myDir = File("$root/$directoryNameDefault")

    if (!myDir.exists()) {
        myDir.mkdirs()
    }

    val random = Random()
    val numbers = 100
    val numberResult = random.nextInt(numbers)
    val imageFileName = "IMG_$numberResult.png"
    val existImageFile = File(myDir, imageFileName)
    val outStream: FileOutputStream
    val bitmap = imageBitmap
    var isSuccessFileSaving: Boolean

    try {
        outStream = FileOutputStream(existImageFile)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream)
        /* 100 to keep full quality of the image */
        outStream.flush()
        outStream.close()
        isSuccessFileSaving = true
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
        isSuccessFileSaving = false
    } catch (e: IOException) {
        e.printStackTrace()
        isSuccessFileSaving = false
    }

    val message = if (isSuccessFileSaving) {
        Const.MESSAGE_SUCCESS_IMAGE_SAVE
    } else {
        Const.MESSAGE_FAILED_IMAGE_SAVE
    }

    if (showMessageStatus) showToast(context, message)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        val scanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
        val contentUri = Uri.fromFile(existImageFile)
        scanIntent.data = contentUri
        context.sendBroadcast(scanIntent)
    } else {
        context.sendBroadcast(
            Intent(
                Intent.ACTION_MEDIA_MOUNTED,
                Uri.parse(
                    Const.SDCARD_URI_PATH + Environment
                        .getExternalStorageDirectory()
                )
            )
        )
    }

    //=========== How to using it ===========
    // saveBitmapToLocalFile(activityContenxt, yourBitmap, dirName, yourBooleanStatus)
    //=======================================
}

/**
 * Using it for show icon inside popup menu
 *
 * @param popupMenu => your popup menu
 */
fun AppCompatActivity.setForceShowIcon(popupMenu: PopupMenu) {
    try {
        val fields = popupMenu.javaClass.declaredFields
        for (field in fields) {
            if ("mPopup" == field.name) {
                field.isAccessible = true
                val menuPopupHelper = field.get(popupMenu)
                val classPopupHelper = Class.forName(menuPopupHelper.javaClass.name)
                val setForceIcons = classPopupHelper.getMethod(
                    "setForceShowIcon", Boolean::class.javaPrimitiveType
                )
                setForceIcons.invoke(menuPopupHelper, true)
                break
            }
        }
    } catch (e: Throwable) {
        e.printStackTrace()
    }
    
    //=========== How to using it ===========
    // popUp.apply {
    //    dismiss()
    //    show()
    //    setForceShowIcon(this)
    // }
    //=======================================
}