package com.yoy.turnpageview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bg1 = layoutInflater.inflate(R.layout.layout_bg1, null)
        val bg2 = layoutInflater.inflate(R.layout.layout_bg2, null)
        val bg3 = layoutInflater.inflate(R.layout.layout_bg3, null)
        mPageView.pageLoader.addPage(bg1)
        mPageView.pageLoader.addPage(bg2)
        mPageView.pageLoader.addPage(bg3)
        mPageView.pageLoader.openChapter()
    }
}
