package com.carveniche.begalileo.activities.payment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.carveniche.begalileo.R
import com.carveniche.begalileo.util.showLongToast
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONObject
import java.util.*


class PaymentActivity : AppCompatActivity(),PaymentResultListener {
    var payAmount : String = "35000"
    override fun onPaymentError(p0: Int, p1: String?) {
        showLongToast(p1!!, this)
    }

    override fun onPaymentSuccess(p0: String?) {
        showLongToast(p0!!, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
    }

    fun startPayment()
    {
        var ch = Checkout()
        ch.setImage(R.drawable.rzp_logo)
        try {
            val options = JSONObject()


            options.put("name", "Test Carveniche")

            options.put("description", "Test Order #123456")

            options.put("currency", "INR")

            options.put("payment_capture",true)
            options.put("amount", payAmount)
            val num_id = arrayOf(12,45,67,78,90)
            val notes = JSONObject()
            notes.put("email", "test@carveniche.com")
            notes.put("contact", "1234567890")
            notes.put("num_id", Arrays.toString(num_id))
            options.put("Notes",notes)

            ch.open(this, options)


        } catch (e: Exception) {

        }

    }
}
