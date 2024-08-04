package com.jecsdev.easyloan.utils.formatters

import java.util.Locale

/**
 * Object to handle string formatters from whole app.
 */
object StringFormatter {
    /**
     * Creates a new amount formatter from an amount.
     */
    fun amountFormatter(amount: Double): String = String.format(Locale.getDefault(), "$%,.2f", amount)
}