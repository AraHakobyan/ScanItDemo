package com.scan.scanit.base.view

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.scan.scanit.R
import com.example.scanitdemo.view.BaseActivity

/**
 * Created by Ara Hakobyan on 3/12/2020.
 * Company IDT
 */
abstract class BaseBottomSheetDialogFragment: BottomSheetDialogFragment() {

    protected var fragmentView: View? = null

    protected var fragmentWasCreated: Boolean = false

    protected var isVisibleToUser: Boolean = true

    abstract val peekHeight: Int

    open val topMargin: Int? = null

    var baseActivity: BaseActivity<*>? = null
        get() = requireActivity() as BaseActivity<*>

    override fun getTheme(): Int = R.style.Hivandanoc_Theme_BottomSheetStyle

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        loadIntentExtras()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (fragmentView == null) {
            fragmentView = inflater.inflate(onCreateView(), null, false)
        }
        return fragmentView
    }

    private fun setupPeekHeight() {
        dialog?.setOnShowListener { dialog ->
            val d = dialog as BottomSheetDialog
            val bottomSheetInternal = d.findViewById<View>(R.id.design_bottom_sheet) as View
            BottomSheetBehavior.from<View>(bottomSheetInternal).peekHeight =
                peekHeight
            topMargin?.let {
                bottomSheetInternal.layoutParams =
                    (bottomSheetInternal.layoutParams as CoordinatorLayout.LayoutParams).apply {
                        topMargin = it
                    }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupPeekHeight()
        if (fragmentWasCreated.not()) {
            setupView()
        }
        fragmentWasCreated = true
    }

    override fun setUserVisibleHint(isVisible: Boolean) {
        super.setUserVisibleHint(isVisible)
        isVisibleToUser = isVisible
    }

    @LayoutRes
    abstract fun onCreateView(): Int

    abstract fun setupView()

    open fun loadIntentExtras() = Unit
}