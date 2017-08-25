package me.tiliondc.atu.modules.sudo;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *  Super user command like linux only you make the command as a player not as an admin.
 *
 *  First param takes the username of the player who will be making the command.
 *
 *  All other params makes the command without an initial backslash.
 *
 *  To implement:
 *  make an instance of the constructor and give it the plugin instance of plugin using this module.
 */
public class SudoCommand implements CommandExecutor{

    JavaPlugin plugin;

    /**
     *
     * @param plugin
     */
    public SudoCommand(JavaPlugin plugin) {
        this.plugin = plugin;

        plugin.getCommand("sudo").setExecutor(this);

    }

    /**
     *
     * @param commandSender
     * @param command
     * @param s
     * @param strings
     * @return
     */
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

            if(strings.length >= 2) {
                if(Bukkit.getServer().getPlayer(strings[0]) != null) {
                    Player p;
                    if (( p = Bukkit.getServer().getPlayer(strings[0]) ).isOnline()) {

                        StringBuilder com = new StringBuilder("");
                        for(int i = 1; i < strings.length; i++) {
                            com.append(strings[i]).append(" ");
                        }

                        p.chat(com.toString());
                        return true;
                    }
                }
            }
        return false;
    }
}
