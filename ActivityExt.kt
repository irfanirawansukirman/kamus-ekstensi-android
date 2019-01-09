/* Extentions activity level */

import android.annotation.SuppressLint
import android.os.Build
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.View

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

private inline fun FragmentManager.transact(
    action: FragmentTransaction.() -> Unit
) {
    beginTransaction().apply {
        action()
    }.commit()
}