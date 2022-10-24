package com.github.recraftedcivilizations.recraftedpurse.guiItems

import com.github.recraftedcivilizations.recraftedpurse.AccountManager
import com.github.recraftedcivilizations.recraftedpurse.coins.CoinManager
import com.github.recraftedcivilizations.recraftedpurse.gui.Clickable
import com.github.recraftedcivilizations.recraftedpurse.gui.DisplayItem
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

//TODO("Weird things happen if stack size is over 64")
//TODO("Full inventory???")

class DepositItem(itemStack: ItemStack, val amount: Int, val coinManager: CoinManager, val accountManager: AccountManager) : Clickable(itemStack) {

    override fun onClick(player: Player) {
        val allCoins = coinManager.getAllCoinsInInventory(player.inventory)
        val totalValue = allCoins.sumOf { coinManager.getValue(it) }

        if(totalValue > amount){
            val newAmount = totalValue - amount
            val newCoins = coinManager.makeCoins(newAmount)

            allCoins.forEach{ player.inventory.removeItem(it) }
            newCoins.forEach { player.inventory.addItem(it) }

            accountManager.depositToPurse(player.uniqueId, amount)
        }


    }

    override fun clone(): DisplayItem {
        return DepositItem(itemStack, amount, coinManager, accountManager)
    }
}