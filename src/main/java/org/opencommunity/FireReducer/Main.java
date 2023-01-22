package org.opencommunity.FireReducer;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private FireReducer fireReducer;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        Bukkit.getServer().getPluginManager().registerEvents(new FireReducer(this.getConfig()), this);
    }

    public void onDisable() {
    }
}
