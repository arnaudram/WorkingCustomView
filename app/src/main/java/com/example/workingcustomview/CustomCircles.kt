package com.example.workingcustomview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.math.roundToInt
import kotlin.properties.Delegates

class CustomCircles(context: Context?, attrs: AttributeSet?) : View(context, attrs) {


    private val strikePaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val fillPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    var moduleStatus: BooleanArray = BooleanArray(15)
    private val shapeSize = 140f
    private val spacing = 25f
    private val strokeWidth = 8f

    private val y1 = paddingTop
    private val x1 = paddingStart
    private val step = (spacing + shapeSize).toInt()

    // desired number of shape per row
    private val r = (shapeSize - strokeWidth) / 2

    private var maxShapePerRow: Int by Delegates.notNull()
    private var maxRows: Int by Delegates.notNull()

    private val exampleModuleStatusSize = 7


    private var rectangle: ArrayList<Rect>

    init {
        strikePaint.style = Paint.Style.STROKE
        strikePaint.strokeWidth = strokeWidth
        strikePaint.color = Color.BLACK

        fillPaint.style = Paint.Style.FILL
        fillPaint.color = Color.YELLOW
        rectangle = arrayListOf()

        if (isInEditMode) {
            setUpEditModeStatus()
        }
////


    }

    private fun setUpEditModeStatus() {
        val exampleModuleStatus = BooleanArray(exampleModuleStatusSize)
        val middle = exampleModuleStatusSize / 2
        for (i in 0..middle) {
            exampleModuleStatus[i] = true
        }

        moduleStatus = exampleModuleStatus
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        val availableWidth = w - paddingEnd - paddingStart
        val maxShapePerRow1 = minOf(availableWidth.div(step), moduleStatus.size)


        for (i in 0 until (moduleStatus.size)) {
            val column = i % maxShapePerRow1
            val row = i.div(maxShapePerRow1)
            val x = x1 + column.times(step)
            val y = y1 + row.times(step)
            val rect = Rect(x, y, x + shapeSize.toInt(), y + shapeSize.toInt())
            rectangle.add(rect)
        }

    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
         return when(event?.action){
                MotionEvent.ACTION_DOWN -> {
                    true
                }

                MotionEvent.ACTION_UP -> {
                    val shapePos=findShapeAtPosition(event.x,event.y)
                    onModuleSelected(shapePos)
                    true
                }
                else -> super.onTouchEvent(event)
            }


    }

    private fun onModuleSelected(shapePos: Int) {

        if (shapePos==-1){
            return
        }else{
            moduleStatus[shapePos]=!moduleStatus[shapePos]
            invalidate()
        }
    }

    private fun findShapeAtPosition(x: Float, y: Float): Int {
         var pos:Int=-1
         for (i in moduleStatus.indices){
              if(rectangle[i].contains(x.toInt(),y.toInt())){
                  pos=i
                  break
              }
         }


        return pos
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // available width
        val availableWidth = MeasureSpec.getSize(widthMeasureSpec) - paddingStart - paddingEnd

        maxShapePerRow = minOf(availableWidth.div(step), moduleStatus.size)
        maxRows = ((moduleStatus.size).toDouble().div(maxShapePerRow)).roundToInt() + 1


        // desired Width and height
        val desiredWidth = paddingStart + paddingEnd + maxShapePerRow.times(step) - spacing.toInt()
        val desiredHeight = paddingTop + paddingBottom + maxRows.times(step) - spacing.toInt()

        /* y(n)=y(0)+ n*(shapeSize + spacing)  with y(0)=paddingTop n>0
         height= y(totalRow) + shapeSize + paddingBottom
         */
        // resolving desired value against constraint value
        val width = resolveSizeAndState(desiredWidth, widthMeasureSpec, 0)
        val height = resolveSizeAndState(desiredHeight, heightMeasureSpec, 0)


        // setting measurement to the system
        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let { canvas: Canvas ->

            for (i in 0 until (moduleStatus.size)) {

                val cx = rectangle[i].centerX().toFloat()
                val cy = rectangle[i].centerY().toFloat()

                if (moduleStatus[i]) {
                    canvas.drawCircle(cx, cy, r, fillPaint)
                }
                canvas.drawCircle(cx, cy, r, strikePaint)

            }


        }
    }


}