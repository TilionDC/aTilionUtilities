package me.tiliondc.atu.listeners;

import me.tiliondc.atu.ATilionUtilities;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.spigotmc.event.entity.EntityDismountEvent;

public class StairChairListener implements Listener {

    ATilionUtilities plugin;
    double distance;
    boolean requireSigns;
    boolean requireEmptyHand;

    public StairChairListener(ATilionUtilities plugin, double distance, boolean requireSigns, boolean requireEmptyHand) {

        this.plugin = plugin;
        this.distance = distance;
        this.requireSigns = requireSigns;
        this.requireEmptyHand = requireEmptyHand;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);

    }

    @EventHandler
    public void playerWantsToSit(PlayerInteractEvent e) {
        if(e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock() != null) {
            if(e.getClickedBlock().getType().toString().toLowerCase().contains("stairs")) {
                if (e.getPlayer().getLocation().distance(e.getClickedBlock().getLocation()) < distance) {
                    if(!e.getPlayer().hasPermission("atu.chairs")) return;

                    if(!e.getPlayer().hasMetadata("USE_CHAIRS"))
                        e.getPlayer().setMetadata("USE_CHAIRS", new FixedMetadataValue(plugin, true));

                    if (e.getPlayer().getMetadata("USE_CHAIRS").get(0).asBoolean()) {

                        Location l = e.getClickedBlock().getLocation();

                        if (requireSigns) {

                            boolean signs;
                            signs = ((e.getClickedBlock().getRelative(BlockFace.EAST).getType() == Material.WALL_SIGN) &&
                                    (e.getClickedBlock().getRelative(BlockFace.WEST).getType() == Material.WALL_SIGN))
                                    || (((e.getClickedBlock().getRelative(BlockFace.NORTH).getType() == Material.WALL_SIGN) &&
                                    (e.getClickedBlock().getRelative(BlockFace.SOUTH).getType() == Material.WALL_SIGN)));
                            if (!signs) return;
                        }

                        if (e.getPlayer().isSneaking()) return;
                        if (requireEmptyHand) {
                            //noinspection deprecation
                            ItemStack item = e.getPlayer().getInventory().getItemInMainHand();
                            if (item.getType() != Material.AIR) return;

                        }


                        Arrow a = l.getWorld().spawnArrow(l.add(0.5, 0, 0.5), l.getDirection(), 0, 0);
                        e.getPlayer().teleport(a);
                        a.setPassenger(e.getPlayer());
                        e.getPlayer().setSneaking(false);
                    }
                }
            }
        }


    }

    @EventHandler
    public void playerWantToUnsit(EntityDismountEvent e) {
        if(e.getDismounted() instanceof Arrow) {
            ((Player) e.getEntity()).setAllowFlight(true);
            e.getEntity().teleport(e.getDismounted());
            e.getDismounted().remove();
        }
    }




}

