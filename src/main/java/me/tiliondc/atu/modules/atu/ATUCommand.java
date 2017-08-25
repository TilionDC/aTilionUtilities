package me.tiliondc.atu.modules.atu;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;


/**
 *  Normal help command. Doesn't do anything but suggest help for the users who use it.
 *
 *  To implement:
 *  make an instance of the constructor and give it the plugin instance of plugin using this module.
 */
public class ATUCommand implements CommandExecutor {

    JavaPlugin plugin;

    /**
     * Creates an instance of the ATUCommand class.
     * @param plugin
     */

    public ATUCommand(JavaPlugin plugin) {
        this.plugin = plugin;

        plugin.getCommand("ATU").setExecutor(this);
    }

    /**
     *  What happens on commmand. Listens only for 'help' as the first word.
     *
     * @param commandSender
     * @param command
     * @param s
     * @param strings
     * @return
     */
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if(strings.length  == 0) return false;

        if(strings[0].equalsIgnoreCase("help")) {
            commandSender.sendMessage(ChatColor.WHITE + "Find more info about this plugin and its features at: https://github.com/TilionDC/aTilionUtilities");
        }

        return false;
    }
}
