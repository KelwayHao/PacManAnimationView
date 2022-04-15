package com.example.pacmananimationview

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

class PacManView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {

    companion object {
        private const val CONTOUR_THICKNESS = 10f
        private const val COEFFICIENT_MARGIN = 0.8f
        private const val TIME_TO_OPEN_MOUTH = 1500L
        private const val START_ANGLE = 0f
        private const val END_ANGLE = 180f
        private const val CORNER_OPEN_MOUTH = 45f
        private const val END_ANGLE_EYE = 360f
        private const val EYE_COEFFICIENT = 0.2f
    }


    private val paintBodyPacMan = Paint(Paint.ANTI_ALIAS_FLAG)
    var colorPacMan: Int = 0
        set(value) {
            paintBodyPacMan.color = value
            field = value
            invalidate()
        }

    private val paintContourBodyPacMan = Paint(Paint.ANTI_ALIAS_FLAG)
        .apply {
            style = Paint.Style.STROKE
            strokeWidth = CONTOUR_THICKNESS
        }
    var colorContourPacMan: Int = 0
        set(value) {
            paintContourBodyPacMan.color = value
            field = value
            invalidate()
        }

    private val paintEyePacMan = Paint(Paint.ANTI_ALIAS_FLAG)
    var colorEye: Int = 0
        set(value) {
            paintEyePacMan.color = value
            field = value
            invalidate()
        }

    init {
        context.theme.obtainStyledAttributes(attributeSet, R.styleable.PacManView, 0, 0)
            .apply {
                colorPacMan =
                    getColor(R.styleable.PacManView_pacmanColor, context.getColor(R.color.yellow))
                colorEye =
                    getColor(R.styleable.PacManView_pacmanEyeColor, context.getColor(R.color.black))
                colorContourPacMan =
                    getColor(
                        R.styleable.PacManView_pacmanContourColor,
                        context.getColor(R.color.black)
                    )
            }
    }


    private var radius = 0f
    private var radiusEye = 0f
    private var centerX = 0f
    private var centerY = 0f

    private val bodyOvalPacMan: RectF by lazy {
        RectF(
            centerX - radius,
            centerY - radius,
            centerX + radius,
            centerY + radius
        )
    }

    private val eyeOvalPacMan: RectF by lazy {
        RectF(
            centerX,
            centerY / 2f + radiusEye,
            centerX + radiusEye * 2f,
            centerY / 2f - radiusEye
        )
    }

    private val openMouth: ValueAnimator
        get() = ValueAnimator.ofFloat(mouthSweep, mouthSweep + CORNER_OPEN_MOUTH)
            .apply {
                duration = TIME_TO_OPEN_MOUTH
                repeatMode = ValueAnimator.REVERSE
                repeatCount = ValueAnimator.INFINITE
                addUpdateListener {
                    mouthSweep = it.animatedValue as Float
                    invalidate()
                }
            }

    private var mouthStart = END_ANGLE
    private var mouthSweep = END_ANGLE - CORNER_OPEN_MOUTH


    fun startAnimation() {
        openMouth.start()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        radius = width / 2f * COEFFICIENT_MARGIN
        centerX = width / 2f
        centerY = height / 2f
        radiusEye = radius * EYE_COEFFICIENT
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawArc(bodyOvalPacMan, mouthStart, mouthSweep, true, paintContourBodyPacMan)
        canvas.drawArc(
            bodyOvalPacMan,
            mouthStart,
            mouthSweep.reverseNumber(),
            true,
            paintContourBodyPacMan
        )
        canvas.drawArc(bodyOvalPacMan, mouthStart, mouthSweep, true, paintBodyPacMan)
        canvas.drawArc(
            bodyOvalPacMan,
            mouthStart,
            mouthSweep.reverseNumber(),
            true,
            paintBodyPacMan
        )
        canvas.drawArc(eyeOvalPacMan, START_ANGLE, END_ANGLE_EYE, true, paintEyePacMan)
    }

    private fun Float.reverseNumber(): Float {
        return -this
    }
}
