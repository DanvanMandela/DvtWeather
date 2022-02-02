package com.example.dvtweather.util.provider

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.google.android.gms.common.internal.Preconditions
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class ResourceProvider @Inject constructor(@ApplicationContext context: Context) :
    BaseResourceProvider {
    private var mContext: Context = Preconditions.checkNotNull(context, "context cannot be null")

    override fun getString(@StringRes id: Int): String {
        return mContext.getString(id)
    }

    override fun getString(@StringRes resId: Int, vararg formatArgs: Any?): String {
        return mContext.getString(resId, *formatArgs)
    }

    override fun getDrawable(resId: Int): Drawable{
        return ContextCompat.getDrawable(mContext,resId)!!
    }



}