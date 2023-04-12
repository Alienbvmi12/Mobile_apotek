package com.example.apotek

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil.setContentView
import androidx.lifecycle.ViewModelProvider
import com.example.apotek.data.MainViewModel
import com.example.apotek.databinding.ActivityStrukBinding
import java.io.File
import java.io.FileOutputStream

class Struk : AppCompatActivity() {
    val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityStrukBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.textView11.text = viewModel.price.value.toString()
        binding.textView13.text = "Nama pembeli : ${viewModel.namaPembeli.value}"
        binding.textView14.text = "Obat yang dibeli : ${viewModel.listObat.value.toString()}"
        binding.textView15.text = "Jenis obat : ${viewModel.tipeObat.value.toString()}"
        binding.button7.setOnClickListener {
            val view: View = getLayoutInflater().inflate(R.layout.activity_struk, null)
            val bitmap: Bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888)
            val canvas: Canvas = Canvas(bitmap)
            view.draw(canvas)
            val invoiceImage: File = File(getExternalCacheDir(), "invoice.png")

            try{
                val fos: FileOutputStream = FileOutputStream(invoiceImage)
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
                fos.flush()
                fos.close()
            }
            catch (e: Exception){
                e.printStackTrace()
            }

            val shareIntent: Intent = Intent(Intent.ACTION_SEND)
            shareIntent.setType("image/png")
            val uri: Uri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", invoiceImage)
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
            startActivity(Intent.createChooser(shareIntent, "Share invoice"))
        }
    }
}