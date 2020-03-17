package my.app.sportvideofeedapp.utlis.widgets

import com.google.android.material.snackbar.Snackbar

object GeneralErrorSnackBarBuilder {

    fun showErrorSnackBar(root: android.view.View) {
        Snackbar.make(root, "Something went wrong try again", Snackbar.LENGTH_LONG) }
}
