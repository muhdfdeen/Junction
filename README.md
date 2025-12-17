# Junction

**Junction** is a streamlined solution for Geyser/Floodgate servers. It automatically detects Bedrock Edition players upon join and assigns them to a specific permission group.

This allows server admins to easily separate Java and Bedrock permissions (e.g., giving Bedrock players access to specific commands, kits, or prefixes) without manual intervention.

## Features

- Identifies Bedrock Edition players via Floodgate API.
- Works with LuckPerms or any Vault-compatible permission plugin.

## Requirements
- **Java 21** or higher
- [Paper 1.21+](https://papermc.io/downloads/paper)
- [Floodgate](https://geysermc.org/download/?project=floodgate)
- A permission plugin (LuckPerms recommended)

## Commands & Permissions

| Command | Permission | Description |
| :--- | :--- | :--- |
| `/junction` | None | Displays plugin version info. |
| `/junction reload` | `junction.reload` | Reloads the configuration. |
| N/A | `junction.admin` | Receive update notifications. |

## Configuration

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
./gradlew clean shadowJar
```

The compiled JAR will be in `build/libs/`.

## Support

For issues or questions, open an issue on [GitHub](https://github.com/muhdfdeen/junction/issues).
