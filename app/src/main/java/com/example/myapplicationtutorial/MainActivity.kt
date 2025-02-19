package com.example.myapplicationtutorial

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    public fun setColorSeek(seek: SeekBar){
        if(seek.progress < 25){
            seek.progressTintList = ColorStateList.valueOf(Color.rgb(195,0,0))
            seek.thumb.setTint(Color.rgb(195,0,0))
        }
        else if(seek.progress < 50){
            seek.progressTintList = ColorStateList.valueOf(Color.rgb(255,150,50))
            seek.thumb.setTint(Color.rgb(255,150,50))
        }
        else if(seek.progress < 75){
            seek.progressTintList = ColorStateList.valueOf(Color.rgb(255,255,0))
            seek.thumb.setTint(Color.rgb(255,255,0))
        }
        else{
            seek.progressTintList = ColorStateList.valueOf(Color.GREEN)
            seek.thumb.setTint(Color.GREEN)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //setup a listener for all seekbars
        val seekbars: MutableList<SeekBar> = ArrayList()
        val seekbarIds = intArrayOf(R.id.seekBar1,R.id.seekBar2,R.id.seekBar3,R.id.seekBar4,
                                    R.id.seekBar5,R.id.seekBar6,R.id.seekBar7,R.id.seekBar8,)
        for (seekbarId in seekbarIds){
            seekbars.add(findViewById<View>(seekbarId) as SeekBar)
        }

        val sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE)

        for (seek in seekbars){
            seek.progress = sharedPreferences.getInt(seek.id.toString(),0)
            setColorSeek(seek)

            seek.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
                override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {}
                override fun onStartTrackingTouch(p0: SeekBar?) {}
                override fun onStopTrackingTouch(p0: SeekBar?) {

                    // store value to persistent storage
                    val myEdit = sharedPreferences.edit()
                    myEdit.putInt(seek.id.toString(), seek.progress)
                    myEdit.apply()
                    setColorSeek(seek);
                }
            })
        }
    }
}