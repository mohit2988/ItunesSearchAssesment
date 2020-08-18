package com.itunessearchassesment.view.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.fragment.app.Fragment
import java.util.*

open class BaseFragment : Fragment() {
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

    protected fun hideKeyBoard() {
        val view = requireActivity().currentFocus
        if (view != null) {
            (Objects.requireNonNull(requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE)) as InputMethodManager).hideSoftInputFromWindow(
                view.windowToken,
                0
            )
        }
    }
}