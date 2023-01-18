package co.tsw.mlkit.app.view.activity

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import co.tsw.mlkit.app.R
import co.tsw.mlkit.app.utils.MediaLoader
import co.tsw.mlkit.app.utils.mlutils.FaceContourGraphic
import co.tsw.mlkit.app.utils.mlutils.GraphicOverlay.Graphic
import co.tsw.mlkit.app.utils.mlutils.TextGraphic
import co.tsw.mlkit.app.utils.toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetector
import com.google.mlkit.vision.face.FaceDetectorOptions
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumConfig
import kotlinx.android.synthetic.main.activity_text_detection.button_face
import kotlinx.android.synthetic.main.activity_text_detection.button_select
import kotlinx.android.synthetic.main.activity_text_detection.button_text
import kotlinx.android.synthetic.main.activity_text_detection.graphic_overlay
import kotlinx.android.synthetic.main.activity_text_detection.image_view
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.PermissionRequest
import java.io.IOException
import java.io.InputStream
import java.util.Locale
import java.util.PriorityQueue
import kotlin.math.max


class TextDetectionActivity : BaseActivity() , AdapterView.OnItemSelectedListener {

    companion object {
        private const val CAMERA_PERMISSION_CODE = 100
        private const val STORAGE_PERMISSION_CODE = 101
    }

    private var mSelectedImage: Bitmap? = null

    // Max width (portrait mode)
    private var mImageMaxWidth: Int? = null

    // Max height (portrait mode)
    private var mImageMaxHeight: Int? = null

    /**
     * Number of results to show in the UI.
     */
    private val RESULTS_TO_SHOW = 3

    /**
     * Dimensions of inputs.
     */
    private val DIM_IMG_SIZE_X = 224
    private val DIM_IMG_SIZE_Y = 224
    private val sortedLabels = PriorityQueue<Map.Entry<String, Float>>(
        RESULTS_TO_SHOW
    ) { (_, value), (_, value1) -> value.compareTo(value1) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_detection)

        Album.initialize(
            AlbumConfig.newBuilder(this)
                .setAlbumLoader(MediaLoader())
                .setLocale(Locale.getDefault())
                .build()
        )


        button_text?.setOnClickListener { runTextRecognition() }
        button_face?.setOnClickListener { runFaceContourDetection() }
        button_select?.setOnClickListener {
            checkPermission(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                STORAGE_PERMISSION_CODE)
        }
    }
    fun openAlbum(){
        ImagePicker.with(this)
            .compress(1024)         //Final image size will be less than 1 MB(Optional)
            .maxResultSize(1080, 1080)  //Final image resolution will be less than 1080 x 1080(Optional)
            .createIntent { intent ->
                startForProfileImageResult.launch(intent)
            }
    }

    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            if (resultCode == Activity.RESULT_OK) {
                //Image Uri will not be null for RESULT_OK
                val fileUri = data?.data!!

                val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    ImageDecoder.decodeBitmap(ImageDecoder.createSource(this@TextDetectionActivity.contentResolver, fileUri))
                } else {
                    MediaStore.Images.Media.getBitmap(this@TextDetectionActivity.contentResolver, fileUri)
                }

                mSelectedImage = bitmap
                image_view.setImageURI(fileUri)


            }
            else if (resultCode == ImagePicker.RESULT_ERROR) {
                Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }


    // Function to check and request permission.
    private fun checkPermission(permission: String, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(this@TextDetectionActivity, permission) == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(this@TextDetectionActivity, arrayOf(permission), requestCode)
        } else {
            Toast.makeText(this@TextDetectionActivity, "Permission already granted", Toast.LENGTH_SHORT).show()
        }
    }

    // This function is called when the user accepts or decline the permission.
    // Request Code is used to check which permission called this function.
    // This request code is provided when the user is prompt for permission.
    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this@TextDetectionActivity, "Camera Permission Granted", Toast.LENGTH_SHORT).show()
                openAlbum()
            } else {
                Toast.makeText(this@TextDetectionActivity, "Camera Permission Denied", Toast.LENGTH_SHORT).show()
            }
        } else if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this@TextDetectionActivity, "Storage Permission Granted", Toast.LENGTH_SHORT).show()
                openAlbum()
            } else {
                Toast.makeText(this@TextDetectionActivity, "Storage Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun runTextRecognition() {
        val image = InputImage.fromBitmap(mSelectedImage!!, 0)
        val recognizer = TextRecognition.getClient()
        button_text!!.isEnabled = false
        recognizer.process(image)
            .addOnSuccessListener { texts ->
                button_text.isEnabled = true
                processTextRecognitionResult(texts)
            }
            .addOnFailureListener { e -> // Task failed with an exception
                button_text.isEnabled = true
                e.printStackTrace()
            }
    }

    private fun processTextRecognitionResult(texts: Text) {
        val blocks: List<Text.TextBlock> = texts.getTextBlocks()
        if (blocks.size == 0) {
            showToast("No text found")
            return
        }
        graphic_overlay!!.clear()
        for (i in blocks.indices) {
            val lines: List<Text.Line> = blocks[i].getLines()
            for (j in lines.indices) {
                val elements: List<Text.Element> = lines[j].getElements()
                for (k in elements.indices) {
                    val textGraphic: Graphic = TextGraphic(graphic_overlay, elements[k])
                    graphic_overlay.add(textGraphic)
                }
            }
        }
    }

    private fun runFaceContourDetection() {
        val image = InputImage.fromBitmap(mSelectedImage!!, 0)
        val options = FaceDetectorOptions.Builder()
            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_FAST)
            .setContourMode(FaceDetectorOptions.CONTOUR_MODE_ALL)
            .build()
        button_face!!.isEnabled = false
        val detector: FaceDetector = FaceDetection.getClient(options)
        detector.process(image)
            .addOnSuccessListener { faces ->
                button_face.isEnabled = true
                processFaceContourDetectionResult(faces)
            }
            .addOnFailureListener { e -> // Task failed with an exception
                button_face.isEnabled = true
                e.printStackTrace()
            }
    }

    private fun processFaceContourDetectionResult(faces: List<Face>) {
        // Task completed successfully
        if (faces.isEmpty()) {
            showToast("No face found")
            return
        }
        graphic_overlay!!.clear()
        for (i in faces.indices) {
            val face: Face = faces[i]
            val faceGraphic = FaceContourGraphic(graphic_overlay)
            graphic_overlay.add(faceGraphic)
            faceGraphic.updateFace(face)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    // Functions for loading images from app assets.

    // Functions for loading images from app assets.
    // Returns max image width, always for portrait mode. Caller needs to swap width / height for
    // landscape mode.
    private fun getImageMaxWidth(): Int {
        if (mImageMaxWidth == null) {
            // Calculate the max width in portrait mode. This is done lazily since we need to
            // wait for
            // a UI layout pass to get the right values. So delay it to first time image
            // rendering time.
            mImageMaxWidth = image_view!!.width
        }
        return mImageMaxWidth!!
    }

    // Returns max image height, always for portrait mode. Caller needs to swap width / height for
    // landscape mode.
    private fun getImageMaxHeight(): Int {
        if (mImageMaxHeight == null) {
            // Calculate the max width in portrait mode. This is done lazily since we need to
            // wait for
            // a UI layout pass to get the right values. So delay it to first time image
            // rendering time.
            mImageMaxHeight = image_view!!.height
        }
        return mImageMaxHeight!!
    }

    // Gets the targeted width / height.
    private fun getTargetedWidthHeight(): Pair<Int, Int> {
        val targetWidth: Int
        val targetHeight: Int
        val maxWidthForPortraitMode = getImageMaxWidth()
        val maxHeightForPortraitMode = getImageMaxHeight()
        targetWidth = maxWidthForPortraitMode
        targetHeight = maxHeightForPortraitMode
        return Pair(targetWidth, targetHeight)
    }

    override fun onItemSelected(parent: AdapterView<*>?, v: View?, position: Int, id: Long) {
        try{
            graphic_overlay!!.clear()
            when (position) {
                0 -> mSelectedImage = getBitmapFromAsset(this, "Please_walk_on_the_grass.jpg")
                1 ->                 // Whatever you want to happen when the thrid item gets selected
                    mSelectedImage = getBitmapFromAsset(this, "grace_hopper.jpg")
            }
            if (mSelectedImage != null) {
                // Get the dimensions of the View
                val (targetWidth, maxHeight) = getTargetedWidthHeight()

                // Determine how much to scale down the image
                val scaleFactor = max(
                    mSelectedImage!!.width.toFloat() / targetWidth.toFloat(),
                    mSelectedImage!!.height.toFloat() / maxHeight.toFloat()
                )
                val resizedBitmap = Bitmap.createScaledBitmap(
                    mSelectedImage!!,
                    (mSelectedImage!!.width / scaleFactor).toInt(),
                    (mSelectedImage!!.height / scaleFactor).toInt(),
                    true
                )
                image_view!!.setImageBitmap(resizedBitmap)
                mSelectedImage = resizedBitmap
            }
        }catch (e:NullPointerException){
            Log.e("ERROR",e.message!!)
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        // Do nothing
    }

    fun getBitmapFromAsset(context: Context, filePath: String?): Bitmap? {
        val assetManager = context.assets
        val `is`: InputStream
        var bitmap: Bitmap? = null
        try {
            `is` = assetManager.open(filePath!!)
            bitmap = BitmapFactory.decodeStream(`is`)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return bitmap
    }
}