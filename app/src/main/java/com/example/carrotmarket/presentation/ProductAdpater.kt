package com.example.carrotmarket.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.carrotmarket.R
import com.example.carrotmarket.data.Product
import com.example.carrotmarket.databinding.ListItemBinding


class ProductAdpater(): RecyclerView.Adapter<ProductAdpater.ProductViewHolder>() {

    var productList = listOf<Product>()


    // 화면(레이아웃) 연결
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ProductViewHolder(ListItemBinding.bind(view))
    }


    // 아이템 개수 리턴
    override fun getItemCount(): Int {
        return productList.size
    }


    // 데이터 연결
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        // bind함수에 있는 함수를 가져와서 데이터 뿌려줌
        holder.bind(productList[position])

    }



    class ProductViewHolder(private var binding : ListItemBinding) : RecyclerView.ViewHolder(binding.root){
        private var currentProduct : Product ?= null

        init {
            // 클릭 리스너
            itemView.setOnClickListener {

            }
        }

        // 레이아웃과 데이터 연결
        fun bind(product: Product){
            currentProduct = product

            binding.itemNameTv.text = product.name
            binding.itemAreaTv.text = product.area
            binding.itemPriceTv.text = product.price
            binding.itemCommentTv.text = product.comment.toString()
            binding.itemHeartTv.text = product.favorate.toString()

            Glide.with(itemView.context)
                .load(product.image)
                .into(binding.itemIv)
        }

    }



}