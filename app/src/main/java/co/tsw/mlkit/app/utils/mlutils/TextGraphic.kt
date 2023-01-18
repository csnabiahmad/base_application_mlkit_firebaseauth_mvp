package co.tsw.mlkit.app.utils.mlutils

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.Log
import com.google.mlkit.vision.text.Text


class TextGraphic(overlay: GraphicOverlay, element: Text.Element) : GraphicOverlay.Graphic(overlay) {
    private val TAG = "TextGraphic"
    private val TEXT_COLOR = Color.RED
    private val TEXT_SIZE = 54.0f
    private val STROKE_WIDTH = 4.0f

    private var rectPaint: Paint? = null
    private var textPaint: Paint? = null
    private var element: Text.Element? = null

    init {
//        super(overlay)
        this.element = element
        rectPaint = Paint()
        rectPaint!!.color = TEXT_COLOR
        rectPaint!!.style=(Paint.Style.STROKE)
        rectPaint!!.strokeWidth = STROKE_WIDTH
        textPaint = Paint()
        textPaint!!.color = TEXT_COLOR
        textPaint!!.textSize = TEXT_SIZE
        // Redraw the overlay, as this graphic has been added.
        postInvalidate()

    }

    /**
     * Draws the text block annotations for position, size, and raw value on the supplied canvas.
     */
    override fun draw(canvas: Canvas?) {
        Log.d(TAG, "on draw text graphic")
        checkNotNull(element) { "Attempting to draw a null text." }

        // Draws the bounding box around the TextBlock.
        val rect = RectF(element?.boundingBox)
        canvas?.drawRect(rect, rectPaint!!)

        // Renders the text at the bottom of the box.
        canvas?.drawText(element?.text.toString(), rect.left, rect.bottom, textPaint!!)
    }


}