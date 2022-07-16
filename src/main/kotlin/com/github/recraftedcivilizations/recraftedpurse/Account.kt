package com.github.recraftedcivilizations.recraftedpurse

import kotlin.math.roundToInt


data class Account(var bankBalance: Int, var purseBalance: Int) {


    fun depositToBank(amount: Int): Boolean {

        return if (purseBalance >= amount) {
            this.purseBalance -= amount
            this.bankBalance += amount
            true
        } else {
            false
        }

    }

    fun depositToPurse(amount: Int): Boolean {
        this.purseBalance += amount
        return true

    }

    fun withdrawFromPurse(amount: Int): Boolean {
        return if (this.purseBalance >= amount) {
            this.purseBalance -= amount
            true
        } else {
            false
        }
    }

    fun withdrawFromBank(amount: Int): Boolean {
        return if (this.bankBalance >= amount) {
            this.bankBalance -= amount
            true
        } else {
            false
        }
    }

    fun deathTax(deathTaxAmount: Double) {

        val amountToTax: Int = (this.purseBalance * deathTaxAmount).roundToInt()
        this.purseBalance -= amountToTax
    }

    fun value(): Int {
        return this.purseBalance + this.bankBalance
    }
}