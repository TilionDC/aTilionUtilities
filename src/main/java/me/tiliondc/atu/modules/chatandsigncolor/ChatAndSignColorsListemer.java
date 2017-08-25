package me.tiliondc.atu.modules.chatandsigncolor;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 */

public class ChatAndSignColorsListemer implements Listener {

    JavaPlugin plugin;
    char prefix;

    /**
     *
     * @param plugin
     * @param prefix
     */

    public ChatAndSignColorsListemer(JavaPlugin plugin, char prefix) {

        this.plugin = plugin;
        this.prefix = prefix;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);

    }

    /**
     *
     * @param e
     */

    @EventHandler
    public void chatStuff(AsyncPlayerChatEvent e) {
        if(!e.getPlayer().hasPermission("atu.chatcolors")) return;
        e.setMessage(e.getMessage().replace(prefix, ChatColor.COLOR_CHAR).
                replace(ChatColor.COLOR_CHAR + " ", prefix + " "));

    }

    /**
     *
     * @param e
     */

    @EventHandler
    public void signStuff(SignChangeEvent e ) {
        if(!e.getPlayer().hasPermission("atu.chatcolors")) return;
        for(int i = 0; i < e.getLines().length; i++) {
            e.setLine(i, e.getLine(i).replace(prefix, ChatColor.COLOR_CHAR).
                    replace(ChatColor.COLOR_CHAR + " ", prefix + " "));
        }
    }

}
