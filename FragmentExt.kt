/* Extentions fragment level */

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.PopupMenu

inline fun <FRAGMENT : Fragment> FRAGMENT.putArgs(argsBuilder: Bundle.() -> Unit):
        FRAGMENT = this.apply { arguments = Bundle().apply(argsBuilder) }

fun Fragment.onFinish() {
    activity?.finish()
}