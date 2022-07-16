package com.github.recraftedcivilizations.recraftedpurse

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class AccountTest {

    @Test
    fun depositToBank() {
        val account = Account(10, 10)

        // Successfully deposit
        assert(account.depositToBank(10))
        assertEquals(0, account.purseBalance)
        assertEquals(20, account.bankBalance)

        // Deposit more then possible
        assert(!account.depositToBank(10))
        assertEquals(0, account.purseBalance)
        assertEquals(20, account.bankBalance)

    }

    @Test
    fun depositToPurse() {
        val account = Account(10, 10)

        assert(account.depositToPurse(10))
        assertEquals(20, account.purseBalance)
        assertEquals(10, account.bankBalance)
    }

    @Test
    fun withdrawFromPurse() {
        val account = Account(10, 10)

        assert(account.withdrawFromPurse(10))
        assertEquals(0, account.purseBalance)
        assertEquals(10, account.bankBalance)

        assert(!account.withdrawFromPurse(10))
        assertEquals(0, account.purseBalance)
        assertEquals(10, account.bankBalance)
    }

    @Test
    fun withdrawFromBank() {
        val account = Account(10, 10)

        assert(account.withdrawFromBank(10))
        assertEquals(10, account.purseBalance)
        assertEquals(0, account.bankBalance)

        assert(!account.withdrawFromBank(10))
        assertEquals(10, account.purseBalance)
        assertEquals(0, account.bankBalance)
    }

    @Test
    fun deathTax() {
        var account = Account(10, 10)

        account.deathTax(0.1)

        assertEquals(9, account.purseBalance)
        assertEquals(10, account.bankBalance)

        account = Account(0, 0)

        account.deathTax(0.1)

        assertEquals(0, account.purseBalance)
        assertEquals(0, account.bankBalance)
    }

    @Test
    fun value() {
        val account = Account(10, 10)
        assertEquals(20, account.value())
    }
}