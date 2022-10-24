package com.github.recraftedcivilizations.recraftedpurse.commands

import com.github.recraftedcivilizations.recraftedpurse.AccountManager
import org.bukkit.Bukkit
import org.bukkit.Color
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.UUID

/**
 * Shows the wealth in bank account and purse of a given player
 * @param accountManager The account manager
 * @param currencySymbol The symbol of the currency(gets added behind the value)
 */
class Wealth(private val accountManager: AccountManager, private val currencySymbol: String) : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {

        val name = args[0]
        val uuid = Bukkit.getPlayer(name)?.uniqueId

        if(uuid == null){
            sender.sendMessage("${Color.RED}No such player!")
            return true
        }
        val wealth = accountManager.accountValue(uuid)

        sender.sendMessage("${Color.GREEN}The player $name has a total wealth of $wealth$currencySymbol")
        return true

    }

}