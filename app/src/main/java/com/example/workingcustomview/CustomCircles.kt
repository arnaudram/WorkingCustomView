package com.example.workingcustomview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import kotlin.math.*
import kotlin.properties.Delegates

class CustomCircles @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {


    private val strikePaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val fillPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    val pathOne=Path()

    val pathTwo =Path()

    var moduleStatus: BooleanArray = BooleanArray(15)
    private val shapeSize = 140f
    private val spacing = 25f
    private val strokeWidth = 2f

    private val y1 = paddingTop
    private val x1 = paddingStart
    private val step = (spacing + shapeSize).toInt()

    // desired number of shape per row
    private val r = (shapeSize - strokeWidth) / 2

    private var maxShapePerRow: Int by Delegates.notNull()
    private var maxRows: Int by Delegates.notNull()

    private val exampleModuleStatusSize = 7
       private  val shapeDefaultType=0
    private val shapeType: Int
    private var rectangle: ArrayList<Rect>
    private lateinit var points: ArrayList<PointF>

    init {

        val a=getContext().obtainStyledAttributes(attrs,R.styleable.CustomCircles,defStyleAttr,0)
        val outlineColor=a.getColor(R.styleable.CustomCircles_outlineColor,Color.BLACK)

        shapeType = a.getInt(R.styleable.CustomCircles_shapetype,shapeDefaultType)
        val outlineStrokeWidth=a.getDimension(R.styleable.CustomCircles_outlineWidth,strokeWidth)
        a.recycle()
        strikePaint.style = Paint.Style.STROKE
        strikePaint.strokeWidth = getPixelValueFromDp(outlineStrokeWidth)
        strikePaint.strokeJoin=Paint.Join.MITER
        strikePaint.color = outlineColor

        fillPaint.style = Paint.Style.FILL
        fillPaint.color = Color.YELLOW
        rectangle = arrayListOf()
        setUpPathPoint()


        if (isInEditMode) {
            setUpEditModeStatus()
        }
////


    }

    private fun getPixelValueFromDp(value: Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,value,context.resources.displayMetrics)
    }


    private fun setUpPathPoint() {
        val tetha =(2* PI).div(5)

        points = arrayListOf<PointF>()
        for (i in 0..5) {
            val px = r * cos(PI / 2 + (i) * tetha)
            val py=r* sin(PI / 2 + (i ) * tetha)
            val pointF=PointF(px.toFloat(),py.toFloat())
               points.add(pointF)
        }
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
                  if (shapeType==shapeDefaultType) {
                      val cx = rectangle[i].centerX().toFloat()
                      val cy = rectangle[i].centerY().toFloat()

                      if (moduleStatus[i]) {
                          canvas.drawCircle(cx, cy, r, fillPaint)
                      }
                      canvas.drawCircle(cx, cy, r, strikePaint)
                  }
                  else{
                      drawStars(canvas,i)
                  }
            }


        }
    }

    private fun drawStars(canvas: Canvas, i: Int) {
        val pts= arrayListOf<PointF>()
        val innerPts= arrayListOf<PointF>()
        val tetha =(2* PI).div(5)

        val cx = rectangle[i].centerX().toFloat()
        val cy = rectangle[i].centerY().toFloat()
        val innerRadius=r*(0.5)*(3- sqrt(5.0))
        canvas.save()
        canvas.translate(cx,cy)
        // val rx=  (r)-cx
         // val ry=(r)-cy
      //  val radiusStars= hypot(rx,ry)
        for (k in 0..5) {
            val px = r * cos( PI / 2 + (k) * tetha)
            val py=r* sin( PI / 2 + (k ) * tetha)
            val pointF=PointF(px.toFloat(),py.toFloat())
            pts.add(pointF)
        }
        for (k in 0..10) {
            val px = innerRadius * cos(  PI / 2 +(k) * tetha)
            val py=innerRadius* sin(  PI / 2 +(k ) * tetha)
            val pointF=PointF(px.toFloat(),py.toFloat())
            innerPts.add(pointF)
        }

         pathOne.apply {
             moveTo(pts[3].x,pts[3].y)
             lineTo(pts[0].x,pts[0].y)
             lineTo(pts[2].x,pts[2].y)
             lineTo(pts[4].x,pts[4].y)
             lineTo(pts[1].x,pts[1].y)
             lineTo(pts[3].x,pts[3].y)


             close()
         }
          /*pathTwo.apply {
              moveTo(pts[2].x,pts[2].y)
              lineTo(pts[4].x,pts[4].y)
              lineTo(pts[1].x,pts[1].y)
              lineTo(pts[3].x,pts[3].y)
              close()
          }
        pathOne.op(pathTwo,Path.Op.UNION)*/

        if (moduleStatus[i]) {
           canvas.drawPath(pathOne,fillPaint)

        }
        canvas.drawPath(pathOne, strikePaint)
        canvas.restore()
    }


}