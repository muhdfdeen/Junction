# Junction

Junction is a [Floodgate](https://geysermc.org/download/?project=floodgate) addon for servers that are running Paper (and its forks). It allows administrators to automate permission assignments and execute edition-specific commands upon join and quit. This allows server admins to seamlessly manage the differences between editions, such as:
- Assigning Bedrock-specific prefixes or permission groups.
- Executing compensation commands automatically.
- Granting access to simplified sell menus or GUIs.

## Features

- Automatically assigns Bedrock Edition players to specific permission groups via LuckPerms or Vault.
- Execute console commands when players join or quit, with separate lists for Java and Bedrock editions.
- Includes internal placeholders (`{player}`, `{uuid}`) and supports PlaceholderAPI.

## Installation

1. Ensure your server is running **Java 21**+ and **Paper 1.21**+ (or a fork).
2. Install [Floodgate](https://geysermc.org/download/?project=floodgate) (Required for Bedrock player detection).
3. Download the latest release of Junction and place it in your `plugins` folder.
4. Optional Dependencies:
   - [LuckPerms](https://luckperms.net/) or [Vault](https://github.com/MilkBowl/Vault) or [VaultUnlocked](https://modrinth.com/plugin/vaultunlocked): Required only if you want to use the group assignment feature.
   - [PlaceholderAPI](https://placeholderapi.com/): Recommended if you want to use external placeholders in commands.
5. Restart your server.

## Commands & Permissions

| Command | Permission | Description |
| :--- | :--- | :--- |
| `/junction` | None | Displays plugin version info. |
| `/junction reload` | `junction.reload` | Reloads the configuration. |
| N/A | `junction.admin` | Receive update notifications. |

## Configuration

`config.yml`

```yaml
# Should debug mode be enabled for detailed logs?
debug: false
# This module automatically assigns Bedrock Edition players to a specific group.
permissions:
  # Should this module be enabled?
  enabled: false
  # Which permission provider should be used?
  # Available options: LuckPerms, Vault
  provider: LuckPerms
  # Which permission group should players be assigned to?
  group: geyser
# This module automatically executes commands based on Minecraft editions.
# Available internal placeholders: {player}, {uuid}
# PlaceholderAPI support is enabled if installed.
commands:
  # Should this module be enabled?
  enabled: false
  # Commands specific to Java Edition players.
  java:
    # List of console commands to execute when a player joins.
    join:
    - say Java player {player} joined
    # List of console commands to execute when a player quits.
    quit:
    - say Java player {player} left
  # Commands specific to Bedrock Edition players.
  bedrock:
    # List of console commands to execute when a player joins.
    join:
    - say Bedrock player {player} joined
    # List of console commands to execute when a player quits.
    quit:
    - say Bedrock player {player} left
```

`messages.yml`

```yaml
# Settings related to messages sent by the plugin.
messages:
  # Prefix for all messages sent by the plugin.
  prefix: <color:#00D4FF><bold>Junction</bold> ➟ </color>
  # Message displayed when the plugin is reloaded.
  reloadSuccess: Plugin configuration has been reloaded successfully.
  # Message displayed when the plugin fails to reload.
  reloadFail: <red>Failed to reload plugin configuration! Check console for errors.</red>
  # Message displayed when a new version of the plugin is available.
  updateAvailable: 'A new version is available! <gray>(Current: <red>{current_version}</red>
    | Latest: <green>{latest_version}</green>)</gray>'
```

## Building

```bash
./gradlew build
```

The compiled JAR will be in `build/libs/`.

## Support

For issues or questions, open an issue on [GitHub](https://github.com/muhdfdeen/junction/issues).
