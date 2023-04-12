package com.example.apotek

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.apotek.data.MainViewModel
import com.example.apotek.databinding.ActivityProfileBinding
import com.example.apotek.databinding.ActivityStrukBinding

class Profile : AppCompatActivity() {
    val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding= ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.navbotomes.selectedItemId = R.id.bottom_item2
        binding.navbotomes.setOnNavigationItemSelectedListener{
            when(binding.navbotomes.selectedItemId){
                R.id.bottom_item2 ->{}
                R.id.bottom_item1 -> {
                    val intent = Intent(this, Transaksi::class.java)
                    startActivity(intent)
                }
            }
            true
        }
        binding.textView.text = viewModel.userData.value?.nama_user
        binding.textView8.text = "Username :" + viewModel.userData.value?.username
        binding.textView9.text = "Alamat :" + viewModel.userData.value?.alamat
    }
}