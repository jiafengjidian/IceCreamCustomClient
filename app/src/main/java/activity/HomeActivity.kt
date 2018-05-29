package activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MotionEvent
import android.widget.Button
import android.widget.PopupWindow
import app.App
import com.hontech.icecreamcustomclient.R
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import popup.PasswordPopupWindow
import util.log
import java.util.concurrent.TimeUnit


class HomeActivity : AppCompatActivity()
{
    private var mLastTouchTime: Long = 0
    private var mTouchSteps = 0

    private val mButtonBuy: Button by lazy { findViewById<Button>(R.id.id_home_button_buy) }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        mButtonBuy.setOnClickListener {
            val intent = Intent(this@HomeActivity, IceCreamActivity::class.java)
            startActivity(intent)
        }
    }

    private fun onEnterPassword()
    {
        PasswordPopupWindow.instance.show(mButtonBuy) {
            val intent = Intent(this@HomeActivity, SettingActivity::class.java)
            startActivity(intent)
        }
    }

    private fun onTouch(event: MotionEvent)
    {
        val t = System.currentTimeMillis()
        if ((mTouchSteps != 0) && ((t - mLastTouchTime) > 800))
        {
            mTouchSteps = 0
        }
        val x = event.x.toInt()
        val y = event.y.toInt()
        when (mTouchSteps)
        {
            0 -> {
                if (!App.touchRectLeft.contains(x, y))
                {
                    mTouchSteps = 0
                    mLastTouchTime = 0
                    return
                }
                mTouchSteps ++
            }

            1 -> {
                if (!App.touchRectRight.contains(x, y))
                {
                    mLastTouchTime = 0
                    mTouchSteps = 0
                    return
                }
                mTouchSteps ++
            }

            2 -> {
                if (!App.touchRectLeft.contains(x, y))
                {
                    mLastTouchTime = 0
                    mTouchSteps = 0
                    return
                }
                mTouchSteps ++
            }

            3 -> {
                if (!App.touchRectRight.contains(x, y))
                {
                    mLastTouchTime = 0
                    mTouchSteps = 0
                    return
                }
                mTouchSteps = 0
                onEnterPassword()
                return
            }
        }
        mLastTouchTime = t
    }


    override fun onTouchEvent(event: MotionEvent): Boolean
    {
        if (event.action == MotionEvent.ACTION_DOWN)
        {
            onTouch(event)
        }
        return super.onTouchEvent(event)
    }
}