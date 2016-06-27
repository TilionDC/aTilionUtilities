package me.tiliondc.atu.modules.essentials;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class FeedCommand implements CommandExecutor {

    JavaPlugin plugin;

    public FeedCommand(JavaPlugin plugin) {

        this.plugin = plugin;

        plugin.getCommand("Feed").setExecutor(this);

    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if(!(commandSender instanceof Player)) return false;
        Player player = (Player) commandSender;

        if(strings.length == 0) {
            player.setSaturation(20);
            player.setFoodLevel(20);
        }

        if(strings.length == 1) {
            if(plugin.getServer().getPlayer(strings[0]) != null) {
                Player p = plugin.getServer().getPlayer(strings[0]);
                p.setFoodLevel(20);
                p.setSaturation(20);
                return true;
            } else {
                int hunger = Integer.parseInt(strings[0]);
                if(!commandSender.isOp()) hunger = player.getFoodLevel() + hunger > 20 ? 20 : hunger;
                player.setFoodLevel(hunger);
                player.setSaturation(20);
                return true;
            }

        }

        if(strings.length == 2) {
            int hunger = Integer.parseInt(strings[0]);
            if(plugin.getServer().getPlayer(strings[0]) != null) {
                Player p = plugin.getServer().getPlayer(strings[0]);
                hunger = p.getFoodLevel() + hunger > 20 ? 20 : p.getFoodLevel() + hunger;
                p.setFoodLevel(hunger);
                p.setSaturation(20);
                return true;
            }
        }


        return false;
    }
}
