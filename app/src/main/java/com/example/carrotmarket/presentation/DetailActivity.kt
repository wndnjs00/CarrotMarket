package com.example.carrotmarket.presentation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.carrotmarket.R
import com.example.carrotmarket.data.DataSource
import com.example.carrotmarket.data.Product
import com.example.carrotmarket.databinding.ActivityDetailBinding
import com.example.carrotmarket.viewModel.FavorateViewModel
import com.google.android.material.snackbar.Snackbar
import java.text.DecimalFormat

class DetailActivity : AppCompatActivity() {

    private val TAG = DetailActivity::class.java.simpleName

    private val binding: ActivityDetailBinding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }

//    lateinit var favorateViewModel : FavorateViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        // getParcelableExtra사용해서 MainActivtiy에서 보낸 데이터 받아오기
        val intent = getIntent()
        val productItem = intent?.getParcelableExtra<Product>("product")

        val dataSoure = DataSource.getDataSource().getProductList()
        val position = intent?.getIntExtra("position", 0)
        val product = dataSoure[position!!]

//        productItem?.let {
//            with(binding) {
//                Glide.with(this@DetailActivity).load(it.image).into(detailItemIv)
//                detailSellerTv.text = it.seller
//                detailAreaTv.text = it.area
//                detailNameTv.text = it.name
//                detailContentTv.text = it.description
//                detailPriceTv.text = DecimalFormat("#,###").format(it.price) + "원"
//            }
//        }

        Glide.with(this@DetailActivity).load(productItem?.image).into(binding.detailItemIv)
        binding.detailSellerTv.text = productItem?.seller
        binding.detailAreaTv.text = productItem?.area
        binding.detailNameTv.text = productItem?.name
        binding.detailContentTv.text = productItem?.description
        binding.detailPriceTv.text = DecimalFormat("#,###").format(productItem?.price) + "원"

        if(product.isLike){
            binding.detailHeartIv.setImageResource(R.drawable.fullheart_img)
        }else{
            binding.detailHeartIv.setImageResource(R.drawable.heat_img)
        }



        // 이전버튼 눌렀을때
        binding.detailLeftArrowIv.setOnClickListener {
//            val intent2 = Intent(this, MainActivity::class.java)

//            favorateViewModel = ViewModelProvider(this).get(FavorateViewModel::class.java)
//            favorateViewModel.sendfavorateData("favorate", isLiked)
//            Log.d(TAG, isLiked.toString())

//            startActivity(intent2)
            finish()
        }


        binding.detailHeartIv.setOnClickListener {
            val product = dataSoure[position]
            product.isLike = !product.isLike

            if (product.isLike){
                Snackbar.make(binding.linearLayout, "관심 목록에 추가되었습니다", Snackbar.LENGTH_SHORT).show()
                product.favorate++
                binding.detailHeartIv.setImageResource(R.drawable.fullheart_img)
            }else{
                product.favorate--
                binding.detailHeartIv.setImageResource(R.drawable.heat_img)
            }

            //좋아요 수를 메인으로 연결하기
            val intent = Intent()
            intent.putExtra("position", position)
            intent.putExtra("likeCount", product.favorate)
            setResult(Activity.RESULT_OK, intent)
        }


//        // 빈하트 클릭시
//        binding.detailHeartIv.setOnClickListener {
//            favorateBtn(false)
//            isLiked = true
//            Snackbar.make(binding.linearLayout, "관심 목록에 추가되었습니다", Snackbar.LENGTH_SHORT).show()
//        }
//
//        // 채워진 하트 클릭시
//        binding.detailFullheartIv.setOnClickListener {
//            favorateBtn(true)
//            isLiked = false
//            Snackbar.make(binding.linearLayout, "관심 목록에서 해제되었습니다", Snackbar.LENGTH_SHORT).show()
//        }

    }


    // 좋아요 눌렀을때 바뀌는 함수
//    private fun favorateBtn(favorate: Boolean){
//        if (favorate == true){
//            binding.detailHeartIv.visibility = View.VISIBLE
//            binding.detailFullheartIv.visibility = View.GONE
//        }
//        else{
//            binding.detailHeartIv.visibility = View.GONE
//            binding.detailFullheartIv.visibility = View.VISIBLE
//        }
//
//    }




}