package com.scan.scanit.base.view

import com.scan.scanit.base.BaseActivityViewModel
import com.scan.scanit.base.BaseFragmentViewModel
import com.example.scanitdemo.extensions.hideKeyboard
import com.scan.scanit.base.types.ErrorType
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import com.example.scanitdemo.view.BaseActivity
import com.scan.scanit.R

/**
 * Created by Ara Hakobyan on 8/11/2020.
 * Company IDT
 */
abstract class BaseFragment<F : BaseFragmentViewModel, A : BaseActivityViewModel> :
    Fragment() {

    lateinit var fragmentViewModel: F

    protected fun isFragmentViewModelInitialised(): Boolean = ::fragmentViewModel.isInitialized

    lateinit var activityViewModel: A

    var baseActivity: BaseActivity<*>? = null
        get() = requireActivity() as BaseActivity<*>

    override fun onAttach(context: Context) {
        super.onAttach(context)
        initFragmentViewModel()
        handleErrors()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initActivityViewModel()
        initActivityObservers()
        initFragmentObservers()
        fragmentViewModel.isLoadingLiveData.observe(this, Observer {
            activityViewModel.isLoadingLiveData.postValue(it)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(onCreateView(), null, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideKeyboard()
        loadIntentExtras()
        loadData()
        setupView()
        setupOnViewClicked()
    }

    fun navigate(directions: NavDirections) {
        hideKeyboard()
        findNavController().navigate(directions)
    }

    fun navigate(
        id: Int,
        bundle: Bundle? = null,
        extras: FragmentNavigator.Extras? = null
    ) {
        hideKeyboard()
        findNavController().navigate(id, bundle)
    }

    private fun handleErrors() {
        fragmentViewModel.errorLiveData.observe(this, Observer {
            when (it.errorCode) {
                ErrorType.ERROR_TYPE_BAD_SQL_CONNECTION -> {
                    baseActivity?.showInfoDialog(onPosBtnClicked = {
                        navigate(R.id.to_configFragment)
                    }, message = "change connection configs", isCancelable = false)
                }
                ErrorType.ERROR_TYPE_UNKNOWN -> {
                    baseActivity?.showInfoDialog(
                        message = "${it.errorMessage}",
                        isCancelable = true
                    )
                }
            }
        })
    }

    protected fun pushNestedFragment(
        fragment: Fragment,
        fragmentContainer: Int
    ) {
        hideKeyboard()
        childFragmentManager.commit {
            setReorderingAllowed(true)
            replace(fragmentContainer, fragment)
        }
    }

    fun addToChildFragment(
        @IdRes componentId: Int,
        component: Fragment
    ) {
        childFragmentManager.commit {
            setReorderingAllowed(true)
            replace(componentId, component)
        }
    }

    @LayoutRes
    abstract fun onCreateView(): Int

    abstract fun initActivityViewModel()

    abstract fun initFragmentViewModel()

    abstract fun setupView()

    open fun setupOnViewClicked() = Unit

    open fun loadIntentExtras() = Unit

    open fun loadData() = Unit

    open fun initActivityObservers() = Unit

    open fun initFragmentObservers() = Unit

    companion object {
        const val EXTRA_ERROR_GET_VIEW_MODEL = "error from getViewModel"
        const val EXTRA_FDOC_TYPE = "fDOC_TYPE"
        const val EXTRA_FISN = "fISN"
        const val EXTRA_FBODY = "FBODY"
    }
}