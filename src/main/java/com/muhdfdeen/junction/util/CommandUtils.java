package com.muhdfdeen.junction.util;

import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CommandUtils {
    public static void dispatch(Player player, List<String> commands) {
        if (commands == null || commands.isEmpty())
            return;
        for (String command : commands) {
            String parsedCommand = command.replace("{player}", player.getName());
            if (parsedCommand.startsWith("/"))
                parsedCommand = parsedCommand.substring(1);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), parsedCommand);
        }
    }
}
