package my.app.sportvideofeedapp.utlis.helper

import android.content.Context
import android.view.View
import android.view.animation.AnimationUtils

fun loadAnimation(view: View, context: Context, animationId: Int) {
    view.animation =
        AnimationUtils.loadAnimation(context, animationId)
}
