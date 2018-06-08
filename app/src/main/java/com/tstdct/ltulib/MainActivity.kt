package com.tstdct.ltulib

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.tstdct.lib.DensityUtil
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        show.text="屏幕宽${DensityUtil.getScreenWidth(this)} 高度：${DensityUtil.getScreenHeight(this)}"
    }
}
