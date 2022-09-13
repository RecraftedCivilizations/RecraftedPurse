package com.github.recraftedcivilizations.recraftedpurse.gui.elements

import com.github.recraftedcivilizations.recraftedpurse.gui.Clickable
import com.github.recraftedcivilizations.recraftedpurse.gui.DisplayItem
import com.github.recraftedcivilizations.recraftedpurse.gui.GUIManager
import org.bukkit.Material
import org.bukkit.entity.Player
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

/**
 * The factory for the [CloseButton], do not create a close button
 * manually call [getCloseButton] to get a close button with a barrier
 * as icon and the correct name and lore.
 */
object CloseButtonFactory{
    private val closeItemStack = ItemStack(Material.BARRIER)
    private val closeButton: CloseButton
    init{
        closeItemStack.setName("Close")
        closeItemStack.setLore("Close this inventory")

        closeButton = CloseButton(closeItemStack)
    }

    /**
     * Get a [CloseButton] as Clickable
     * @return Returns a [CloseButton] with a barrier as icon and the correct lore and name
     */
    fun getCloseButton(): Clickable {
        return closeButton
    }
}

/**
 * A [Clickable] item that closes the current GUI
 */
internal class CloseButton(itemStack: ItemStack) : Clickable(itemStack) {

    override fun onClick(player: Player) {
        GUIManager.forceClose(player)
    }

    override fun clone(): DisplayItem {
        return CloseButton(itemStack)
    }
}