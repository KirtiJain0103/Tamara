package com.example.tamara

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.widget.Toast
import co.tamara.sdk.PaymentResult
import co.tamara.sdk.TamaraPayment
import co.tamara.sdk.TamaraPaymentHelper
import com.example.tamara.databinding.ActivityMainBinding


const val API_URL = "https://api-sandbox.tamara.co/"

const val AUTH_TOKEN ="eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhY2NvdW50SWQiOiI0NWQwMzAzOC1kM2I2LTQ1ODctYWY2Ny1hNDNlY2FlYjFiZDMiLCJ0eXBlIjoibWVyY2hhbnQiLCJzYWx0IjoiOTIwNDFjZDVlOTJlNDQ1MDg1ZTQ2NzgyZWFhYTY3NjkiLCJpYXQiOjE1ODc3NjM2MzksImlzcyI6IlRhbWFyYSJ9.jPwKTNMwGFZ-gueED91piwXk8EA2OcqlYDlDZwg67Oo7ec7E-7Qao89BTJc-gfdyQ8542JqdAuGlg3PlC7-he5fhCUyGAbnEVy9r2pO8ROO9sINXCkdi-CotLTnO_ENWd6AMCnNlNx7hZ1wsVCVSQS6RNZjOm8iEdHxyCRU13pLWxzsSR2WGsqplVprgeMxSdMKjLatdGEk7ipp4mVUTPba4rSYvselIOfpVvX8XDN1y_aYPIIVlCtpLeJ7MRrkyH0LBZ-4b2Ac1iDAjg51K_qvYng4xNiNEyflmy9kmtkNIrPMP1IlAR0ZEx2epAlhKU7TgIiVC1bs0hjMe6M2QMQ"

const val NOTIFICATION_WEB_HOOK_URL = "https://www.ubuy.co.in"

val checkouturl = "https://checkout.tamara.co/checkout/98bcfd99-2f71-49dd-90b8-84bfda795174?locale=ar_SA&orderId=b0fe54cc-02c3-4735-8ad2-8a7feda6fc41&paymentType=PAY_BY_INSTALMENTS&show_item_images=with_item_images_shown&ajs_aid=b0fe54cc-02c3-4735-8ad2-8a7feda6fc41"
val success = "https://www.a.ubuy.com.kw/en/ubpay/tamara/redirect/success"
val failure = "https://www.a.ubuy.com.kw/en/ubpay/tamara/redirect/failure"
val cancel = "https://www.a.ubuy.com.kw/en/ubpay/tamara/redirect/cancel"

/*
*
* checkout url :https://checkout.tamara.co/checkout/98bcfd99-2f71-49dd-90b8-84bfda795174?locale=ar_SA&orderId=b0fe54cc-02c3-4735-8ad2-8a7feda6fc41&paymentType=PAY_BY_INSTALMENTS&show_item_images=with_item_images_shown&ajs_aid=b0fe54cc-02c3-4735-8ad2-8a7feda6fc41
2023-02-02 09:41:28.328  2608-2608  TagUbuy                 com.ubuy                             V  tamara payment: checkout ::https://checkout.tamara.co/checkout/98bcfd99-2f71-49dd-90b8-84bfda795174?locale=ar_SA&orderId=b0fe54cc-02c3-4735-8ad2-8a7feda6fc41&paymentType=PAY_BY_INSTALMENTS&show_item_images=with_item_images_shown&ajs_aid=b0fe54cc-02c3-4735-8ad2-8a7feda6fc41 success ::https://www.a.ubuy.com.kw/en/ubpay/tamara/redirect/success fail  ::https://www.a.ubuy.com.kw/en/ubpay/tamara/redirect/failure cancel  ::https://www.a.ubuy.com.kw/en/ubpay/tamara/redirect/cancel
*
* */


class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.payment.setOnClickListener {

            binding.webview.loadUrl(checkouturl)
            binding.webview.settings.javaScriptEnabled = true
            TamaraPayment.Companion.startPayment(this, checkouturl, success, failure, cancel)
        }

}

override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if(TamaraPaymentHelper.shouldHandleActivityResult(requestCode, resultCode, data)){
        val result = TamaraPaymentHelper.getData(data!!)
        Log.e("OnActivityResult", "$result")
        when(result?.status){
            PaymentResult.STATUS_CANCEL ->{
                Toast.makeText(this,"cancled",Toast.LENGTH_SHORT).show()
                Log.e("OnActivityResult", " Canceled ")
            }
            PaymentResult.STATUS_FAILURE -> {
                //Payment has occurred an error
                Toast.makeText(this,"Payment has occurred an error",Toast.LENGTH_SHORT).show()

            }
            PaymentResult.STATUS_SUCCESS -> {
                //Payment has been made successfully
                Toast.makeText(this,"Payment has been made successfully",Toast.LENGTH_SHORT).show()

            }
        }
    }
 }
}