package fragment

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import app.App
import com.hontech.icecreamcustomclient.R
import data.IceCreamManager
import popup.IceCreamSettingPopupWindow
import util.log

class SettingFragment : Fragment()
{
    companion object
    {
        const val PAGE_INDEX_KEY = "page.index.key"
    }

    private var mIndex = -1

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mRecyclerViewAdapter: RecyclerViewAdapter

    override fun onAttach(context: Context?)
    {
        super.onAttach(context)
        val arg = arguments
        mIndex = arg!!.getInt(PAGE_INDEX_KEY, -1)
        log(mIndex.toString())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        val view = inflater.inflate(R.layout.fragment_setting, null)
        initUi(view)
        return view
    }

    private fun onItemClick(index: Int)
    {
        IceCreamSettingPopupWindow.instance.show(mRecyclerView, index) {
            mRecyclerViewAdapter.notifyDataSetChanged()
            IceCreamManager.instance.save()
        }
    }

    private fun initUi(view: View)
    {
        mRecyclerView = view.findViewById(R.id.id_fragment_setting_recycler_view)
        mRecyclerView.addItemDecoration(RecyclerViewItemDecoration())
        mRecyclerView.layoutManager = GridLayoutManager(context, 2)
        mRecyclerViewAdapter = RecyclerViewAdapter(mIndex, ::onItemClick)
        mRecyclerView.adapter = mRecyclerViewAdapter
    }

    private class RecyclerViewItem(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        companion object
        {
            private val images = arrayOf(R.drawable.ic_cream_1, R.drawable.ic_cream_2, R.drawable.ic_cream_3,
                    R.drawable.ic_cream_4, R.drawable.ic_cream_5, R.drawable.ic_cream_6, R.drawable.ic_cream_7, R.drawable.ic_cream_8)
        }

        private val mLinearLayout = itemView.findViewById<LinearLayout>(R.id.id_item_setting_layout)
        private val mImageView = itemView.findViewById<ImageView>(R.id.id_item_setting_image_view)
        private val mTextName = itemView.findViewById<TextView>(R.id.id_item_setting_text_view_name)
        private val mTextNumber = itemView.findViewById<TextView>(R.id.id_item_setting_text_view_number)
        private val mTextPrice = itemView.findViewById<TextView>(R.id.id_item_setting_text_view_price)
        private val mTextGoodsType = itemView.findViewById<TextView>(R.id.id_item_setting_text_view_goodsType)

        fun set(index: Int, onItemClick: (Int) -> Unit)
        {
            mImageView.setImageResource(images[index % 8])
            val info = IceCreamManager.instance.get(index)
            mTextName.text = info.name
            mTextNumber.text = "库存:${info.number}"
            mTextPrice.text = "价格:${info.price}"
            mTextGoodsType.text = "货道:${info.goodsType}"
            mLinearLayout.setOnClickListener {
                onItemClick(index)
            }
        }

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

    private class RecyclerViewAdapter(val index: Int, val onItemClick: (Int) -> Unit) : RecyclerView.Adapter<RecyclerViewItem>()
    {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewItem
        {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_setting, parent, false)
            return RecyclerViewItem(view)
        }

        override fun onBindViewHolder(holder: RecyclerViewItem, position: Int)
        {
            holder.set(position + index * 6, onItemClick)
        }

        override fun getItemCount() = 6
    }

}