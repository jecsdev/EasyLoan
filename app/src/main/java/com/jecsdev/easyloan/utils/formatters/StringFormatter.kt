package com.jecsdev.easyloan.utils.formatters

/**
 * Object to handle string formatters from whole app.
 */
object StringFormatter {
    /**
     * Creates a new amount formatter from an amount.
     */
    fun amountFormatter(amount: Double) : String{
        return String.format("$%,.2f", amount)
    }
}