package com.github.recraftedcivilizations.recraftedpurse

import java.util.UUID
import kotlin.math.roundToInt

/**
 *  Represents a player account
 *  @param accountHolder The uuid of the player this account belongs to
 *  @param bankBalance The bank balance the account holder has
 *  @param purseBalance The purse balance the account holder has
 */
data class Account(val accountHolder: UUID, var bankBalance: Int, var purseBalance: Int) {

    /**
     * Deposit an amount from the purse to the bank
     * @param amount The amount to deposit
     * @return True if the transaction succeeds(aka the account holder has enough money in the purse)else false
     */
    fun depositToBank(amount: Int): Boolean {

        return if (purseBalance >= amount) {
            this.purseBalance -= amount
            this.bankBalance += amount
            true
        } else {
            false
        }

    }

    /**
     * Deposit an amount to the purse(you should remove your currency from the inventory)
     * @param amount The amount to deposit
     * @return Always true don't ask
     */
    fun depositToPurse(amount: Int): Boolean {
        this.purseBalance += amount
        return true

    }

    /**
     * Withdraw an amount from the purse(you should add the currency to the players' inv)
     * @param amount The amount to withdraw
     * @return If the player has enough money in his purse
     */
    fun withdrawFromPurse(amount: Int): Boolean {
        return if (this.purseBalance >= amount) {
            this.purseBalance -= amount
            true
        } else {
            false
        }
    }

    /**
     * Remove an amount from the holders bank(use for shops and co)
     * @param amount The amount to remove from the account
     * @return True if the player has enough money in his bank else false
     */
    fun withdrawFromBank(amount: Int): Boolean {
        return if (this.bankBalance >= amount) {
            this.bankBalance -= amount
            true
        } else {
            false
        }
    }

    /**
     * Apply a death tax to the players purse money
     * @param deathTaxAmount The amount to tax in decimal
     */
    fun deathTax(deathTaxAmount: Double) {

        val amountToTax: Int = (this.purseBalance * deathTaxAmount).roundToInt()
        this.purseBalance -= amountToTax
    }

    /**
     * Get the total value of this account
     * @return The purse balance + the bank balance
     */
    fun value(): Int {
        return this.purseBalance + this.bankBalance
    }

    /**
     * Serialize to a map
     * @return Return the account info's as map
     */
    fun asMap(): Map<String, Any>{
        return mapOf(Pair("accountHolder", accountHolder), Pair("purseBalance", purseBalance), Pair("bankBalance", bankBalance))
    }
}