package me.tiliondc.atu.modules;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class BedSleepVote implements Listener {

    JavaPlugin plugin;

    public BedSleepVote(JavaPlugin plugin) {

        this.plugin = plugin;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);

    }

    @EventHandler
    public void onPlayerSleep(PlayerBedEnterEvent e) {

        int sleeping = 0;
        int notsleeping = 0;
        World world = e.getBed().getWorld();
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            if (player.isSleepingIgnored()) continue;
            if (player.hasPermission("atu.ignorebeds")) continue;
            if (player.isSleeping()) sleeping++;
            else notsleeping++;
        }
        if(sleeping >= notsleeping) world.setFullTime(23500);
    }


}

