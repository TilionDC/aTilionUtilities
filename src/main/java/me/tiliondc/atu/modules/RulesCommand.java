package me.tiliondc.atu.modules;

import me.tiliondc.atu.ATilionUtilities;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class RulesCommand implements CommandExecutor {

    ATilionUtilities plugin;

    FileConfiguration config = null;

    public RulesCommand(ATilionUtilities plugin) {

        this.plugin = plugin;

        plugin.getCommand("rules").setExecutor(this);

        File file = createRulesFile();

        config = YamlConfiguration.loadConfiguration(file);

    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(config == null) {
            commandSender.sendMessage(ChatColor.RED + "Something went wrong with this command");
            return true;
        }
        if(strings.length > 1) return false;
        String world;

        if(strings.length == 0) {
            if(commandSender instanceof Player) {
                world = ((Player)commandSender).getWorld().getName();
            } else {
                world = "global";
            }
        } else {
            if(plugin.getServer().getWorld(strings[0]) ==  null) world = "global";
            else world = strings[0];
        }

        if(!config.contains(world)) world = "global";
        if(!config.contains(world)) {
            commandSender.sendMessage(ChatColor.BLUE + "There are no rules specified in the rules.yml");
            return true;
        }
        List<String> listofrules = config.getStringList(world);
        for(String str : listofrules) {
            str = str.replace("&", ChatColor.COLOR_CHAR + "").replace(ChatColor.COLOR_CHAR + " ", "& ");
            commandSender.sendMessage(str);
        }

        return true;
    }

    private File createRulesFile() {
        try {
            if (!plugin.getDataFolder().exists()) {
                //noinspection ResultOfMethodCallIgnored
                plugin.getDataFolder().mkdirs();
            }
            File file = new File(plugin.getDataFolder(), "rules.yml");
            if (!file.exists()) {
                plugin.getLogger().info("rules.yml not found, creating!");
                InputStream rules = plugin.getResource("rules.yml");
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

        return new File(plugin.getDataFolder(), "rules.yml");

    }
}
