package com.muhdfdeen.junction.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import org.geysermc.floodgate.api.FloodgateApi;

import com.muhdfdeen.junction.Junction;
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
        Logger log = plugin.getPluginLogger();

        log.debug("Player join event triggered: " + player.getName());

        boolean isBedrockPlayer = FloodgateApi.getInstance().isFloodgatePlayer(player.getUniqueId());
        log.debug(player.getName() + " detected as " + (isBedrockPlayer ? "Bedrock" : "Java") + " Edition");

        if (!isBedrockPlayer) {
            log.debug("Skipping Java player: " + player.getName());
            return;
        }

        log.debug("Processing Bedrock player: " + player.getName());

        PermissionProvider permissionProvider = plugin.getPermissionProvider();

        if (permissionProvider == null) {
            log.warn("Can't assign group to " + player.getName() + ", no permission provider available");
            return;
        }

        log.debug("Permission provider: " + permissionProvider.getName());

        String groupName = plugin.getConfig().getString("permissions.group");
        if (groupName == null || groupName.isEmpty()) {
            log.error("Bedrock group name not configured, check your config file");
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
