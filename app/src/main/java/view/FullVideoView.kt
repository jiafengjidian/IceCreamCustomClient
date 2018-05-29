package view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.VideoView

class FullVideoView : VideoView
{
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, desStyleAttr: Int) : super(context, attrs, desStyleAttr)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val w = View.getDefaultSize(widthMeasureSpec, 0)
        val h = View.getDefaultSize(heightMeasureSpec, 0)
        setMeasuredDimension(w, h)
    }
}