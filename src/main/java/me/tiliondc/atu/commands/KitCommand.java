package me.tiliondc.atu.commands;

import me.tiliondc.atu.ATilionUtilities;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class KitCommand implements CommandExecutor {

    ATilionUtilities plugin;

    public KitCommand(ATilionUtilities plugin) {

        this.plugin = plugin;

    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        return false;
    }
}
