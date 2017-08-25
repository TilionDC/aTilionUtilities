package me.tiliondc.atu.modules.editsign;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 *
 */

public class EditSignCommand implements CommandExecutor {

    JavaPlugin plugin;

    /**
     *
     * @param plugin
     */
    public EditSignCommand(JavaPlugin plugin) {
        this.plugin = plugin;

        plugin.getCommand("signedit").setExecutor(this);
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

        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage(ChatColor.RED + "Only a player can do this command");
            return true;
        }
        if(strings.length > 0) {
            int line;
            try {
                line = Integer.parseInt(strings[0]);

                line--;
            } catch (NumberFormatException e) {
                return false;
            }
            if(line > 3 || line < 0) {
                commandSender.sendMessage(ChatColor.RED + "Line must be betwen 1 and 4");
                return true;
            }
            String text = "";
            for(int i = 1; i < strings.length; i++) {
                text += strings[i];
            }
            text = text.replace('&', ChatColor.COLOR_CHAR).replace(ChatColor.COLOR_CHAR + " ", "&" + " ");
            Set<Material> blocks = new HashSet<>();
            blocks.add(Material.AIR);
            blocks.add(Material.STATIONARY_WATER);
            blocks.add(Material.STATIONARY_LAVA);
            Sign sign = null;
            if(((Player)commandSender).getTargetBlock(blocks, 6).getState() instanceof Sign) sign = (Sign) ((Player)commandSender).getTargetBlock(blocks, 6).getState();
            if(sign == null) {

                commandSender.sendMessage(ChatColor.RED + "You need to be looking at a sign when doing this");
                return true;

            }
            String old = sign.getLine(line);
            String[] lines = sign.getLines();
            lines[line] = text;

            SignChangeEvent e = new SignChangeEvent(sign.getBlock(), (Player) commandSender, lines);
            plugin.getServer().getPluginManager().callEvent(e);
            String all = Arrays.toString(lines);
            all = all.substring(1, all.length() - 1);
            if(all.contains("[") && all.contains("]")) e.setCancelled(true);

            if(!e.isCancelled()) {
                commandSender.sendMessage(ChatColor.GREEN + "You changed the sign line " + ChatColor.AQUA + line +
                        ChatColor.GREEN + " from " + ChatColor.GOLD + old + ChatColor.GREEN + " to " + ChatColor.GOLD +
                        sign.getLine(line));
                sign.setLine(line, text);
                sign.update();
                sign.update(true);
            } else {
                commandSender.sendMessage(ChatColor.RED + " You can't do this");
            }
            return true;
        }

        return false;
    }
}
