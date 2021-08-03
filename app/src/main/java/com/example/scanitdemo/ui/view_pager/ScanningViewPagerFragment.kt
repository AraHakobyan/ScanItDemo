package com.example.scanitdemo.ui.view_pager

import com.example.scanitdemo.ui.main.MainActivityViewModel
import com.google.android.material.tabs.TabLayoutMediator
import com.scan.scanit.base.view.BaseFragment
import org.koin.androidx.viewmodel.ext.android.getViewModel
import java.lang.Exception


/**
 * Created by Ara Hakobyan on 19.06.21.
 * Preezma
 */
class ScanningViewPagerFragment : BaseFragment<ScanningPagerViewModel, MainActivityViewModel>() {
    override fun onCreateView(): Int = R.layout.scanning_pager_fragment

    override fun initActivityViewModel() {
        activityViewModel = baseActivity?.getViewModel() ?: throw Exception()
    }

    override fun initFragmentViewModel() {
        fragmentViewModel = getViewModel()
    }

    override fun initFragmentObservers() = Unit

    override fun initActivityObservers() {
        super.initActivityObservers()

    }

    override fun setupView() {
        initViewPager()
    }

    private fun initViewPager() {
        viewPager.run {
            adapter = ScanningDocumentsPagerAdapter(
                requireActivity(), arguments?.getString(EXTRA_FISN),
                arguments?.getInt(EXTRA_FDOC_TYPE)!!,
                arguments?.getString(EXTRA_FBODY),
                fromScanItDocument = arguments?.getBoolean(ScanningDocumentsFragment.EXTRA_IS_FROM_SCAN_IT_DOCUMENT) ?: false
            )
        }
        TabLayoutMediator(tabBar, viewPager) { tab, position ->
            tab.text = when (position) {
                1 -> getString(R.string.tab_item_list)
                else -> getString(R.string.tab_item_scan)
            }
        }.attach()
    }
}