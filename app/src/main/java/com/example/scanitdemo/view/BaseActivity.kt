package com.example.scanitdemo.view

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigator
import com.example.scanitdemo.R
import com.example.scanitdemo.extensions.hideKeyboard
import org.koin.android.ext.android.inject
import java.lang.Exception

/**
 * Created by Ara Hakobyan on 8/11/2020.
 * Company IDT
 */
abstract class BaseActivity<ACTIVITY_VIEW_MODEL : BaseActivityViewModel> : AppCompatActivity() {

    protected lateinit var viewModel: ACTIVITY_VIEW_MODEL
    lateinit var dialogBuilder: AlertDialog.Builder
    lateinit var alertDialog: AlertDialog
    var loadingProgressBar: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        initActivityViewModel()
        initObservers()
        super.onCreate(savedInstanceState)
        loadIntentExtras()
        onCreateView()?.let {
            setContentView(it)
            setupView()
            try {
                hideKeyboardOnOutsideClicked(window.decorView.rootView)
            } catch (ex: Exception) {
            }
        } ?: onCreateWithoutView()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun hideKeyboardOnOutsideClicked(rootView: View) {
        if (rootView !is EditText) {
            rootView.setOnTouchListener { v, event ->
                this.hideKeyboard()
                false
            }
        }

        if (rootView is ViewGroup) {
            for (i in 0 until (rootView as ViewGroup).childCount) {
                val innerView = (rootView as ViewGroup).getChildAt(i)
                hideKeyboardOnOutsideClicked(innerView)
            }
        }
    }

    fun navigate(id: Int, bundle: Bundle? = null, extras: FragmentNavigator.Extras? = null) {

        val navOptions: NavOptions = NavOptions.Builder()
            .setEnterAnim(R.anim.slide_in_from_right)
            .setExitAnim(R.anim.slide_out_to_left)
            .setPopEnterAnim(R.anim.slide_in_from_left)
            .setPopExitAnim(R.anim.slide_out_to_right)
            .build()

        findNavController(R.id.nav_host_fragment).navigate(id, bundle, navOptions, extras)
    }

    fun onBackBtnPressed(view: View) {
        findNavController(R.id.nav_host_fragment).navigateUp()
    }

    fun showInfoDialog(
        onPosBtnClicked: (() -> Unit)? = null,
        onNegButtonClicked: (() -> Unit)? = null,
        title: String = EMPTY,
        message: String = EMPTY,
        posBtnText: String = "Ok",
        negBtnText: String = "Cancel",
        isCancelable: Boolean = false
    ) {
        dialogBuilder = AlertDialog.Builder(this)
        alertDialog = dialogBuilder.create()
        dialogBuilder
            .setTitle(title)
            .setMessage(message)
            .setCancelable(isCancelable)
        onNegButtonClicked?.let {
            dialogBuilder.setNegativeButton(negBtnText) { _: DialogInterface?, _: Int ->
                onNegButtonClicked.invoke()
                alertDialog.dismiss()
            }
        }
        dialogBuilder.setPositiveButton(posBtnText) { _: DialogInterface?, _: Int ->
            onPosBtnClicked?.invoke()
            alertDialog.dismiss()
        }
        dialogBuilder.show()
    }

    @LayoutRes
    abstract fun onCreateView(): Int?

    abstract fun initActivityViewModel()

    abstract fun setupView()

    open fun initObservers() {
//        viewModel.errorLiveData.observe(this, Observer {
//            when (it.errorCode) {
//
//            }
//        })
//        viewModel.isLoadingLiveData.observe(this, Observer {
//            loadingProgressBar?.visibility = if (it) View.VISIBLE else View.GONE
//        })
    }

    open fun loadIntentExtras() = Unit

    open fun onCreateWithoutView() = Unit
}

const val EMPTY = ""