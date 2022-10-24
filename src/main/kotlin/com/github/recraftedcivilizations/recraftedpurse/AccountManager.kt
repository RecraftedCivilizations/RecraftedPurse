package com.github.recraftedcivilizations.recraftedpurse

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import java.util.UUID

/**
 * Manage all accounts, this is mostly a wrapper class to [Account]
 * @param isCached Cache accounts or save them to the disk every time
 * @param deathTaxAmount The amount to tax on death as decimal
 * @param accountParser The account parser to write to the disk
 */
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
     * @param uuid The uuid of the player
     * @param amount The amount to pay
     * @return True if the player has enough money else false
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
     * @param uuid The uuid of the player
     * @param amount The amount to remove
     * @return True if the player has enough money in the bank else false
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

    /**
     * Check if a player has the given amount in his purse
     * @param uuid The uuid of the player
     * @param amount The amount to check for
     * @return True if the player has enough money else false
     */
    fun hasInPurse(uuid: UUID, amount: Int): Boolean{
        return if(amount > 0){
            val acc = getAccountOf(uuid)
            acc.purseBalance >= amount
        }else{
            false
        }
    }

    /**
     * Check if a player has the given amount in his bank
     * @param uuid The uuid of the player
     * @param amount The amount to check for
     * @return True if the player has enough money else false
     */
    fun hasInBank(uuid: UUID, amount: Int): Boolean{
        return if(amount > 0){
            val acc = getAccountOf(uuid)
            acc.bankBalance >= amount
        }else{
            false
        }
    }

    /**
     * Get the bank balance of a given player
     * @param uuid The uuid of the player
     * @return The bank balance of the player
     */
    fun bankBalance(uuid: UUID): Int{
        val acc = getAccountOf(uuid)
        return acc.bankBalance
    }

    /**
     * Get the purse balance of a given player
     * @param uuid The uuid of the player
     * @return The purse balance of the player
     */
    fun purseBalance(uuid: UUID): Int{
       val acc = getAccountOf(uuid)
       return acc.purseBalance
    }

    /**
     * Deposit an amount from the purse to the bank
     * @param uuid The player uuid to deposit to
     * @param amount The amount to deposit
     * @return True if enough money is in the purse else false
     */
    fun depositToBank(uuid: UUID, amount: Int): Boolean{
        val acc = getAccountOf(uuid)
        val res = acc.depositToBank(amount)
        saveAccountChanges(acc)
        return res
    }

    /**
     * Deposit an amount to the purse
     * @param uuid The uuid of the account holder
     * @param amount The amount to deposit
     * @return Always true don't ask
     */
    fun depositToPurse(uuid: UUID, amount: Int): Boolean{
        val acc = getAccountOf(uuid)
        val res = acc.depositToPurse(amount)
        saveAccountChanges(acc)
        return res
    }

    /**
     * Check if a player has an account
     * In the end this will always be true,
     * but we need to call getAccount of to create a new account if necessary
     * @param uuid The uuid of the player to check
     * @return Always true, don't ask
     */
    fun hasAccount(uuid: UUID) : Boolean{
        getAccountOf(uuid)
        return true
    }

    /**
     * Get the account value of a given player
     * @param uuid The player to get the account value from
     * @return The value of the account
     */
    fun accountValue(uuid: UUID): Int{
        return getAccountOf(uuid).value()
    }

    /**
     * Load all accounts
     * @return A list with all accounts
     */
    fun getAllAccounts(): List<Account>{
        return accountParser.loadAllAccounts()
    }

    /**
     * Get the account of a given player, if he has no account create one
     * @param uuid The uuid of the player to get the account from
     * @return The account of the player
     */
    private fun getAccountOf(uuid: UUID): Account{

        // If we have the account cached use it if not try to load it
        var acc: Account?
        if (isCached){
            acc = accountsCache!![uuid]

            if(acc == null){
                acc = loadAccount(uuid)
                // We loaded the account now cache it
                if (acc != null){
                    accountsCache!![uuid] = acc
                }
            }

        }else{
            acc = loadAccount(uuid)
        }

        // If acc is null aka if the player doesn't have an acc yet create one
        // this shouldn't t happen anyway but let s make sure
        return acc ?: createAccount(uuid)
    }

    /**
     * Create a new account for a player
     * @param uuid The uuid of the player to create the account for
     */
    private fun createAccount(uuid: UUID): Account{
        val acc = Account(uuid, 0, 0)

        saveAccount(acc)

        if (isCached){
            accountsCache!![uuid] = acc
        }

        return acc
    }

    /**
     * Apply the death tax, don't call manually
     */
    @EventHandler
    fun onPlayerDeath(playerDeathEvent: PlayerDeathEvent){
        val player = playerDeathEvent.entity

        val acc = getAccountOf(player.uniqueId)

        acc.deathTax(deathTaxAmount)

        saveAccountChanges(acc)
    }

    /**
     * Load a given account from the parser
     * @param uuid the uuid of the player to load for
     * @return The Account or null if no account exists
     */
    private fun loadAccount(uuid: UUID) : Account?{
        return accountParser.loadAccount(uuid)
    }

    /**
     * Used to save an account to the data file
     * @param account The account to save
     */
    private fun saveAccount(account: Account){
        accountParser.saveAccount(account)
    }

    /**
     * Used to save changes to an account if
     * this is cached don't write the changes to the file
     * if not cached this will write the changes
     * @param account The account
     */
    private fun saveAccountChanges(account: Account){
        if(!isCached){
            saveAccount(account)
        }

    }

}