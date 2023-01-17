package co.tsw.mlkit.app.common


import android.app.ProgressDialog
import android.content.Context


class ProgressDlg(context: Context?) {

    private var progressDialog: ProgressDialog? = null

    /**
     * Check if Progress Dialog is showing.
     */
    val isShowing: Boolean
        get() = progressDialog!!.isShowing


    init {
        if (null != context) {
            progressDialog = ProgressDialog(context)
            progressDialog!!.setCanceledOnTouchOutside(false)
        }
    }

    /**
     * Show Progress Dialog on top screen with message
     */
    fun show() {
        progressDialog!!.show()

    }

    /**
     * Show Progress Dialog on top screen with message
     */
    fun show(message: String) {
        progressDialog!!.setMessage(message)
        progressDialog!!.show()
    }

    /**
     * Hide Progress Dialog, if it is showing.
     */
    fun hide() {
        progressDialog!!.dismiss()
    }
}