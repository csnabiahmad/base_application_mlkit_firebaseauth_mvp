package co.tsw.mlkit.app.utils.mlutils

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceContour
import com.google.mlkit.vision.face.FaceLandmark
import java.lang.String


class FaceContourGraphic(overlay: GraphicOverlay) : GraphicOverlay.Graphic(overlay) {
    private val FACE_POSITION_RADIUS = 10.0f
    private val ID_TEXT_SIZE = 70.0f
    private val ID_Y_OFFSET = 80.0f
    private val ID_X_OFFSET = -70.0f
    private val BOX_STROKE_WIDTH = 5.0f

    private val COLOR_CHOICES = intArrayOf(
        Color.BLUE, Color.CYAN, Color.GREEN, Color.MAGENTA, Color.RED, Color.WHITE, Color.YELLOW
    )
    private var currentColorIndex = 0

    private var facePositionPaint: Paint? = null
    private var idPaint: Paint? = null
    private var boxPaint: Paint? = null

    @Volatile
    private var face: Face? = null

    init{
//        super(overlay)
        currentColorIndex = (currentColorIndex + 1) % COLOR_CHOICES.size
        val selectedColor = COLOR_CHOICES[currentColorIndex]
        facePositionPaint = Paint()
        facePositionPaint!!.setColor(selectedColor)
        idPaint = Paint()
        idPaint!!.color = selectedColor
        idPaint!!.
        textSize = ID_TEXT_SIZE
        boxPaint = Paint()
        boxPaint!!.color = selectedColor
        boxPaint!!.style = Paint.Style.STROKE
        boxPaint!!.strokeWidth = BOX_STROKE_WIDTH
    }

    /**
     * Updates the face instance from the detection of the most recent frame. Invalidates the relevant
     * portions of the overlay to trigger a redraw.
     */
    fun updateFace(face: Face?) {
        this.face = face
        postInvalidate()
    }

    /** Draws the face annotations for position on the supplied canvas.  */
    override fun draw(canvas: Canvas?) {
        val face: Face = face ?: return

        // Draws a circle at the position of the detected face, with the face's track id below.
        val x = translateX(face.boundingBox.centerX().toFloat())
        val y = translateY(face.boundingBox.centerY().toFloat())
        canvas?.drawCircle(x, y, FACE_POSITION_RADIUS, facePositionPaint!!)
        canvas?.drawText("id: " + face.getTrackingId(), x + ID_X_OFFSET, y + ID_Y_OFFSET, idPaint!!)

        // Draws a bounding box around the face.
        val xOffset = scaleX(face.getBoundingBox().width() / 2.0f)
        val yOffset = scaleY(face.getBoundingBox().height() / 2.0f)
        val left = x - xOffset
        val top = y - yOffset
        val right = x + xOffset
        val bottom = y + yOffset
        canvas?.drawRect(left, top, right, bottom, boxPaint!!)
        val contour: List<FaceContour> = face.getAllContours()
        for (faceContour in contour) {
            for (point in faceContour.points) {
                val px = translateX(point.x)
                val py = translateY(point.y)
                canvas?.drawCircle(px, py, FACE_POSITION_RADIUS, facePositionPaint!!)
            }
        }
        if (face.getSmilingProbability() != null) {
            canvas?.drawText(
                "happiness: " + String.format("%.2f", face.getSmilingProbability()),
                x + ID_X_OFFSET * 3,
                y - ID_Y_OFFSET,
                idPaint!!
            )
        }
        if (face.getRightEyeOpenProbability() != null) {
            canvas?.drawText(
                "right eye: " + String.format("%.2f", face.getRightEyeOpenProbability()),
                x - ID_X_OFFSET,
                y,
                idPaint!!
            )
        }
        if (face.getLeftEyeOpenProbability() != null) {
            canvas?.drawText(
                "left eye: " + String.format("%.2f", face.getLeftEyeOpenProbability()),
                x + ID_X_OFFSET * 6,
                y,
                idPaint!!
            )
        }
        val leftEye: FaceLandmark? = face.getLandmark(FaceLandmark.LEFT_EYE)
        if (leftEye != null) {
            canvas?.drawCircle(
                translateX(leftEye.position.x),
                translateY(leftEye.position.y),
                FACE_POSITION_RADIUS,
                facePositionPaint!!
            )
        }
        val rightEye: FaceLandmark? = face.getLandmark(FaceLandmark.RIGHT_EYE)
        if (rightEye != null) {
            canvas?.drawCircle(
                translateX(rightEye.position.x),
                translateY(rightEye.position.y),
                FACE_POSITION_RADIUS,
                facePositionPaint!!
            )
        }
        val leftCheek: FaceLandmark? = face.getLandmark(FaceLandmark.LEFT_CHEEK)
        if (leftCheek != null) {
            canvas?.drawCircle(
                translateX(leftCheek.position.x),
                translateY(leftCheek.position.y),
                FACE_POSITION_RADIUS,
                facePositionPaint!!
            )
        }
        val rightCheek: FaceLandmark? = face.getLandmark(FaceLandmark.RIGHT_CHEEK)
        if (rightCheek != null) {
            canvas?.drawCircle(
                translateX(rightCheek.position.x),
                translateY(rightCheek.position.y),
                FACE_POSITION_RADIUS,
                facePositionPaint!!
            )
        }
    }

}