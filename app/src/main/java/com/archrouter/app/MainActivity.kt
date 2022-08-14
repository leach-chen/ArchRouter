package com.archrouter.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.archrouter.arch.annotation.Print

class MainActivity : AppCompatActivity() {

    @Print
    var name = ""

    @Print
    var age = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        PrintUtil.`print$$age`()
    }
}