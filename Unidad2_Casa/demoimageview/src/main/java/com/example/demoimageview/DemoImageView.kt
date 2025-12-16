package com.example.demoimageview

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.demoimageview.databinding.ActivityDemoImageViewBinding
import com.google.android.material.snackbar.Snackbar

class DemoImageView : AppCompatActivity() {

    private lateinit var img1 : ImageView
    private lateinit var img2 : ImageView

    private lateinit var binding: ActivityDemoImageViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDemoImageViewBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.imageView2.setOnClickListener {
            Snackbar.make(binding.root,
                    "Has pulsado en la imagen",
                    Snackbar.LENGTH_LONG)
                    .show()
        }



    }
}