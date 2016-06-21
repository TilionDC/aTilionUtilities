package me.tiliondc.atu.modules;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class ElevatorSignListener implements Listener {

    JavaPlugin plugin;

    int maxDistance;
    boolean allowJumpAndSneak;
    boolean allowRedstone;
    int maxPadSize;

    public ElevatorSignListener(JavaPlugin plugin, int maxDistance, boolean allowJumpAndSneak, boolean allowRedstone, int maxPadSize) {

        this.plugin = plugin;
        this.maxDistance = maxDistance;
        this.allowJumpAndSneak = allowJumpAndSneak;
        this.allowRedstone = allowRedstone;
        this.maxPadSize = maxPadSize;


        plugin.getServer().getPluginManager().registerEvents(this, plugin);

    }

    @EventHandler
    public void playerChangeSign(SignChangeEvent e) {

        if(e.getLine(0).equalsIgnoreCase("[ELEVATOR]")) {
            if(!e.getPlayer().hasPermission("atu.elevators")) return;

            e.setLine(0, "[ELEVATOR]");
            Location loc = new Location(e.getBlock().getWorld(), e.getBlock().getX(), e.getBlock().getY() - 1, e.getBlock().getZ());
            if(!loc.getBlock().getType().isSolid()) loc = new Location(e.getBlock().getWorld(), e.getBlock().getX(), e.getBlock().getY() - 2, e.getBlock().getZ());
            if(!loc.getBlock().getType().isSolid()) loc = new Location(e.getBlock().getWorld(), e.getBlock().getX(), e.getBlock().getY() - 3, e.getBlock().getZ());

            if(!loc.getBlock().getType().isSolid()) {
                e.setLine(0, ChatColor.DARK_RED + "[ELEVATOR]");
                return;
            }

            if(!e.getLine(1).equalsIgnoreCase("[DOWN]") && !e.getLine(1).equalsIgnoreCase("[UP]")) {
                Sign s = findSign(e.getBlock().getLocation(), true);
                if(s == null) {
                    s = findSign(e.getBlock().getLocation(), false);
                }
                if(s == null) {
                    e.setLine(0, ChatColor.DARK_RED + "[ELEVATOR]");
                } else {
                    enableSigns(s, true);
                    e.setLine(0, ChatColor.BLUE + "[ELEVATOR]");
                }

            }

            if(e.getLine(1).equalsIgnoreCase("[UP]")) {
                Sign sign = findSign(e.getBlock().getLocation(), true);
                if(sign != null) {

                    if(sign.getLine(1).contains("[DOWN]")) {
                        e.setLine(0, ChatColor.BLUE + "[ELEVATOR]");
                        e.setLine(1, ChatColor.DARK_GREEN + "[UP]");
                        enableSigns(sign, true);
                    }

                }
            }

            if(e.getLine(1).equalsIgnoreCase("[DOWN]")) {
                Sign sign = findSign(e.getBlock().getLocation(), false);
                if(sign != null) {

                    if(sign.getLine(1).contains("[UP]")) {
                        e.setLine(0, ChatColor.BLUE + "[ELEVATOR]");
                        e.setLine(1, ChatColor.DARK_GREEN + "[DOWN]");
                        enableSigns(sign, true);
                    }
                }
            }

            if(e.getLine(1).equalsIgnoreCase("[DOWN]") || e.getLine(1).equalsIgnoreCase("[UP]")) {
                e.setLine(1, ChatColor.DARK_RED + e.getLine(1).toUpperCase());
            }
        }
    }

    private Sign findSign(Location loc, boolean up) {

        int wall = up ? loc.getBlockY() + maxDistance : loc.getBlockY() - maxDistance;
        wall = wall < 0 ? 0 : wall;
        wall = wall > 255 ? 255 : wall;
        if(up) {
            for (int i = loc.getBlockY() + 1; i < wall; i++) {

                Location l = new Location(loc.getWorld(), loc.getX(), i, loc.getZ());

                if (l.getBlock().getType().equals(Material.WALL_SIGN)) {

                    Sign sign = (Sign) l.getBlock().getState();
                    if (sign.getLine(0).contains("[ELEVATOR]")) {

                        if (sign.getLine(1).contains("[DOWN]")) {
                            return sign;
                        }

                        if (!sign.getLine(1).contains("[DOWN]") && !sign.getLine(1).contains("[UP]")) {
                            return sign;
                        }
                    }
                }
            }
        } else {
            for (int i = loc.getBlockY() - 1; i > wall; i--) {

                Location l = new Location(loc.getWorld(), loc.getX(), i, loc.getZ());

                if (l.getBlock().getType().equals(Material.WALL_SIGN)) {

                    Sign sign = (Sign) l.getBlock().getState();
                    if (sign.getLine(0).contains("[ELEVATOR]")) {

                        if (sign.getLine(1).contains("[UP]")) {
                            return sign;
                        }

                        if (!sign.getLine(1).contains("[DOWN]") && !sign.getLine(1).contains("[UP]")) {
                            return sign;
                        }
                    }
                }
            }
        }
        return null;
    }

    private void enableSigns(Sign sign, boolean enabled) {

        if(enabled) {
            sign.setLine(0, ChatColor.BLUE + "[ELEVATOR]");
            if (sign.getLine(1).contains("[UP]")) sign.setLine(1, ChatColor.DARK_GREEN + "[UP]");
            if (sign.getLine(1).contains("[DOWN]")) sign.setLine(1, ChatColor.DARK_GREEN + "[DOWN]");
            if (sign.getLine(1).equalsIgnoreCase("[UP]")) sign.setLine(1, ChatColor.DARK_GREEN + "[UP]");
            if (sign.getLine(1).equalsIgnoreCase("[DOWN]")) sign.setLine(1, ChatColor.DARK_GREEN + "[DOWN]");
            sign.update();
        } else {
            sign.setLine(0, ChatColor.DARK_RED + "[ELEVATOR]");
            if (sign.getLine(1).contains("[UP]")) sign.setLine(1, ChatColor.DARK_RED + "[UP]");
            if (sign.getLine(1).contains("[DOWN]")) sign.setLine(1, ChatColor.DARK_RED + "[DOWN]");
            if (sign.getLine(1).equalsIgnoreCase("[UP]")) sign.setLine(1, ChatColor.DARK_RED + "[UP]");
            if (sign.getLine(1).equalsIgnoreCase("[DOWN]")) sign.setLine(1, ChatColor.DARK_RED + "[DOWN]");
            sign.update();
        }

        sign.update(true);

    }

    @EventHandler
    public void playerJumpEvent(PlayerMoveEvent e) {

        if(allowJumpAndSneak) {
            if(e.getFrom().getY() < e.getTo().getY()) {
                if (new Location(e.getFrom().getWorld(), e.getFrom().getX(), e.getFrom().getY() + 1, e.getFrom().getZ())
                        .getBlock().getType().equals(Material.WALL_SIGN)) {
                    if(!e.getPlayer().hasPermission("atu.elevators")) return;
                    Sign sign = (Sign) new Location(e.getFrom().getWorld(), e.getFrom().getX(),
                            e.getFrom().getY() + 1, e.getFrom().getZ()).getBlock().getState();
                    if(sign.getLine(0).equals(ChatColor.BLUE + "[ELEVATOR]")) {
                        if(!sign.getLine(1).contains("[DOWN]") && !sign.getLine(1).contains("[UP]")) {

                            Sign s = findSign(sign.getLocation(), true);

                            if(s != null) {
                                Location l = e.getPlayer().getLocation().clone();
                                l.setY(s.getY());
                                e.getPlayer().teleport(l);
                            } else {
                                s = findSign(sign.getLocation(), false);
                                if(s == null) {
                                    enableSigns(sign, false);
                                }
                            }

                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void playerSneakEvent(PlayerToggleSneakEvent e) {
        if(!e.getPlayer().hasPermission("atu.elevators")) return;

        if(allowJumpAndSneak) {
            if (new Location(e.getPlayer().getWorld(), e.getPlayer().getLocation().getX(), e.getPlayer().getLocation()
                    .getY() + 1, e.getPlayer().getLocation().getZ()).getBlock().getType().equals(Material.WALL_SIGN)) {

                Sign sign = (Sign) new Location(e.getPlayer().getWorld(), e.getPlayer().getLocation().getX(), e.getPlayer().getLocation()
                        .getY() + 1, e.getPlayer().getLocation().getZ()).getBlock().getState();

                if (sign.getLine(0).equals(ChatColor.BLUE + "[ELEVATOR]")) {

                    if(!sign.getLine(1).contains("[DOWN]") && !sign.getLine(1).contains("[UP]")) {

                        Sign s = findSign(sign.getLocation(), false);

                        if(s != null) {
                            Location l = e.getPlayer().getLocation().clone();
                            l.setY(s.getY());
                            e.getPlayer().teleport(l);
                        } else {
                            s = findSign(sign.getLocation(), true);
                            if(s == null) {
                                enableSigns(sign, false);
                            }
                        }

                    }
                }
            }
        }
    }

    public void powerSign(Sign sign) {
        if(sign.getLine(0).equals(ChatColor.BLUE + "[ELEVATOR]")) {

            Block base = sign.getBlock().getRelative(BlockFace.DOWN);
            if(base.isEmpty() || !base.getType().isSolid()) base = base.getRelative(BlockFace.DOWN);
            if(base.isEmpty() || !base.getType().isSolid()) base = base.getRelative(BlockFace.DOWN);
            if(base.isEmpty() || !base.getType().isSolid()) return;
            if(base.getType().toString().contains("PLATE"))
                base = base.getRelative(BlockFace.DOWN);
            ArrayList<Entity> ents = new ArrayList<>();


            for(Entity ent : sign.getWorld().getNearbyEntities(sign.getLocation(), maxPadSize, 2, maxPadSize)) {
                ents.add(ent);
            }

            if(sign.getLine(1).equals(ChatColor.DARK_GREEN + "[UP]")) {

                Sign s = findSign(sign.getLocation(), true);
                if(s == null) return;
                if(!s.getLine(1).contains("[DOWN]")) return;
                Block ba = s.getBlock().getRelative(BlockFace.DOWN);
                if(ba != null && !ba.getType().isSolid()) ba = ba.getRelative(BlockFace.DOWN);
                if(ba != null && !ba.getType().isSolid()) ba = ba.getRelative(BlockFace.DOWN);
                if(ba == null || !ba.getType().isSolid()) return;
                if(ba.getType().toString().contains("PLATE"))
                    ba = ba.getRelative(BlockFace.DOWN);

                for(Entity e : ents) {
                    Location loc = e.getLocation();
                    loc.setY(ba.getY() + 1);
                    if(loc.getBlock().getRelative(BlockFace.DOWN).isEmpty()) continue;
                    if(e.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() != base.getType()) continue;
                    e.teleport(loc);
                }
            }
            if(sign.getLine(1).equals(ChatColor.DARK_GREEN + "[DOWN]")) {

                Sign s = findSign(sign.getLocation(), false);
                if(s == null) return;
                if(!s.getLine(1).contains("[UP]")) return;
                Block ba = s.getBlock().getRelative(BlockFace.DOWN);
                if(ba != null && !ba.getType().isSolid()) ba = ba.getRelative(BlockFace.DOWN);
                if(ba != null && !ba.getType().isSolid()) ba = ba.getRelative(BlockFace.DOWN);
                if(ba == null || !ba.getType().isSolid()) return;
                if(ba.getType().toString().contains("PLATE"))
                    ba = ba.getRelative(BlockFace.DOWN);
                for(Entity e : ents) {
                    Location loc = e.getLocation();
                    loc.setY(ba.getY() + 1);
                    if(loc.getBlock().getRelative(BlockFace.DOWN).isEmpty()) continue;
                    if(e.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() != base.getType()) continue;
                    e.teleport(loc);
                }
            }
        }
    }

    @EventHandler
    public void redstoneEvent(BlockRedstoneEvent e ) {

        if(allowRedstone) {
            BlockFace[] faces = {BlockFace.DOWN, BlockFace.EAST, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.UP, BlockFace.WEST};

            if(e.getBlock().getType().equals(Material.REDSTONE_LAMP_OFF)) {

                for(BlockFace f : faces) {
                    if(e.getBlock().getRelative(f).getType().equals(Material.WALL_SIGN)) {
                        Sign sign = (Sign) e.getBlock().getRelative(f).getState();
                        if(sign.getLine(0).equals(ChatColor.BLUE + "[ELEVATOR]")) {
                            powerSign(sign);
                        }
                    }
                }
            }
        }
    }
}
