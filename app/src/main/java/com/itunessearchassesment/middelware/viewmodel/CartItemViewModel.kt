package com.itunessearchassesment.middelware.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.itunessearchassesment.middelware.model.ITunesResultResponse

class CartItemViewModel : ViewModel() {

    var cartItemListMutableLiveData: MutableLiveData<ArrayList<ITunesResultResponse.Result>> =
        MutableLiveData(arrayListOf())


    fun updateCartData(item: ITunesResultResponse.Result, isAdd: Boolean) {
        val cartList = cartItemListMutableLiveData.value
        if (isAdd) {
            cartList?.add(item)
        } else {
            val it = cartList?.iterator()
            if (it != null) {
                while (it.hasNext()) {
                    if (item.trackId == it.next().trackId) {
                        it.remove()
                    }
                }
            } else {
                cartList?.remove(item)
            }
        }
        cartItemListMutableLiveData.value = cartList
    }
}
