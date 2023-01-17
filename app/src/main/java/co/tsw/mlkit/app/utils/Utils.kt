package co.tsw.mlkit.app.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Environment
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.FileProvider
import co.tsw.mlkit.app.BuildConfig
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*


object Utils {
    fun hideSoftKeyboard(act: Activity) {
        val inputMethodManager = act.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        val v = act.currentFocus
        if (v != null) {
            inputMethodManager.hideSoftInputFromWindow(v.windowToken, 0)
        }
    }

    fun showSoftKeyboard(act: Activity, view: View) {
        val inputMethodManager = act.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }

    fun getString(data: String?): String {
        return when (data.isNullOrEmpty() || data == "false" || data == "null") {
            true -> ""
            else -> data
        }
    }

    /**
     * Convert Date object to string of date in specified format
     *
     * @param date   date object need to convert to string
     * @param format format of string date that want to be converted to
     * @return string of date
     */
    fun dateToString(date: Date?, format: String): String {
        var strDate = ""
        if (date != null) {
            val simpleDateFormat = SimpleDateFormat(format)
            strDate = simpleDateFormat.format(date)
        }
        return strDate
    }

    fun getFolder(name: String): String {
        //Create Folder
        val folder = File(Environment.getExternalStorageDirectory().toString() + "/Teeni/" + name)
        if (!folder.exists())
            folder.mkdirs()
        //Save the path as a string value
        return folder.absolutePath
    }

    fun saveFileSignature(bitmap: Bitmap, folder: File): String {
        val newBitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(newBitmap)
        canvas.drawColor(Color.WHITE)
        canvas.drawBitmap(bitmap, 0f, 0f, null)
        val stream = FileOutputStream(folder)
        newBitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream)
        stream.close()

        return folder.absolutePath
    }


    fun shareFilePdf(path: String, context: Context) {
        // create new Intent
        val intent = Intent(Intent.ACTION_VIEW)
        // set flag to give temporary permission to external app to use your FileProvider
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        val file = File(path)
        // generate URI, I defined authority as the application ID in the Manifest, the last param is file I want to open
        val uri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileprovider", file)
        // I am opening a PDF file so I give it a valid MIME type
        intent.setDataAndType(uri, "application/pdf")

        // validate that the device can open your File!
        val pm = context.packageManager
        if (intent.resolveActivity(pm) != null) {
            context.startActivity(intent)
        }
    }

    fun sendEmail(context: Context, email: String, pathFile: String, subject: String, body: String) {
        val emailIntent = Intent(Intent.ACTION_SEND)
        // set the type to 'email'
        emailIntent.type = "plain/text"
        val to = arrayOf(email)
        emailIntent.putExtra(Intent.EXTRA_EMAIL, to)
        // the attachment
        val file = File(pathFile)
        val uri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileprovider", file)
        emailIntent.putExtra(Intent.EXTRA_STREAM, uri)
        // the mail subject
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        emailIntent.putExtra(Intent.EXTRA_TEXT, body)

        context.startActivity(Intent.createChooser(emailIntent, "Sending email...").addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
    }




    @SuppressLint("NewApi")
    fun encoder(filePath: String): String {
        val bytes = File(filePath).readBytes()
        val base64 = Base64.getEncoder().encodeToString(bytes)
        return base64
    }




}