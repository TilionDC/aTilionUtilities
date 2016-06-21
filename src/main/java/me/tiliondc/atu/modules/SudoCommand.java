package me.tiliondc.atu.modules;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class SudoCommand implements CommandExecutor{

    JavaPlugin plugin;

    public SudoCommand(JavaPlugin plugin) {
        this.plugin = plugin;

        plugin.getCommand("sudo").setExecutor(this);

    }

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
