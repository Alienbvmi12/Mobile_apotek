package com.example.apotek

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.apotek.data.MainViewModel
import com.example.apotek.databinding.ActivityLoginBinding

class Login : AppCompatActivity() {
    val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityLoginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.loginResponse.observe(this){
            if(viewModel.loginResponse.value?.body()?.status!! ){
                binding.alert.text = "berhasil login!!"
                viewModel.getUserData(binding.editTextTextPersonName.text.toString(), binding.editTextTextPassword.text.toString())
                val intent = Intent(this, Transaksi::class.java)
                intent.putExtra("password", binding.editTextTextPassword.text.toString())
                intent.putExtra("username", binding.editTextTextPersonName.text.toString())
                startActivity(intent)
            }
            else{
                binding.alert.text = "username atau password salah!!"
            }
        }
        findViewById<Button>(R.id.button).setOnClickListener {
            if(binding.editTextTextPersonName.text.length < 1 || binding.editTextTextPassword.text.length < 1){
                binding.alert.text = "Mohon isi field terlebih dahulu!!"
            }
            else{
                viewModel.login(binding.editTextTextPersonName.text.toString(),  binding.editTextTextPassword.text.toString())
            }
        }
        findViewById<Button>(R.id.button3).setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
    }
}