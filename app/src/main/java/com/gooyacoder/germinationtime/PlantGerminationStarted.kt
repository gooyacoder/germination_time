package com.gooyacoder.germinationtime

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.FileNotFoundException
import java.util.Date
import android.graphics.Bitmap.createScaledBitmap




class PlantGerminationStarted : AppCompatActivity() {

    var imageBitmap: Bitmap? = null
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_plant_germination_started)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val imageView = findViewById<ImageView>(R.id.seedImage)
        val startButton = findViewById<Button>(R.id.start_button)

        imageView.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                if (event?.action == MotionEvent.ACTION_DOWN) {
                    // Perform your action here
                    //Toast.makeText(applicationContext, "Add Seed Image", Toast.LENGTH_LONG).show()
                    val intent = Intent(Intent.ACTION_PICK)
                    intent.type = "image/*"
                    val mimeTypes = arrayOf("image/jpeg", "image/png")
                    intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
                    startActivityForResult(intent, 2)
                    return true
                }
                return false
            }
        })

        startButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val name_text: EditText = findViewById(R.id.plantName)
                if(name_text.text.toString() != ""){
                    if(imageBitmap == null){
                        imageBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
                    }
                    var image_byte_array = imageBitmap?.let { DbBitmapUtility.getBytes(it) }
                    var db = DatabaseHelper(applicationContext)

                    var plant_name:String = name_text.text.toString()
                    val today = Date()
                    val germinationDate = GerminationDate()
                    val result = db.addEntry(plant_name, image_byte_array!!, germinationDate.dateToString(today))
                    if(result.toInt() == -1){
                        Toast.makeText(applicationContext, "Try another plant name.", Toast.LENGTH_LONG).show()
                    }else{
                        db.close()
                        Toast.makeText(applicationContext, "$plant_name added to database, successfully.", Toast.LENGTH_LONG).show()
                        finish()
                    }
                }
            }
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val imageView = findViewById<ImageView>(R.id.seedImage)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            imageBitmap = data?.extras?.get("data") as Bitmap
            imageBitmap = resizeBitmapToQuarter(imageBitmap!!)
            imageView.setImageBitmap(imageBitmap)
        }
        else if (requestCode == 2 && resultCode == RESULT_OK){
            var selectedImage: Uri? = null
            if (data != null) {
                selectedImage = data?.data
                try {
                    val input = contentResolver.openInputStream(selectedImage!!)
                    val selectedImg = BitmapFactory.decodeStream(input)
                    imageBitmap = selectedImg
                    imageBitmap = resizeBitmapToQuarter(imageBitmap!!)
                    imageView.setImageBitmap(imageBitmap)

                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                    Toast.makeText(this, "An error occurred!", Toast.LENGTH_LONG).show()
                }
            }

        }
    }

    fun resizeBitmapToQuarter(bitmap: Bitmap): Bitmap {
        // Calculate the new width and height
        val newWidth = bitmap.width / 2
        val newHeight = bitmap.height / 2

        // Create the resized bitmap
        return createScaledBitmap(bitmap, newWidth, newHeight, true)
    }

}