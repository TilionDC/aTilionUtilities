package me.tiliondc.atu.modules;

import me.tiliondc.atu.ATilionUtilities;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FeedCommand implements CommandExecutor {

    ATilionUtilities plugin;

    public FeedCommand(ATilionUtilities plugin) {

        this.plugin = plugin;

        plugin.getCommand("Feed").setExecutor(this);

    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if(!(commandSender instanceof Player)) return false;
        Player player = (Player) commandSender;

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
