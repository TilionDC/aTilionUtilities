package me.tiliondc.atu.listeners;

import me.tiliondc.atu.ATilionUtilities;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PathBlockListener implements Listener {

    ATilionUtilities plugin;
    float speed;

    public PathBlockListener(ATilionUtilities plugin, float speed) {
        this.plugin = plugin;
        if(speed > 1) speed = 1;
        if(speed < 0) speed = 0;
        this.speed = speed;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void playerWalk(PlayerMoveEvent e) {

        Material type = e.getFrom().getBlock().getType();
        Material type2 = e.getFrom().getBlock().getRelative(BlockFace.DOWN).getType();
        Material type3 = e.getFrom().getBlock().getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN).getType();

        // TODO: 16/06/03 Figure out a way to somehow change the value to twise of what the previous run speed was. Maybe use player metadata?
        if(type == Material.GRASS_PATH || type2 == Material.GRASS_PATH && type == Material.AIR || type3 == Material.GRASS_PATH && type2 == Material.AIR && type == Material.AIR) {
            e.getPlayer().setWalkSpeed(speed);
        } else {
            e.getPlayer().setWalkSpeed(0.2f);
        }
    }



}
