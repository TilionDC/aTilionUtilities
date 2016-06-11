package me.tiliondc.atu.listeners;

import me.tiliondc.atu.ATilionUtilities;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Arrays;

public class ChestLockListener implements Listener{

    ATilionUtilities plugin;

    public ChestLockListener(ATilionUtilities plugin) {

        this.plugin = plugin;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);

    }

    @EventHandler
    public void playerChangeSign(SignChangeEvent e) {

        if(e.getLine(0).equalsIgnoreCase("[PRIVATE]")) {

            e.setLine(0, ChatColor.BLUE + "[PRIVATE]");
            e.setLine(1, e.getPlayer().getName());
            e.setLine(2, ChatColor.DARK_GRAY + e.getLine(2));
            e.setLine(3, ChatColor.DARK_GRAY + e.getLine(3));

        }

    }

    @EventHandler
    public void playerEditSign(PlayerInteractEvent e) {
        if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(e.getClickedBlock().getType() == Material.WALL_SIGN) {
                Sign sign = (Sign) e.getClickedBlock().getState();


            }
        }
    }

}
