package com.yazhiyue.stylish.payment

import android.annotation.SuppressLint
import android.app.Application
import android.graphics.Color
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.*
import com.yazhiyue.stylish.R
import com.yazhiyue.stylish.data.source.local.StylishDatabaseDao
import tech.cherri.tpdirect.api.*
import tech.cherri.tpdirect.model.TPDStatus

class PaymentViewModel(
    private val databaseDao: StylishDatabaseDao, application: Application
) : AndroidViewModel(application) {

    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext

    // get products in cart from Room database
    val products = databaseDao.getAllProduct()

    val name = MutableLiveData<String>()

    val email = MutableLiveData<String>()

    val phone = MutableLiveData<String>()

    val address = MutableLiveData<String>()

    val selectedTimeRadio = MutableLiveData<Int>()


    private val shippingTime: String
        get() = when (selectedTimeRadio.value) {
            R.id.radio_shipping_morning -> context.resources.getString(R.string.morning)
            R.id.radio_shipping_afternoon -> context.resources.getString(R.string.afternoon)
            R.id.radio_shipping_anytime -> context.resources.getString(R.string.anytime)
            else -> ""
        }

    val selectedPaymentMethodPosition = MutableLiveData<Int>()

    val paymentMethod: LiveData<PaymentMethod> =
        Transformations.map(selectedPaymentMethodPosition) {
            PaymentMethod.values()[it]
        }

    val totalPrice: LiveData<Int> = Transformations.map(products) {
        var totalPrice = 0
        products.value?.let {
            for (product in it) {
                product.quantity?.let { quantity ->
                    totalPrice += (product.price * quantity)
                }
            }
        }
        totalPrice
    }

    private val totalFreight = 60

    val totalOrderPrice = Transformations.map(totalPrice) {
        var totalOrderPrice = 0
        totalOrderPrice += (totalPrice.value!!.plus(totalFreight))
        totalOrderPrice
    }


    // Handle the error for checkout
    private val _invalidCheckout = MutableLiveData<Int?>()

    val invalidCheckout: LiveData<Int?>
        get() = _invalidCheckout


    var tpdCard: TPDCard? = null

    private var isCanGetPrime: Boolean = false

    var tpdErrorMessage: String = ""


    // Handle navigation to checkout success page
    private val _navigateToCheckoutSuccess = MutableLiveData<Boolean?>()

    val navigateToCheckoutSuccess: MutableLiveData<Boolean?>
        get() = _navigateToCheckoutSuccess


    fun prepareCheckout() {
        shippingTime?.let { Log.i("selectedTime", "${paymentMethod.value}") }
        when {
            name.value.isNullOrEmpty() -> _invalidCheckout.value = INVALID_FORMAT_NAME_EMPTY
            email.value.isNullOrEmpty() -> _invalidCheckout.value = INVALID_FORMAT_EMAIL_EMPTY
            phone.value.isNullOrEmpty() -> _invalidCheckout.value = INVALID_FORMAT_PHONE_EMPTY
            address.value.isNullOrEmpty() -> _invalidCheckout.value = INVALID_FORMAT_ADDRESS_EMPTY
            shippingTime.isEmpty() -> _invalidCheckout.value = INVALID_FORMAT_TIME_EMPTY

            paymentMethod.value == PaymentMethod.CREDIT_CARD && !isCanGetPrime ->
                _invalidCheckout.value = CREDIT_CART_FORMAT_INCORRECT

            !phone.value.isNullOrEmpty() && (!phone.value!!.isDigitsOnly() || phone.value!!.length != 10
                    || phone.value!![0] != '0' || phone.value!![1] != '9') ->
                _invalidCheckout.value = INVALID_FORMAT_PHONE_INCORRECT

            else -> _invalidCheckout.value = null
        }
    }

    private val tpdTokenSuccessCallback = { token: String, _: TPDCardInfo, _: String ->

    }

    // it will occur when get prime failure
    private val tpdTokenFailureCallback = { status: Int, reportMsg: String ->
        tpdErrorMessage = status.toString() + reportMsg
        _invalidCheckout.value = CREDIT_CART_PRIME_FAIL
    }

    /**
     * Set up the [TPDForm]
     */
    fun setupTpd(tpdForm: TPDForm) {

        TPDSetup.initInstance(
            tpdForm.context,
            context.resources.getString(R.string.tp_app_id).toInt(),
            context.resources.getString(R.string.tp_app_key),
            TPDServerType.Sandbox
        )

        tpdErrorMessage = context.resources.getString(R.string.tpd_general_error)
        isCanGetPrime = false


        tpdForm.setTextErrorColor(ContextCompat.getColor(context, R.color.red_d0021b))
        tpdForm.setOnFormUpdateListener { tpdStatus ->
            when {
                tpdStatus.cardNumberStatus == TPDStatus.STATUS_ERROR ->
                    tpdErrorMessage = context.resources.getString(R.string.tpd_card_number_error)
                tpdStatus.expirationDateStatus == TPDStatus.STATUS_ERROR ->
                    tpdErrorMessage =
                        context.resources.getString(R.string.tpd_expiration_date_error)
                tpdStatus.ccvStatus == TPDStatus.STATUS_ERROR ->
                    tpdErrorMessage = context.resources.getString(R.string.tpd_ccv_error)
                !tpdStatus.isCanGetPrime ->
                    tpdErrorMessage = context.resources.getString(R.string.tpd_general_error)
            }
            isCanGetPrime = tpdStatus.isCanGetPrime
        }

        tpdCard = TPDCard.setup(tpdForm)
            .onSuccessCallback(tpdTokenSuccessCallback)
            .onFailureCallback(tpdTokenFailureCallback)
    }

    fun navigateToCheckoutSuccess() {
        _navigateToCheckoutSuccess.value = true
    }

    fun onCheckoutSuccessNavigated() {
        _navigateToCheckoutSuccess.value = null
    }

    companion object {

        const val INVALID_FORMAT_NAME_EMPTY = 0x11
        const val INVALID_FORMAT_EMAIL_EMPTY = 0x12
        const val INVALID_FORMAT_PHONE_EMPTY = 0x13
        const val INVALID_FORMAT_PHONE_INCORRECT = 0x14
        const val INVALID_FORMAT_ADDRESS_EMPTY = 0x15
        const val INVALID_FORMAT_TIME_EMPTY = 0x16
        const val CREDIT_CART_FORMAT_INCORRECT = 0x17
        const val CREDIT_CART_PRIME_FAIL = 0x18

    }
}