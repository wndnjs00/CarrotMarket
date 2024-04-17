package com.example.carrotmarket.presentation

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.carrotmarket.R
import com.example.carrotmarket.data.Product
import com.example.carrotmarket.databinding.ActivityDetailBinding
import com.google.android.material.snackbar.Snackbar
import java.lang.System.exit
import java.text.DecimalFormat

class DetailActivity : AppCompatActivity() {

    private val binding: ActivityDetailBinding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }

//    private var isLiked = false

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


        // 빈하트 클릭시
        binding.detailHeartIv.setOnClickListener {
            favorateBtn(false)
            Snackbar.make(binding.linearLayout, "관심 목록에 추가되었습니다", Snackbar.LENGTH_SHORT).show()
        }

        // 채워진 하트 클릭시
        binding.detailFullheartIv.setOnClickListener {
            favorateBtn(true)
            Snackbar.make(binding.linearLayout, "관심 목록에서 해제되었습니다", Snackbar.LENGTH_SHORT).show()
        }

    }


    // 좋아요 눌렀을때 바뀌는 함수
    private fun favorateBtn(favorate: Boolean){
        if (favorate == true){
            binding.detailHeartIv.visibility = View.VISIBLE
            binding.detailFullheartIv.visibility = View.GONE
        }
        else{
            binding.detailHeartIv.visibility = View.GONE
            binding.detailFullheartIv.visibility = View.VISIBLE
        }

    }




}