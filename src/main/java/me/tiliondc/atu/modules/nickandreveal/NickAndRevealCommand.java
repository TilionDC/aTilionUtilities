package me.tiliondc.atu.modules.nickandreveal;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

public class NickAndRevealCommand implements CommandExecutor, Listener {

    JavaPlugin plugin;

    public NickAndRevealCommand(JavaPlugin plugin) {

        this.plugin = plugin;

        plugin.getCommand("nick").setExecutor(this);
        plugin.getCommand("reveal").setExecutor(this);
        plugin.getServer().getPluginManager().registerEvents(this, plugin);

    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if(command.getName().equalsIgnoreCase("nick")) {

            if(strings.length == 1) {

                if(!(commandSender instanceof Player)) {
                    commandSender.sendMessage(ChatColor.RED + "Too few arguments");
                    return true;
                }
                strings[0] = strings[0].replace('&', ChatColor.COLOR_CHAR).replace(ChatColor.COLOR_CHAR + " ", "& ");

                Player player = (Player) commandSender;
                player.setDisplayName(strings[0]);
                player.setPlayerListName(strings[0]);
                player.setMetadata("Nick", new FixedMetadataValue(plugin, strings[0]));
                player.sendMessage(ChatColor.GREEN + "You changed your name to: " + ChatColor.AQUA + strings[0]);
                return true;

            }

            if(strings.length == 2) {
                Player player = plugin.getServer().getPlayer(strings[0]);
                if(player == null) {
                    commandSender.sendMessage(ChatColor.RED + "Could not find player");
                    return true;
                }
                player.setDisplayName(strings[1]);
                player.setPlayerListName(strings[1]);
                player.setMetadata("Nick", new FixedMetadataValue(plugin, strings[1]));
                commandSender.sendMessage(ChatColor.GREEN + "You changed " + ChatColor.AQUA + player.getName() +
                        ChatColor.GREEN + " name to: " + ChatColor.AQUA + strings[1]);
                player.sendMessage(ChatColor.AQUA + commandSender.getName() + ChatColor.GREEN +
                        " changed your name to: " + ChatColor.AQUA + strings[1]);
                return true;
            }
            return false;
        }

        if(command.getName().equalsIgnoreCase("reveal")) {

            if(strings.length == 1) {

                String name = "";

                for(Player p : plugin.getServer().getOnlinePlayers()) {
                    if(p.getDisplayName().equalsIgnoreCase(strings[0])) {
                        name = p.getName();
                    }
                }
                if(name.isEmpty()) {
                    commandSender.sendMessage(ChatColor.RED + "Could not find player " + ChatColor.AQUA + strings[0]);
                    return true;
                }
                else {
                    commandSender.sendMessage(ChatColor.GREEN + "The real name of " + ChatColor.AQUA + strings[0] +
                            ChatColor.GREEN + " is " + ChatColor.AQUA + name);
                    return true;
                }
            }
        }

        return false;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerLogin(PlayerLoginEvent e) {

        if(e.getPlayer().hasMetadata("Nick")) {
            String nick = e.getPlayer().getMetadata("Nick").get(0).asString();
            e.getPlayer().setDisplayName(nick);
            e.getPlayer().setPlayerListName(nick);
        }

    }

}
