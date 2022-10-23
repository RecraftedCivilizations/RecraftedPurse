package com.github.recraftedcivilizations.recraftedpurse.coins

import com.github.recraftedcivilizations.recraftedpurse.gui.elements.getLore
import com.github.recraftedcivilizations.recraftedpurse.gui.elements.getName
import com.github.recraftedcivilizations.recraftedpurse.gui.elements.setLore
import com.github.recraftedcivilizations.recraftedpurse.gui.elements.setName
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

/**
 * @param name The name of the coin item
 * @param lore The lore this coin should have
 * @param material The material this coin should consist off
 * @param value The value of a single coin
 */
class CoinItem(val name: String, val lore: String, val material: Material, val value: Int){

    fun isCoin(itemStack: ItemStack) : Boolean{
        return itemStack.type == material && itemStack.getLore() == lore && itemStack.getName() == name
    }

    fun makeCoin(amount: Int): ItemStack{
        val coinStack = ItemStack(material, amount)
        coinStack.setName(name)
        coinStack.setLore(lore)
        return coinStack

    }

}