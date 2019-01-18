package com.ducks.sungwon.phundemo.api.rest

import com.google.gson.Gson

class GsonHolder{
    companion object {
        var instance: GsonHolder = GsonHolder()
            private set
    }

    private var gson: Gson? = null

    init {
        gson = Gson()
    }

    fun get(): Gson? = gson
}