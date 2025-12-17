package com.muhdfdeen.junction.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.geysermc.floodgate.api.FloodgateApi;

import com.muhdfdeen.junction.Junction;
import com.muhdfdeen.junction.config.Config.MainConfiguration; 
import com.muhdfdeen.junction.permission.PermissionProvider;
import com.muhdfdeen.junction.util.Logger;

public class PlayerJoinListener implements Listener {
    private final Junction plugin;

    public PlayerJoinListener(Junction plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        MainConfiguration config = plugin.getConfiguration();
        Logger log = plugin.getPluginLogger();

        log.debug("Player join event triggered: " + player.getName());

        var floodgatePlayer = FloodgateApi.getInstance().getPlayer(player.getUniqueId());

        if (floodgatePlayer == null) {
            log.debug("Skipping Java player: " + player.getName());
            return;
        }

        log.debug("Processing Bedrock player: " + player.getName());
        log.debug("UUID: " + player.getUniqueId());
        log.debug("Device: " + floodgatePlayer.getDeviceOs());
        log.debug("Input: " + floodgatePlayer.getInputMode());

        PermissionProvider permissionProvider = plugin.getPermissionProvider();

        if (permissionProvider == null) {
            log.warn("Can't assign group to " + player.getName() + ", no permission provider available.");
            return;
        }

        log.debug("Permission provider: " + permissionProvider.getName());

        String groupName = config.permissions.group();

        if (groupName == null || groupName.isEmpty()) {
            log.error("Bedrock group name not configured, check your config file.");
            return;
        }

        if (permissionProvider.isPlayerInGroup(player, groupName)) {
            log.debug(player.getName() + " already in group: " + groupName + " - skipping...");
            return;
        }

        log.debug("Attempting to add " + player.getName() + " to group: " + groupName);

        boolean success = permissionProvider.addPlayerToGroup(player, groupName);
        if (success) {
            log.info("Added Bedrock player " + player.getName() + " to group '" + groupName + "'");
        } else {
            log.warn("Failed to add " + player.getName() + " to group '" + groupName + "'");
        }
    }
}