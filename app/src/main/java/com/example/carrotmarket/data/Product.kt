package com.example.carrotmarket.data

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val id : Long,              // id
    @DrawableRes
    val image : Int,            // 이미지
    val name : String,          // 제품먕
    val area : String,          // 지역명
    val price : Int,            // 가격
    val comment : Int,          // 댓글 수
    var favorate : Int,         // 좋아요 수!
    val description : String,   // 내용
    val seller : String,         // 판매자

    var isLike : Boolean,        // 좋아요 처리!?!
) : Parcelable
