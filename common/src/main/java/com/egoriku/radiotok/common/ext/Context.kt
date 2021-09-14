package com.egoriku.radiotok.common.ext

import android.content.Context
import android.telephony.TelephonyManager
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources

val Context.telephonyManager: TelephonyManager
    get() = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

fun Context.drawableCompat(@DrawableRes resId: Int) = requireNotNull(
    AppCompatResources.getDrawable(this, resId)
)
