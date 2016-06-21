package me.tiliondc.atu;

import me.tiliondc.atu.database.SQLiteDB;
import me.tiliondc.atu.modules.*;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.world.WorldUnloadEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class ATilionUtilities extends JavaPlugin {

    SQLiteDB database = null;

    @Override
    public void onEnable() {
        super.onEnable();
        createConfig();

        new ATUCommand(this);
        new DatabaseListener(this, getDB());

        if (getConfig().getBoolean("Path.Enabled")) {
            new PathBlockListener(this, (float) getConfig().getDouble("Path.Speed"));
        }
        if (getConfig().getBoolean("Chair.Enabled")) {
            new StairChairListener(this, (float) getConfig().getDouble("Chair.Distance"),
                    getConfig().getBoolean("Chair.Require-Signs"), getConfig().getBoolean("Chair.Require-Empty-Hand"));
        }

        if (getConfig().getBoolean("Elevator-Signs.Enabled")) {
            new ElevatorSignListener(this, getConfig().getInt("Elevator-Signs.Maximum-Distance"),
                    getConfig().getBoolean("Elevator-Signs.Allow-Jump-And-Sneak"),
                    getConfig().getBoolean("Elevator-Signs.Allow-Redstone"),
                    getConfig().getInt("Elevator-Signs.Max-Pad-Size"));
        }
        if (getConfig().getBoolean("Chat-Colors.Enabled")) {
            new ChatAndSignColorsListemer(this, getConfig().getString("Chat-Colors.Prefix").charAt(0));
        }

        if (getConfig().getBoolean("Sudo.Enabled")) {
            new SudoCommand(this);
        }
        if (getConfig().getBoolean("Fly.Enabled")) {
            new FlyCommand(this, getConfig().getBoolean("Fly.Take-no-falldamage"));
        }
        if (getConfig().getBoolean("Rules.Enabled")) {
            new RulesCommand(this);
        }
        if (getConfig().getBoolean("Hats.Enabled")) {
            new HatCommand(this);
        }
        if (getConfig().getBoolean("Kit.Enabled")) {
            new KitCommand(this);
        }
        if (getConfig().getBoolean("Heal.Enabled")) {
            new HealCommand(this);
        }
        if (getConfig().getBoolean("Feed.Enabled")) {
            new FeedCommand(this);
        }
        if (getConfig().getBoolean("Sign-Edit.Enabled")) {
            new EditSignCommand(this);
        }
        if (getConfig().getBoolean("Locked-Chests.Enabled")) {
            new ChestLockListener(this);
        }
        if (getConfig().getBoolean("Spawn.Enabled")) {
            new SpawnCommand(this);
        }
        if (getConfig().getBoolean("Nick.Enabled")) {
            new NickAndRevealCommand(this);
        }

    }

    @Override
    public void onDisable() {
        super.onDisable();

        for (Player p : getServer().getOnlinePlayers()) {
            getServer().getPluginManager().callEvent(new PlayerQuitEvent(p, "AtilionUtilitiesDisabled"));
        }
        for (World w : getServer().getWorlds()) {
            getServer().getPluginManager().callEvent(new WorldUnloadEvent(w));
        }
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
                if (version == null) version = "ERROR";
                if (!getConfig().getString("Version").equals(version) || getConfig().getString("Version") == null) {
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

    private SQLiteDB getDB() {

        if (database == null) database = new SQLiteDB(this);
        return database;
    }
}