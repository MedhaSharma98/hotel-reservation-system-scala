package com.hashmap.sample.service

import java.util.{Calendar, Date, GregorianCalendar}

import com.hashmap.sample.model.{Database, Hotel, Request}
import com.hashmap.sample.utilties.{CustomerType, DayType}

class HotelServiceImple extends HotelService{

  override def findCheapestHotel(request: Request): String = {
    val dateArray = request.dates.split(" ")
    val dateMap=dateArray.map(x=>getWeekno(x)).groupBy(identity).mapValues(_.length)
    val hotelTotalMap=Database.hotelList.map(x=>x.name->gettotalPrice(x.name,request.customerType,dateMap)).toMap.toList.sortWith(_._2<_._2)
    var minHotelList=hotelTotalMap.filter(x=>x._2==hotelTotalMap.min._2)
    var minPriceHotelName= minHotelList.length match{
      case 0|1=>hotelTotalMap.min._1
      case _=> Database.hotelList.filter(x=>minHotelList.contains(x.name,hotelTotalMap.min._2)).map(x=>x.name->x.rating).toMap.toList.sortWith(_._2>_._2).head._1
    }
    minPriceHotelName
  }



  private def getWeekno(date:String):Int={
    val calendar = new GregorianCalendar()
      calendar.setTime(new Date(date))
      calendar.get(Calendar.DAY_OF_WEEK)
  }

  def gettotalPrice(hotelName:String,customerType:CustomerType.Value,dateMap:Map[Int,Int]): Int ={

    val hotel= Database.hotelList.filter(_.name.equalsIgnoreCase(hotelName))
    var total=dateMap.foldLeft(0){(a,b)=>a+b._2*hotel.head.menuCard(customerType)(getDayType(b._1))}
    total
  }

  private def getDayType(day:Int):DayType.Value={
    day match{
      case 1|7=>DayType.WeekEnd
      case _=>DayType.WeekDay
    }
  }
}

object main extends App{
  var mapLakewood=Map(CustomerType.Regular->Map(DayType.WeekDay->110,DayType.WeekEnd->90),CustomerType.Reward->Map(DayType.WeekDay->80,DayType.WeekEnd->80))
  var mapBridgewood=Map(CustomerType.Regular->Map(DayType.WeekDay->160,DayType.WeekEnd->60),CustomerType.Reward->Map(DayType.WeekDay->110,DayType.WeekEnd->50))
  var mapcopyBridgewood=Map(CustomerType.Regular->Map(DayType.WeekDay->160,DayType.WeekEnd->60),CustomerType.Reward->Map(DayType.WeekDay->110,DayType.WeekEnd->50))
  var mapRidgewood=Map(CustomerType.Regular->Map(DayType.WeekDay->220,DayType.WeekEnd->150 ),CustomerType.Reward->Map(DayType.WeekDay->100,DayType.WeekEnd->40))

  var manageHotelImple=new ManageHotelImple
  val request=new Request(CustomerType.Regular,"2019/3/2 2019/3/3")
  val hotelServiceImple=new HotelServiceImple

  manageHotelImple.addHotel(new Hotel("LAKEWOOD",3,mapLakewood))
  manageHotelImple.addHotel(new Hotel("BRIDGEWOOD",4,mapBridgewood))
  manageHotelImple.addHotel(new Hotel("CBRIDGEWOOD",5,mapcopyBridgewood))
  manageHotelImple.addHotel(new Hotel("RIDGEWOOD",5,mapRidgewood))

  println(hotelServiceImple.findCheapestHotel(request))
}
