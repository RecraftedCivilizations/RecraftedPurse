package com.github.recraftedcivilizations.recraftedpurse.commands

import com.github.recraftedcivilizations.recraftedpurse.AccountManager
import io.mockk.*
import org.bukkit.Bukkit
import org.bukkit.Color
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.event.entity.PlayerDeathEvent
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.util.UUID

internal class WealthTest {

    @Test
    fun onCommandNoPlayer() {
        val managerMock = mockk<AccountManager>()
        val sender = mockk<CommandSender>()
        val command = mockk<Command>()

        every { sender.sendMessage(any<String>()) } just Runs

        mockkStatic(Bukkit::class){

            every { Bukkit.getPlayer("RandomPlayer") } returns null

            val wealth = Wealth(managerMock, "$")
            assert(wealth.onCommand(sender, command, "wealth", arrayOf("RandomPlayer")))

            verify { Bukkit.getPlayer("RandomPlayer") }
            verify { sender.sendMessage("${Color.RED}No such player!") }

        }

    }

    @Test
    fun onCommandValidPlayer(){

        val managerMock = mockk<AccountManager>()
        val sender = mockk<CommandSender>()
        val command = mockk<Command>()
        val player = mockk<Player>()
        val uuid = UUID.randomUUID()

        every { sender.sendMessage(any<String>()) } just Runs
        every { managerMock.accountValue(uuid) } returns 10
        every { player.uniqueId } returns uuid

        mockkStatic(Bukkit::class){

            every { Bukkit.getPlayer("RandomPlayer") } returns player

            val wealth = Wealth(managerMock, "$")
            assert(wealth.onCommand(sender, command, "wealth", arrayOf("RandomPlayer")))

            verify { Bukkit.getPlayer("RandomPlayer") }
            verify { managerMock.accountValue(uuid) }
            verify { sender.sendMessage("${Color.GREEN}The player RandomPlayer has a total wealth of 10$") }
            verify { player.uniqueId }

        }

    }
}