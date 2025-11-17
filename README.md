# Junction

A lightweight Minecraft plugin that automatically assigns Bedrock Edition players to a permission group using Floodgate with LuckPerms or Vault support.

## Configuration

```yaml
#    __    __  __    __   __    ______   ______   __    ______    __   __   
#   /\ \  /\ \/\ \  /\ "-.\ \  /\  ___\ /\__  _\ /\ \  /\  __ \  /\ "-.\ \  
#  _\_\ \ \ \ \_\ \ \ \ \-.  \ \ \ \____\/_/\ \/ \ \ \ \ \ \/\ \ \ \ \-.  \ 
# /\_____\ \ \_____\ \ \_\\"\_\ \ \_____\  \ \_\  \ \_\ \ \_____\ \ \_\\"\_\
# \/_____/  \/_____/  \/_/ \/_/  \/_____/   \/_/   \/_/  \/_____/  \/_/ \/_/

# Enable debugging mode for detailed logs
debug: false

# Automatically assign players on Bedrock Edition to a specified group
permissions:
  enabled: true
  
  # Which permission plugin to use
  # Supported: "LuckPerms" or "Vault"
  provider: "LuckPerms"
  
  # Group assignment for Bedrock Edition players
  # Note: Group name must exist in your permission plugin!
  group: "bedrock"

# Do not touch this!
config-version: 1
```

## Building

```bash
./gradlew build
```

The compiled JAR will be in `build/libs/`.

## Support

For issues or questions, open an issue on [GitHub](https://github.com/muhdfdeen/junction/issues).