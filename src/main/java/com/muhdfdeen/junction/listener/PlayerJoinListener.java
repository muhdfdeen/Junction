package com.muhdfdeen.junction.listener;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.geysermc.floodgate.api.FloodgateApi;

import com.muhdfdeen.junction.Junction;
import com.muhdfdeen.junction.config.ConfigManager;
import com.muhdfdeen.junction.permission.PermissionProvider;
import com.muhdfdeen.junction.util.CommandUtils;
import com.muhdfdeen.junction.util.Logger;

public class PlayerJoinListener implements Listener {
    private final Junction plugin;

    public PlayerJoinListener(Junction plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        ConfigManager config = plugin.getConfiguration();
        Logger log = plugin.getPluginLogger();

        PermissionProvider permissionProvider = plugin.getPermissionProvider();
        boolean isBedrock = FloodgateApi.getInstance().isFloodgatePlayer(player.getUniqueId());

        log.debug("Player join event triggered: " + player.getName() + " (Bedrock: " + isBedrock + ")");

        if (permissionProvider != null) {
            String groupName = config.getMainConfig().permissions.group();
            if (isBedrock) {
                if (!permissionProvider.isPlayerInGroup(player, groupName)) {
                    log.debug("Attempting to add " + player.getName() + " to group: " + groupName);
                    boolean success = permissionProvider.addPlayerToGroup(player, groupName);
                    if (success)
                        log.info("Added Bedrock player " + player.getName() + " to group '" + groupName + "'");
                    else
                        log.warn("Failed to add " + player.getName() + " to group '" + groupName + "'");
                }
            } else {
                if (permissionProvider.isPlayerInGroup(player, groupName)) {
                    permissionProvider.removePlayerFromGroup(player, groupName);
                    log.debug("Removed leftover group '" + groupName + "' from Java player " + player.getName());
                }
            }
        }

        if (config.getMainConfig().commands.enabled())
            handleCommands(player, isBedrock, config, log);
    }

    private void handleCommands(Player player, boolean isBedrock, ConfigManager config, Logger log) {
        List<String> commands;

        if (isBedrock) {
            commands = config.getMainConfig().commands.bedrock().quit();
        } else {
            commands = config.getMainConfig().commands.java().quit();
        }

        CommandUtils.dispatch(player, commands);
    }
}