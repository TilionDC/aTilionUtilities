package me.tiliondc.atu;

import me.tiliondc.atu.listeners.PathBlockListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class ATilionUtilities extends JavaPlugin {

    @Override
    public void onEnable() {
        super.onEnable();
        createConfig();
        if(getConfig().getBoolean("Path-Enabled"))
            Bukkit.broadcastMessage("Loading ATU");
        new PathBlockListener(this, (float) getConfig().getDouble("Path-Speed"));
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    private void createConfig() {
        try {
            if (!getDataFolder().exists()) {
                getDataFolder().mkdirs();
            }
            File file = new File(getDataFolder(), "config.yml");
            if (!file.exists()) {
                getLogger().info("Config.yml not found, creating!");
                saveDefaultConfig();
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

}
