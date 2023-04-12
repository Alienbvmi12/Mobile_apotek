package com.example.apotek

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.apotek.data.MainViewModel
import com.example.apotek.databinding.ActivityMainBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class MainActivity : AppCompatActivity() {
    val viewModel: MainViewModel by viewModels()
    private lateinit var dialog: BottomSheetDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.data.observe(this){
            if(viewModel.data.value == true){
                val intent = Intent(this, Login::class.java)
                startActivity(intent)
            }
            else{
                findViewById<TextView>(R.id.connectionTestText).text = "Service unavaible: Check your connection"
            }
        }
        viewModel.testConnection()
    }
    override fun onBackPressed() {
    }
}