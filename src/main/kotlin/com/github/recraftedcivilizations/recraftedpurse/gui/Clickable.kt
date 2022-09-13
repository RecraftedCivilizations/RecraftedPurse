package com.github.recraftedcivilizations.recraftedpurse.gui

import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

/**
 * @author DarkVanityOfLight
 * This GUI manager and all classes of the GUI toolkit
 * are inspired by the CivModCore GUI toolkit
 */

/**
 * Represents a item with functionality when clicked inside a [InventoryGUI]
 * @constructor The Item stack as which this should be displayed
 */
abstract class Clickable(override val itemStack: ItemStack): DisplayItem {

    /**
     * This will be called when the player clicks this item,
     * implement this for functionality on click
     * @param player The player who clicked
     */
    abstract fun onClick(player: Player)
}