package com.example.carrotmarket.presentation

import android.content.Intent
import android.icu.lang.UCharacter.VerticalOrientation
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carrotmarket.R
import com.example.carrotmarket.data.DataSource
import com.example.carrotmarket.data.Product
import com.example.carrotmarket.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // lazy를 사용해서 호출될때 뷰바인딩이 초기회되도록
    private val binding : ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val productAdpater : ProductAdpater by lazy {
        // 클릭 이벤트 (람다함수를 사용해서 아이템 클릭이벤트 구현)
        ProductAdpater{product ->
            // 클릭시 DetailActivity로 이동
            adpaterOnClick(product)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 데이터리스트 가져오기
        val dataSource = DataSource.getDataSource().getProductList()

        // 어댑터와 데이터리스트 연결
        productAdpater.productList = dataSource

        // 리사이클러뷰 레이아웃과 어댑터연결
        with(binding.RecyclerView){
            adapter = productAdpater
        }

        // LinearLayoutManager사용
        binding.RecyclerView.layoutManager = LinearLayoutManager(this)

        // 리사이클러뷰 구분선 표시
        val decoration = DividerItemDecoration(binding.RecyclerView.context, LinearLayoutManager(this).orientation)
        binding.RecyclerView.addItemDecoration(decoration)
    }


     // 클릭했을때 DetailActivity로 이동하게끔하는 함수 [ 1) 그냥 intent를 사용해서 넘기는 방법 ]
    private fun adpaterOnClick(product: Product) {
        val intent = Intent(this, DetailActivity()::class.java)
         // 데이터 전달 (product 전체를 전달)
         intent.putExtra("product", product)

        startActivity(intent)
    }
}


