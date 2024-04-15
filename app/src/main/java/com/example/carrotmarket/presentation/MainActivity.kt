package com.example.carrotmarket.presentation

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.icu.lang.UCharacter.VerticalOrientation
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import android.window.OnBackInvokedDispatcher
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carrotmarket.R
import com.example.carrotmarket.data.DataSource
import com.example.carrotmarket.data.Product
import com.example.carrotmarket.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // lazy를 사용해서 호출될때 뷰바인딩이 초기회되도록
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val productAdpater: ProductAdpater by lazy {
        // 클릭 이벤트 (람다함수를 사용해서 아이템 클릭이벤트 구현)
        ProductAdpater { product ->
            // 클릭시 DetailActivity로 이동
            adpaterOnClick(product)
        }
    }

    private val notificationID = 1
    private val channelId = "default"


    // 뒤로가기 버튼 눌렀을때 실행되는 콜백메소드
    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            // 뒤로가기 실행시 실행할 다이얼로그(기본 다이얼로그 사용)
            val dialog = AlertDialog.Builder(this@MainActivity)
                .setTitle("종료")
                .setMessage("정말로 종료하시겠습니까?")
                .setIcon(R.drawable.chat_img)
                .setPositiveButton("확인") { dialog, id ->
                    finish()
                }
                .setNegativeButton("취소") { dialog, id ->
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
        with(binding.RecyclerView) {
            adapter = productAdpater
        }

        // LinearLayoutManager사용
        binding.RecyclerView.layoutManager = LinearLayoutManager(this)

        // 리사이클러뷰 구분선 표시
        val decoration = DividerItemDecoration(
            binding.RecyclerView.context,
            LinearLayoutManager(this).orientation
        )
        binding.RecyclerView.addItemDecoration(decoration)

        // 뒤로가기를 onBackPressedDispatcher를 통해 등록
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)


        binding.notificationImg.setOnClickListener {
            notification()
        }
        createNotificationChannel()


    }


    // 클릭했을때 DetailActivity로 이동하게끔하는 함수
    private fun adpaterOnClick(product: Product) {
        val intent = Intent(this, DetailActivity()::class.java)
        // 데이터 전달 (product 전체를 전달)
        intent.putExtra("product", product)

        startActivity(intent)
    }



    // 사용자에게 알림 권한요청
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (!NotificationManagerCompat.from(this).areNotificationsEnabled()) {
                // 알림 권한이 없다면, 사용자에게 권한 요청
                val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
                    putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
                }
                startActivity(intent)
            }
        }
    }

    // 알림 생성
    @SuppressLint("MissingPermission")
    private fun notification(){
        val builder = NotificationCompat.Builder(this,channelId)
            .setSmallIcon(R.drawable.notification_img)
            .setContentTitle("키워드 알림")
            .setContentText("설정한 키워드에 대한 알림이 도착했습니다!!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        NotificationManagerCompat.from(this).notify(notificationID, builder.build())
    }



}

