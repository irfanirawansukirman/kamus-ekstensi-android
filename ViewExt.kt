/* Extentions view level */

iimport android.graphics.Typeface
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import id.ac.unpad.profolio.R

fun View.visible(

) {
    visibility = View.VISIBLE
}

fun View.gone(

) {
    visibility = View.GONE
}

fun View.invisible(

) {
    visibility = View.INVISIBLE
}

fun View.showSnackbarWithCustomColor(
    message: String,
    textColor: Int, backgroundColor: Int,
    duration: Int
) {
    val finalMessage = if (TextUtils.isEmpty(message)) {
        Const.SERVER_ERROR_MESSAGE_DEFAULT
    } else {
        message
    }

    val finalDuration = if (duration == 0) {
        Const.SNACKBAR_TIMER_SHOWING_DEFAULT
    } else {
        duration
    }

    val finalTextColor = if (textColor == 0) {
        ContextCompat.getColor(this.context, R.color.mainWhite)
    } else {
        textColor
    }

    val finalBackgroundColor = if (textColor == 0) {
        ContextCompat.getColor(this.context, R.color.greyBackgroundDefault)
    } else {
        backgroundColor
    }

    val snackView = Snackbar.make(this, finalMessage, finalDuration)
    snackView.setActionTextColor(finalTextColor)
    snackView.view.setBackgroundColor(finalBackgroundColor)
    snackView.show()
}

fun View.showSnackbarDefault(
    message: String,
    duration: Int
) {
    val finalMessage = if (TextUtils.isEmpty(message)) {
        Const.SERVER_ERROR_MESSAGE_DEFAULT
    } else {
        message
    }

    val finalDuration = if (duration == 0) {
        Const.SNACKBAR_TIMER_SHOWING_DEFAULT
    } else {
        duration
    }

    Snackbar.make(this, finalMessage, finalDuration).show()
}

fun View.setCustomFont(
    fontName: String
): Typeface = Typeface
    .createFromAsset(this.context.assets, "fonts/$fontName")


fun RecyclerView.verticalListStyle(

) {
    layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    setHasFixedSize(true)
    itemAnimator = DefaultItemAnimator()
    setItemViewCacheSize(30)
    isDrawingCacheEnabled = true
    drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
}

fun RecyclerView.horizontalListStyle(

) {
    layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    setHasFixedSize(true)
    itemAnimator = DefaultItemAnimator()
    setItemViewCacheSize(30)
    isDrawingCacheEnabled = true
    drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
}

fun View.onShowWithSlide() {
    this.animate()
        .translationY(0f)
        .setInterpolator(DecelerateInterpolator(2f))
        .start()
}

fun View.onHideWithSlide() {
    this.animate()
        .translationY((this.height).toFloat())
        .setInterpolator(AccelerateInterpolator(2f))
        .start()
}