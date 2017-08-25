package me.tiliondc.atu.modules.fly;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class FlyCommand implements CommandExecutor{

    JavaPlugin plugin;
    boolean noFalldamage;

    public FlyCommand(JavaPlugin plugin, boolean falldamage) {
        this.plugin = plugin;

        plugin.getCommand("fly").setExecutor(this);
        this.noFalldamage = falldamage;

        for(Player player : Bukkit.getServer().getOnlinePlayers()) {
            player.setFlying(false);
            player.setAllowFlight(false);
        }

    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player = null;
        if (commandSender instanceof Player) {
            player = (Player) commandSender;
        }
        int secounds = -1;
        if (strings.length > 0) {
            try {
                secounds = Integer.parseInt(strings[0]);
            } catch (NumberFormatException e) {
                if (strings.length == 1 && (commandSender.hasPermission("atu.fly.others") || commandSender.isOp())) {
                    player = Bukkit.getServer().getPlayer(strings[1]);
                }
            }
        }

        if (strings.length == 2 && (commandSender.hasPermission("atu.fly.others") || commandSender.isOp())) {
            player = Bukkit.getServer().getPlayer(strings[1]);
        }

        if (player == null) return false;

        if (secounds < 0) {

            if(player.getAllowFlight()) {
                player.setFlying(false);
                player.setAllowFlight(false);
                player.sendMessage(ChatColor.RED + "You have stopped flying.");
            } else {
                player.sendMessage(ChatColor.DARK_GREEN + "You are now flying.");
                player.setAllowFlight(true);
                player.setFlying(true);
                if(noFalldamage) player.setFallDistance(-10000000);

            }

            return true;
        }

        if (secounds > 0) {
            player.setAllowFlight(true);
            player.setFlying(true);
            player.sendMessage(ChatColor.DARK_GREEN + "You are now flying for " + secounds + " seconds.");
            final Player finalPlayer = player;
            new BukkitRunnable() {
                @Override
                public void run() {
                    finalPlayer.setFlying(false);
                    finalPlayer.setAllowFlight(false);
                    finalPlayer.sendMessage(ChatColor.RED + "You have stopped flying.");
                    if(noFalldamage) finalPlayer.setFallDistance(-10000000);
                }
            }.runTaskLater(plugin, secounds * 20);

            return true;
        }
        return false;
    }
}