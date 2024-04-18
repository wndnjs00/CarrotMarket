package com.example.carrotmarket.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.persistableBundleOf
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.carrotmarket.R
import com.example.carrotmarket.data.DataSource
import com.example.carrotmarket.data.Product
import com.example.carrotmarket.databinding.ListItemBinding
import java.text.DecimalFormat


// 어댑터에 람다함수를 사용해서 아이템 클릭이벤트 구현 (position값을 MainActivity로 전달하기위해 파라미터에 Int값 추가)
class ProductAdpater(var productList : MutableList<Product>, private val onClick : (Product, Int) -> Unit) : RecyclerView.Adapter<ProductAdpater.ProductViewHolder>() {


    // 아이템 롱클릭을 위한 인터페이스 추가
    interface ItemLongClick{
        fun onLongClick(view : View, position: Int)
    }
    var itemLongClick : ItemLongClick?= null



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
        val currentItemPosition = productList[position]

        // bind함수에 있는 함수를 가져와서 데이터 뿌려줌
        holder.bind(currentItemPosition)

        // 아이템 클릭 리스너 설정
        // postion값 넘겨주기!!
        holder.itemView.setOnClickListener {
            onClick(currentItemPosition, position)
        }


        // 2초간 클릭했을때 아이템 삭제 (setOnLongClickListener 사용)
        val longClick = android.os.Handler()
        holder.itemView.setOnLongClickListener{
            longClick.postDelayed({
                itemLongClick?.onLongClick(it, position)
            }, 2000) //2초
            true
        }


    }



    class ProductViewHolder(private var binding : ListItemBinding) : RecyclerView.ViewHolder(binding.root){

        private var currentProduct : Product ?= null

        // 레이아웃과 데이터 연결
        fun bind(product: Product){
            currentProduct = product

            val dataSource = DataSource.getDataSource().getProductList()
            val data = dataSource[position]

            product.let{
                with(binding){
                    itemNameTv.text = it.name
                    itemAreaTv.text = it.area
                    itemPriceTv.text = DecimalFormat("#,###").format(it.price).toString() + "원"    // 가격은 천단위 콤마표시
                    itemCommentTv.text = it.comment.toString()
                    itemHeartTv.text = it.favorate.toString()
                    Glide.with(itemView.context).load(it.image).into(itemIv)
                }
            }

            // 좋아요 버튼 클릭되어있다면
            if (data.isLike == true){
                binding.itemHeartIv.setImageResource(R.drawable.fullheart_img)
            }else{
                binding.itemHeartIv.setImageResource(R.drawable.heat_img)
            }

            binding.itemHeartIv.setOnClickListener {
                if (data.isLike == true){
                    // 좋아요 수 늘리기
                    data.favorate++
                }else{
                    data.favorate--
                }
            }
        }
    }


}