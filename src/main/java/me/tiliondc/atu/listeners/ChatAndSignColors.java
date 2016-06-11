package me.tiliondc.atu.listeners;

import me.tiliondc.atu.ATilionUtilities;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatAndSignColors implements Listener {

    ATilionUtilities plugin;
    char prefix;

    public ChatAndSignColors(ATilionUtilities plugin, char prefix) {

        this.plugin = plugin;
        this.prefix = prefix;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);

    }

    @EventHandler
    public void chatStuff(AsyncPlayerChatEvent e) {
        e.setMessage(e.getMessage().replace(prefix, ChatColor.COLOR_CHAR).
                replace(ChatColor.COLOR_CHAR + " ", prefix + " "));

    }

    @EventHandler
    public void signStuff(SignChangeEvent e ) {
        for(int i = 0; i < e.getLines().length; i++) {
            e.setLine(i, e.getLine(i).replace(prefix, ChatColor.COLOR_CHAR).
                    replace(ChatColor.COLOR_CHAR + " ", prefix + " "));
        }
    }

}
