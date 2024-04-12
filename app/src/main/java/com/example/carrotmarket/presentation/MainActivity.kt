package com.example.carrotmarket.presentation

import android.content.Intent
import android.icu.lang.UCharacter.VerticalOrientation
import android.os.Bundle
import android.widget.Toast
import android.window.OnBackInvokedDispatcher
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
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


    // 뒤로가기 버튼 눌렀을때 실행되는 콜백메소드
    private val onBackPressedCallback = object : OnBackPressedCallback(true){
        override fun handleOnBackPressed() {
            // 뒤로가기 실행시 실행할 다이얼로그(기본 다이얼로그 사용)
            val dialog = AlertDialog.Builder(this@MainActivity)
                .setTitle("종료")
                .setMessage("정말로 종료하시겠습니까?")
                .setIcon(R.drawable.chat_img)
                .setPositiveButton("확인") { dialog, id ->
                    finish()
                }
                .setNegativeButton("취소"){ dialog, id ->
                    Toast.makeText(this@MainActivity, "취소", Toast.LENGTH_SHORT).show()
                }
            dialog.show()   // 꼭 show적어주기!!
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

        // 뒤로가기를 onBackPressedDispatcher를 통해 등록
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }





     // 클릭했을때 DetailActivity로 이동하게끔하는 함수
    private fun adpaterOnClick(product: Product) {
        val intent = Intent(this, DetailActivity()::class.java)
         // 데이터 전달 (product 전체를 전달)
         intent.putExtra("product", product)

        startActivity(intent)
    }
}


