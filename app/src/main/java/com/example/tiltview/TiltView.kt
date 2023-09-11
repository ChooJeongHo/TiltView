package com.example.tiltview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.hardware.SensorEvent
import android.view.View

class TiltView(context: Context?) : View(context) {
    private val greenPaint: Paint = Paint()
    private val blackPaint: Paint = Paint()

    private var xCoord: Float = 0f
    private var yCoord: Float = 0f
    private var cX: Float = 0f
    private var cY: Float = 0f

    init {
        // 녹색 페인트
        greenPaint.color = Color.GREEN
        // 검은색 페인트
        blackPaint.style = Paint.Style.STROKE
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        // 뷰의 크기가 결정되면 호출
        // 뷰의 크기가 결정되면 뷰의 중심의 좌표를 얻어온다.
        cX = w / 2f
        cY = h / 2f
    }

    // 원을 그리는 메서드, onDraw()
    // drawCircle(cX: Float좌표, cY: Float좌표, radius: Float, paint: Paint!)
    override fun onDraw(canvas: Canvas?) {
        // 바깥 원
        canvas?.drawCircle(cX, cY, 100f, blackPaint)
        // 녹색 원
        canvas?.drawCircle(xCoord + cX, yCoord + cY, 100f, greenPaint)
        // 고정된 원 가운데의 십자가
        canvas?.drawLine(cX - 20, cY, cX + 20, cY, blackPaint)
        canvas?.drawLine(cX, cY - 20, cX, cY + 20, blackPaint)
        super.onDraw(canvas)
    }

    fun onSensorEvent(event: SensorEvent) {
        // 화면을 가로로 돌리면 두 개의 축을 변경
        yCoord = event.values[0] * 20
        yCoord = event.values[1] * 20

        invalidate()
        // onDraw() 메서드를 호출하여 화면을 다시 그린다. 뷰의 onDraw() 메서드는 시스템에 의해 자동으로 호출된다.(뷰를 다시 그리게 한다)
    }
}