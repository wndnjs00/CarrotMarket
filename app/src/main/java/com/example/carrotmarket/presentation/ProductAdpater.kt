package com.example.carrotmarket.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.carrotmarket.R
import com.example.carrotmarket.data.Product
import com.example.carrotmarket.databinding.ListItemBinding
import java.text.DecimalFormat


// 어댑터에 람다함수를 사용해서 아이템 클릭이벤트 구현
class ProductAdpater(private val onClick : (Product) -> Unit) : RecyclerView.Adapter<ProductAdpater.ProductViewHolder>() {

    var productList = listOf<Product>()


    // 화면(레이아웃) 연결
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ProductViewHolder(ListItemBinding.bind(view), onClick)
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



    class ProductViewHolder(private var binding : ListItemBinding, private val onClick: (Product) -> Unit) : RecyclerView.ViewHolder(binding.root){
        private var currentProduct : Product ?= null

        init {
            // 클릭 리스너 (아이템 클릭시)
            itemView.setOnClickListener {
                currentProduct?.let {
                    onClick.invoke(it)
                }
            }
        }

        // 레이아웃과 데이터 연결
        fun bind(product: Product){
            currentProduct = product

            binding.itemNameTv.text = product.name
            binding.itemAreaTv.text = product.area
            binding.itemPriceTv.text = DecimalFormat("#,###").format(product.price).toString() + "원"    // 가격은 천단위 콤마표시
            binding.itemCommentTv.text = product.comment.toString()
            binding.itemHeartTv.text = product.favorate.toString()

            Glide.with(itemView.context)
                .load(product.image)
                .into(binding.itemIv)

        }

    }



}