package me.tiliondc.atu.modules.heal;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class HealCommand implements CommandExecutor {

    JavaPlugin plugin;

    public HealCommand(JavaPlugin plugin) {

        this.plugin = plugin;

        plugin.getCommand("Heal").setExecutor(this);

    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if(!(commandSender instanceof Player)) return false;
        Player player = (Player) commandSender;

        if(strings.length == 0) {
            player.setHealth(20);
            return true;
        }

        if(strings.length == 1) {
            if(plugin.getServer().getPlayer(strings[0]) != null) {
                Player p = plugin.getServer().getPlayer(strings[0]);
                p.setHealth(20);
                if(p.getMaxHealth() > 20) p.setMaxHealth(20);
                return true;
            } else {
                double hearts = Integer.parseInt(strings[0]);
                hearts = (hearts + player.getHealth()) > 20 ? 20 : hearts + player.getHealth();
                player.setHealth(hearts);
                if(player.getMaxHealth() > 20) player.setMaxHealth(20);
                return true;
            }

        }

        if(strings.length == 2 || strings.length == 3) {
            double hearts = Integer.parseInt(strings[1]);
            if(plugin.getServer().getPlayer(strings[0]) != null) {
                Player p = plugin.getServer().getPlayer(strings[0]);
                if(!commandSender.isOp() || strings.length == 2) {
                    hearts = p.getHealth() + hearts > p.getMaxHealth() ? p.getMaxHealth() : hearts;
                    p.setHealth(hearts);
                    if(p.getMaxHealth() > 20) p.setMaxHealth(20);
                } else if(commandSender.isOp()){
                    player.setMaxHealth((hearts + player.getHealth()) > 20 ? player.getHealth() + hearts : 20);
                    p.setHealth(hearts + player.getHealth());
                }
                return true;
            }
        }


        return false;
    }
}
