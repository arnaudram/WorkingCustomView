package com.example.workingcustomview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

class CustomItemView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    var tvName:TextView
    var tvEmail:TextView
    var tvStar:TextView
    var image:ImageView
    var itemBehaviour:((View)->Unit)?=null
    init {
        this.orientation= VERTICAL
        val layoutInflater=context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
          layoutInflater.inflate(R.layout.custom_item_view,this).apply {
            tvName=  findViewById<TextView>(R.id.tv_name)
              tvEmail=findViewById(R.id.tv_email)
              tvStar=findViewById(R.id.star)
              image=findViewById(R.id.image)

              tvStar.setOnClickListener {
                  itemBehaviour?.let { it1 -> it1(it) }
              }

          }
        bindItem()

    }




    fun bindItem(){
     tvEmail.text="myemailhere45@gmail.com"
        tvName.text="This is my name"
    }

    fun setLinster(lambda: (View) -> Unit) {
       itemBehaviour=lambda
    }


}