/* Extentions fragment level */

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.PopupMenu

inline fun <FRAGMENT : Fragment> FRAGMENT.putArgs(argsBuilder: Bundle.() -> Unit):
        FRAGMENT = this.apply { arguments = Bundle().apply(argsBuilder) }

fun Fragment.onFinish() {
    activity?.finish()
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