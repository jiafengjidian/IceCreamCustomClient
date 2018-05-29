package popup

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import app.App
import com.hontech.icecreamcustomclient.R

class PasswordPopupWindow : View.OnClickListener
{
    companion object
    {
        val instance: PasswordPopupWindow by lazy { PasswordPopupWindow() }
    }

    private val mWidth = App.context.resources.getDimension(R.dimen.x800).toInt()
    private val mHeight = App.context.resources.getDimension(R.dimen.y1300).toInt()
    private val mView = LayoutInflater.from(App.context).inflate(R.layout.popup_ice_cream_setting, null)
    private val mTextInput = mView.findViewById<TextView>(R.id.id_popup_setting_text_view_input)
    private val mImageView = mView.findViewById<ImageView>(R.id.id_popup_setting_image_view_enter)
    private val mButtonArray = Array<Button>(10) {
        when (it)
        {
            0 -> mView.findViewById(R.id.id_popup_setting_button_0)
            1 -> mView.findViewById(R.id.id_popup_setting_button_1)
            2 -> mView.findViewById(R.id.id_popup_setting_button_2)
            3 -> mView.findViewById(R.id.id_popup_setting_button_3)
            4 -> mView.findViewById(R.id.id_popup_setting_button_4)
            5 -> mView.findViewById(R.id.id_popup_setting_button_5)
            6 -> mView.findViewById(R.id.id_popup_setting_button_6)
            7 -> mView.findViewById(R.id.id_popup_setting_button_7)
            8 -> mView.findViewById(R.id.id_popup_setting_button_8)
            else -> mView.findViewById(R.id.id_popup_setting_button_9)
        }
    }
    private val mButtonClear = mView.findViewById<Button>(R.id.id_popup_setting_button_clear)

    init
    {
        for (btn in mButtonArray)
        {
            btn.setOnClickListener(this)
        }
        mButtonClear.setOnClickListener(this)

    }

    override fun onClick(v: View)
    {
        if (v.id == R.id.id_popup_setting_button_clear)
        {
            mTextInput.text = ""
            mImageView.visibility = View.INVISIBLE
            return
        }
        mTextInput.append((v as Button).text)
        mImageView.visibility = View.VISIBLE
    }

    fun show(view: View, listener: (String) -> Unit)
    {
        val group = mView.parent
        if (group != null)
        {
            (group as ViewGroup).removeAllViews()
        }
        val popupWindow = PopupWindow(mView, mWidth, mHeight, true)
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)
        mImageView.setOnClickListener {
            listener(mTextInput.text.toString())
            popupWindow.dismiss()
        }
    }

}

