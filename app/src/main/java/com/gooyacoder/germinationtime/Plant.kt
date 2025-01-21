package com.gooyacoder.germinationtime

import java.util.ArrayList
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import java.time.LocalDate


@Serializable
data class Plant(val plant_name: String,
                 val image: ByteArray,
                 val startDate: LocalDate,
                 val germinationDate: LocalDate)