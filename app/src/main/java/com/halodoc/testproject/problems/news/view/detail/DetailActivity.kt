package com.halodoc.testproject.problems.news.view.detail

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.halodoc.testproject.R
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val url = intent.getStringExtra("url");

        webview.loadUrl(url)


    }
}
