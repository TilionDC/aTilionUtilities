package me.tiliondc.atu.modules.pathblock;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class PathBlockListener implements Listener {

    JavaPlugin plugin;
    float speed;

    public PathBlockListener(JavaPlugin plugin, float speed) {
        this.plugin = plugin;
        if(speed > 1) speed = 1;
        if(speed < 0) speed = 0;
        this.speed = speed;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void playerWalk(PlayerMoveEvent e) {
        if(!e.getPlayer().hasPermission("atu.pathblock")) return;
        if(e.getPlayer().isFlying()) return;

        Location from = e.getFrom().clone();
        while(from.getBlock().getType() == Material.AIR) from.setY(from.getY() - 1);

        if(from.getBlock().getType() == Material.GRASS_PATH) {
            e.getPlayer().setWalkSpeed(speed);
        } else {
            // TODO: 16/06/03 Figure out a way to somehow change the value to twise of what the previous run speed was. Maybe use player metadata?
            e.getPlayer().setWalkSpeed(0.2f);
        }
    }

}
