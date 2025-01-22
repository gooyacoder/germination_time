package com.gooyacoder.germinationtime
import androidx.core.net.ParseException
import com.aminography.primecalendar.persian.PersianCalendar
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class GerminationDate {
    fun dateToString(date: Date?): String {
        val formatter = SimpleDateFormat("EEEE, MMMM d, yyyy", Locale.ENGLISH)
        return formatter.format(date)
    }

    @Throws(ParseException::class)
    fun stringToDate(dateString: String?): Date {
        val formatter = SimpleDateFormat("EEEE, MMMM d, yyyy", Locale.ENGLISH)
        return formatter.parse(dateString)
    }

    fun dateToPersian(date: Date?): PersianCalendar {
        val calendar = PersianCalendar()
        calendar.timeInMillis = date!!.time
        return calendar
    }
}