package com.github.recraftedcivilizations.recraftedpurse.coins

import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

class CoinManager(val coins: List<CoinItem>) {

    /**
     * Get the value of a coin
     * @param itemStack The item stack we want the coin value of
     * @return 0 if not a coin stack else the coin value * amount of coins
     */
    fun getValue(itemStack: ItemStack): Int{

        var value: Int = 0
        for (coin in coins){
            if (coin.isCoin(itemStack)){
                value += coin.value * itemStack.amount
                break
            }
        }

        return value
    }

    /**
     * Get all coin item stacks in an inventory
     * @param inv The inventory we want to check
     * @return A list with all coin stacks
     */
    fun getAllCoinsInInventory(inv: Inventory): List<ItemStack>{

        val foundCoins = emptyList<ItemStack>().toMutableList()
        for(itemStack in inv){
            for (coin in this.coins){
                if (coin.isCoin(itemStack)){
                   foundCoins.add(itemStack)
                }
            }
        }

        return foundCoins
    }


    /**
     * Create an amount as coins this only works correctly if we
     * have the right coins available, e.g. we need a 1 coin
     * @param value The value we want to create
     * @return A list of coin item stacks that match the value as well as possible
     */
    fun makeCoins(value: Int): List<ItemStack>{

        val outputCoins = emptyList<ItemStack>().toMutableList()
        var remainder = value
        for (coin in this.coins.sortedBy { it.value }.reversed()){
            val amount: Int = remainder / coin.value
            remainder %= coin.value
            outputCoins.add(coin.makeCoin(amount))
        }

        return outputCoins
    }

}