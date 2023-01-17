package co.tsw.mlkit.app.common

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.text.TextUtils

/**
 * Constructor
 *
 * @param context
 * @param modal   if modal is true then user must close popup by interacting with controls
 * inside popup. Touching out side popup to canceled is not permit
 */
class PopUpDlg(context: Context?, modal: Boolean) {
    private var mDlg: AlertDialog? = null
    private var mContext: Context? = null
    private val alertDialog: AlertDialog? = null


    init {
        if (context != null) {
            mDlg = AlertDialog.Builder(context).create()
            mDlg!!.setCanceledOnTouchOutside(!modal)
            mContext = context
            //Here's the magic..
            //Set the dialog to not focusable (makes navigation ignore us adding the window)
            // mDlg.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);

        } else {
            throw RuntimeException("Context is null. Need a context to create Popup!")
        }
    }

    /**
     * Show common popup with message and return response for caller by callback function
     * that mean caller must implement callback function to get response from user and do
     * business logic
     *
     * @param title
     * @param msg
     * @param positiveBnt positive button title
     * @param negativeBnt negative button title
     * @param onOK
     * @param onCancel
     */
    fun show(title: String, msg: String, positiveBnt: String, negativeBnt: String,
             onOK: DialogInterface.OnClickListener,
             onCancel: DialogInterface.OnClickListener?) {

        if (mDlg != null) {
            mDlg!!.setTitle(title)
            mDlg!!.setMessage(msg)
            if (!TextUtils.isEmpty(positiveBnt)) {
                mDlg!!.setButton(DialogInterface.BUTTON_POSITIVE, positiveBnt, onOK)
            }
            if (!TextUtils.isEmpty(negativeBnt)) {
                mDlg!!.setButton(DialogInterface.BUTTON_NEGATIVE, negativeBnt, onCancel)
            }
            mDlg!!.show()
        }
    }

}