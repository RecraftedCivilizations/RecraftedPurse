package com.github.recraftedcivilizations.recraftedpurse.gui

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory

/**
 * @author DarkVanityOfLight
 * This GUI manager and all classes of the GUI toolkit
 * are inspired by the CivModCore GUI toolkit
 */

/**
 * This represents an inventory GUI consisting of different implementations of the [DisplayItem] interface.
 * To have a item work as clickable GUI item extend the [Clickable] class and overwrite
 * the onClick method
 * @see Clickable.onClick
 * @constructor Construct using a specific inventory type and a title
 * @constructor Construct using a inventory size and a title the size has to be dividable by 9 and can't be bigger then 54
 */
class InventoryGUI() {
    private lateinit var inventory: Inventory
    private val clickableItems: Array<DisplayItem?> by lazy{ Array(inventory.size) { null }}
    private var type: InventoryType? = null
    private var title: String? = null
    private var size: Int? = null

    /**
     * Creates a new InventoryGUI using a specific inventory type and
     * a title
     * @param type The inventory type, eg. CHEST
     * @param title The title of the inventory gui, this can't be bigger then 32
     */
    constructor(type: InventoryType, title: String) : this() {
        if (title.length > 32){
            Bukkit.getLogger().warning("InventoryGUI $title exceeds bukkits maximum title length(32)")
            val title = title.substring(0, 32)
        }
        this.inventory = Bukkit.createInventory(null, type, title)
        this.type = type
        this.title = title
    }

    /**
     * Creates a new InventoryGUI of type CHEST using the inventory size which has to be dividable by 9 and can't be bigger then 54
     * and a title
     * @param size The size of the inventory, has to be dividable by 9 and can't be bigger then 54
     *@param title The title of the inventory gui, this can't be bigger then 32
     */
    constructor(size: Int, title: String): this(){
        if (title.length > 32){
            Bukkit.getLogger().warning("InventoryGUI $title exceeds bukkits maximum title length(32)")
            val title = title.substring(0, 32)
        }
        this.inventory = Bukkit.createInventory(null, size, title)
        this.size = size
        this.title = title
    }

    /**
     * Set the item in a specific slot
     * @param c The item to display
     * @param pos The index to display the item on
     */
    fun setSlot(c: DisplayItem, pos: Int){
        clickableItems[pos] = c
        inventory.setItem(pos, c.itemStack)
    }

    /**
     * Get the [DisplayItem] at a specific slot
     * @param pos The position of the slot
     * @return The [DisplayItem] at that given slot or null if there is no item at that position
     */
    fun getSlot(pos: Int): DisplayItem?{
        return if (pos > clickableItems.size){
            null
        }else{
            clickableItems[pos]
        }
    }

    /**
     * Add an item to the end of the inventory gui
     * @param displayItem The item to display
     */
    fun addItem(displayItem: DisplayItem){
        var index = 0
        for (item in clickableItems){
            if (item == null){
                clickableItems[index] = displayItem
                break
            }
            index++
        }

        inventory.addItem(displayItem.itemStack)
    }

    /**
     * Handles an item click inside the gui
     * @param player The player who clicked
     * @param index The index of the item which got clicked
     */
    fun itemClick(player: Player, index: Int){
        if (index > clickableItems.size || index < 0 || clickableItems[index] == null){
            return
        }

        // Get the item at that slot and check if it is a clickable item to execute its functionality
        val item = getSlot(index)
        if (item is Clickable){
            item.onClick(player)
        }

        // If a new gui is opened close this one, this might close the inventory on every click that does not open another gui
        // but i need to test that
        if (clickableItems[index] !is DecorationStack && GUIManager.getOpenInventory(player) == this) {
            GUIManager.forceClose(player)
        }
    }

    /**
     * Display this GUI to a given player
     * @param player The player to open the GUI for
     */
    fun show(player: Player?){
        player?.openInventory(inventory)
        player?.updateInventory()
        if (player != null) {
            GUIManager.setOpenInventory(player, this)
        }
    }

    /**
     * Update this inventory and reopen it for every player who has it open at the moment
     */
    fun updateInventory(){
        for (entry in GUIManager.getOpenInventories().entries){
            if (entry.value == this){
                val player = Bukkit.getPlayer(entry.key)
                GUIManager.forceClose(player)
                show(player)
            }
        }
    }

    /**
     * Clone this InventoryGUI
     * @return An exact copy of this GUI
     */
    fun clone(): InventoryGUI {
        val invGui: InventoryGUI = if (type != null){
            InventoryGUI(type!!, title!!)
        }else{
            InventoryGUI(size!!, title!!)
        }

        for (slotNum in 0 until size!!){
            val item = getSlot(slotNum)
            if (item != null){
                invGui.setSlot(item, slotNum)
            }
        }

        return invGui
    }

    /**
     * Get the size of this inventory
     * @return The size of the inventory
     */
    fun getSize(): Int{
        return clickableItems.size
    }
}