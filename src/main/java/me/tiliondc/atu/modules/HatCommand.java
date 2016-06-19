package me.tiliondc.atu.modules;

import me.tiliondc.atu.ATilionUtilities;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class HatCommand implements CommandExecutor {

    ATilionUtilities plugin;

    public HatCommand(ATilionUtilities plugin) {
        this.plugin = plugin;

        plugin.getCommand("hat").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(strings.length > 0) return false;
        if(!(commandSender instanceof Player)) return false;
        Player player = (Player) commandSender;
        ItemStack helmet = player.getInventory().getHelmet();
        ItemStack hand = player.getInventory().getItemInMainHand();
        player.getInventory().setHelmet(hand);
        player.getInventory().setItemInMainHand(helmet);

        return true;
    }
}
