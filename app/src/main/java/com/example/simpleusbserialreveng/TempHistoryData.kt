package com.example.simpleusbserialreveng

import java.util.*


//data class that stores the captured temperature ID, tempreture value, unit, date, title
data class TempHistoryData(val id: UUID = UUID.randomUUID(),
                           var tempreture: String = "",
                           var unit: String = "",
                           var title: String = "",
                           var date: Date = Date() )

