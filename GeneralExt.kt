/* Extentions general level */

import android.content.Context
import android.net.ConnectivityManager
import android.provider.Settings
import android.telephony.PhoneNumberUtils
import android.text.TextUtils
import android.util.DisplayMetrics
import android.view.WindowManager
import id.co.gits.gitsdriver.utils.GitsHelper
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern


fun String.getDeviceId(
        context: Context
): String {
    return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
}

fun String.currencyFormatToRupiah(
        data: Double
): String {
    val kursIndonesia = DecimalFormat.getCurrencyInstance() as DecimalFormat
    val formatRp = DecimalFormatSymbols()

    formatRp.currencySymbol = "Rp. "
    formatRp.monetaryDecimalSeparator = '.'
    formatRp.groupingSeparator = ','

    kursIndonesia.decimalFormatSymbols = formatRp
    return kursIndonesia.format(data)
}

fun String.webviewJustifyContent(
        bodyHTML: String
): String {
    val head = "<head><style>img{max-width: 100%; height: auto;} body { margin: 0; }" +
            "iframe {display: block; background: #000; border-top: 4px solid #000; border-bottom: 4px solid #000;" +
            "top:0;left:0;width:100%;height:235;}</style></head>"
    return "<html>$head<body>$bodyHTML</body></html>"
}

fun Boolean.isLocaleDate(
        isLocale: Boolean
): Locale {
    return if (isLocale) Locale("id", "ID")
    else Locale("en", "EN")
}

fun String.emailValidate(
        email: String
): Boolean {
    val emailPattern = Pattern.compile(
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
                    "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
    )
    return emailPattern.matcher(email).matches()
}

fun Boolean.phoneValidate(
        phone: String
): Boolean {
    return PhoneNumberUtils.isGlobalPhoneNumber(phone)
}

fun Boolean.isNetworkAvailable(
        context: Context
): Boolean {
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (cm != null) {
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }
    return false
}

fun Int.getScreenHeight(
        context: Context
): Int {
    val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val dm = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(dm)
    return dm.heightPixels
}

fun Int.getScreenWidth(
        context: Context
): Int {
    val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val dm = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(dm)
    return dm.widthPixels
}

fun Int.getStatusBarHeight(
        context: Context
): Int {
    var result = 0
    val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
        result = context.resources.getDimensionPixelSize(resourceId)
    }
    return result
}

fun Int.autofitColumnsGrid(
        context: Context
): Int {
    val displayMetrics = context.resources.displayMetrics
    val dpWidth = displayMetrics.widthPixels / displayMetrics.density
    return (dpWidth / 180).toInt()
}

fun Long.getTimeLongFromDate(
        date: String,
        dateFormat: String,
        isLocale: Boolean
): Long {
    return if (!TextUtils.isEmpty(date)) SimpleDateFormat(dateFormat, isLocale.isLocaleDate(isLocale))
            .parse(date).time else GitsHelper.Const.NUMBER_DEFAULT.toLong()
}

fun String.dateFormatFromTimeLong(
        timeLong: Long,
        newFormat: String,
        isLocale: Boolean
): String {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = timeLong

    return if (timeLong != 0.toLong() && timeLong != null) SimpleDateFormat(newFormat, isLocale.isLocaleDate(isLocale))
            .format(calendar.time) else SimpleDateFormat(newFormat, isLocale.isLocaleDate(isLocale))
            .format(System.currentTimeMillis())
}

fun String.dateFormatFromTimeString(
        date: String,
        oldFormat: String,
        newFormat: String,
        isLocale: Boolean
): String {
    val dateTimeMillis = if (!TextUtils.isEmpty(date)) {
        SimpleDateFormat(oldFormat, isLocale.isLocaleDate(isLocale)).parse(date).time
    } else {
        GitsHelper.Const.NUMBER_DEFAULT.toLong()
    }

    val calendar = Calendar.getInstance()
    calendar.timeInMillis = dateTimeMillis

    return if (dateTimeMillis != 0.toLong() && dateTimeMillis != null) {
        SimpleDateFormat(newFormat, isLocale.isLocaleDate(isLocale))
                .format(calendar.time)
    } else {
        SimpleDateFormat(newFormat, isLocale.isLocaleDate(isLocale))
                .format(System.currentTimeMillis())
    }
}