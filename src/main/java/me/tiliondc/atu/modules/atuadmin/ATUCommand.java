package me.tiliondc.atu.modules.atuadmin;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class ATUCommand implements CommandExecutor {

    JavaPlugin plugin;

    public ATUCommand(JavaPlugin plugin) {
        this.plugin = plugin;

    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if(strings.length  == 0) return false;

        if(strings[0].equalsIgnoreCase("help")) {
            commandSender.sendMessage(ChatColor.WHITE + "Find more info about this plugin and its features at: https://github.com/TilionDC/aTilionUtilities");
        }

        return false;
    }
}
