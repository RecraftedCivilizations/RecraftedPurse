package com.github.recraftedcivilizations.recraftedpurse

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import java.io.File
import java.util.UUID

internal class AccountParserTest {
    private lateinit var dataFile: File

    @BeforeEach
    fun file(){
        dataFile = File("./data.yml/")
    }

    @AfterEach
    fun cleanUpDataFile(){

        dataFile.delete()

    }

    @Test
    fun loadAccount() {
        val uuid1 = UUID.randomUUID()
        val account1 = Account(uuid1, 10, 10)
        val uuid2 = UUID.randomUUID()
        val account2 = Account(uuid2, 20, 15)

        val accountParser = AccountParser("./")

        dataFile.writeText("accounts:\n" +
                "  $uuid1:\n" +
                "    purseBalance: 10\n" +
                "    bankBalance: 10\n" +
                "  $uuid2:\n" +
                "    purseBalance: 15\n" +
                "    bankBalance: 20\n")



        assertEquals(account1, accountParser.loadAccount(uuid1))
        assertEquals(account2, accountParser.loadAccount(uuid2))

    }

    @Test
    fun saveAccount() {
        val uuid1 = UUID.randomUUID()
        val account1 = Account(uuid1, 10, 10)
        val uuid2 = UUID.randomUUID()
        val account2 = Account(uuid2, 20, 15)

        val accountParser = AccountParser("./")
        accountParser.saveAccount(account1)
        accountParser.saveAccount(account2)

        assertEquals("accounts:\n" +
                "  $uuid1:\n" +
                "    purseBalance: 10\n" +
                "    bankBalance: 10\n" +
                "  $uuid2:\n" +
                "    purseBalance: 15\n" +
                "    bankBalance: 20\n", readFile(dataFile))

    }

    private fun readFile(file: File): String {
        val inputStream = file.inputStream()
        return inputStream.bufferedReader().use { it.readText() }

    }
}