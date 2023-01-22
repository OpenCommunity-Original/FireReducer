package org.opencommunity.FireReducer;

import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockSpreadEvent;

public class FireReducer implements Listener {
    private int fireBlockCount = 100;
    private int radius = 15;
    private boolean reportToConsole = true;

    public FireReducer(FileConfiguration config) {
        fireBlockCount = config.getInt("big-fire.block-count", 100);
        radius = config.getInt("big-fire.radius", 15);
        reportToConsole = config.getBoolean("big-fire.report-to-console", true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockSpread(BlockSpreadEvent event) {
        if (event.getSource().getType() == Material.FIRE) {
            Location location = event.getBlock().getLocation();
            World world = location.getWorld();
            int fireX = location.getBlockX();
            int fireY = location.getBlockY();
            int fireZ = location.getBlockZ();
            int fireSize = 0;
            for (int x = fireX - radius; x <= fireX + radius; x++) {
                for (int y = fireY - radius; y <= fireY + radius; y++) {
                    for (int z = fireZ - radius; z <= fireZ + radius; z++) {
                        if (world.getBlockAt(x, y, z).getType().equals(Material.FIRE)) {
                            fireSize++;
                        }
                    }
                }
            }
            if (fireSize > fireBlockCount) {
                for (int x = fireX - radius; x <= fireX + radius; x++) {
                    for (int y = fireY - radius; y <= fireY + radius; y++) {
                        for (int z = fireZ - radius; z <= fireZ + radius; z++) {
                            if (world.getBlockAt(x, y, z).getType().equals(Material.FIRE)) {
                                world.getBlockAt(x, y, z).setType(Material.AIR);
                            }
                        }
                    }
                }
                if (reportToConsole) {
                    Bukkit.getLogger().info("An excessively large fire was detected and eliminated at x:" + fireX + " y:" + fireY + " z:" + fireZ + " with " + fireSize + " blocks");
                }
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (player.hasPermission("opencommynity.moder")) {
                        player.sendMessage(ChatColor.RED + "An excessively large fire was detected and eliminated at x:" + fireX + " y:" + fireY + " z:" + fireZ + " with " + fireSize + " blocks");
                    }
                }

            }
        }
    }
}