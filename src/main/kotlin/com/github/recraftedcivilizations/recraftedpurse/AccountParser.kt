package com.github.recraftedcivilizations.recraftedpurse

import org.bukkit.configuration.ConfigurationSection
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.util.UUID

/**
 * Parse accounts to and from the data file
 * @param filePath The file path of the data file
 */
class AccountParser(var filePath: String) {
    private val dataFile: YamlConfiguration = YamlConfiguration()

    companion object{
        const val fileName = "data.yml"
        const val accountSectionName = "accounts"
        const val bankBalanceName = "bankBalance"
        const val purseBalanceName = "purseBalance"
    }

    init {
        filePath = if (filePath.endsWith("/")){
            "$filePath$fileName"
        }else{
            "$filePath/$fileName"
        }

        val file = File(filePath)

        if (file.exists()){
            dataFile.load(file)
        }else{
            file.createNewFile()
        }
    }

    /**
     * Load all accounts in the data file
     * @return A list of all accounts in the data file
     */
    fun loadAllAccounts(): List<Account>{
        load()
        val accounts = emptyList<Account>().toMutableList()
        val configSection = dataFile.getConfigurationSection(accountSectionName) ?: return emptyList()

        for (uuid in configSection.getKeys(false)){
            val account = loadAccount(UUID.fromString(uuid))
            if (account != null) {
                accounts.add(account)
            }

        }

        return accounts

    }

    /**
     * Load a specific account from the data file
     * @param uuid The uuid of the account holder to search
     * @return The account or null if no account exists for this holder
     */
    fun loadAccount(uuid: UUID): Account?{
        load()

        val configSection = dataFile.getConfigurationSection("$accountSectionName.$uuid") ?: return null
        return configSectionToAccount(configSection, uuid)


    }

    /**
     * Parse a config section to an account
     * @param configurationSection The config section to parse
     * @return The account
     */
    private fun configSectionToAccount(configurationSection: ConfigurationSection, uuid: UUID): Account{
        val bankBalance = configurationSection.getInt(bankBalanceName, 0)
        val purseBalance = configurationSection.getInt(purseBalanceName, 0)
        return Account(uuid, bankBalance, purseBalance)
    }

    /**
     * Save an account to the data file
     * @param account The account to save
     */
    fun saveAccount(account: Account){

        val accMap = account.asMap().toMutableMap()
        accMap.remove("accountHolder")
        saveMap("$accountSectionName.${account.accountHolder}", accMap)

        save()

    }

    /**
     * Convert a configuration section to a map of the type [K], [V]
     * @param K The type of the keys
     * @param V The type of the Values
     * @param configurationSection The config section to read from
     */
    private fun <K, V> configSectionToMap(configurationSection: ConfigurationSection): Map<K, V> {
        val output = emptyMap<K, V>().toMutableMap()
        for (key in configurationSection.getKeys(false)){
            output[key as K] = configurationSection.get(key) as V
        }

        return output
    }

    /**
     * Save a map to the given Path
     * @param path The path to save the map under
     * @param map The map to save
     */
    private fun saveMap(path: String, map: Map<String, Any>){
        for (key in map.keys){
            dataFile.set("$path.$key", map[key])
        }
    }

    /**
     * Load the datafile
     */
    private fun load(){
        dataFile.load(filePath)
    }

    /**
     * Save the datafile
     */
    private fun save(){
        dataFile.save(filePath)
    }
}