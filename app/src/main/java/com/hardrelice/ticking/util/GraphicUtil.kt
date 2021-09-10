package com.hardrelice.ticking.util

import android.content.res.Resources

object GraphicUtil {
    fun getStatusBarHeight(resources: Resources):Int{
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        return resources.getDimensionPixelSize(resourceId)
    }
}