package com.github.recraftedcivilizations.recraftedpurse

import io.mockk.*
import org.bukkit.entity.Player
import org.bukkit.event.entity.PlayerDeathEvent
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
        val uuid1 = UUID.randomUUID()
        val uuid2 = UUID.randomUUID()
        val uuid3 = UUID.randomUUID()

        val account1 = mockk<Account>()
        val account2 = mockk<Account>()
        val account3 = mockk<Account>()

        every { accountParser.loadAccount(uuid1) } returns account1
        every { accountParser.loadAccount(uuid2) } returns account2
        every { accountParser.loadAccount(uuid3) } returns account3

        every { account1.bankBalance } returns 10
        every { account2.bankBalance } returns 5
        every { account3.bankBalance } returns 15

        val accountManager = AccountManager(true, 0.0, accountParser)

        assert(accountManager.hasInBank(uuid1, 10))
        verify { account1.bankBalance }
        assert(!accountManager.hasInBank(uuid2, 10))
        verify { account2.bankBalance }
        assert(accountManager.hasInBank(uuid3, 10))
        verify { account3.bankBalance }
    }

    @Test
    fun bankBalance() {

        val uuid = UUID.randomUUID()
        val account = mockk<Account>()

        every { accountParser.loadAccount(uuid) } returns account

        every { account.bankBalance } returns 10

        val accountManager = AccountManager(true, 0.0, accountParser)

        assertEquals(10, accountManager.bankBalance(uuid))
        verify { account.bankBalance }

    }

    @Test
    fun purseBalance() {
        val uuid = UUID.randomUUID()
        val account = mockk<Account>()

        every { accountParser.loadAccount(uuid) } returns account

        every { account.purseBalance } returns 10

        val accountManager = AccountManager(true, 0.0, accountParser)

        assertEquals(10, accountManager.purseBalance(uuid))
        verify { account.purseBalance }
    }

    @Test
    fun depositToBank() {
        val uuid1 = UUID.randomUUID()
        val uuid2 = UUID.randomUUID()
        val account = mockk<Account>()
        val account2 = mockk<Account>()

        every { accountParser.loadAccount(uuid1) } returns account
        every { accountParser.loadAccount(uuid2) } returns account2

        every { account.depositToBank(any()) } returns true
        every { account2.depositToBank(any()) } returns false

        val accountManager = AccountManager(true, 0.0, accountParser)

        assert(accountManager.depositToBank(uuid1, 10))
        assert(!accountManager.depositToBank(uuid2, 10))

        verify { account.depositToBank(any()) }
        verify { account2.depositToBank(any()) }

    }

    @Test
    fun depositToPurse() {
        val uuid1 = UUID.randomUUID()
        val account = mockk<Account>()

        every { accountParser.loadAccount(uuid1) } returns account
        every { account.depositToPurse(any()) } returns true

        val accountManager = AccountManager(true, 0.0, accountParser)

        assert(accountManager.depositToPurse(uuid1, 10))
        verify { account.depositToPurse(any()) }
    }

    @Test
    fun hasAccount() {

        val uuid1 = UUID.randomUUID()
        val uuid2 = UUID.randomUUID()

        val account1 = mockk<Account>()

        every { accountParser.loadAccount(uuid1) } returns account1
        every { accountParser.loadAccount(uuid2) } returns null
        every { accountParser.saveAccount(any()) } just runs

        val accountManager = AccountManager(true, 0.0, accountParser)

        assert(accountManager.hasAccount(uuid1))
        assert(accountManager.hasAccount(uuid2))

        verify { accountParser.saveAccount(Account(uuid2, 0, 0)) }

        val f = accountManager::class.java.getDeclaredField("accountsCache")
        f.isAccessible = true
        val cache = f.get(accountManager) as MutableMap<UUID, Account>

        assertEquals(mapOf(Pair(uuid1, account1), Pair(uuid2, Account(uuid2, 0, 0))), cache)


    }

    @Test
    fun accountValue() {
        val uuid = UUID.randomUUID()
        val account = mockk<Account>()

        every { accountParser.loadAccount(uuid) } returns account
        every { account.value() } returns 10

        val accountManager = AccountManager(false, 0.0, accountParser)

        assertEquals(10, accountManager.accountValue(uuid))
        verify { account.value() }

    }

    @Test
    fun onPlayerDeath() {
        val uuid = UUID.randomUUID()
        val player = mockk<Player>()
        val playerDeathEvent = mockk<PlayerDeathEvent>()
        val account = mockk<Account>()

        every { playerDeathEvent.entity } returns player
        every { player.uniqueId } returns uuid
        every { accountParser.loadAccount(uuid) } returns account
        every { account.deathTax(any()) } just runs

        val accountManager = AccountManager(true, 1.0, accountParser)

        accountManager.onPlayerDeath(playerDeathEvent)

        verify { account.deathTax(1.0) }


    }
}