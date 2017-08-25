package me.tiliondc.atu.modules.playerspecifictime;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerSpecificTimeCommand implements CommandExecutor {

    JavaPlugin plugin;

    public PlayerSpecificTimeCommand(JavaPlugin plugin) {

        this.plugin = plugin;

        plugin.getCommand("playertime").setExecutor(this);

    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if(!(commandSender instanceof Player)) return false;
        Player player = (Player) commandSender;

        if(strings.length == 0) {
            player.resetPlayerTime();
            return true;
        }

        if(strings.length == 1) {
            Long time = 0l;
            try {
                time = Long.parseLong(strings[0]);
            } catch (NumberFormatException e) {
                time = 12000l;
            }
            player.setPlayerTime(time, false);
            return true;
        }

        if(strings.length == 2) {

            player = Bukkit.getServer().getPlayer(strings[0]);
            if(player == null) {
                commandSender.sendMessage(ChatColor.RED + "Couldn't find that player");
                return true;
            }
            Long time = 0l;
            try {
                time = Long.parseLong(strings[1]);
            } catch (NumberFormatException e) {
                time = 12000l;
            }
            player.setPlayerTime(time, false);

            return true;
        }

        return false;
    }
}
