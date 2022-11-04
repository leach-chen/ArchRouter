package com.archrouter.demob.contents.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.archrouter.arch.annotation.RouterUri
import com.archrouter.demob.R
import com.archrouter.provider.constants.ConstantsUri

@RouterUri(path = [ConstantsUri.PATH_DEMOB])
class DemoBActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demob)
    }
}