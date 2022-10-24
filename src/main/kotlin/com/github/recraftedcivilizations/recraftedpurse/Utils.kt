package com.github.recraftedcivilizations.recraftedpurse

import org.bukkit.inventory.ItemStack

/**
 * Set the name of an item stack
 * @param name The name to set
 */
fun ItemStack.setName(name: String?){
    val meta = this.itemMeta
    meta?.setDisplayName(name)
    this.itemMeta = meta
}

/**
 * Set the lore of an item stack
 * @param lore The lore to set
 */
fun ItemStack.setLore(vararg lore: String){
    val meta = this.itemMeta
    meta?.lore = lore.toMutableList()
}

/**
 * Get the lore of an item stack
 * @return The lore
 */
fun ItemStack.getLore(): String{
    val meta = this.itemMeta
    return meta?.lore.toString()
}

/**
 * Get the name of an item stack
 * @return The name
 */
fun ItemStack.getName(): String{
    val meta = this.itemMeta
    return meta?.lore.toString()
}

