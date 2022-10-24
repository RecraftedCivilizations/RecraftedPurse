package com.github.recraftedcivilizations.recraftedpurse.guiItems

import com.github.recraftedcivilizations.recraftedpurse.AccountManager
import com.github.recraftedcivilizations.recraftedpurse.coins.CoinManager
import com.github.recraftedcivilizations.recraftedpurse.gui.Clickable
import com.github.recraftedcivilizations.recraftedpurse.gui.DisplayItem
import com.github.recraftedcivilizations.recraftedpurse.gui.InventoryGUI
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class DepositOpenItem(itemStack: ItemStack, private val coinManager: CoinManager, private val accountManager: AccountManager) : Clickable(itemStack) {
    private val depositGUI = InventoryGUI(9, "Deposit")

    init {
        val depositItem1 = DepositItem(ItemStack(Material.GREEN_WOOL), 1, coinManager, accountManager)
        val depositItem5 = DepositItem(ItemStack(Material.GREEN_WOOL), 5, coinManager, accountManager)
        val depositItem10 = DepositItem(ItemStack(Material.GREEN_WOOL), 10, coinManager, accountManager)
        val depositItem50 = DepositItem(ItemStack(Material.GREEN_WOOL), 50, coinManager, accountManager)
        val depositItem100 = DepositItem(ItemStack(Material.GREEN_WOOL), 100, coinManager, accountManager)

        depositGUI.addItem(depositItem1)
        depositGUI.addItem(depositItem5)
        depositGUI.addItem(depositItem10)
        depositGUI.addItem(depositItem50)
        depositGUI.addItem(depositItem100)

    }

    override fun onClick(player: Player) {
        depositGUI.show(player)
    }

    override fun clone(): DisplayItem {
       return DepositOpenItem(itemStack, coinManager, accountManager)
    }
}