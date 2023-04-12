package com.example.apotek

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.viewModels
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil.setContentView
import androidx.databinding.adapters.TextViewBindingAdapter.setText
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.RecyclerView
import com.example.apotek.data.MainViewModel
import com.example.apotek.data.adapter.obatAdapter
import com.example.apotek.databinding.ActivityProfileBinding
import com.example.apotek.databinding.ActivityStrukBinding
import com.example.apotek.databinding.ActivityTransaksiBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.io.File
import java.io.FileOutputStream
import java.util.*

class Transaksi : AppCompatActivity() {
    private lateinit var dialog: BottomSheetDialog
    val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getObat()
        transaksi()
    }

    private fun interval(binding: ActivityTransaksiBinding){
    }
    private fun transaksi(){
        val binding = ActivityTransaksiBinding.inflate(layoutInflater)
        viewModel.price.value = 0
        setContentView(binding.root)
        viewModel.triggerListObat.observe(this){
            binding.textView2.text = viewModel.listObat.value.toString()
        }
        viewModel.listObat.value = mutableListOf()
        viewModel.userData.observe(this){
            binding.textView5.text = viewModel.userData.value.toString()
        }
        viewModel.getUserData(getIntent().getStringExtra("username").toString(), getIntent().getStringExtra("password").toString())
        dialog = BottomSheetDialog(this)
        binding.button11.setOnClickListener{
            showObat(viewModel)
        }
        binding.button12.setOnClickListener{
            showTipe(viewModel)
        }
        viewModel.price.observe(this){
            binding.textView7.text = "Subtotal : Rp.${viewModel.price.value}"
        }
        binding.button6.setOnClickListener {
            viewModel.namaPembeli.value = binding.editTextTextPersonName3.text.toString()
            struk()
        }
        viewModel.tipeObat.observe(this){
            binding.textView16.text = viewModel.tipeObat.value.toString()
        }
        binding.navBottom.setOnNavigationItemSelectedListener{
            when(binding.navBottom.selectedItemId){
                R.id.bottom_item1 ->{}
                R.id.bottom_item2 -> {
                    profile()
                }
            }
            true
        }
    }
    private fun showObat(viewModel: MainViewModel){
        val dialogView = layoutInflater.inflate(R.layout.bottom_sheet, null)
        viewModel.price.value = 0
        dialog.setContentView(dialogView)
        viewModel.listObat.value = mutableListOf()
        dialogView.findViewById<RecyclerView>(R.id.recycler_view).adapter =
            obatAdapter(this, viewModel.allListObat.value!!, viewModel)
        dialog.show()
    }

    private fun showTipe(viewModel: MainViewModel){
        dialog.hide()
        val dialogView = layoutInflater.inflate(R.layout.bottom_sheet2, null)
        dialogView.findViewById<Button>(R.id.button9).setOnClickListener {
            viewModel.tipeObat.value = "Sirup"
        }
        dialogView.findViewById<Button>(R.id.button10).setOnClickListener {
            viewModel.tipeObat.value = "Tablet"
        }
        dialog.setContentView(dialogView)
        dialog.show()
    }

    private fun struk(){
        val binding = ActivityStrukBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.textView11.text = "Rp. "+viewModel.price.value.toString()
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
        binding.button8.setOnClickListener {
            transaksi()
        }
    }

    private fun profile(){
        val binding= ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.navbotomes.selectedItemId = R.id.bottom_item2
        binding.navbotomes.setOnNavigationItemSelectedListener{
            when(binding.navbotomes.selectedItemId){
                R.id.bottom_item2 ->{}
                R.id.bottom_item1 -> {
                    transaksi()
                }
            }
            true
        }
        binding.textView.text = viewModel.userData.value?.nama_user
        binding.textView8.text = "Username :" + viewModel.userData.value?.username
        binding.textView9.text = "Alamat :" + viewModel.userData.value?.alamat
    }
}

