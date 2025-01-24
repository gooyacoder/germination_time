package com.gooyacoder.germinationtime

import java.util.Date

class CalculateDays() {

    fun Calculate(day1: Date, day2: Date): Long {
        var days: Long = 0;
        days = day2.time - day1.time
        return days/(1000*60*60*24)
    }

}