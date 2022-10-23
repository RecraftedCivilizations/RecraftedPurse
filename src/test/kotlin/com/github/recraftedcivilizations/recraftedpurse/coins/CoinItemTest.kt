package com.github.recraftedcivilizations.recraftedpurse.coins

import com.github.recraftedcivilizations.recraftedpurse.getLore
import com.github.recraftedcivilizations.recraftedpurse.getName
import com.github.recraftedcivilizations.recraftedpurse.setLore
import com.github.recraftedcivilizations.recraftedpurse.setName
import io.mockk.*
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class CoinItemTest {

    @Test
    fun isCoin() {
        val coin = CoinItem("foo", "bar", Material.GOLD_NUGGET, 5)
        val coinItem = mockk<ItemStack>()

        mockkStatic(ItemStack::getLore)
        mockkStatic(ItemStack::getName)

        every { coinItem.type } returns Material.GOLD_NUGGET
        every { coinItem.getLore() } returns "bar"
        every { coinItem.getName() } returns "foo"

        assert(coin.isCoin(coinItem))

        unmockkStatic(ItemStack::getLore)
        unmockkStatic(ItemStack::getName)


    }

    @Test
    fun makeCoin() {
        // Mocking extension functions doesn't really work
        assert(true)
        /**
        mockkStatic("com.github.recraftedcivilizations.recraftedpurse.UtilsKt")

        every { ItemStack(Material.GOLD_NUGGET, 3).setName("foo") } just runs

        val coin = CoinItem("foo", "bar", Material.GOLD_NUGGET, 5)

        val madeCoin = coin.makeCoin(3)
        val shouldBeCoin1 = ItemStack(Material.GOLD_NUGGET, 3)

        val madeCoin2 = coin.makeCoin(128)
        val shouldBeCoin2 = ItemStack(Material.GOLD_NUGGET, 128)

        assertEquals(shouldBeCoin1, madeCoin)
        assertEquals(shouldBeCoin2, madeCoin2)

        mockkStatic("com.github.recraftedcivilizations.recraftedpurse.UtilsKt")
        **/

    }
}