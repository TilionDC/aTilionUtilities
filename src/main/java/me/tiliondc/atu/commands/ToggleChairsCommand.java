package me.tiliondc.atu.commands;


import me.tiliondc.atu.ATilionUtilities;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

public class ToggleChairsCommand implements CommandExecutor {

    ATilionUtilities plugin;

    public ToggleChairsCommand(ATilionUtilities plugin) {

        this.plugin = plugin;

        plugin.getCommand("togglechairs").setExecutor(this);

    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if(commandSender instanceof Player) {

            Player p = (Player) commandSender;
            if(!p.hasMetadata("USE_CHAIRS")) p.setMetadata("USE_CHAIRS", new FixedMetadataValue(plugin, true));

            p.setMetadata("USE_CHAIRS", new FixedMetadataValue(plugin, !p.getMetadata("USE_CHAIRS").get(0).asBoolean()));
            p.sendMessage(ChatColor.DARK_GREEN + "You turned chairs " + (p.getMetadata("USE_CHAIRS").get(0).asBoolean() ? ChatColor.GREEN + "on" : ChatColor.RED + "off"));
            return true;
        }

        return false;
    }
}
