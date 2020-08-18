package com.itunessearchassesment.view.fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.itunessearchassesment.MainActivity
import com.itunessearchassesment.R
import com.itunessearchassesment.databinding.FragmentITunesMusicBinding
import com.itunessearchassesment.middelware.listeners.header.IHeaderListener
import com.itunessearchassesment.middelware.model.ITunesResultResponse
import com.itunessearchassesment.middelware.viewmodel.CartItemViewModel
import com.itunessearchassesment.middelware.viewmodel.ITunesMusicViewModel
import com.itunessearchassesment.view.adapter.AlbumAdapter
import java.util.*

open class ITunesMusicFragment : Fragment(), AlbumAdapter.OnItemClickListenerRecyclerView {

    lateinit var viewModel: ITunesMusicViewModel
    lateinit var cartItemViewModel: CartItemViewModel
    lateinit var mBinding: FragmentITunesMusicBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding =
            FragmentITunesMusicBinding.inflate(inflater, container, false)
        hideKeyBoardOnTouch(mBinding.root)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ITunesMusicViewModel::class.java)
        cartItemViewModel =
            ViewModelProvider(requireActivity())[CartItemViewModel::class.java]
        mBinding.recyclerviewAlbum.adapter = AlbumAdapter(arrayListOf(), this)

        handleObserver()

        viewModel.getITunesResults()

        setToolBarData(requireActivity())

        mBinding.searchAlbum.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(newString: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.performFiltering(newText)
                return true
            }
        })
    }

    fun setToolBarData(activity: Activity) {
        if (activity is MainActivity) {
            activity.setToolBarData(
                title = getString(R.string.app_name),
                isCartHide = false,
                iHeaderListener = object : IHeaderListener {
                    override fun cartIconClick() {
                        Navigation.findNavController(requireView())
                            .navigate(ITunesMusicFragmentDirections.actionITunesMusicFragmentToFragmentCartItem())
                    }

                })
        }
    }

    private fun handleObserver() {
        viewModel.resultsMutableLiveData.observe(viewLifecycleOwner, Observer {
            val cartItems = cartItemViewModel.cartItemListMutableLiveData.value ?: arrayListOf()
            for (cartItem in cartItems) {
                for (result in it) {
                    if (cartItem.trackId == result.trackId) {
                        result.isItemAddedInCart = cartItem.isItemAddedInCart
                    }
                }
            }

            (mBinding.recyclerviewAlbum.adapter as AlbumAdapter).addListData(it)
        })
        viewModel.errorLiveData.observe(viewLifecycleOwner, Observer {
            if (it.message.isNotEmpty()) {
                Toast.makeText(requireActivity(), it.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onItemClick(album: ITunesResultResponse.Result) {
        cartItemViewModel.updateCartData(item = album, isAdd = album.isItemAddedInCart)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.collection_name -> {
                viewModel.sortByCollectionName()
                true
            }
            R.id.track_name -> {
                viewModel.sortByTrackName()
                true
            }
            R.id.artist_name -> {
                viewModel.sortByArtistName()
                true
            }
            R.id.collection_price -> {
                viewModel.sortByCollectionPrice()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    protected fun hideKeyBoardOnTouch(view: View) {
        if (view !is EditText) {
            view.setOnTouchListener { v: View?, event: MotionEvent? ->
                hideKeyBoard()
                false
            }
        }
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val innerView = view.getChildAt(i)
                hideKeyBoardOnTouch(innerView)
            }
        }
    }

    private fun hideKeyBoard() {
        val view = requireActivity()?.currentFocus
        if (view != null) {
            (Objects.requireNonNull(requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE)) as InputMethodManager).hideSoftInputFromWindow(
                view.windowToken,
                0
            )
        }
    }
}