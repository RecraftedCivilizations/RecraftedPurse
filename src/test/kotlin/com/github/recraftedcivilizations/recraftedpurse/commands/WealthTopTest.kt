package com.github.recraftedcivilizations.recraftedpurse.commands

import com.github.recraftedcivilizations.recraftedpurse.Account
import com.github.recraftedcivilizations.recraftedpurse.AccountManager
import io.mockk.*
import org.bukkit.Bukkit
import org.bukkit.Color
import org.bukkit.command.Command
import org.bukkit.entity.Player
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.util.UUID

internal class WealthTopTest {

    @Test
    fun onCommand() {

        val uuid1 = UUID.randomUUID()
        val uuid2 = UUID.randomUUID()

        val account1 = mockk<Account>()
        val account2 = mockk<Account>()

        every { account1.value() } returns 20
        every { account2.value() } returns 15
        every { account1.accountHolder } returns uuid1
        every { account2.accountHolder } returns uuid2

        val player1 = mockk<Player>()
        val player2 = mockk<Player>()

        every { player1.name } returns "Player1"
        every { player2.name } returns "Player2"

        val sender = mockk<Player>()
        val command = mockk<Command>()

        every { sender.sendMessage(any<String>()) } just runs

        val accountManager = mockk<AccountManager>()

        every { accountManager.getAllAccounts() } returns listOf(account2, account1)

        val wealthTop = WealthTop(accountManager, "$")

        mockkStatic(Bukkit::class) {
            every { Bukkit.getOfflinePlayer(uuid1) } returns player1
            every { Bukkit.getOfflinePlayer(uuid2) } returns player2
            wealthTop.onCommand(sender, command, "wealthtop", emptyArray())
        }

        verify { sender.sendMessage("${Color.GREEN}Top 10:\n1. Player1: 20$\n2. Player2: 15$\n") }

    }
}