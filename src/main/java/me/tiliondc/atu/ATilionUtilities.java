package me.tiliondc.atu;

import com.avaje.ebean.EbeanServer;
import me.tiliondc.atu.Database.SQLiteDB;
import me.tiliondc.atu.commands.FlyCommand;
import me.tiliondc.atu.commands.SudoCommand;
import me.tiliondc.atu.listeners.PathBlockListener;
import me.tiliondc.atu.listeners.StairChairListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class ATilionUtilities extends JavaPlugin {

    SQLiteDB database = null;

    @Override
    public void onEnable() {
        super.onEnable();
        createConfig();

        getDB();

        if(getConfig().getBoolean("Path.Enabled")) {
            new PathBlockListener(this, (float) getConfig().getDouble("Path.Speed"));
        }
        if(getConfig().getBoolean("Chair.Enabled")) {
            new StairChairListener(this, (float) getConfig().getDouble("Chair.Distance"),
                    getConfig().getBoolean("Chair.Require-Signs"), getConfig().getBoolean("Chair.Require-Sneak"));
        }


        new SudoCommand(this);
        new FlyCommand(this, getConfig().getBoolean("Fly.Take-no-falldamage"));

    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    private void createConfig() {
        try {
            if (!getDataFolder().exists()) {
                //noinspection ResultOfMethodCallIgnored
                getDataFolder().mkdirs();
            }
            File file = new File(getDataFolder(), "config.yml");
            if (!file.exists()) {
                getLogger().info("Config.yml not found, creating!");
                saveDefaultConfig();
            } else {
                if(!getConfig().getString("Version").equals(getDescription().getVersion()) || getConfig().getString("Version") == null) {
                    //noinspection ResultOfMethodCallIgnored
                    file.delete();
                    saveDefaultConfig();
                    getLogger().info("Old config version, recreating config.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

    }


    public SQLiteDB getDB() {

        if(database == null) database = new SQLiteDB(this);
        return database;
    }



}
