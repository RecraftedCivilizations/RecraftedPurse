package com.github.recraftedcivilizations.recraftedpurse.gui

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.player.PlayerQuitEvent

/**
 * @author DarkVanityOfLight
 */

/**
 * This class makes Clickable items work,
 * if you want any clickable items in your GUI you have to
 * register this listener in your plugin
 */
class GUIListener: Listener {

    @EventHandler(ignoreCancelled = true)
    fun onGUIClicked(e: InventoryClickEvent){
        // Check that the one who clicked is a player
        if(e.whoClicked !is Player){ return }
        val player = e.whoClicked as Player
        val inv = GUIManager.getOpenInventory(player)

        e.isCancelled = true
        inv?.itemClick(player, e.rawSlot)
    }

    @EventHandler(ignoreCancelled = true)
    fun onGUIClosed(e: InventoryCloseEvent){
        if(e.player !is Player){ return }
        val player = e.player as Player
        GUIManager.inventoryWasClosed(player)
    }

    @EventHandler(ignoreCancelled = true)
    fun onPlayerLeave(e: PlayerQuitEvent){
        val player = e.player
        GUIManager.inventoryWasClosed(player)
    }
}