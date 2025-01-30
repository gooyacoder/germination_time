package com.gooyacoder.germinationtime

import java.util.ArrayList
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import java.time.LocalDate
import java.util.Date
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import kotlinx.serialization.modules.*



@Serializable
data class Plant(val plant_name: String, val image: ByteArray, val startDate: String)