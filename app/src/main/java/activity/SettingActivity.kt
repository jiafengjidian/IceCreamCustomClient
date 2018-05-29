package activity

import android.os.Bundle

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import com.hontech.icecreamcustomclient.R
import data.IceCreamManager
import fragment.SettingFragment


class SettingActivity : AppCompatActivity(), ViewPager.OnPageChangeListener
{
    private val mButtonUp: Button by lazy { findViewById<Button>(R.id.id_setting_button_up) }
    private val mButtonDown: Button by lazy { findViewById<Button>(R.id.id_setting_button_down) }
    private val mViewPager: ViewPager by lazy { findViewById<ViewPager>(R.id.id_setting_view_pager) }
    private val mButtonSave: Button by lazy { findViewById<Button>(R.id.id_setting_button_save) }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        initViewPager()
    }

    private fun initViewPager()
    {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        mViewPager.adapter = adapter
        mViewPager.offscreenPageLimit = 3
        mViewPager.addOnPageChangeListener(this)
        mButtonUp.setOnClickListener {
            val index = mViewPager.currentItem
            if (index <= 0) {
                return@setOnClickListener
            }
            mViewPager.currentItem = index - 1
        }
        mButtonDown.setOnClickListener {
            val index = mViewPager.currentItem
            if (index >= 2 ) {
                return@setOnClickListener
            }
            mViewPager.currentItem = index + 1
        }
        mButtonUp.isEnabled = false
        mButtonDown.isEnabled = true
        mButtonUp.setBackgroundResource(R.drawable.shape_setting_disable_button)
        mButtonDown.setBackgroundResource(R.drawable.selector_setting_button)

        mButtonSave.setOnClickListener {
            IceCreamManager.instance.save()
            finish()
        }
    }

    override fun onPageScrollStateChanged(state: Int)
    {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int)
    {
    }

    override fun onPageSelected(position: Int)
    {
        when (position)
        {
            0 -> {
                mButtonUp.isEnabled = false
                mButtonDown.isEnabled = true
                mButtonUp.setBackgroundResource(R.drawable.shape_setting_disable_button)
                mButtonDown.setBackgroundResource(R.drawable.selector_setting_button)
            }

            1 -> {
                mButtonUp.isEnabled = true
                mButtonDown.isEnabled = true
                mButtonUp.setBackgroundResource(R.drawable.selector_setting_button)
                mButtonDown.setBackgroundResource(R.drawable.selector_setting_button)
            }

            2 -> {
                mButtonDown.isEnabled = false
                mButtonUp.isEnabled = true
                mButtonUp.setBackgroundResource(R.drawable.selector_setting_button)
                mButtonDown.setBackgroundResource(R.drawable.shape_setting_disable_button)
            }
        }
    }

    private class ViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager)
    {
        private val mFragments = arrayOf(SettingFragment(), SettingFragment(), SettingFragment())

        init
        {
            mFragments.forEachIndexed { index, fragment ->
                val arg = Bundle()
                arg.putInt(SettingFragment.PAGE_INDEX_KEY, index)
                fragment.arguments = arg
            }
        }

        override fun getItem(position: Int) = mFragments[position]

        override fun getCount() = 3
    }

}