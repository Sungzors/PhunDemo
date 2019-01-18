package com.ducks.sungwon.phundemo.api.rest

import com.ducks.sungwon.phundemo.api.response.RebelScumResponse
import retrofit2.Call
import retrofit2.http.GET

interface Caller {

    @GET("https://raw.githubusercontent.com/phunware-services/dev-interview-homework/master/feed.json")
    fun getRebelScum(): Call<ArrayList<RebelScumResponse>>

}