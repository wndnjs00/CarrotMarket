package com.example.carrotmarket.data

import androidx.annotation.DrawableRes

data class Product(
    val id : Long,      // id
    @DrawableRes
    val image : Int,    // 이미지
    val name : String,  // 제품먕
    val area : String,  // 지역명
    val price : String,    // 가격
    val comment : Int,  // 댓글 수
    val favorate : Int,  // 좋아요 수

    val description : String,   // 상품소개
    val seller : String         // 판매자
)
