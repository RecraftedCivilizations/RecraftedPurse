package com.github.recraftedcivilizations.recraftedpurse.commands

import com.github.recraftedcivilizations.recraftedpurse.AccountManager
import org.bukkit.Bukkit
import org.bukkit.Color
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class WealthTop(private val accountManager: AccountManager, private val currencySymbol: String) : CommandExecutor{
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {

        var number = 10
        if (args.isNotEmpty()){
            number = args[0].toIntOrNull() ?: 10
        }

        val accounts = accountManager.getAllAccounts()
        val sorted = accounts.sortedBy { it.value() }.reversed()

        var string = ""
        sorted.take(number).forEachIndexed{ i, account ->
            string += "${i+1}. ${Bukkit.getOfflinePlayer(account.accountHolder).name}: ${account.value()}$currencySymbol\n"
        }


        sender.sendMessage("${Color.GREEN}Top $number:\n$string")
        return true
    }

}