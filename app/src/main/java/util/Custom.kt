package util

import android.util.Log

inline fun log(msg: String)
{
    Log.e("调试信息", msg)
}