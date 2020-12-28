package com.example.finalproject.Main

import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.MediaController
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.example.finalproject.Main.Fragment.Fragment0
import com.example.finalproject.Main.Fragment.Fragment1
import com.example.finalproject.Main.Fragment.Fragment2
import com.example.finalproject.Main.Fragment.Fragment3
import com.example.finalproject.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import kotlinx.android.synthetic.main.activity_main_page.*
import kotlinx.android.synthetic.main.items.*


class MainPage : AppCompatActivity() {
    val Lfragments: Array<Fragment> = arrayOf<Fragment>(
        Fragment0(),
        Fragment1(),
        Fragment2(),
        Fragment3()
    )
    val Ltitles = arrayOf("可回收物", "湿垃圾", "干垃圾", "有害垃圾")

    //未选中图片
    val Limg = intArrayOf(
        R.drawable.trash1,
        R.drawable.trash2,
        R.drawable.trash3,
        R.drawable.trash4
    )

    private val ItemWhat = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_page)
        supportActionBar?.hide()

        titleText.text = "垃圾分类"
        button1.setImageResource(R.drawable.pic12)
        text1.textSize = 20F
        text1.typeface = Typeface.defaultFromStyle(Typeface.BOLD)

        val uri = Uri.parse("android.resource://$packageName/${R.raw.video}")
        videoView.setVideoURI(uri)
        videoView.setMediaController(MediaController(this))
        videoView.requestFocus()

        viewpager.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment {
                return Lfragments[position]
            }

            override fun getCount(): Int {
                return Lfragments.size
            }

            override fun getPageTitle(position: Int): CharSequence? {
                return Ltitles[position]
            }
        }

        //绑定
        tabs2.setupWithViewPager(viewpager)

        //设置默认选中页，宏定义
        tabs2.getTabAt(ItemWhat)?.select()
        viewpager.offscreenPageLimit = 3 //设置向左和向右都缓存的页面个数

        //初始化菜单栏显示
        //初始化菜单栏显示
        for (i in 0 until tabs2.tabCount) {
            //寻找到控件
            val view: View = LayoutInflater.from(this@MainPage).inflate(R.layout.items, null)
            val mTabView = view.findViewById<View>(R.id.item_view) as LinearLayout
            val mTabText = view.findViewById<View>(R.id.item_text) as TextView
            val mTabIcon = view.findViewById<View>(R.id.item_img) as ImageView

            mTabText.text = Ltitles[i]
            mTabIcon.setImageResource(Limg[i])

            //设置不可点击
            mTabView.isClickable = false

            //更改选中项样式
            if (i === ItemWhat) {
                mTabIcon.setImageResource(Limg[i])
                mTabText.setTextColor(ContextCompat.getColor(this, R.color.purple_200))
            }

            //设置样式
            tabs2.getTabAt(i)?.customView = view
        }

        tabs2.setOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                //选中时进入，改变样式
                ItemSelect(tab)
                //onTabselected方法里面调用了viewPager的setCurrentItem 所以要想自定义OnTabSelectedListener，也加上mViewPager.setCurrentItem(tab.getPosition())就可以了
                viewpager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                //未选中进入，改变样式
                ItemNoSelect(tab)
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
                //重新选中
            }
        })

    }

    override fun onDestroy() {
        super.onDestroy()
        videoView.suspend()
    }

    //某个项选中，改变其样式
    private fun ItemSelect(tab: TabLayout.Tab) {
        val customView = tab.customView
        val tabText = customView!!.findViewById<View>(R.id.item_text) as TextView
        val tabIcon: ImageView = customView.findViewById<View>(R.id.item_img) as ImageView
        tabText.setTextColor(ContextCompat.getColor(this, R.color.purple_200))
        val stitle = tabText.text.toString()
        for (i in Ltitles.indices) {
            if (Ltitles[i] == stitle) {
                //Toast.makeText(MainActivity.this,"xxx+"+i,Toast.LENGTH_SHORT).show();
                tabIcon.setImageResource(Limg[i])
            }
        }
    }

    //某个项非选中，改变其样式
    private fun ItemNoSelect(tab: TabLayout.Tab) {
        val customView = tab.customView
        val tabText = customView!!.findViewById<View>(R.id.item_text) as TextView
        val tabIcon: ImageView = customView.findViewById<View>(R.id.item_img) as ImageView
        tabText.setTextColor(ContextCompat.getColor(this, R.color.black))
        val stitle = tabText.text.toString()
        for (i in Ltitles.indices) {
            if (Ltitles[i] == stitle) {
                tabIcon.setImageResource(Limg[i])
            }
        }
    }
}