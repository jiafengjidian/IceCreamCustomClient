package fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.hontech.icecreamcustomclient.R

import data.IceCreamManager
import util.log
import activity.IceCreamActivity
import android.graphics.Rect
import android.support.v7.widget.GridLayoutManager
import app.App


class IceCreamFragment : Fragment()
{
    private lateinit var mRecyclerView: RecyclerView
    private var mPageIndex = 0

    override fun onAttach(context: Context?)
    {
        super.onAttach(context)
        val arg = arguments!!
        mPageIndex = arg.getInt(IceCreamActivity.IceCreamPageIndex)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        val view = inflater.inflate(R.layout.fragment_setting, null)
        mRecyclerView = view.findViewById(R.id.id_fragment_setting_recycler_view)
        initUi()
        return view
    }

    private fun initUi()
    {
        val adapter = RecyclerViewAdapter(mPageIndex)
        mRecyclerView.adapter = adapter
        mRecyclerView.layoutManager = GridLayoutManager(context, 2)
        mRecyclerView.addItemDecoration(RecyclerViewItemDecoration())
    }

    private class RecyclerViewItemDecoration : RecyclerView.ItemDecoration()
    {
        companion object
        {
            private val hSize = App.context.resources.getDimension(R.dimen.x60).toInt()
            private val vSize = App.context.resources.getDimension(R.dimen.y55).toInt()
        }

        override fun getItemOffsets(outRect: Rect, itemPosition: Int, parent: RecyclerView?)
        {
            super.getItemOffsets(outRect, itemPosition, parent)
            val col = itemPosition % 2
            if (itemPosition < 2) {
                outRect.top = vSize
            }
            outRect.bottom = vSize
            outRect.left = hSize - col * hSize / 2
        }
    }

    private class RecyclerViewItem(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        private val mImageView = itemView.findViewById<ImageView>(R.id.id_item_ice_cream_image_view)
        private val mTextName = itemView.findViewById<TextView>(R.id.id_item_ice_cream_text_view_name)
        private val mTextPrice = itemView.findViewById<TextView>(R.id.id_item_ice_cream_text_view_price)
        private val mLayout = itemView.findViewById<LinearLayout>(R.id.id_item_ice_cream_linear_layout)

        fun set(index: Int)
        {
            val info = IceCreamManager.instance.getNoEmpty(index)
            mImageView.setImageResource(R.drawable.ic_cream_2)
            mTextName.text = info.name
            mTextPrice.text = "${info.price}元"
            mLayout.setOnClickListener {
                log(index.toString())
            }
        }

    }

    private class RecyclerViewAdapter(val pageIndex: Int) : RecyclerView.Adapter<RecyclerViewItem>()
    {
        override fun getItemCount(): Int
        {
            val number = IceCreamManager.instance.noEmptyIceCreamNumber
            return if ((number - pageIndex * 6) > 6) {
                6
            } else {
                number - pageIndex * 6
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewItem
        {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ice_cream, parent, false)
            return RecyclerViewItem(view)
        }

        override fun onBindViewHolder(holder: RecyclerViewItem, position: Int)
        {
            holder.set(position)
        }
    }
}
