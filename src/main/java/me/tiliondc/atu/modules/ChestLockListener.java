package me.tiliondc.atu.modules;

import me.tiliondc.atu.ATilionUtilities;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.*;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.InventoryHolder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ChestLockListener implements Listener{

    ATilionUtilities plugin;

    public ChestLockListener(ATilionUtilities plugin) {

        this.plugin = plugin;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);

    }

    @EventHandler
    public void playerChangeSign(SignChangeEvent e) {
        if(e.getLine(0).contains("cancel")) e.setCancelled(true);

        if(!e.getPlayer().hasPermission("atu.chestlock")) return;

        Block b = e.getBlock();
        Set<Sign> signs = findAttachedSigns(b);
        Block c = null;
        if(b.getType().equals(Material.CHEST) || b.getType().equals(Material.TRAPPED_CHEST)) {
            c = findOtherDoubleChest((Chest) b.getState());
        }
        if(c != null) {
            signs.addAll(findAttachedSigns(c));
        }

        Set<String> names = getAllNames(signs);
        String owner = getOwnerName(names);

        if(signs != null && owner != null && !owner.toLowerCase().contains(e.getPlayer().getName().toLowerCase())) {
            e.setCancelled(true);
            return;
        }

        if(e.getLine(0).equalsIgnoreCase("[PRIVATE]")) {

            org.bukkit.material.Sign s = (org.bukkit.material.Sign) e.getBlock().getState().getData();

            if (!s.isWallSign()) return;

            if (!(e.getBlock().getRelative(s.getAttachedFace()).getState() instanceof InventoryHolder)) return;

            e.setLine(0, ChatColor.GREEN + "[PRIVATE]");
            if(owner != null) {
                if(Arrays.toString(names.toArray()).contains(e.getLine(1).toLowerCase())) {
                    e.setLine(1, "");
                } else {
                    e.setLine(1, ChatColor.GRAY + e.getLine(1));
                }
            } else {
                e.setLine(1, ChatColor.DARK_GRAY + e.getPlayer().getName());
            }
            if(Arrays.toString(names.toArray()).contains(e.getLine(2).toLowerCase())) {
                e.setLine(2, "");
            } else {
                e.setLine(2, ChatColor.GRAY + e.getLine(2));
            }
            if(Arrays.toString(names.toArray()).contains(e.getLine(3).toLowerCase())) {
                e.setLine(3, "");
            } else {
                e.setLine(3, ChatColor.GRAY + e.getLine(3));
            }
        }
    }

    @EventHandler
    public void playerInterract(InventoryOpenEvent e) {
        if(e.getPlayer().isOp()) return;
        Block b = e.getInventory().getLocation().getBlock();
        Set<Sign> signs = findAttachedSigns(b);

        Block c = null;
        if(b.getType().equals(Material.CHEST) || b.getType().equals(Material.TRAPPED_CHEST)) {
            c = findOtherDoubleChest((Chest) b.getState());
        }

        if(c != null) {
            signs.addAll(findAttachedSigns(c));
        }

        if(signs.isEmpty()) return;

        for(Sign s : signs) {
            if(Arrays.toString(s.getLines()).toLowerCase().contains(e.getPlayer().getName().toLowerCase())) return;
        }
        e.setCancelled(true);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if(e.getPlayer().isOp()) return;

        Block b = null;
        if(e.getBlock().getType().equals(Material.WALL_SIGN)) {
            if (!((Sign) e.getBlock().getState()).getLine(0).equals(ChatColor.GREEN + "[PRIVATE]")) return;
            org.bukkit.material.Sign sign = (org.bukkit.material.Sign) e.getBlock().getState().getData();
            b = e.getBlock().getRelative(sign.getAttachedFace());
        }

        if(e.getBlock().getState() instanceof InventoryHolder) {
            b = e.getBlock();
        }

        if(b == null) return;

            Set<Sign> signs = findAttachedSigns(b);
            Block c = null;
            if(b.getType().equals(Material.CHEST) || b.getType().equals(Material.TRAPPED_CHEST)) {
                c = findOtherDoubleChest((Chest) b.getState());
            }
            if(c != null) {
                signs.addAll(findAttachedSigns(c));
            }

            Set<String> names = getAllNames(signs);
            String owner = getOwnerName(names);
            if(owner != null && !owner.toLowerCase().contains(e.getPlayer().getName().toLowerCase())) e.setCancelled(true);
    }

    @EventHandler
    public void onInventoryEdit(InventoryMoveItemEvent e) {
        Block b1 = e.getSource().getLocation().getBlock();
        Block b2 = e.getDestination().getLocation().getBlock();

        Set<Sign> signs1 = findAttachedSigns(b1);
        Block c1 = null;
        if(b1.getType().equals(Material.CHEST) || b1.getType().equals(Material.TRAPPED_CHEST)) {
            c1 = findOtherDoubleChest((Chest) b1.getState());
        }
        if(c1 != null) {
            signs1.addAll(findAttachedSigns(c1));
        }

        Set<String> names1 = getAllNames(signs1);
        String owner1 = getOwnerName(names1);

        Set<Sign> signs2 = findAttachedSigns(b2);
        Block c2 = null;
        if(b2.getType().equals(Material.CHEST) || b2.getType().equals(Material.TRAPPED_CHEST)) {
            c2 = findOtherDoubleChest((Chest) b2.getState());
        }
        if(c2 != null) {
            signs2.addAll(findAttachedSigns(c2));
        }

        Set<String> names2 = getAllNames(signs2);
        String owner2 = getOwnerName(names2);

        if(owner1 != null && owner2 != null && owner1.equals(owner2)) return;

        e.setCancelled(true);
    }




    private Set<Sign> findAttachedSigns(Block b) {

        Set<Sign> signs = new HashSet<>();

        Sign sign = null;
        if(b.getRelative(BlockFace.NORTH).getType().equals(Material.WALL_SIGN))
            sign = (Sign) b.getRelative(BlockFace.NORTH).getState();
        if(sign != null && sign.getLine(0).equalsIgnoreCase(ChatColor.GREEN + "[PRIVATE]"))
            signs.add(sign);

        if(b.getRelative(BlockFace.EAST).getType().equals(Material.WALL_SIGN))
            sign = (Sign) b.getRelative(BlockFace.EAST).getState();
        if(sign != null && sign.getLine(0).equalsIgnoreCase(ChatColor.GREEN + "[PRIVATE]"))
            signs.add(sign);

        if(b.getRelative(BlockFace.SOUTH).getType().equals(Material.WALL_SIGN))
            sign = (Sign) b.getRelative(BlockFace.SOUTH).getState();
        if(sign != null && sign.getLine(0).equalsIgnoreCase(ChatColor.GREEN + "[PRIVATE]"))
            signs.add(sign);

        if(b.getRelative(BlockFace.WEST).getType().equals(Material.WALL_SIGN))
            sign = (Sign) b.getRelative(BlockFace.WEST).getState();
        if(sign != null && sign.getLine(0).equalsIgnoreCase(ChatColor.GREEN + "[PRIVATE]"))
            signs.add(sign);

        return signs;
    }

    private Block findOtherDoubleChest(Chest b) {
        Block c = b.getBlock();

        if(!(b.getInventory().getSize() > 50)) {
            return null;
        }
        Material chest = b.getType();
        if(c.getRelative(BlockFace.NORTH).getType().equals(chest)) return c.getRelative(BlockFace.NORTH);
        if(c.getRelative(BlockFace.EAST).getType().equals(chest)) return c.getRelative(BlockFace.EAST);
        if(c.getRelative(BlockFace.SOUTH).getType().equals(chest)) return c.getRelative(BlockFace.SOUTH);
        if(c.getRelative(BlockFace.WEST).getType().equals(chest)) return c.getRelative(BlockFace.WEST);

        return null;
    }

    private Set<String> getAllNames(Set<Sign> signs) {

        Set<String> names = new HashSet<>();

        for(Sign s : signs) {
            for(String st : s.getLines()) {
                names.add(st.toLowerCase());
            }
        }
        return names;
    }

    private String getOwnerName(Set<String> names){

        for(String s : names) {
            if(s.contains(ChatColor.DARK_GRAY.toString())) {
                return s;
            }
        }
        return null;

    }


}
