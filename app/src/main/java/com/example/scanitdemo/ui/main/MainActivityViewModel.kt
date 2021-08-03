package com.example.scanitdemo.ui.main

import androidx.lifecycle.MutableLiveData
import com.example.scanitdemo.view.BaseActivityViewModel

/**
 * Created by Ara Hakobyan on 09.06.21.
 * Preezma
 */
class MainActivityViewModel( private val repository: MainRepository): BaseActivityViewModel() {
    val userIdLiveData: MutableLiveData<String> = MutableLiveData()


}