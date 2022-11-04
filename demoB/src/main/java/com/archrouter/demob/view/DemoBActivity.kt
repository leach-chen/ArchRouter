package com.archrouter.demob.contents.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.archrouter.arch.annotation.RouterUri
import com.archrouter.demob.R
import com.archrouter.provider.constants.ConstantsUri
import com.archrouter.router.Router

@RouterUri(path = [ConstantsUri.PATH_DEMOB])
class DemoBActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demob)

        if (this.intent?.extras?.getString("testValue") != null) {
            Toast.makeText(this, this.intent?.extras?.getString("testValue"), Toast.LENGTH_SHORT).show()
        }
    }

    fun goFun1(view: View) {
        setResult(100, Intent().putExtra("testValue","DemoB的回传的数据"))
        this.finish()
    }
}