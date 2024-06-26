package com.example.carrotmarket.presentation

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carrotmarket.R
import com.example.carrotmarket.data.DataSource
import com.example.carrotmarket.data.Product
import com.example.carrotmarket.databinding.ActivityMainBinding

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
        ProductAdpater(productList = ArrayList<Product>()) { product, position ->
            // 클릭시 DetailActivity로 이동
            adpaterOnClick(product, position)
        }
    }


    // 데이터를 받을 엑티비티에서 생성해주기
    @SuppressLint("SuspiciousIndentation")
    private val getResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result ->
            if (result.resultCode == Activity.RESULT_OK) {

                // DetailActivity에서 전달한 데이터 받아옴
                val position = result.data?.getIntExtra("position", 0)   // 클릭한 위치

                if (position != null) {
                    productAdpater.notifyItemChanged(position)
                }
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
            adapter = productAdpater    // 리사이클러뷰와 어뎁터 연결
            layoutManager = LinearLayoutManager(this@MainActivity)  // LinearLayoutManager사용
        }


        // 리사이클러뷰 구분선 표시
        with(binding.RecyclerView){
            val decoration = DividerItemDecoration(
                context, LinearLayoutManager(this@MainActivity).orientation
            )
            addItemDecoration(decoration)
        }


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
    private fun adpaterOnClick(product: Product, position : Int) {
        val intent = Intent(this, DetailActivity()::class.java)

        // 데이터 전달 (product 전체를 전달)
        intent.putExtra("product", product)

        intent.putExtra("position", position)   // 클릭한 위치값 전달!!!
        Log.d(TAG, position.toString())

        getResult.launch(intent)
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

            @SuppressLint("NotifyDataSetChanged")
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


