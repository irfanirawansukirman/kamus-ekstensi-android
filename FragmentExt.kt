/* Extentions fragment level */

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.PopupMenu
import android.text.TextUtils
import android.widget.Toast
import id.co.gits.gitsdriver.utils.GitsHelper
import timber.log.Timber
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

inline fun <FRAGMENT : Fragment> FRAGMENT.putArgs(argsBuilder: Bundle.() -> Unit):
        FRAGMENT = this.apply { arguments = Bundle().apply(argsBuilder) }

inline fun <reified T : AppCompatActivity> Fragment.navigator(

) {
    val intent = Intent(context, T::class.java)
    startActivity(intent)
}

inline fun <reified T : AppCompatActivity> Fragment.navigatorWithActivityClearTop(

) {
    val intent = Intent(context, T::class.java)

    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

    startActivity(intent)
}

inline fun <reified T : AppCompatActivity> Fragment.navigator(
        param: String
) {
    val intent = Intent(context, T::class.java)
    intent.putExtra("param", param)
    startActivity(intent)
}

fun Fragment.onFinish() {
    activity?.finish()
}

/**
 * @param activityPackage => exp : id.co.gits.feature_home_detail.HomeDetailActivity
 */
fun Fragment.navigatorImplicit(
        context: Context,
        activityPackage: String
) {
    val intent = Intent()
    try {
        intent.setClass(
                context,
                Class.forName(activityPackage)
        )
        startActivity(intent)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun Fragment.showToast(
        context: Context?,
        message: String
) {
    Toast.makeText(
            context, if (TextUtils.isEmpty(message))
        GitsHelper.Const.SERVER_ERROR_MESSAGE_DEFAULT else message, Toast.LENGTH_SHORT
    ).show()
}

fun Fragment.logD(
        TAG: Fragment,
        message: String
) {
    Timber.d("${TAG::class.java.simpleName} $message")
}

fun Fragment.logV(
        TAG: Fragment,
        message: String
) {
    Timber.v("${TAG::class.java.simpleName} $message")
}

fun Fragment.logE(
        TAG: Fragment,
        message: String
) {
    Timber.e("${TAG::class.java.simpleName} $message")
}

fun Fragment.saveBitmapToLocalFile(
        context: Context,
        imageBitmap: Bitmap,
        directoryName: String?,
        showMessageStatus: Boolean
) {
    val root = Environment.getExternalStorageDirectory().toString()

    val directoryNameDefault = if (TextUtils.isEmpty(directoryName)) {
        GitsHelper.Const.APP_FOLDER_DEFAULT
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
        GitsHelper.Const.MESSAGE_SUCCESS_IMAGE_SAVE
    } else {
        GitsHelper.Const.MESSAGE_FAILED_IMAGE_SAVE
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
                                GitsHelper.Const.SDCARD_URI_PATH + Environment
                                        .getExternalStorageDirectory()
                        )
                )
        )
    }
}

fun Fragment.setForceShowIcon(popupMenu: PopupMenu) {
    try {
        val fields = popupMenu.javaClass.declaredFields
        for (field in fields) {
            if ("mPopup" == field.name) {
                field.isAccessible = true
                val menuPopupHelper = field.get(popupMenu)
                val classPopupHelper = Class.forName(menuPopupHelper.javaClass.name)
                val setForceIcons = classPopupHelper.getMethod(
                        "setForceShowIcon", Boolean::class.javaPrimitiveType)
                setForceIcons.invoke(menuPopupHelper, true)
                break
            }
        }
    } catch (e: Throwable) {
        e.printStackTrace()
    }
}