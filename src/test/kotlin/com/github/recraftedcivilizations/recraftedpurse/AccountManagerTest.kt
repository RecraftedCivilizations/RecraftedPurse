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
        val uuid1 = UUID.randomUUID()
        val uuid2 = UUID.randomUUID()

        val account = mockk<Account>()
        val account2 = mockk<Account>()

        every { accountParser.loadAccount(uuid1) } returns account
        every { accountParser.loadAccount(uuid2) } returns account2

        every { account.withdrawFromBank(any()) } returns true
        every { account2.withdrawFromBank(any()) } returns false

        val accountManager = AccountManager(true, 0.0, accountParser)

        // Account has enough money
        assert(accountManager.payWithBank(uuid1, 10))
        verify { account.withdrawFromBank(10) }

        // Account doesn't have enough money
        assert(!accountManager.payWithBank(uuid2, 10))
        verify { account2.withdrawFromBank(10) }

        verify(exactly = 2) { accountParser.loadAccount(any()) }

    }

    @Test
    fun hasInPurse() {

        val uuid1 = UUID.randomUUID()
        val uuid2 = UUID.randomUUID()
        val uuid3 = UUID.randomUUID()

        val account1 = mockk<Account>()
        val account2 = mockk<Account>()
        val account3 = mockk<Account>()

        every { accountParser.loadAccount(uuid1) } returns account1
        every { accountParser.loadAccount(uuid2) } returns account2
        every { accountParser.loadAccount(uuid3) } returns account3

        every { account1.purseBalance } returns 10
        every { account2.purseBalance } returns 5
        every { account3.purseBalance } returns 15

        val accountManager = AccountManager(true, 0.0, accountParser)

        assert(accountManager.hasInPurse(uuid1, 10))
        verify { account1.purseBalance }
        assert(!accountManager.hasInPurse(uuid2, 10))
        verify { account2.purseBalance }
        assert(accountManager.hasInPurse(uuid3, 10))
        verify { account3.purseBalance }


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