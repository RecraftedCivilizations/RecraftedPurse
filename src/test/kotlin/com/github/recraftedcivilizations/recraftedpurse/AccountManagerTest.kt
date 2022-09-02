package com.github.recraftedcivilizations.recraftedpurse

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import java.util.UUID

internal class AccountManagerTest {
    lateinit var accountParser : AccountParser

    @BeforeEach
    fun mock(){
        accountParser = mockk<AccountParser>()
    }

    @Test
    fun payWithPurseCached() {
        val uuid1 = UUID.randomUUID()
        val uuid2 = UUID.randomUUID()

        val account = mockk<Account>()
        val account2 = mockk<Account>()

        every { accountParser.loadAccount(uuid1) } returns account
        every { accountParser.loadAccount(uuid2) } returns account2

        every { account.withdrawFromPurse(any()) } returns true
        every { account2.withdrawFromPurse(any()) } returns false

        val accountManager = AccountManager(true, 0.0, accountParser)

        // Account has enough money
        assert(accountManager.payWithPurse(uuid1, 10))
        verify { account. withdrawFromPurse(10) }

        // Account doesn't have enough money
        assert(!accountManager.payWithPurse(uuid2, 10))
        verify { account2.withdrawFromPurse(10) }

        verify(exactly = 2) { accountParser.loadAccount(any()) }




    }

    @Test
    fun payWithBank() {
    }

    @Test
    fun hasInPurse() {
    }

    @Test
    fun hasInBank() {
    }

    @Test
    fun bankBalance() {
    }

    @Test
    fun purseBalance() {
    }

    @Test
    fun depositToBank() {
    }

    @Test
    fun depositToPurse() {
    }

    @Test
    fun hasAccount() {
    }

    @Test
    fun accountValue() {
    }

    @Test
    fun onPlayerDeath() {
    }

    @Test
    fun isCached() {
    }

    @Test
    fun getDeathTaxAmount() {
    }
}