package com.work.cuidese.data

import androidx.room.TypeConverter
import java.util.*

class Converters {
    @TypeConverter fun calendarToDate(calendar: Calendar): Long = calendar.timeInMillis
    @TypeConverter fun datesToCalendar(value: Long): Calendar =
        Calendar.getInstance().apply { timeInMillis = value }
}