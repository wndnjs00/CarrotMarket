package com.example.carrotmarket.presentation

import android.annotation.SuppressLint
import android.app.Activity
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
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.animation.AlphaAnimation
import android.widget.Toast
import android.window.OnBackInvokedDispatcher
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.example.carrotmarket.R
import com.example.carrotmarket.data.DataSource
import com.example.carrotmarket.data.Product
import com.example.carrotmarket.databinding.ActivityMainBinding
import java.lang.NullPointerException

class MainActivity : AppCompatActivity() {

    private val notificationID = 1
    private val channelId = "default"

    private val TAG = MainActivity::class.java.simpleName


    // lazy를 사용해서 호출될때 뷰바인딩이 초기회되도록
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val productAdpater: ProductAdpater by lazy {
        // 클릭 이벤트 (람다함수를 사용해서 아이템 클릭이벤트 구현)
        ProductAdpater(productList = ArrayList<Product>()) { product ->
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


        // 알람
        binding.notificationImg.setOnClickListener {
            notification()
        }
        createNotificationChannel()


        // 플로팅 버튼 클릭시
        binding.floatingButton.setOnClickListener {
            // 최상단 이동
            binding.RecyclerView.smoothScrollToPosition(0)
        }


        // 리사이클러뷰 아래로 스크롤할때 플로팅버튼 나타나게
        binding.RecyclerView.addOnScrollListener(floatingScroll())


        // 아이템 롱클릭시 삭제 다이얼로그 띄우고 삭제되도록
        productAdpater.itemLongClick = longClick()

    }




    // 클릭했을때 DetailActivity로 이동하게끔하는 함수
    private fun adpaterOnClick(product: Product) {
        val intent = Intent(this, DetailActivity()::class.java)

        // 데이터 전달 (product 전체를 전달)
        intent.putExtra("product", product)

        startActivity(intent)

    }



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


    // 리사이클러뷰 아래로 스크롤할때 플로팅버튼 나타나도록 / 최상단일때는 플로팅버튼 안보이게
    // OnScrollListener는 스크롤 상태가 변경될때마다 호출
    // 나타나고 사라질때 fade효과
    private fun floatingScroll() : RecyclerView.OnScrollListener{
        return object : RecyclerView.OnScrollListener(){

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                with(binding.floatingButton){
                    // 최상단일때 플로팅버튼 안보이게
                    if (!binding.RecyclerView.canScrollVertically(-1)){     // 최상단
                        animate().alpha(0f).duration = 1    // fade효과(투면도조절, 딜레이)
                        visibility = GONE
                    }else {
                        // 최상단이 아닐때 플로팅버튼 보이게
                        visibility = VISIBLE
                        animate().alpha(1f).duration = 1
                    }
                }
            }
        }
    }


    //아이템 롱클릭시 삭제 다이얼로그 띄우고 삭제하는 함수
    private fun longClick() : ProductAdpater.ItemLongClick{
        return object : ProductAdpater.ItemLongClick {

            override fun onLongClick(view: View, position: Int) {

                val dataSource = DataSource.getDataSource().getProductList()

                AlertDialog.Builder(this@MainActivity)
                    .setTitle("삭제")
                    .setMessage("정말로 삭제하시겠습니까?")
                    .setIcon(R.drawable.delete_img)
                    .setPositiveButton("삭제") { dialog, _ ->
                        // 아이템 롱클릭시 클릭한 아이템 삭제
                        dataSource.removeAt(position)
                        // 리스트크기와 아이템 동시에 갱신
                        productAdpater.notifyDataSetChanged()
                        dialog.dismiss()
                    }
                    .setNegativeButton("취소"){ dialog, _ ->
                        dialog.dismiss()
                        Toast.makeText(this@MainActivity, "취소", Toast.LENGTH_SHORT).show()
                    }
                    .show()
            }
        }
    }




}


