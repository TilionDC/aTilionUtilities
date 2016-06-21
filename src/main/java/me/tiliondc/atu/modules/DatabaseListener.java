package me.tiliondc.atu.modules;

import me.tiliondc.atu.database.SQLiteDB;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.event.world.WorldUnloadEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Date;
import java.util.GregorianCalendar;

public class DatabaseListener implements Listener {

    JavaPlugin plugin;
    SQLiteDB database;

    public DatabaseListener(JavaPlugin plugin, SQLiteDB database) {

        this.plugin = plugin;
        this.database = database;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);

        database.createTable("PLAYERS", 2, 1);
        database.getColumnCount("PLAYERS");

    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerJoin(PlayerLoginEvent e) {

        database.insertToTable("PLAYERS", e.getPlayer().getName(),"LOGIN", new Date(System.currentTimeMillis()) + "");

    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerLeave(PlayerQuitEvent e) {

    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerKicked(PlayerKickEvent e) {

    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onWorldLoad(WorldLoadEvent e) {

    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onWorldUnload(WorldUnloadEvent e) {

    }





}
