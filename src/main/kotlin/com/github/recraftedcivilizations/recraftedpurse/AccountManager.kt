package com.github.recraftedcivilizations.recraftedpurse

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import java.util.UUID

class AccountManager(val isCached: Boolean, val deathTaxAmount: Double, private val accountParser: AccountParser): Listener {
    private var accountsCache: MutableMap<UUID, Account>?

    init {
        if (isCached) {
            this.accountsCache = emptyMap<UUID, Account>().toMutableMap()
        }else{
            this.accountsCache = null
        }
    }

    /**
     * Remove a give amount from a players purse
     */
    fun payWithPurse(uuid: UUID, amount: Int): Boolean {

        return if (amount > 0){

            val acc = getAccountOf(uuid)
            val suc = acc.withdrawFromPurse(amount)

            saveAccountChanges(acc)

            return suc

        }else{
            false
        }

    }

    /**
     * Remove a given amount from a players bank savings
     */
    fun payWithBank(uuid: UUID, amount: Int): Boolean{
        return if(amount > 0){
            val acc = getAccountOf(uuid)
            val suc = acc.withdrawFromBank(amount)
            saveAccountChanges(acc)
            return suc
        }else{
            false
        }
    }

    fun hasInPurse(uuid: UUID, amount: Int): Boolean{
        return if(amount > 0){
            val acc = getAccountOf(uuid)
            acc.purseBalance >= amount
        }else{
            false
        }
    }

    fun hasInBank(uuid: UUID, amount: Int): Boolean{
        return if(amount > 0){
            val acc = getAccountOf(uuid)
            acc.bankBalance >= amount
        }else{
            false
        }
    }

    fun bankBalance(uuid: UUID): Int{
        val acc = getAccountOf(uuid)
        return acc.bankBalance
    }

    fun purseBalance(uuid: UUID): Int{
       val acc = getAccountOf(uuid)
       return acc.purseBalance
    }

    fun depositToBank(uuid: UUID, amount: Int): Boolean{
        val acc = getAccountOf(uuid)
        return acc.depositToBank(amount)
    }

    fun depositToPurse(uuid: UUID, amount: Int): Boolean{
        val acc = getAccountOf(uuid)
        return acc.depositToPurse(amount)
    }

    /**
     * Check if a player has an account
     * In the end this will always be true
     * but we need to call getAccount of to create a new account if necessary
     */
    fun hasAccount(uuid: UUID) : Boolean{
        getAccountOf(uuid)
        return true
    }

    fun accountValue(uuid: UUID): Int{
        return getAccountOf(uuid).value()
    }

    private fun getAccountOf(uuid: UUID): Account{

        // If we have the account cached use it if not try to load it
        val acc : Account? = if (isCached){
            accountsCache!![uuid] ?: loadAccount(uuid)
        }else{
            loadAccount(uuid)
        }

        // If acc is null aka if the player doesn't have an acc yet create one
        // this shouldn t happen anyway but let s make sure
        return acc ?: createAccount(uuid)
    }

    private fun createAccount(uuid: UUID): Account{
        val acc = Account(uuid, 0, 0)

        saveAccount(acc)

        if (isCached){
            accountsCache!![uuid] = acc
        }

        return acc
    }

    @EventHandler
    fun onPlayerDeath(playerDeathEvent: PlayerDeathEvent){
        val player = playerDeathEvent.entity

        val acc = getAccountOf(player.uniqueId)

        acc.deathTax(deathTaxAmount)

        saveAccountChanges(acc)
    }

    private fun loadAccount(uuid: UUID) : Account?{
        return accountParser.loadAccount(uuid)
    }

    /**
     * Used to save an account to the data file
     */
    private fun saveAccount(account: Account){
        accountParser.saveAccount(account)
    }

    /**
     * Used to save changes to an account if
     * this is cached don't write the changes to the file
     * if not cached this will write the changes
     */
    private fun saveAccountChanges(account: Account){
        if(!isCached){
            saveAccount(account)
        }

    }

}