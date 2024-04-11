package com.example.carrotmarket.presentation

import android.icu.lang.UCharacter.VerticalOrientation
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carrotmarket.R
import com.example.carrotmarket.data.DataSource
import com.example.carrotmarket.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // lazy를 사용해서 호출될때 뷰바인딩이 초기회되도록
    private val binding : ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val productAdpater : ProductAdpater by lazy {
        ProductAdpater()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 데이터리스트 가져오기
        val dataSource = DataSource.getDataSource().getProductList()

        // 어뎁터와 데이터리스트 연결
        productAdpater.productList = dataSource

        // 리사이클러뷰 레이아웃과 연결
        with(binding.RecyclerView){
            adapter = productAdpater
        }

        // layoutManager사용
        binding.RecyclerView.layoutManager = LinearLayoutManager(this)

        // 리사이클러뷰 구분선 표시
        val decoration = DividerItemDecoration(binding.RecyclerView.context, LinearLayoutManager(this).orientation)
        binding.RecyclerView.addItemDecoration(decoration)
    }
}