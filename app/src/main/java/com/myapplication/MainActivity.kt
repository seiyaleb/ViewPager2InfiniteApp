package com.myapplication

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import androidx.annotation.RequiresApi
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback

class MainActivity : AppCompatActivity() {

    private lateinit var viewPager2: ViewPager2
    private lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //ViewPager2の設定
        viewPager2 = findViewById(R.id.viewPager2)
        viewPagerAdapter = ViewPagerAdapter(this)
        viewPager2.apply {
            adapter = viewPagerAdapter
            setCurrentItem(1,false)
            offscreenPageLimit = 1
            registerOnPageChangeCallback(callBack)
        }
    }

    //全画面表示
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if(hasFocus) {
            window.decorView.windowInsetsController?.hide(
                WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars()
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewPager2.unregisterOnPageChangeCallback(callBack)
    }

    private val callBack = object : OnPageChangeCallback() {
        private var realPosition = -1
        override fun onPageScrollStateChanged(state: Int) {
            super.onPageScrollStateChanged(state)
            //最初と最終ページの場合、無限ループになるように該当ページへ移動
            if (state == ViewPager2.SCROLL_STATE_IDLE && realPosition >= 0) {
                viewPager2.setCurrentItem(realPosition, false)
                realPosition = -1
            }
        }
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            //最初と最終ページの場合、実際の位置を変数realPositionに代入
            when (position) {
                0 -> realPosition = viewPagerAdapter.getRealCount()
                viewPagerAdapter.getRealCount() + 1 -> realPosition = 1
            }
        }
    }
}