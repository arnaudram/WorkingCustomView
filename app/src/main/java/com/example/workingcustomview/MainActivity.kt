package com.example.workingcustomview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val lambda:(View)->Unit={
            Toast.makeText(this,"view",Toast.LENGTH_LONG).show()
        }




    }

    fun addShape(view: View) {



    }

}