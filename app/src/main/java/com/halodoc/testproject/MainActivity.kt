package com.halodoc.testproject

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.halodoc.testproject.problems.news.view.NewsActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonAndroid.setOnClickListener {
            val intent = Intent(this,NewsActivity::class.java)
            intent.putExtra("category","android")
            startActivity(intent)
        }

        buttonHealth.setOnClickListener {
            val intent = Intent(this,NewsActivity::class.java)
            intent.putExtra("category","health")
            startActivity(intent)
        }

        buttonsport.setOnClickListener {
            val intent = Intent(this,NewsActivity::class.java)
            intent.putExtra("category","sport")
            startActivity(intent)
        }

        buttonBooliwood.setOnClickListener {
            val intent = Intent(this,NewsActivity::class.java)
            intent.putExtra("category","bollywood")
            startActivity(intent)
        }


    }

}
