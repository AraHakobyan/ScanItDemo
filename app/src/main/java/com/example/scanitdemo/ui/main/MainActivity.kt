package com.example.scanitdemo.ui.main

import com.example.scanitdemo.R
import com.example.scanitdemo.view.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : BaseActivity<MainActivityViewModel>() {
    override fun onCreateView(): Int = R.layout.activity_main

    override fun initActivityViewModel() {
        viewModel = getViewModel()
    }

    override fun setupView() {

        loadingProgressBar = progressBarLoading
    }
}