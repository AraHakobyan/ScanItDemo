package com.scan.scanit.base.view

import com.example.scanitdemo.view.BaseActivity
import com.scan.scanit.scan.ScanActivityViewModel
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView

/**
 * Created by Ara Hakobyan on 8/11/2020.
 * Company IDT
 */
abstract class BaseScanActivity: BaseActivity<ScanActivityViewModel>(), ZXingScannerView.ResultHandler {
    open var scannerView: ZXingScannerView? = null

    override fun onResume() {
        super.onResume()
        scannerView?.setResultHandler(this)
        scannerView?.startCamera()
    }

    override fun handleResult(rawResult: Result?) {
        scannerView?.resumeCameraPreview(this)
        viewModel.barcodeReady(rawResult)
    }

    override fun onPause() {
        super.onPause()
        scannerView?.stopCamera()
    }
}