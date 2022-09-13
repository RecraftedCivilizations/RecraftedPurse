package com.github.recraftedcivilizations.recraftedpurse.gui

import org.bukkit.entity.Player
import java.util.*

/**
 * @author DarkVanityOfLight
 * This GUI manager and all classes of the GUI toolkit
 * are inspired by the CivModCore GUI toolkit
 */

/**
 * The GUI manager stores all currently opened GUIS and provides
 * access to them
 */
object GUIManager {
    private val openInventories = emptyMap<UUID, InventoryGUI>().toMutableMap()

    /**
     * Get the open inventory for the player p
     * @param p The player to get the [InventoryGUI] for
     * @return Returns the open [InventoryGUI] or null if none is open
     */
    fun getOpenInventory(p: Player): InventoryGUI?{
        return openInventories[p.uniqueId]
    }

    /**
     * Force close an open inventory for the player p
     * @param p  The player to close the inventory for
     */
    fun forceClose(p: Player?){
        if (p != null){
            p.closeInventory()
            openInventories.remove(p.uniqueId)
        }
    }

    /**
     * Set the current open inventory for a player
     * @param player The player to set the gui for
     * @param inventory The inventory gui to open
     */
    fun setOpenInventory(player: Player, inventory: InventoryGUI){
        openInventories[player.uniqueId] = inventory
    }

    /**
     * Get all open inventories as an immutable map
     * @return An immutable map of all opened inventory guis and their owners
     */
    fun getOpenInventories(): Map<UUID, InventoryGUI>{
        return openInventories
    }

    /**
     * This is called when an inventory gui is closed
     * @param player The player who closed the inventory
     */
    fun inventoryWasClosed(player: Player?){
        if (player != null){
            openInventories.remove(player.uniqueId)
        }
    }

    /**
     * Check if the player has a inventory gui open
     * @param player The player to check for
     */
    fun hasInventoryGUIOpen(player: Player): Boolean{
        return openInventories[player.uniqueId] != null
    }
}