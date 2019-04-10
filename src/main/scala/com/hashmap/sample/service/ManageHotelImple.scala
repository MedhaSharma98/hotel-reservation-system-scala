package com.hashmap.sample.service
import com.hashmap.sample.model.{Database, Hotel}

class ManageHotelImple extends ManageHotel {
  override def addHotel(hotel: Hotel): Unit = {
    Database.hotelList=   hotel::Database.hotelList}

  override def removeHotel(hotelName: String): Unit = {
    Database.hotelList=Database.hotelList.filterNot((i:Hotel)=>i.name.equalsIgnoreCase(hotelName))
  }
}
