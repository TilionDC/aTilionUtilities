package me.tiliondc.atu.modules;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public class RepairExplosionDamage implements Listener {

    JavaPlugin plugin;
    List<Block> blocks;
    List<Material> data;

    boolean repairTnt;
    boolean dropBlocks;
    int ticksDelay;

    public RepairExplosionDamage(JavaPlugin plugin, boolean repairTnt, boolean dropBlocks, int ticksDelay) {

        this.plugin = plugin;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);

        this.repairTnt = repairTnt;
        this.dropBlocks = dropBlocks;
        this.ticksDelay = ticksDelay;

        blocks = new ArrayList<>();
        data = new ArrayList<>();

        BukkitTask task = new BukkitRunnable() {

            @Override
            public void run() {
                for(int i = 0; i < blocks.size(); i++) {
                    blocks.get(i).setType(data.get(i));
                    blocks.remove(i);
                    data.remove(i);
                }
            }
        }.runTaskTimer(plugin, ticksDelay, ticksDelay);

    }

    @EventHandler
    public void onExplosion(EntityExplodeEvent e) {
        if(!repairTnt && e.getEntityType().equals(EntityType.PRIMED_TNT)) return;
        blocks.addAll(e.blockList());
        for(Block b : e.blockList()) {
            data.add(b.getType());
            b.setType(Material.AIR);
        }
    }

}
