package com.gooyacoder.germinationtime

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.Date


class PlantGerminationCompleted : AppCompatActivity(), ItemAdapter.OnItemClickListener,
ItemAdapter.OnItemLongClickListener {
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
        val adapter = ItemAdapter(list, this, this)
        seedList.adapter = adapter

        val stopBtn = findViewById<Button>(R.id.stop_button)
        stopBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                finish()
            }
        })

    }
    override fun onItemClick(item: Item) {
        val today: Date = Date()
        val g_date = GerminationDate()
        val start: Date = g_date.stringToDate(item.startDate)
        val c_date = CalculateDays()
        val days = c_date.Calculate(start, today)
        Toast.makeText(applicationContext, "${days} days.", Toast.LENGTH_LONG).show()
    }

    override fun onItemLongClick(item: Item) {
        // Handle the long-press event
        val builder = AlertDialog.Builder(this)
        val plant_name = item.title
        builder.setMessage("Are you sure you want to Delete $plant_name?")
            .setCancelable(false)
            .setPositiveButton("Yes") { dialog, id ->
                val db = DatabaseHelper(this)
                db.removePlant(plant_name)
                db.close()

            }
            .setNegativeButton("No") { dialog, id ->
                // Dismiss the dialog
                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()
    }

}