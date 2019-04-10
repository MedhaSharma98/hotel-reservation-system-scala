package com.hashmap.sample.model

import com.hashmap.sample.utilties.{CustomerType, DayType}

class Hotel( val name:String, val rating:Int, val menuCard:Map[CustomerType.Value,Map[DayType.Value,Int]]){


}
