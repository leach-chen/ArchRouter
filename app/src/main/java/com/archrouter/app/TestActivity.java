package com.archrouter.app;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.archrouter.arch.annotation.RouterUri;

@RouterUri(path = "/TestActivity")
public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
    }
}