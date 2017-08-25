package me.tiliondc.atu.modules.playerspecificweather;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.WeatherType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerSpecificWeatherCommand implements CommandExecutor {

    JavaPlugin plugin;

    public PlayerSpecificWeatherCommand(JavaPlugin plugin) {

        this.plugin = plugin;

        plugin.getCommand("playerweather").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if(!(commandSender instanceof Player)) return false;
        Player player = (Player) commandSender;

        if(strings.length == 0) {
            player.resetPlayerWeather();
            return true;
        }

        if(strings.length == 1) {

            if("CLEAR".equalsIgnoreCase(strings[0])) {
                player.setPlayerWeather(WeatherType.CLEAR);
            } else if ("DOWNFALL".equalsIgnoreCase(strings[0])) {
                player.setPlayerWeather(WeatherType.DOWNFALL);
            } else {
                player.sendMessage(ChatColor.RED + "The weather must be either: " +
                        ChatColor.AQUA + WeatherType.CLEAR + ChatColor.RED + " or " + ChatColor.DARK_GRAY + WeatherType.DOWNFALL);
            }


            return true;
        }

        if(strings.length == 2) {

            player = Bukkit.getServer().getPlayer(strings[0]);
            if(player == null) {
                commandSender.sendMessage(ChatColor.RED + "Couldn't find that player");
                return true;
            }
            if("CLEAR".equalsIgnoreCase(strings[1])) {
                player.setPlayerWeather(WeatherType.CLEAR);
            } else if ("DOWNFALL".equalsIgnoreCase(strings[1])) {
                player.setPlayerWeather(WeatherType.DOWNFALL);
            } else {
                player.sendMessage(ChatColor.RED + "The weather must be either: " +
                        ChatColor.AQUA + WeatherType.CLEAR + ChatColor.RED + " or " + ChatColor.DARK_GRAY + WeatherType.DOWNFALL);
            }

            return true;
        }

        return false;
    }
}
