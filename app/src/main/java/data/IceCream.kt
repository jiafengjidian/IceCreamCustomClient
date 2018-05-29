package data

import android.content.Context
import android.content.SharedPreferences
import app.App


data class IceCream(var name: String, var goodsType: String, var price: String, var imageMinId: Int, var imageMaxId: Int, var number: Int)
{
    companion object
    {
        fun readIceCream(keyName: String): IceCream
        {
            val shared = App.context.getSharedPreferences(keyName, Context.MODE_PRIVATE)
            val name = shared.getString("$keyName-name", "")
            val goodsType = shared.getString("$keyName-goodsType", "")
            val price = shared.getString("$keyName-price", "0.0")
            val minId = shared.getInt("$keyName-imageMinId", 0)
            val maxId = shared.getInt("$keyName-imageMaxId", 0)
            val number = shared.getInt("$keyName-number", 0)

            return IceCream(name, goodsType, if (price.isEmpty()) "0.0" else price, minId, maxId, number)
        }
    }

    constructor(name: String) : this(name, "1", "",1, 1, 1)

    fun save(keyName: String): Boolean
    {
        val shared = App.context.getSharedPreferences(keyName, Context.MODE_PRIVATE)
        return shared.edit().
                putString("$keyName-name", name).
                putString("$keyName-goodsType", goodsType).
                putString("$keyName-price", price).
                putInt("$keyName-imageMinId", imageMinId).
                putInt("$keyName-imageMaxId", imageMaxId).
                putInt("$keyName-number", number).
                commit()
    }
}



