package com.carveniche.begalileo.activities.payment

import android.os.AsyncTask
import android.util.Log

import com.razorpay.RazorpayClient
import org.json.JSONObject


class CapturePayment(amount: String,paymentId: String) : AsyncTask<Void, Void, Void>()
{
    val innerAmount:String = amount
    val innerPaymentId:String = paymentId
    override fun doInBackground(vararg params: Void?): Void? {
        try{
            var razorpayClient = RazorpayClient("rzp_test_CFYpymECku6QK2","si16YKWoezBkQ0mqZhUjMU7O")
            var captureOptions = JSONObject()
            captureOptions.put("amount",innerAmount)
            val payment = razorpayClient.Payments.capture(innerPaymentId, captureOptions)
            Log.d("Payment Captured",payment.toString())
        }
        catch (ae : Exception)
        {
            Log.d("ExcepCapture",ae.message)
        }
        return null
    }
}

