package me.tiliondc.atu.commands;


import javafx.scene.control.Toggle;
import me.tiliondc.atu.ATilionUtilities;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

public class ToggleChairsCommand implements CommandExecutor{

    ATilionUtilities plugin;

    public ToggleChairsCommand(ATilionUtilities plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if(commandSender instanceof Player) {

            Player p = (Player) commandSender;

            p.setMetadata("USE_CHAIRS", new FixedMetadataValue(plugin, true));

        }

        return false;
    }
}
