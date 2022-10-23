package com.github.recraftedcivilizations.recraftedpurse.coins

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class CoinManagerTest {

    @Test
    fun getValue() {

        val coin1 = mockk<CoinItem>()
        val coinStack = mockk<ItemStack>()
        val nonCoinStack = mockk<ItemStack>()

        every { coin1.value } returns 1
        every { coin1.isCoin(coinStack) } returns true
        every { coin1.isCoin(nonCoinStack) }returns false

        every { coinStack.amount }returns 20
        every { nonCoinStack.amount }returns 10

        val manager = CoinManager(listOf(coin1))

        assertEquals(20, manager.getValue(coinStack))
        assertEquals(0, manager.getValue(nonCoinStack))

        verify { coin1.isCoin(coinStack) }
        verify { coin1.isCoin(nonCoinStack) }

    }

    @Test
    fun getAllCoinsInInventory() {

        val coin1 = mockk<CoinItem>()
        val coin2 = mockk<CoinItem>()
        val coinStack1 = mockk<ItemStack>()
        val coinStack2 = mockk<ItemStack>()
        val nonCoinStack1 = mockk<ItemStack>()
        val nonCoinStack2 = mockk<ItemStack>()
        val inv = mockk<Inventory>()

        every { inv.iterator() }returns mutableListOf(coinStack1,
            nonCoinStack1, coinStack2, nonCoinStack2).listIterator()

        every { coin1.isCoin(any()) } returns false
        every { coin1.isCoin(coinStack1) } returns true

        every { coin2.isCoin(any()) } returns false
        every { coin2.isCoin(coinStack2) } returns true

        val manager = CoinManager(listOf(coin1, coin2))

        val coinStacks = manager.getAllCoinsInInventory(inv)
        assertEquals(listOf(coinStack1, coinStack2), coinStacks)

    }

    @Test
    fun makeCoins() {

        val coin1 = mockk<CoinItem>()
        val coin2 = mockk<CoinItem>()
        val coin3 = mockk<CoinItem>()

        every { coin1.value } returns 1
        every { coin2.value } returns 5
        every { coin3.value } returns 3

        every {
            coin1.makeCoin(any())
        } answers {
            val itemStack = mockk<ItemStack>()
            every { itemStack.amount } returns firstArg() as Int
            every { coin1.isCoin(itemStack) } returns true
            itemStack
        }
        every {
            coin2.makeCoin(any())
        } answers {
            val itemStack = mockk<ItemStack>()
            every { itemStack.amount } returns firstArg() as Int
            every { coin2.isCoin(itemStack) } returns true
            itemStack
        }
        every {
            coin3.makeCoin(any())
        } answers {
            val itemStack = mockk<ItemStack>()
            every { itemStack.amount } returns firstArg() as Int
            every { coin3.isCoin(itemStack) } returns true
            itemStack
        }


        val manager = CoinManager(listOf(coin1, coin2))
        val coinList = manager.makeCoins(15)
        val coinList2 = manager.makeCoins(16)

        assertEquals(1,coinList.size)
        assertEquals(3, coinList[0].amount)
        assert(coin2.isCoin(coinList[0]))

        assertEquals(2,coinList2.size)
        assertEquals(3, coinList2[0].amount)
        assertEquals(1, coinList2[1].amount)
        assert(coin2.isCoin(coinList2[0]))
        assert(coin1.isCoin(coinList2[1]))

        val manager2 = CoinManager(listOf(coin3, coin2))
        val coinList3 = manager2.makeCoins(7)

        assertEquals(1, coinList3.size)
        assertEquals(1, coinList3[0].amount)
        assert(coin2.isCoin(coinList3[0]))



    }
}