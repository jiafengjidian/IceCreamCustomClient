package app

import android.app.Application
import android.content.Context
import android.graphics.Rect
import util.log

class App : Application()
{
    companion object
    {
        lateinit var context: Context
            private set

        var screenWidth: Int = 0
            private set
        var screenHeight: Int = 0
            private set
        val touchRectLeft = Rect()
        val touchRectRight = Rect()
    }

    override fun onCreate()
    {
        super.onCreate()
        context = applicationContext
        setScreenSize()
    }

    private fun setScreenSize()
    {
        screenWidth = resources.displayMetrics.widthPixels
        screenHeight = resources.displayMetrics.heightPixels
        log("屏幕宽度:$screenWidth, 屏幕高度:$screenHeight")
        touchRectLeft.set(0, 0, screenWidth / 4, screenHeight / 4)
        touchRectRight.set(screenWidth / 4 * 3, 0, screenWidth, screenHeight / 4)
        log(touchRectLeft.toString())
        log(touchRectRight.toString())
    }

}