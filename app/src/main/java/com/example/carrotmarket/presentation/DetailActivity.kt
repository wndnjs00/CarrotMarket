package com.example.carrotmarket.presentation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.carrotmarket.R
import com.example.carrotmarket.data.DataSource
import com.example.carrotmarket.data.Product
import com.example.carrotmarket.databinding.ActivityDetailBinding
import com.google.android.material.snackbar.Snackbar
import java.text.DecimalFormat

class DetailActivity : AppCompatActivity() {

    private val TAG = DetailActivity::class.java.simpleName

    private val binding: ActivityDetailBinding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        // getParcelableExtra사용해서 MainActivtiy에서 보낸 데이터 받아오기
        val intent = getIntent()
        val productItem = intent?.getParcelableExtra<Product>("product")

        val dataSoure = DataSource.getDataSource().getProductList()
        val position = intent?.getIntExtra("position",0)

        productItem?.let {
            with(binding) {
                Glide.with(this@DetailActivity).load(it.image).into(detailItemIv)
                detailSellerTv.text = it.seller
                detailAreaTv.text = it.area
                detailNameTv.text = it.name
                detailContentTv.text = it.description
                detailPriceTv.text = DecimalFormat("#,###").format(it.price) + "원"

                if (it.isLike) {
                    // isLike가 true일때 색칠
                    detailHeartIv.setImageResource(R.drawable.fullheart_img)
                } else {
                    detailHeartIv.setImageResource(R.drawable.heat_img)
                }
            }
        }


        // 이전버튼 눌렀을때
        binding.detailLeftArrowIv.setOnClickListener {

            finish()
        }


        binding.detailHeartIv.setOnClickListener {
            val product = dataSoure[position!!]
            product.isLike = !product.isLike

            if (product.isLike == true) {
                Snackbar.make(binding.linearLayout, "관심 목록에 추가되었습니다", Snackbar.LENGTH_SHORT).show()
                product.favorate++
                binding.detailHeartIv.setImageResource(R.drawable.fullheart_img)
            } else {
                product.favorate--
                binding.detailHeartIv.setImageResource(R.drawable.heat_img)
            }

            // Mainactivtiy로 전달
            intent.putExtra("position", position)           // 클릭한 위치
            intent.putExtra("likeCount", product.favorate)  // 좋아요 수

            setResult(Activity.RESULT_OK, intent)
        }


    }
}