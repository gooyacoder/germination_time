package com.gooyacoder.germinationtime

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class PlantGerminationCompleted : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_plant_germination_completed)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        var db = DatabaseHelper(applicationContext)
        val plants_in_database = db.getPlants()
        val list: ArrayList<Item> = ArrayList()
        val g_date = GerminationDate()
        for(plant in plants_in_database){
            val item = Item(plant.plant_name, g_date.dateToString(plant.startDate), plant.image)
            list.add(item)
        }
        val seedList = findViewById<RecyclerView>(R.id.seedList)
        seedList.layoutManager = LinearLayoutManager(this)
        val adapter = ItemAdapter(list)
        seedList.adapter = adapter

    }
}