package com.github.recraftedcivilizations.recraftedpurse.guiItems

import com.github.recraftedcivilizations.recraftedpurse.AccountManager
import com.github.recraftedcivilizations.recraftedpurse.coins.CoinManager
import io.mockk.*
import org.bukkit.entity.Item
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.PlayerInventory
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.util.*
import kotlin.collections.HashMap

internal class DepositItemTest {

    @Test
    fun onClickWithTotalAmount() {
        val player = mockk<Player>()
        val coinManager = mockk<CoinManager>()
        val accountManager = mockk<AccountManager>()
        val inv = mockk<PlayerInventory>()
        val coinsInInv = mockk<ItemStack>()
        val returnCoins = mockk<ItemStack>()
        val mockMap = HashMap<Int, ItemStack>()

        every { coinsInInv.amount } returns 64
        every { returnCoins.amount } returns 54
        every { player.inventory } returns inv
        every { player.uniqueId } returns UUID.randomUUID()
        every { inv.iterator() } returns listOf<ItemStack>(mockk())
            .toMutableList()
                .listIterator()

        every { inv.addItem(returnCoins) } returns mockMap
        every { inv.removeItem(coinsInInv) } returns mockMap

        every { coinManager.getValue(coinsInInv) } returns 64
        every { coinManager.makeCoins(54) } returns listOf(returnCoins)
        every { coinManager.getAllCoinsInInventory(inv) } returns listOf(coinsInInv)

        every { accountManager.depositToPurse(any(), any()) } returns true


        val depositItem = DepositItem(mockk(), 10, coinManager, accountManager)
        depositItem.onClick(player)

        verify { accountManager.depositToPurse(any(), 10) }
        verify { inv.addItem(returnCoins) }
        verify { inv.removeItem(coinsInInv) }

    }

    @Test
    fun onClickWithoutTotalAmount() {
        val player = mockk<Player>()
        val coinManager = mockk<CoinManager>()
        val accountManager = mockk<AccountManager>()
        val inv = mockk<PlayerInventory>()
        val coinsInInv = mockk<ItemStack>()
        val returnCoins = mockk<ItemStack>()
        val mockMap = HashMap<Int, ItemStack>()

        every { coinsInInv.amount } returns 64
        every { returnCoins.amount } returns 54
        every { player.inventory } returns inv
        every { player.uniqueId } returns UUID.randomUUID()
        every { inv.iterator() } returns listOf<ItemStack>(mockk())
            .toMutableList()
            .listIterator()

        every { coinManager.getValue(coinsInInv) } returns 64
        every { coinManager.getAllCoinsInInventory(inv) } returns listOf(coinsInInv)


        val depositItem = DepositItem(mockk(), 128, coinManager, accountManager)
        depositItem.onClick(player)

    }
}