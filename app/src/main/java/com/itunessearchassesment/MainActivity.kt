package com.itunessearchassesment

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.itunessearchassesment.databinding.ActivityMainBinding
import com.itunessearchassesment.middelware.listeners.header.IHeaderListener
import com.itunessearchassesment.middelware.viewmodel.CartItemViewModel

class MainActivity : AppCompatActivity() {

    lateinit var mBinding: ActivityMainBinding
    lateinit var cartItemViewModel: CartItemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        cartItemViewModel =
            ViewModelProvider(this)[CartItemViewModel::class.java]

        setSupportActionBar(findViewById(R.id.toolbar))
        setToolBarData()

    }

    /*   override fun onCreateOptionsMenu(menu: Menu): Boolean {
           // Inflate the menu; this adds items to the action bar if it is present.
           menuInflater.inflate(R.menu.menu_main, menu)
           return true
       }*/

    fun setToolBarData(
        title: String = "",
        isCartHide: Boolean = false, iHeaderListener: IHeaderListener? = null
    ) {
        mBinding.title = title
        mBinding.clCart.setOnClickListener { iHeaderListener?.cartIconClick() }
        mBinding.clCart.visibility = if (isCartHide) View.GONE else View.VISIBLE
    }

}