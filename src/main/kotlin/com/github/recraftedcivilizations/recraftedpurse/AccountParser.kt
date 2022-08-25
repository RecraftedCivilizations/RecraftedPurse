package com.github.recraftedcivilizations.recraftedpurse

import org.bukkit.configuration.ConfigurationSection
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.util.UUID

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

    fun loadAccount(uuid: UUID): Account?{
        load()

        val configSection = dataFile.getConfigurationSection("$accountSectionName$uuid") ?: return null
        return configSectionToAccount(configSection, uuid)


    }

    private fun configSectionToAccount(configurationSection: ConfigurationSection, uuid: UUID): Account{
        val bankBalance = configurationSection.getInt(bankBalanceName, 0)
        val purseBalance = configurationSection.getInt(purseBalanceName, 0)
        return Account(uuid, bankBalance, purseBalance)
    }

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

    private fun load(){
        dataFile.load(filePath)
    }

    private fun save(){
        dataFile.save(filePath)
    }
}