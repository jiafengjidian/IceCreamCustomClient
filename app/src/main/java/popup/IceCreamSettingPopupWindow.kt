package popup

import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import app.App
import com.hontech.icecreamcustomclient.R
import data.IceCreamManager

class IceCreamSettingPopupWindow
{
    companion object
    {
        val instance: IceCreamSettingPopupWindow by lazy { IceCreamSettingPopupWindow() }
    }

    private val mWidth = App.context.resources.getDimension(R.dimen.x600).toInt()
    private val mHeight = App.context.resources.getDimension(R.dimen.x1030).toInt()
    private val mView = LayoutInflater.from(App.context).inflate(R.layout.popup_setting, null)
    private val mEditGoodsType = mView.findViewById<TextView>(R.id.id_popup_ice_cream_setting_edit_text_goodsType)
    private val mEditName = mView.findViewById<TextView>(R.id.id_popup_ice_cream_setting_edit_text_name)
    private val mEditPrice = mView.findViewById<TextView>(R.id.id_popup_ice_cream_setting_edit_text_price)
    private val mEditNumber = mView.findViewById<TextView>(R.id.id_popup_ice_cream_setting_edit_text_number)
    private val mImageName = mView.findViewById<ImageView>(R.id.id_popup_ice_cream_setting_name_image_clear)
    private val mImagePrice = mView.findViewById<ImageView>(R.id.id_popup_ice_cream_setting_price_image_clear)
    private val mImageNumber = mView.findViewById<ImageView>(R.id.id_popup_ice_cream_setting_image_number_clear)
    private val mImageGoodsType = mView.findViewById<ImageView>(R.id.id_popup_ice_cream_setting_image_goodsType_clear)
    private val mButtonCancel = mView.findViewById<Button>(R.id.id_popup_ice_cream_setting_button_cancel)
    private val mButtonOk = mView.findViewById<Button>(R.id.id_popup_ice_cream_setting_button_ok)

    init
    {
        mEditName.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable)
            {
                if (s.isEmpty()) {
                    mImageName.visibility = View.INVISIBLE
                } else {
                    mImageName.visibility = View.VISIBLE
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int)
            {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int)
            {
            }
        })

        mEditPrice.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable)
            {
                if (s.isEmpty()) {
                    mImagePrice.visibility = View.INVISIBLE
                } else {
                    mImagePrice.visibility = View.VISIBLE
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int)
            {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int)
            {
            }
        })

        mEditNumber.addTextChangedListener(object: TextWatcher {

            override fun afterTextChanged(s: Editable)
            {
                if (s.isEmpty()) {
                    mImageNumber.visibility = View.INVISIBLE
                } else {
                    mImageNumber.visibility = View.VISIBLE
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int)
            {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int)
            {
            }
        })

        mEditGoodsType.addTextChangedListener(object: TextWatcher {

            override fun afterTextChanged(s: Editable)
            {
                if (s.isEmpty()) {
                    mImageGoodsType.visibility = View.INVISIBLE
                } else {
                    mImageGoodsType.visibility = View.VISIBLE
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int)
            {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int)
            {
            }
        })
    }

    fun show(view: View, index: Int, onOk: () -> Unit)
    {
        val group = mView.parent
        if (group != null)
        {
            (group as ViewGroup).removeAllViews()
        }
        val info = IceCreamManager.instance.get(index)
        mEditName.text = ""
        mEditNumber.text = ""
        mEditPrice.text = ""
        mEditGoodsType.text = ""
        mEditNumber.hint = if (info.number == 0) "请输入数量" else info.number.toString()
        mEditPrice.hint = if (info.price.isEmpty()) "请输入价格" else info.price
        mEditName.hint = if (info.name.isEmpty()) "请输入名称" else info.name
        mEditGoodsType.hint = if (info.goodsType.isEmpty()) "请输入货道" else info.goodsType

        val popupWindow = PopupWindow(mView, mWidth, mHeight, true)
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)

        mButtonCancel.setOnClickListener { popupWindow.dismiss() }
        mButtonOk.setOnClickListener {

            if (mEditName.text.isNotEmpty())
            {
                info.name = mEditName.text.toString()
            }
            if (mEditPrice.text.isNotEmpty())
            {
                info.price= mEditPrice.text.toString()
            }
            if (mEditNumber.text.isNotEmpty())
            {
                info.number = mEditNumber.text.toString().toInt()
            }
            if (mEditGoodsType.text.isNotEmpty())
            {
                info.goodsType = mEditGoodsType.text.toString()
            }
            val msg = "${info.name},${info.price},${info.number},${info.goodsType}"
            Toast.makeText(App.context, "设置成功:$msg", Toast.LENGTH_SHORT).show()
            onOk()
            popupWindow.dismiss()
        }
    }

    private constructor()
}
