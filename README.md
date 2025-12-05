# Junction

A Minecraft plugin that automatically assigns Bedrock Edition players to a permission group using Floodgate. Supports LuckPerms and Vault.

> [!NOTE]
> Inspired by [BedrockPlayerManager](https://github.com/ofunny/BedrockPlayerManager). Junction is a modernized alternative, created as an initiative to refine my Java development skills.

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

## Configuration

```yaml
# Should debug mode be enabled for detailed logs?
debug: true
# This module automatically assigns Bedrock Edition players to a specific group.
permissions:
  # Should this module be enabled?
  enabled: true
  # Which permission provider should be used?
  # Available options: LuckPerms, Vault
  provider: LuckPerms
  # Which permission group should players be assigned to?
  group: geyser
```

## Building

```bash
./gradlew clean shadowJar
```

The compiled JAR will be in `build/libs/`.

## Support

For issues or questions, open an issue on [GitHub](https://github.com/muhdfdeen/junction/issues).
