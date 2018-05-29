package data

import android.content.Context
import app.App
import util.log

class IceCreamManager
{
    companion object
    {
        private const val ICE_BASE_KEY = "ice.base.key"
        val instance = IceCreamManager()
    }

    private val iceCreamList = ArrayList<IceCream>()
    private val noEmptyIceCreamList = ArrayList<IceCream>()

    var selectIceCreamIndex: Int = 0

    var noEmptyIceCreamNumber = 0

    init
    {
        readData(18)
        noEmptyIceCreamNumber = getIceCreamNumber()
    }

    fun get(index: Int) = iceCreamList[index]

    fun getNoEmpty(index: Int) = noEmptyIceCreamList[index]

    private fun getIceCreamNumber(): Int
    {
        var size = 0
        noEmptyIceCreamList.clear()
        iceCreamList.forEach {
            log("价格${it.price}")
            if (it.price.toDouble() > 0.0) {
                size ++
                noEmptyIceCreamList.add(it)
            }
        }
        return size
    }

    fun save()
    {
        iceCreamList.forEachIndexed { index, iceCream ->
            iceCream.save("$ICE_BASE_KEY-$index")
        }
        noEmptyIceCreamNumber = getIceCreamNumber()
    }

    fun save(index: Int)
    {
        val info = iceCreamList[index]
        info.save("$ICE_BASE_KEY-$index")
    }

    private fun readData(number: Int)
    {
        for (i in 0 until number)
        {
            val info = IceCream.readIceCream("$ICE_BASE_KEY-$i")
            iceCreamList.add(info)
        }
    }

    private constructor()

}