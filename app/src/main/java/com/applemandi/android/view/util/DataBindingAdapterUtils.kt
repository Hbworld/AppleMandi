package com.applemandi.android.view.util

import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.applemandi.android.R
import com.applemandi.android.data.model.Village
import java.util.*


object DataBindingAdapterUtils {


    @BindingAdapter("buttonState")
    @JvmStatic
    fun setButtonState(button: Button, enabled: Boolean) {
        if (enabled) {
            button.isEnabled = true
            button.background =
                ContextCompat.getDrawable(button.context, R.drawable.bg_solid_green_round_rect)
        } else {
            button.isEnabled = false
            button.background =
                ContextCompat.getDrawable(button.context, R.drawable.bg_solid_grey_round_rect)

        }
    }

    @BindingAdapter("loyaltyIndex")
    @JvmStatic
    fun setLoyaltyIndex(textView: TextView, double: Double) {
        textView.text = textView.resources.getString(R.string.applied_loyalty_index, double)
    }

    @BindingAdapter("grossPrice")
    @JvmStatic
    fun setGrossPrice(textView: TextView, grossPrice : Double) {
        textView.text = textView.resources.getString(R.string.gross_price, grossPrice)
    }

    @BindingAdapter("villages")
    @JvmStatic
    fun setVillageDropDownAdapter(spinner: Spinner, villages: List<Village>?) {
        if(!villages.isNullOrEmpty()){
            val arrayAdapter = ArrayAdapter(spinner.context,
                R.layout.item_village_drop_down, villages)
            spinner.adapter = arrayAdapter
        }
    }

    @BindingAdapter("thankYou")
    @JvmStatic
    fun setThankYouText(textView: TextView, sellerName : String) {
        textView.text = textView.resources.getString(R.string.thank_you_seller, sellerName)
    }

    @BindingAdapter("grossPrice", "grossWeight")
    @JvmStatic
    fun setPriceConfirmText(textView: TextView, grossPrice : Double, grossWeight : Int) {
        textView.text = textView.resources.getString(R.string.price_confirmation, grossPrice, grossWeight)
    }


}