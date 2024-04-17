package com.example.carrotmarket.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FavorateViewModel : ViewModel(){

    val favorateData = MutableLiveData<Boolean>()


    // 전송할 데이터
    fun sendfavorateData(favorate: String, isLiked: Boolean){
        favorateData.value = true
    }
}