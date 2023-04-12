package com.example.apotek

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.apotek.data.MainViewModel
import com.example.apotek.data.model.User
import com.example.apotek.databinding.ActivityRegisterBinding

class Register : AppCompatActivity() {
    val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.registResponse.observe(this){
            if(viewModel.registResponse.value?.body()?.status!!){
                val intent = Intent(this, Login::class.java)
                startActivity(intent)
            }
            else{
                binding.alert.text = "Username sudah ada!!"
            }
        }
        findViewById<Button>(R.id.button5).setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
        binding.button4.setOnClickListener {
            if(
                binding.editTextTextPersonName2.text.length < 1 ||
                binding.editTextTextPersonName5.text.length < 1 ||
                binding.editTextTextPersonName6.text.length < 1 ||
                binding.editTextTextPassword3.text.length < 1 ||
                binding.editTextTextPassword2.text.length < 1
            ){
                binding.alert.text = "Mohon isi field terlebih dahulu!!"
            }
            else{
                if(binding.editTextTextPassword3.text.toString() == binding.editTextTextPassword2.text.toString()){
                    viewModel.register(User(
                        "0",
                        "Pelanggan",
                        binding.editTextTextPersonName2.text.toString(),
                        binding.editTextTextPersonName6.text.toString(),
                        "",
                        binding.editTextTextPersonName5.text.toString(),
                        binding.editTextTextPassword3.text.toString()
                    ))
                }
                else{
                    binding.alert.text = "Password tidak sama!!"
                }
            }
        }
    }
}