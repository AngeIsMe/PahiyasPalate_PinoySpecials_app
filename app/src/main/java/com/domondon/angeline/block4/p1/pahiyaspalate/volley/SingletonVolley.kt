package com.domondon.angeline.block4.p1.pahiyaspalate.volley

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

object SingletonVolley {
    private var requestQueue: RequestQueue? = null

    fun getRequestQueue(context: Context): RequestQueue {
        if (requestQueue == null) {
            synchronized(SingletonVolley::class.java) {
                if (requestQueue == null) {
                    requestQueue = Volley.newRequestQueue(context.applicationContext)
                }
            }
        }
        return requestQueue!!
    }
}