package com.ducks.sungwon.phundemo.manager

import android.arch.lifecycle.ViewModel
import android.util.Log
import com.ducks.sungwon.phundemo.api.response.RebelScumResponse
import com.ducks.sungwon.phundemo.api.rest.Rest
import com.ducks.sungwon.phundemo.model.RebelScum
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RebelScumManager : ViewModel() {

    val mRebelList = mutableListOf<RebelScum>()
    val TAG = "RebelScumManager"

    fun getRebelList(callback: (Boolean) -> Unit){
        val call = Rest.instance.getCaller().getRebelScum()
        call.enqueue(
                object : Callback<ArrayList<RebelScumResponse>>{
                    override fun onResponse(call: Call<ArrayList<RebelScumResponse>>?, response: Response<ArrayList<RebelScumResponse>>?) {
                        response?.let {
                            it.body()?.let {
                                for(entry in it.iterator()){
                                    mRebelList.add(RebelScum(entry))
                                }
                            } ?: callback(false)
                        }
                        callback(true)
                    }

                    override fun onFailure(call: Call<ArrayList<RebelScumResponse>>?, t: Throwable?) {
                        Log.e(TAG, "getRebelList() Failed")
                        callback(false)
                    }
                }
        )
    }

}