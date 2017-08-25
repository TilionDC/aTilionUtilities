package me.tiliondc.atu.modules.spawn;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class SpawnCommand implements CommandExecutor {

    JavaPlugin plugin;

    public SpawnCommand(JavaPlugin plugin) {
        this.plugin = plugin;

        plugin.getCommand("spawn").setExecutor(this);
    }


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage(ChatColor.RED + "Only a player can do this command");
        }
        Player player = (Player) commandSender;
        if(strings.length == 0 ) {
            player.teleport(player.getWorld().getSpawnLocation());
            return true;
        }

        if(strings.length == 1) {
            World world = plugin.getServer().getWorld(strings[0]);
            if(world != null) {
                player.teleport(world.getSpawnLocation());
                player.sendMessage(ChatColor.GREEN + "You have just teleported to the spawn of world: " + ChatColor.AQUA + world.getName());
            } else {
                commandSender.sendMessage(ChatColor.RED + "Could not find world: " + ChatColor.AQUA + strings[0]);
            }
            return true;
        }

        if(strings.length == 2) {
            if(!commandSender.hasPermission("atu.admin")) {
                commandSender.sendMessage(ChatColor.RED + "You don't have sufficient permissions to do this.");
                return true;
            }
            World world = plugin.getServer().getWorld(strings[0]);
            player = plugin.getServer().getPlayer(strings[1]);
            if(player == null) {
                commandSender.sendMessage(ChatColor.RED + "Could not find player: " + ChatColor.AQUA + strings[1]);
                return true;
            }
            if(world != null) {
                player.teleport(world.getSpawnLocation());
                player.sendMessage(ChatColor.AQUA + commandSender.getName() + ChatColor.GREEN + " have just teleported to the spawn of world: " + ChatColor.AQUA + world.getName());
                commandSender.sendMessage(ChatColor.GREEN + "Teleported " + ChatColor.AQUA + player.getName() + ChatColor.GREEN + " to spawn of world " + ChatColor.AQUA + world.getName());
            } else {
                commandSender.sendMessage(ChatColor.RED + "Could not find world: " + ChatColor.AQUA + strings[0]);
            }
            return true;
        }

        return false;
    }
}
