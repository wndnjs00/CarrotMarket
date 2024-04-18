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

                // 이코드 안적어주면 다시 들어갔을때 DetailActivity에 이미지 반영X
                if (it.isLike == true) {
                    // isLike가 true일때 색칠된 하트로
                    Glide.with(this@DetailActivity).load(R.drawable.fullheart_img).into(detailHeartIv)
                } else {
                    Glide.with(this@DetailActivity).load(R.drawable.heat_img).into(detailHeartIv)
                }
            }
        }


        // 이전버튼 눌렀을때
        binding.detailLeftArrowIv.setOnClickListener {

            finish()
        }


        // 하트 눌렀을때
        binding.detailHeartIv.setOnClickListener {
            val product = dataSoure[position!!]
            product.isLike = !product.isLike

            if (product.isLike == true) {
                Snackbar.make(binding.linearLayout, "관심 목록에 추가되었습니다", Snackbar.LENGTH_SHORT).show()
                product.favorate++
                Glide.with(this@DetailActivity).load(R.drawable.fullheart_img).into(binding.detailHeartIv)
            } else {
                Snackbar.make(binding.linearLayout, "관심 목록에서 해제되었습니다", Snackbar.LENGTH_SHORT).show()
                product.favorate--
                Glide.with(this@DetailActivity).load(R.drawable.heat_img).into(binding.detailHeartIv)
            }

            // Mainactivtiy로 전달
            intent.putExtra("position", position)           // 클릭한 위치

            setResult(Activity.RESULT_OK, intent)
        }


    }
}