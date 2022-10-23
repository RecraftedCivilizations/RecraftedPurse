package com.github.recraftedcivilizations.recraftedpurse

import org.bukkit.inventory.ItemStack

fun ItemStack.setName(name: String?){
    val meta = this.itemMeta
    meta?.setDisplayName(name)
    this.itemMeta = meta
}

fun ItemStack.setLore(vararg lore: String){
    val meta = this.itemMeta
    meta?.lore = lore.toMutableList()
}

fun ItemStack.getLore(): String{
    val meta = this.itemMeta
    return meta?.lore.toString()
}

fun ItemStack.getName(): String{
    val meta = this.itemMeta
    return meta?.lore.toString()
}

