package com.github.recraftedcivilizations.recraftedpurse

import net.milkbowl.vault.economy.Economy
import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.ServicePriority
import org.bukkit.plugin.java.JavaPlugin


class RecraftedPurse : JavaPlugin() {
    private var vault: Plugin? = null

    override fun onEnable() {
        Bukkit.getLogger().info("Starting RecraftedPurse")
        this.vault = hookPlugin("Vault")
    }

    private fun registerEconomy(){
        if (vault != null){
            server.servicesManager.register(Economy::class.java, VaultConnector(), this, ServicePriority.Highest)
            Bukkit.getLogger().info("Registered Vault interface")
        }

    }

    private fun hookPlugin(name: String): Plugin?{
        val plugin = Bukkit.getServer().pluginManager.getPlugin(name)

        return if (plugin != null){
            plugin
        }else{
            Bukkit.getLogger().warning("Unable to hook plugin $name")
            null
        }
    }

}