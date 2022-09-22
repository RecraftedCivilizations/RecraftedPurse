package com.github.recraftedcivilizations.recraftedpurse.coins

import com.github.recraftedcivilizations.recraftedpurse.gui.elements.setLore
import com.github.recraftedcivilizations.recraftedpurse.gui.elements.setName
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class CoinItem(val name: String, val lore: String, val material: Material, val value: Int){

    fun isCoin(itemStack: ItemStack) : Boolean{
        return true
    }

    fun makeCoin(amount: Int): ItemStack{
        val coinStack = ItemStack(material, amount)
        coinStack.setName(name)
        coinStack.setLore(lore)
        return coinStack

    }

}