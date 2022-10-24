package com.github.recraftedcivilizations.recraftedpurse.coins

import com.github.recraftedcivilizations.recraftedpurse.getLore
import com.github.recraftedcivilizations.recraftedpurse.getName
import com.github.recraftedcivilizations.recraftedpurse.setLore
import com.github.recraftedcivilizations.recraftedpurse.setName
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

/**
 * Represents a Coin
 * @param name The name of the coin item
 * @param lore The lore this coin should have
 * @param material The material this coin should consist off
 * @param value The value of a single coin
 */
class CoinItem(val name: String, val lore: String, val material: Material, val value: Int){

    /**
     * Check if an item stack is a coin of this type
     * @param itemStack The item stack to check
     * @return True if it is a coin of this type else false
     */
    fun isCoin(itemStack: ItemStack) : Boolean{
        return itemStack.type == material && itemStack.getLore() == lore && itemStack.getName() == name
    }

    /**
     * Make a new coin item stack of this type
     * @param amount The amount of coins to create
     * @return A new Item stack with this coin type and the given amount of coins
     */
    fun makeCoin(amount: Int): ItemStack{
        val coinStack = ItemStack(material, amount)
        coinStack.setName(name)
        coinStack.setLore(lore)
        return coinStack

    }

}