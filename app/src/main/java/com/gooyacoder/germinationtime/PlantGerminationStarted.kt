package com.gooyacoder.germinationtime

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.FileNotFoundException

class PlantGerminationStarted : AppCompatActivity() {

    var imageBitmap: Bitmap? = null
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
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val imageView = findViewById<ImageView>(R.id.seedImage)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            imageBitmap = data?.extras?.get("data") as Bitmap
            //val plant_preview: ImageView = findViewById(R.id.plant_preview)
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
                    //val plant_preview: ImageView = findViewById(R.id.plant_preview)
                    imageView.setImageBitmap(imageBitmap)

                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                    Toast.makeText(this, "An error occurred!", Toast.LENGTH_LONG).show()
                }
            }

        }
    }
}