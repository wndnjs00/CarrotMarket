package com.example.carrotmarket.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.carrotmarket.R
import com.example.carrotmarket.data.Product
import com.example.carrotmarket.databinding.ActivityDetailBinding
import java.text.DecimalFormat

class DetailActivity : AppCompatActivity() {

    private val binding: ActivityDetailBinding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        // getParcelableExtra사용해서 MainActivtiy에서 보낸 데이터 받아오기
        val intent = getIntent()
        val productItem = intent?.getParcelableExtra<Product>("product")

        productItem?.let {
            with(binding) {
                Glide.with(this@DetailActivity).load(it.image).into(this.detailItemIv)
                detailSellerTv.text = it.seller
                detailAreaTv.text = it.area
                detailNameTv.text = it.name
                detailContentTv.text = it.description
                detailPriceTv.text = DecimalFormat("#,###").format(it.price) + "원"
            }
        }


        binding.detailLeftArrowIv.setOnClickListener {
            finish()
        }


    }
}