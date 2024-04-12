package com.example.carrotmarket.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.carrotmarket.R
import com.example.carrotmarket.data.Product
import com.example.carrotmarket.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private val binding: ActivityDetailBinding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val intent = getIntent()
        var productItem = intent?.getParcelableExtra<Product>("product")

        productItem?.let { binding.detailItemIv.setImageResource(it.image) }
        productItem?.let { binding.detailSellerTv.text = it.seller }
        productItem?.let { binding.detailAreaTv.text = it.area }
        productItem?.let { binding.detailNameTv.text = it.name }
        productItem?.let { binding.detailContentTv.text = it.description }
        productItem?.let { binding.detailPriceTv.text = it.price + "원" }


        binding.detailLeftArrowIv.setOnClickListener {
            finish()
        }
    }
}