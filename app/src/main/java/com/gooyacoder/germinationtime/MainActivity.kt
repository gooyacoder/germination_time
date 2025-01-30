package com.gooyacoder.germinationtime

import android.content.Intent
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.gooyacoder.germinationtime.databinding.ActivityMainBinding
import java.util.Date

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var mediaPlayer_002: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        val dateTextView = findViewById<TextView>(R.id.dateTextView)
        val date = Date()
        val germinationDate = GerminationDate()
        dateTextView.setText(germinationDate.dateToPersian(date).longDateString)

        val g_completed_btn = findViewById<Button>(R.id.stop_button)
        mediaPlayer_002 = MediaPlayer.create(this, R.raw.audio_002)
        g_completed_btn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                var intent = Intent(applicationContext, PlantGerminationCompleted::class.java)
                mediaPlayer_002.start()
                startActivity(intent)
            }
        })
        mediaPlayer = MediaPlayer.create(this, R.raw.audio_001)

        val g_started_btn = findViewById<Button>(R.id.start_activity_button)
        g_started_btn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                var intent = Intent(applicationContext, PlantGerminationStarted::class.java)
                mediaPlayer.start()
                startActivity(intent)
            }
        })

        val dataManager: Button = findViewById(R.id.dataManager)
        dataManager.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                showDialog()
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        return when (item.itemId) {
//            R.id.action_settings -> true
//            else -> super.onOptionsItemSelected(item)
//        }
//    }
    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release() // Release resources when done
        mediaPlayer_002.release()
    }

    private fun showDialog() {
        val options = arrayOf("Save Data", "Upload Data")
        var selectedOption = -1

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Choose an option")
        builder.setSingleChoiceItems(options, -1) { dialog, which ->
            selectedOption = which
        }

        builder.setPositiveButton("OK") { dialog, which ->
            if (selectedOption != -1) {
                //Toast.makeText(this, "Selected: ${options[selectedOption]}", Toast.LENGTH_SHORT).show()
                if(options[selectedOption] == "Save Data"){
                    //Save Data to File



                    Toast.makeText(this, "Data Saved to File Successfully.", Toast.LENGTH_SHORT).show()
                }
                else if(options[selectedOption] == "Upload Data"){
                    //Upload Data from File




                    Toast.makeText(this, "Data Uploaded form File Successfully.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        builder.setNegativeButton("Cancel") { dialog, which ->
            dialog.dismiss()
        }

        builder.create().show()
    }
}