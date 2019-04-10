package com.hashmap.sample.service

import com.hashmap.sample.model.Hotel

trait ManageHotel {
  def addHotel(hotel:Hotel):Unit
  def removeHotel(hotelName:String):Unit
}
