package com.hashmap.sample.service

import com.hashmap.sample.model.Request

trait HotelService {
 def findCheapestHotel(request:Request):String
}
