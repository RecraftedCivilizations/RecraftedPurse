package com.github.recraftedcivilizations.recraftedpurse

import net.milkbowl.vault.economy.Economy
import net.milkbowl.vault.economy.EconomyResponse
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import java.util.UUID
import kotlin.math.roundToInt

class VaultConnector(val accountManager: AccountManager) : Economy {
    override fun isEnabled(): Boolean {
        return true
    }

    override fun getName(): String {
        return "RecraftedPurse"
    }

    override fun hasBankSupport(): Boolean {
        return false
    }

    override fun fractionalDigits(): Int {
        return 0
    }

    override fun format(amount: Double): String {
        return "$amount${currencyNameSingular()}"
    }

    override fun currencyNamePlural(): String {
        TODO("Not yet implemented")
    }

    override fun currencyNameSingular(): String {
        TODO("Not yet implemented")
    }

    @Deprecated("Deprecated in Java")
    override fun hasAccount(playerName: String?): Boolean {
        if(playerName != null){
            val uuid = Bukkit.getPlayer(playerName)?.uniqueId ?: return false
            return accountManager.hasAccount(uuid)
        }else{
            return false
        }
    }

    override fun hasAccount(player: OfflinePlayer?): Boolean {
        val uuid = player?.uniqueId ?: return false
        return accountManager.hasAccount(uuid)
    }

    @Deprecated("Deprecated in Java")
    override fun hasAccount(playerName: String?, worldName: String?): Boolean {
        return hasAccount(playerName)
    }

    override fun hasAccount(player: OfflinePlayer?, worldName: String?): Boolean {
        return hasAccount(player)
    }

    @Deprecated("Deprecated in Java")
    override fun getBalance(playerName: String?): Double {
       val uuid = getUUID(playerName) ?: return 0.0
        return accountManager.bankBalance(uuid).toDouble()
    }

    override fun getBalance(player: OfflinePlayer?): Double {
        val uuid = player?.uniqueId ?: return 0.0
        return accountManager.bankBalance(uuid).toDouble()
    }

    @Deprecated("Deprecated in Java")
    override fun getBalance(playerName: String?, world: String?): Double {
        return getBalance(playerName)
    }

    override fun getBalance(player: OfflinePlayer?, world: String?): Double {
        return getBalance(player)
    }

    @Deprecated("Deprecated in Java")
    override fun has(playerName: String?, amount: Double): Boolean {
        if(playerName != null) {
            val uuid = Bukkit.getPlayer(playerName)?.uniqueId ?: return false
            return accountManager.hasInBank(uuid, amount.roundToInt())

        }else{
            return false
        }

    }

    override fun has(player: OfflinePlayer?, amount: Double): Boolean {
        val uuid = player?.uniqueId ?: return false
        return accountManager.hasInBank(uuid, amount.roundToInt())
    }

    @Deprecated("Deprecated in Java", ReplaceWith("has(playerName, amount)"))
    override fun has(playerName: String?, worldName: String?, amount: Double): Boolean {
        return has(playerName, amount)
    }

    override fun has(player: OfflinePlayer?, worldName: String?, amount: Double): Boolean {
        return has(player, amount)
    }

    @Deprecated("Deprecated in Java")
    override fun withdrawPlayer(playerName: String?, amount: Double): EconomyResponse {

        val uuid: UUID
        if (playerName != null){
            uuid = Bukkit.getPlayer(playerName)?.uniqueId ?: return EconomyResponse(amount, 0.0, EconomyResponse.ResponseType.FAILURE, "There is no such player/account")
        }else{
            return EconomyResponse(amount, 0.0, EconomyResponse.ResponseType.FAILURE, "There is no such player/account")
        }

        val suc = accountManager.payWithBank(uuid, amount.roundToInt())
        return if (suc){
            EconomyResponse(amount.roundToInt().toDouble(), accountManager.bankBalance(uuid).toDouble(), EconomyResponse.ResponseType.SUCCESS, null)
        }else{
            EconomyResponse(amount.roundToInt().toDouble(), accountManager.bankBalance(uuid).toDouble(), EconomyResponse.ResponseType.FAILURE, "Invalid amount or not enough money")
        }

    }

    override fun withdrawPlayer(player: OfflinePlayer?, amount: Double): EconomyResponse {
        val uuid = player!!.uniqueId

        val suc = accountManager.payWithBank(uuid, amount.roundToInt())
        return if(suc){
            EconomyResponse(amount.roundToInt().toDouble(), accountManager.bankBalance(uuid).toDouble(), EconomyResponse.ResponseType.SUCCESS, null)
        }else{
            EconomyResponse(amount.roundToInt().toDouble(), accountManager.bankBalance(uuid).toDouble(), EconomyResponse.ResponseType.FAILURE, "Invalid amount or not enough money")
        }

    }

    @Deprecated("Deprecated in Java")
    override fun withdrawPlayer(playerName: String?, worldName: String?, amount: Double): EconomyResponse {
        return withdrawPlayer(playerName, amount)
    }

    override fun withdrawPlayer(player: OfflinePlayer?, worldName: String?, amount: Double): EconomyResponse {
        return withdrawPlayer(player, amount)
    }

    @Deprecated("Deprecated in Java")
    override fun depositPlayer(playerName: String?, amount: Double): EconomyResponse {

        val newAmount = amount.roundToInt().toDouble()

        val uuid: UUID
        if (playerName != null){
            uuid = Bukkit.getPlayer(playerName)?.uniqueId ?: return EconomyResponse(amount, 0.0, EconomyResponse.ResponseType.FAILURE, "There is no such player/account")
        }else{
            return EconomyResponse(newAmount, 0.0, EconomyResponse.ResponseType.FAILURE, "There is no such player/account")
        }

        val suc = accountManager.depositToBank(uuid, amount.roundToInt())
        return if(suc){
            EconomyResponse(newAmount, accountManager.bankBalance(uuid).toDouble(), EconomyResponse.ResponseType.SUCCESS, null)
        }else{
            EconomyResponse(newAmount, accountManager.bankBalance(uuid).toDouble(), EconomyResponse.ResponseType.FAILURE, "Not a valid amount")
        }

    }

    override fun depositPlayer(player: OfflinePlayer?, amount: Double): EconomyResponse {

        val newAmount = amount.roundToInt().toDouble()
        val uuid = player?.uniqueId ?: return EconomyResponse(newAmount, 0.0, EconomyResponse.ResponseType.FAILURE, "Not a valid player")

        val suc = accountManager.depositToBank(uuid, amount.roundToInt())
        return if(suc){
            EconomyResponse(newAmount, accountManager.bankBalance(uuid).toDouble(), EconomyResponse.ResponseType.SUCCESS, null)
        }else{
            EconomyResponse(newAmount, accountManager.bankBalance(uuid).toDouble(), EconomyResponse.ResponseType.FAILURE, "Not a valid amount")
        }

    }

    @Deprecated("Deprecated in Java")
    override fun depositPlayer(playerName: String?, worldName: String?, amount: Double): EconomyResponse {
        return depositPlayer(playerName, amount)
    }

    override fun depositPlayer(player: OfflinePlayer?, worldName: String?, amount: Double): EconomyResponse {
        return depositPlayer(player, amount)
    }

    @Deprecated("Deprecated in Java")
    override fun createBank(name: String?, player: String?): EconomyResponse {
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Not implemented")
    }

    override fun createBank(name: String?, player: OfflinePlayer?): EconomyResponse {
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Not implemented")
    }

    override fun deleteBank(name: String?): EconomyResponse {
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Not implemented")
    }

     override fun bankBalance(name: String?): EconomyResponse {
         return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Not implemented")
    }

    override fun bankHas(name: String?, amount: Double): EconomyResponse {
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Not implemented")
    }

    override fun bankWithdraw(name: String?, amount: Double): EconomyResponse {
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Not implemented")
    }

    override fun bankDeposit(name: String?, amount: Double): EconomyResponse {
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Not implemented")
    }

    @Deprecated("Deprecated in Java")
    override fun isBankOwner(name: String?, playerName: String?): EconomyResponse {
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Not implemented")
    }

    override fun isBankOwner(name: String?, player: OfflinePlayer?): EconomyResponse {
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Not implemented")
    }

    @Deprecated("Deprecated in Java")
    override fun isBankMember(name: String?, playerName: String?): EconomyResponse {
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Not implemented")
    }

    override fun isBankMember(name: String?, player: OfflinePlayer?): EconomyResponse {
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Not implemented")
    }

    override fun getBanks(): MutableList<String> {
        return emptyList<String>().toMutableList()
    }

    @Deprecated("Deprecated in Java")
    override fun createPlayerAccount(playerName: String?): Boolean {

        val uuid: UUID
        if (playerName != null){
            uuid = Bukkit.getPlayer(playerName)?.uniqueId ?: return false
        }else{
            return false
        }

        return accountManager.hasAccount(uuid)
    }

    override fun createPlayerAccount(player: OfflinePlayer?): Boolean {
        val uuid = player?.uniqueId ?: return false

        return accountManager.hasAccount(uuid)
    }

    @Deprecated("Deprecated in Java")
    override fun createPlayerAccount(playerName: String?, worldName: String?): Boolean {
        return createPlayerAccount(playerName)
    }

    override fun createPlayerAccount(player: OfflinePlayer?, worldName: String?): Boolean {
        return createPlayerAccount(player)
    }

    private fun getUUID(playerName: String?): UUID?{
        return if (playerName != null){
            Bukkit.getPlayer(playerName)?.uniqueId
        }else{
            null
        }
    }
}