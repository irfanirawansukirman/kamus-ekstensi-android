/* Extentions context level */

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import timber.log.Timber
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.util.*
import android.annotation.SuppressLint
import android.os.Build
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.PopupMenu
import android.view.View

/**
 * Using it for replacement fragment
 *
 * @param fragment => your fragment class
 * @param frameId => your container layout id
 */
fun Context.replaceFragmentInActivity(
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
fun Context.replaceFragmentInActivityWithBackStack(
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
fun Context.transparentStatusBar(
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
 * Using it for show icon inside popup menu
 *
 * @param popupMenu => your popup menu
 */
fun Context.setForceShowIcon(popupMenu: PopupMenu) {
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

/**
 * Using it for move to another page with clearing existing activity stack
 */
inline fun <reified T : AppCompatActivity> Context.navigatorWithActivityClearTop() {
    val intent = Intent(this, T::class.java)

    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

    startActivity(intent)

    //=========== How to using it ===========
    // navigatorWithActivityClearTop<YourActivity>()
    //=======================================
}

/**
 * Using it for move to another page without param
 */
inline fun <reified T : AppCompatActivity> Context.navigator(

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
inline fun <reified T : AppCompatActivity> Context.navigator(
    intentParams : Intent.() -> Unit
) {
    val intent = Intent(this, T::class.java)
    intent.intentParams()
    startActivity(intent)

    //=========== How to using it ===========
    // navigator<DetailActivity> {
    //        putExtra("KEY1" , "VALUE1")
    //        putExtra("KEY2" , "VALUE2")
    //    }
    //=======================================
}

/**
 * Using it for moving to another page with activity package name (usually modular package) without params
 *
 * @param activityPackage => exp : id.co.gits.feature_home_detail.HomeDetailActivity
 */
fun Context.navigatorImplicit(
    activityPackage: String
) {
    val intent = Intent()
    try {
        intent.setClass(
            this,
            Class.forName(activityPackage)
        )
        startActivity(intent)
    } catch (e: Exception) {
        e.printStackTrace()
    }

    //=========== How to using it ===========
    // navigatorImplicit(yourActivityPackageName)
    //=======================================
}

/**
 * Using it for moving to another page with activity package name (usually modular package) with params
 *
 * @param activityPackage => exp : id.co.gits.feature_home_detail.HomeDetailActivity
 */
fun Context.navigatorImplicit(
    activityPackage: String,
    intentParams : Intent.() -> Unit
) {
    val intent = Intent()
    try {
        intent.intentParams()
        intent.setClass(
            this,
            Class.forName(activityPackage)
        )
        startActivity(intent)
    } catch (e: Exception) {
        e.printStackTrace()
    }

    //=========== How to using it ===========
    // navigatorImplicit(yourActivityPackageName) {
    //        putExtra("KEY1" , "VALUE1")
    //        putExtra("KEY2" , "VALUE2")
    //    }
    //=======================================
}

fun Context.showToast(
    message: String
) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

/**
 * Using it for loggin debug data using Timber
 *
 * @param TAG => your TAG usually the name of activity class
 * @param message => text info
 */
fun Context.logD(
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
fun Context.logV(
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
fun Context.logE(
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
fun Context.saveBitmapToLocalFile(
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