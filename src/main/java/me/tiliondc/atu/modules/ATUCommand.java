package me.tiliondc.atu.modules;

import me.tiliondc.atu.ATilionUtilities;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ATUCommand implements CommandExecutor {

    ATilionUtilities plugin;

    public ATUCommand(ATilionUtilities plugin) {
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
