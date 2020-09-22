package com.example.workingcustomview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var customCircles: CustomCircles
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        customCircles = findViewById(R.id.customCircles)
          loadUpModuleStatus()

    }

    private fun loadUpModuleStatus() {
        val moduleStatusSize=11
        val moduleStatus=BooleanArray(moduleStatusSize)
        for (i in 0 until 7){
            moduleStatus[i]=true
        }
       customCircles.moduleStatus=moduleStatus
    }


}