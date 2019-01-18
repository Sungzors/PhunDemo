package com.ducks.sungwon.phundemo.model

import com.ducks.sungwon.phundemo.api.response.RebelScumResponse
import java.util.*

class RebelScum (response: RebelScumResponse){


    var id : Int
    var description : String
    var title : String
    var timestamp : Date
    var image : String? = null
    var phone : String? = null
    var date : Date
    var locationline1 : String
    var locationline2 : String

    init {
        id = response.id!!
        description = response.description!!
        title = response.title!!
        timestamp = response.date!!
        response.image?.let {
            image = it
        }
        response.phone?.let {
            phone = it
        }
        date = response.date!!
        locationline1 = response.locationline1!!
        locationline2 = response.locationline2!!
    }
}