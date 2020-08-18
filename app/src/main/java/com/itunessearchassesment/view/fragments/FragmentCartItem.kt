package com.itunessearchassesment.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.itunessearchassesment.MainActivity
import com.itunessearchassesment.R
import com.itunessearchassesment.databinding.FragmentCartItemBinding
import com.itunessearchassesment.middelware.model.ITunesResultResponse
import com.itunessearchassesment.middelware.viewmodel.CartItemViewModel
import com.itunessearchassesment.view.adapter.AlbumAdapter

class FragmentCartItem : BaseFragment(), AlbumAdapter.OnItemClickListenerRecyclerView {

    lateinit var mBinding: FragmentCartItemBinding
    lateinit var cartItemViewModel: CartItemViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentCartItemBinding.inflate(inflater, container, false)
        hideKeyBoard()
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cartItemViewModel =
            ViewModelProvider(requireActivity())[CartItemViewModel::class.java]

        (requireActivity() as MainActivity).setToolBarData(
            title = getString(R.string.cart),
            isCartHide = true
        )

        mBinding.recyclerviewCart.adapter =
            AlbumAdapter(
                cartItemViewModel.cartItemListMutableLiveData.value ?: arrayListOf(),
                this
            )
    }

    override fun onItemClick(album: ITunesResultResponse.Result) {
        cartItemViewModel.updateCartData(item = album, isAdd = album.isItemAddedInCart)
    }

}