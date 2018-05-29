package activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import com.hontech.icecreamcustomclient.R
import data.IceCreamManager
import fragment.IceCreamFragment
import util.log

class IceCreamActivity : AppCompatActivity(), ViewPager.OnPageChangeListener
{
    companion object
    {
        const val IceCreamPageIndex = "ice.cream.page.index"
    }

    private val mViewPager: ViewPager by lazy { findViewById<ViewPager>(R.id.id_ice_cream_view_pager) }
    private val mButtonUp: Button by lazy { findViewById<Button>(R.id.id_setting_button_up) }
    private val mButtonDown: Button by lazy { findViewById<Button>(R.id.id_setting_button_down) }

    private val mPagerNumber = if (IceCreamManager.instance.noEmptyIceCreamNumber <= 0) 1 else (IceCreamManager.instance.noEmptyIceCreamNumber + 5) / 6

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ice_cream)
        log("page数量:$mPagerNumber")
        initUi()
    }

    private fun initUi()
    {
        mViewPager.addOnPageChangeListener(this)
        mViewPager.offscreenPageLimit = mPagerNumber
        val adapter = ViewPagerAdapter(supportFragmentManager, mPagerNumber)
        mViewPager.adapter = adapter

        mButtonUp.setOnClickListener {

            val index = mViewPager.currentItem
            if (index <= 0) {
                return@setOnClickListener
            }
            mViewPager.currentItem = index - 1
        }

        mButtonDown.setOnClickListener {

            val index = mViewPager.currentItem
            if (index >= mPagerNumber) {
                return@setOnClickListener
            }
            mViewPager.currentItem = index + 1
        }

        if (mPagerNumber == 1) {
            mButtonUp.isEnabled = false
            mButtonDown.isEnabled = false
            mButtonDown.setBackgroundResource(R.drawable.shape_setting_disable_button)
            mButtonUp.setBackgroundResource(R.drawable.shape_setting_disable_button)
        }
        else
        {
            mButtonUp.isEnabled = false
            mButtonDown.isEnabled = true
            mButtonDown.setBackgroundResource(R.drawable.selector_setting_button)
            mButtonUp.setBackgroundResource(R.drawable.shape_setting_disable_button)
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
        if (position == 0)
        {
            mButtonUp.isEnabled = false
            mButtonDown.isEnabled = true
            mButtonUp.setBackgroundResource(R.drawable.shape_setting_disable_button)
            mButtonDown.setBackgroundResource(R.drawable.selector_setting_button)
        }
        else if (position == (mPagerNumber - 1))
        {
            mButtonUp.isEnabled = true
            mButtonDown.isEnabled = false
            mButtonDown.setBackgroundResource(R.drawable.shape_setting_disable_button)
            mButtonUp.setBackgroundResource(R.drawable.selector_setting_button)
        }
        else
        {
            mButtonUp.isEnabled = true
            mButtonDown.isEnabled = true
            mButtonUp.setBackgroundResource(R.drawable.selector_setting_button)
            mButtonDown.setBackgroundResource(R.drawable.selector_setting_button)
        }
    }

    private class ViewPagerAdapter(manager: FragmentManager, val pageNumber: Int) : FragmentPagerAdapter(manager)
    {
        private val mFragments = Array(pageNumber) {
            IceCreamFragment()
        }

        init
        {
            mFragments.forEachIndexed { index, fragment ->
                val arg = Bundle()
                arg.putInt(IceCreamPageIndex, index)
                fragment.arguments = arg
            }
        }

        override fun getCount() = pageNumber

        override fun getItem(position: Int) = mFragments[position]
    }

}