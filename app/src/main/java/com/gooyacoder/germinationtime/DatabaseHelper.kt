package com.gooyacoder.germinationtime

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import java.io.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import java.time.LocalDate
import java.util.Date


class DatabaseHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    var plants = mutableListOf<Plant>()


    override fun onCreate(db: SQLiteDatabase) {

        // creating table
        db.execSQL(CREATE_TABLE_PLANT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE)

        // create new table
        onCreate(db)
    }

    companion object {
        // Database Version
        private const val DATABASE_VERSION = 1

        // Database Name
        private const val DATABASE_NAME = "plants_db"

        // Table Names
        private const val DB_TABLE = "plant_table"

        // column names
        private const val KEY_NAME = "plant_name"
        private const val KEY_IMAGE = "plant_image"
        private const val KEY_PLANT_START_DATE = "plant_start_date"




        // Table create statement
        private const val CREATE_TABLE_PLANT = "CREATE TABLE " + DB_TABLE + "(" +
                KEY_NAME + " TEXT NOT NULL UNIQUE," +
                KEY_IMAGE + " BLOB," +
                KEY_PLANT_START_DATE + " TEXT);"

    }

    @Throws(SQLiteException::class)
    fun updatePlants(plants: MutableList<Plant>) {
        val database = this.writableDatabase
        for (plant in plants) {
            val cv = ContentValues()
            cv.put(KEY_NAME, plant.plant_name)
            cv.put(KEY_IMAGE, plant.image)

            database.insert(DB_TABLE, null, cv)
        }
        database.close()
    }

    @Throws(SQLiteException::class)
    fun addEntry(name: String, image: ByteArray, startDate: String): Long {
        val database = this.writableDatabase
        val cv = ContentValues()
        cv.put(KEY_NAME, name)
        cv.put(KEY_IMAGE, image)
        cv.put(KEY_PLANT_START_DATE, startDate)
        val result = database.insert(
            DB_TABLE,
            null,
            cv
        ) // returns -1 if insert fails, show message that the plant name exists.
        database.close()
        return result
    }



    fun getPlant(name: String?): Plant {
        val db = this.writableDatabase
        val query = "SELECT * FROM $DB_TABLE where $KEY_NAME = '$name';"
        val cursor = db.rawQuery(query, null)
        cursor.moveToNext()
        val name = cursor.getString(0)
        val imagebyte = cursor.getBlob(1)
        val start_date = cursor.getString(2)


        val g_date = GerminationDate()
        val startDate: Date = g_date.stringToDate(start_date)


        val plant = Plant(
            name,
            imagebyte,
            startDate

        )
        db.close()
        return plant
    }


    @JvmName("getPlants1")
    fun getPlants(): MutableList<Plant> {
        val db = this.writableDatabase
        val query = "SELECT * FROM " + DB_TABLE
        val cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()) {
            val name = cursor.getString(0)
            val imagebyte = cursor.getBlob(1)
            val start_date = cursor.getString(2)


            val g_date = GerminationDate()
            val startDate: Date = g_date.stringToDate(start_date)

            val plant = Plant(
                name,
                imagebyte,
                startDate
            )
            plants.add(plant)
        }
        cursor.close()
        db.close()
        return plants
    }

    fun removePlant(name: String?) {
        val db = this.writableDatabase
        db.execSQL(
            "delete from plant_table where" +
                    " plant_name = \"" + name + "\";"
        );
        db.close()
    }

    fun updatePlantImage(name: String, image: ByteArray) : Int{
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(KEY_NAME, name)
        cv.put(KEY_IMAGE, image)
        val args = Array<String>(1) { name }
        val result = db.update(DB_TABLE, cv, "plant_name=?", args)
        db.close()
        return result
    }


    fun updatePlant(plant: Plant) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(KEY_NAME, plant.plant_name)
        cv.put(KEY_IMAGE, plant.image)
        cv.put(KEY_PLANT_START_DATE, plant.startDate.toString())
        db.insert(DB_TABLE, null, cv)
        db.close()
    }


}