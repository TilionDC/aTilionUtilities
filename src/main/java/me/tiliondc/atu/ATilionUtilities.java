package me.tiliondc.atu;

import me.tiliondc.atu.modules.atu.ATUCommand;
import me.tiliondc.atu.modules.bedsleepvote.BedSleepVote;
import me.tiliondc.atu.modules.chatandsigncolor.ChatAndSignColorsListemer;
import me.tiliondc.atu.modules.chestlock.ChestLockListener;
import me.tiliondc.atu.modules.editsign.EditSignCommand;
import me.tiliondc.atu.modules.elevatorsign.ElevatorSignListener;
import me.tiliondc.atu.modules.feed.FeedCommand;
import me.tiliondc.atu.modules.fly.FlyCommand;
import me.tiliondc.atu.modules.hat.HatCommand;
import me.tiliondc.atu.modules.heal.HealCommand;
import me.tiliondc.atu.modules.kit.KitCommand;
import me.tiliondc.atu.modules.nickandreveal.NickAndRevealCommand;
import me.tiliondc.atu.modules.pathblock.PathBlockListener;
import me.tiliondc.atu.modules.playerspecifictime.PlayerSpecificTimeCommand;
import me.tiliondc.atu.modules.playerspecificweather.PlayerSpecificWeatherCommand;
import me.tiliondc.atu.modules.rules.RulesCommand;
import me.tiliondc.atu.modules.spawn.SpawnCommand;
import me.tiliondc.atu.modules.stairchair.StairChairListener;
import me.tiliondc.atu.modules.sudo.SudoCommand;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.world.WorldUnloadEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

/**
 * Main plugin class. Used to load the plugin.
 *
 * This class shouldn't be used by another plugin, however it will be commented for educational purposes.
 *
 */

public class ATilionUtilities extends JavaPlugin {


    /**
     * onEnable() is run when the plugin is being enabled.
     *
     * It load each module individually with consideration to if their enabled node is set to true in the config.
     */
    @Override
    public void onEnable() {
        super.onEnable();
        createConfig();

        new ATUCommand(this);

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
        if (getConfig().getBoolean("Player-Time.Enabled")) {
            new PlayerSpecificTimeCommand(this);
        }
        if (getConfig().getBoolean("Player-Weather.Enabled")) {
            new PlayerSpecificWeatherCommand(this);
        }
        /*
        Disabled because its too buggy. Maybe I'll fix this in the future.

        if (getConfig().getBoolean("Fix-Explosions.Enabled")) {
            new RepairExplosionDamage(
                    this,
                    getConfig().getBoolean("Fix-Explosions.Repair-TNT"),
                    getConfig().getBoolean("Fix-Explosions.Drop-Blocks"),
                    getConfig().getInt("Fix-Explosions.Ticks-Until-Repair")
            );
        }
        */
        if (getConfig().getBoolean("Bed-Vote.Enabled")) {
            new BedSleepVote(this);
        }

    }

    /**
     * onDisable is run when the server is shutting down, restarting or disabling the plugin.
     *
     * When disabling this plugin all players will be kicked(forced to quit) and all worlds will be unloaded.
     * so try to not disable this plugin. It has to do with saving settings.
     */
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

    /**
     *
     * createConfig() creates a costum premade config instead of using the bukkitapi.
     * This makes it a little more work but the config is a lot better.
     *
     * This method is due to change, will be updated to a better system.
     */
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
}