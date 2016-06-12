package me.tiliondc.atu;

import me.tiliondc.atu.commands.*;
import me.tiliondc.atu.database.SQLiteDB;
import me.tiliondc.atu.listeners.ChatAndSignColors;
import me.tiliondc.atu.listeners.ElevatorSignListener;
import me.tiliondc.atu.listeners.PathBlockListener;
import me.tiliondc.atu.listeners.StairChairListener;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Map;

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
                    getConfig().getBoolean("Chair.Require-Signs"), getConfig().getBoolean("Chair.Require-Empty-Hand"));
            new ToggleChairsCommand(this);
        }

        if(getConfig().getBoolean("Elevator-Signs.Enabled")) {
            new ElevatorSignListener(this, getConfig().getInt("Elevator-Signs.Maximum-Distance"),
                    getConfig().getBoolean("Elevator-Signs.Allow-Jump-And-Sneak"),
                    getConfig().getBoolean("Elevator-Signs.Allow-Redstone"),
                    getConfig().getInt("Elevator-Signs.Max-Pad-Size"));
        }
        if(getConfig().getBoolean("Chat-Colors.Enabled")) {
            new ChatAndSignColors(this, getConfig().getString("Chat-Colors.Prefix").charAt(0));
        }

        if(getConfig().getBoolean("Sudo.Enabled")) {
            new SudoCommand(this);
        }
        if(getConfig().getBoolean("Fly.Enabled")) {
            new FlyCommand(this, getConfig().getBoolean("Fly.Take-no-falldamage"));
        }
        if(getConfig().getBoolean("Rules.Enabled")) {
            new RulesCommand(this);
        }
        if(getConfig().getBoolean("Hats.Enabled")) {
            new HatCommand(this);
        }
        if(getConfig().getBoolean("Kit.Enabled")) {
            new KitCommand(this);
        }
        if(getConfig().getBoolean("Heal.Enabled")) {
            new HealCommand(this);
        }
        if(getConfig().getBoolean("Feed.Enabled")) {
            new FeedCommand(this);
        }


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
                @SuppressWarnings("deprecation")
                String version = YamlConfiguration.loadConfiguration(getResource("config.yml")).getString("Version");
                if(version == null) version = "ERROR";
                if(!getConfig().getString("Version").equals(version) || getConfig().getString("Version") == null) {
                    getConfig().save(getDataFolder() + "/old-config-" + getConfig().getString("Version") + ".yml");

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
