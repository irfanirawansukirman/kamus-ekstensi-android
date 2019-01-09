/* Extentions context level */

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.widget.Toast

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

inline fun <reified T : AppCompatActivity> Context.navigator(

) {
    val intent = Intent(this, T::class.java)
    startActivity(intent)
}

inline fun <reified T : AppCompatActivity> Context.navigator(
    param: String
) {
    val intent = Intent(this, T::class.java)
    intent.putExtra("param", param)
    startActivity(intent)
}

inline fun <reified T : AppCompatActivity> Context.navigator(
    intentParams : Intent.() -> Unit
) {
    val intent = Intent(this, T::class.java)
    intent.intentParams()
    startActivity(intent)
}

/**
 * Using it for moving to another page with activity package name (usually modular package)
 *
 * @param activityPackage => exp : id.co.gits.feature_home_detail.HomeDetailActivity
 * @param activityContext => your state app
 */
fun Context.navigatorImplicit(
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

fun Context.showToast(
    message: String
) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}
