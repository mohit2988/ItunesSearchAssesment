package com.itunessearchassesment.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.itunessearchassesment.databinding.AdapterAlbumItemLayoutBinding
import com.itunessearchassesment.middelware.model.ITunesResultResponse

class AlbumAdapter(
    private val albumList: ArrayList<ITunesResultResponse.Result>,
    val onItemClickListenerRecyclerView: OnItemClickListenerRecyclerView
) : RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>() {


    class AlbumViewHolder(binding: AdapterAlbumItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var mBinding: AdapterAlbumItemLayoutBinding = binding
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AlbumViewHolder {
        val binding: AdapterAlbumItemLayoutBinding =
            AdapterAlbumItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        return AlbumViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return albumList.size
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.mBinding.result = albumList[position]

        holder.mBinding.btnAddToCart.setOnClickListener {
            val album = albumList[position]
            album.isItemAddedInCart = !album.isItemAddedInCart
            onItemClickListenerRecyclerView.onItemClick(album)
            notifyDataSetChanged()
        }
    }


    fun addListData(albumListData: List<ITunesResultResponse.Result>) {
        albumList.clear()
        albumList.addAll(albumListData)
        notifyDataSetChanged()
    }


    interface OnItemClickListenerRecyclerView {
        fun onItemClick(album: ITunesResultResponse.Result)
    }
}

