package me.tiliondc.atu.modules.kit;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

//@Todo: Add economy support for the kits.

public class KitCommand implements CommandExecutor {

    JavaPlugin plugin;

    FileConfiguration config = null;

    public KitCommand(JavaPlugin plugin) {

        this.plugin = plugin;

        plugin.getCommand("kit").setExecutor(this);

        File file = createRulesFile();

        config = YamlConfiguration.loadConfiguration(file);

    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(config == null) {
            commandSender.sendMessage(ChatColor.RED + "Something went wrong with this command");
            return true;
        }
        if(!(commandSender instanceof Player)) return false;
        if(strings.length > 1) return false;

        Player player = (Player) commandSender;

        if(strings.length == 0) {
            Set<String> kits = config.getKeys(false);
            commandSender.sendMessage(ChatColor.GOLD + "All kits. Green if you have permission. red if not");
            for(String kit : kits) {
                if (commandSender.hasPermission("atu.kit." + kit))
                    commandSender.sendMessage(ChatColor.DARK_GREEN + kit);
                else
                    commandSender.sendMessage(ChatColor.DARK_RED + kit);
            }
        } else {

            if(!config.contains(strings[0])) {
                commandSender.sendMessage(ChatColor.DARK_RED + "This kit does not exist");
                return true;
            }
            if(!commandSender.hasPermission("atu.kit." + strings[0])) {
                commandSender.sendMessage(ChatColor.DARK_RED + "You don't have permission for this kit");
                return true;
            }
            int cooldown = config.getInt(strings[0] + ".Cooldown");
            long current = System.currentTimeMillis() / 1000;
            if(!player.hasMetadata("Kit." + strings[0]))
                player.setMetadata("Kit." + strings[0], new FixedMetadataValue(plugin, 0L));
            long playerTime = player.getMetadata("Kit." + strings[0]).get(0).asLong() / 1000;

            if(current  - playerTime < cooldown) {
                player.sendMessage(ChatColor.AQUA + "You have to wait for " +
                        ChatColor.DARK_RED + (cooldown - (current - playerTime)) + ChatColor.AQUA + " seconds");
                return true;
            }

            if(cooldown > 0) {
                player.setMetadata("Kit." + strings[0], new FixedMetadataValue(plugin, System.currentTimeMillis()));
            }


            String cost = config.getString(strings[0] + ".Cost");
            Material type = Material.valueOf(cost.split(" ")[0]);
            int amount  = Integer.valueOf(cost.split(" ")[1]);
            if(amount > 0) {
                if(player.getInventory().contains(type, amount)) {
                    ItemStack item = null;
                    for(ItemStack i : player.getInventory().getContents()) {
                        if(i == null) continue;
                        if(i.getType() == type && i.getAmount() >= amount) {
                            item = i;
                            i.setAmount(i.getAmount() - amount);
                        }
                    }
                    player.updateInventory();
                    if(item == null) {
                        player.sendMessage(ChatColor.AQUA + "You need " +
                        ChatColor.DARK_RED + amount + ChatColor.AQUA +  " of " +
                                ChatColor.DARK_RED + type + ChatColor.AQUA + " to get this kit.");
                        return true;
                    }
                }
            }
            List<String> commands = config.getStringList(strings[0] + ".Commands");
            for(String str : commands) {
                str = str.replace("@p", commandSender.getName());
                plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), str);
            }


        }    return true;

    }

    private File createRulesFile() {
        try {
            if (!plugin.getDataFolder().exists()) {
                //noinspection ResultOfMethodCallIgnored
                plugin.getDataFolder().mkdirs();
            }
            File file = new File(plugin.getDataFolder(), "kits.yml");
            if (!file.exists()) {
                plugin.getLogger().info("kits.yml not found, creating!");
                InputStream rules = plugin.getResource("kits.yml");
                if(rules == null) return file;

                //noinspection deprecation
                config = YamlConfiguration.loadConfiguration(rules);
                try {
                    config.save(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

        return new File(plugin.getDataFolder(), "kits.yml");

    }

}
