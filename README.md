# aTilionUtilities

### Version: 0.1

The purpose of this plugin is to add some extra functionality and fun features to your minecraft server.
This is a utilitiy plugin and has some of the features essentials have. But it is not created to take essentials place.
Any features in this mod can be disabled in the config.

## Features:

### Current Features:
- **Fly** - Do /fly [time] [player name] to fly or disable flying.
- **Sudo** - You can can override anyones chat and make them do commands.
- **Path block** - You walk faster on path blocks
- **Stair Chairs** - You can sit on chairs.
- **Elevator Signs** - Use signs to teleport a player up or down.
- **Color to text and signs** - write &[hexadecimal code] to get the color of your sign text or chat text.
- **Show Rules** - Display rules of server with /rules. The rules are defined in a rules.txt
- **Hat Command** - Use /hat to place the block in your hand as your helmet. Some blocks may have effects...
- **Kit** - You can give players the ability to /kit [kit name] if they have the atu.kit.[kit name} permission.
- **Heal command** - Heal yoursel or a player with /heal [player] [hearts] *Contains Easter egg*
- **Feed command** - Feed yourself or a player with /feed [player] [chickenlegs]

###  Upcomming features:
- **Locked Chests Signs** - Use signs to lock a chest to you.
- **Special items and armor** - Special tools and armor that give special gameplay effects.
- **Bridge dispenser/Sign** - Place a sign on dispencers to create a retracting/extracting bridge.
- **Welcome message** - Display a welcome/login message with boss bar.
- **Reply to /tell** - Reply to tell command with . (dot) before the message. Ex: .Hello Player
- **Recipe index** - Do /recpie to display an interface of the items recipe.
- **Nick and reveal command** - do /nick to nick and /reveal to see real name of nick.
- **Show Inventory** - Show inventory of another player with /inventory [player]
- **Lightning** - Shoot a lightning bolt at the location you are looking at.
- **tpa / tph** - Teleport to a player with permission of teleport the player to you.
- **Remote redstone sign** - use signs to setup remote redstone signals.
- **Linked chests** - Setup a sign on a chest to link it to a "Mother chest".
- **Black holes** - Terrain generated black holes that will teleport you somewhere else.

## Commands: 

```
  atu:
    desciption: General atilionUtilities command
    permission: atu.admin
    usage: Error, do '/atu help'
  sudo:
    description: Make a player do a command
    permission: atu.sudo
    usage: /sudo [player] [command]
  togglechairs:
    description: Toggles if you want to sit on stairs or not
    permission: atu.chairs
    usage: /togglechairs to toggle if you want to sit on stairs or not.
  fly:
    description: Enables/Disables flying
    permission: atu.fly
    usage: /fly [time in secounds] [playername]
  rules:
    description: Display the server rules.
    permission: atu.rules
    usage: /rules [world] to display the rules of the world.
  hat:
    desciption: Set block in hand as a hat on the head.
    permission: atu.hat
    usage: /hat to put the block in hand as a hat.
  kit:
    description: Gives a player the desired kit
    permission: atu.kit
    usage: /kit [kitname] to get the kit or only /kit to see the available kits.
  heal:
    description: Heals a player an given amount of health
    permission: atu.heal
    usage: /heal {player] [amount] to heal a player.
  feed:
    description: Feeds a player an given amount of hunger
    permission: atu.feed
    usage: /feed {player] [amount] to feed a player.
```


## Permissions:

```
  atu.admin:
    description: Permission for admin stuff
  atu.sudo:
    description: Permission for the sudo command. Should not be given to anyone.
  atu.kit:
    description: Permission for giving any kit to anyone. atu.kit.[kitname] for specific kits.
  atu.fly:
    description: Permission for being able to fly in survival mode.
  atu.fly.others:
    description: Permission for giving others the fly command.
  atu.chairs:
    description: Permission required to sit and toggle sitting.
  atu.chestlock:
    description: Permission to be able to lock chests.
  atu.rules:
    description: Permission to see the rules.
  atu.hat:
    description: Permission for using hats.
  atu.heal:
    description: Permission to heal player.
  atu.feed:
    description: Permission to feed player.
  atu.chatcolors:
    description: Permission for writng in chat and on signs with color.
  atu.pathblock:
    description: Permission for walking faster on paths.
  atu.elevators:
    description: Permissoin to use elevators.
```

## Config

```
# ---------------------------------------------------------------------
# A Tilion Commands Configuration file
#
#
# Author TilionDC
# @ tiliondc@gmail.com
#
#
# --------------------------------------------------------------------
#
# Materials can be found at:
# https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Material.html
#
# When You update the plugin the config file updates automaticly.
# All your saved values should be saved to the new config file
#

# Version of config. Do NOT change this
Version: 0.1.0

# Path Config
Path:
  # Enable or disable this feature. Must be either true or false.
  Enabled: true
  # Speed between 0 and 1.
  # 0.2 is minecraft walk speed. 0.3 is config default
  Speed: 0.3

# Stair Chairs Config
Chair:
 # Encable or disable this feature. Must be either true or false.
 Enabled: true
 # Maximum distance you can be from the stair block
 # Default config value is 2.5
 Distance: 2.5
 # Do you require signs on both sides of the stair?
 # Default config value is false. Can only be true or false.
 Require-Signs: false
 # Do you require to have nothing in your hand when you are trying to sit?
 # Default config values is true. Can only be true or false.
 Require-empty-hand: true

# Fly command config
Fly:
 # Encable or disable this feature. Must be either true or false.
 Enabled: true
 # Take no fall damage when you are falling if the time runs out. Must be either true or false.
 # Default config value is true
 Take-no-falldamage: true

# Locked Chests config
Locked-Chests:
  # Encable or disable this feature. Must be either true or false.
  Enabled: true
  # If you can have friends accessing your chests or not.
  # Default values is true. Must be either true or false
  Support-Friends: true
  # If hoppers can access your chests or not. They still need to be locked by the same name
  # Default value is true. Can be either true or false.
  Support-Hoppers: true

# Elevator signs config
Elevator-Signs:
  # Enable or disable this feature. Must be either true of false.
  Enabled: true
  # Maximum blocks you can travel with an elevator.
  # Must be an integer between 4 and 255. If less than 4 it will be interpereted as 1.
  # Default config value is 255.
  Maximum-Distance: 255
  # Travel By clicking on the sign or by jumping and sneaking. True means using jump and sneak.
  # Default value is true. Must be either true or false.
  Allow-Jump-And-Sneak: true
  # Use redsont pads if secound line is either [UP] or [DOWN]. They require a redstone signal.
  # Default values is true. Must be either true or false.
  Allow-Redstone: true
  # The maximum pad size for redstone pads. This is in radius of the pad.
  # Default config value is 4. Takes an integer between 1 and 15.
  Max-Pad-Size: 4

# ChatColors config
Chat-Colors:
  # Encable or disable this feature. Must be either true or false.
  Enabled: true
  # Chat color prefix. Has to be single char within single quotes. Default is '&'
  Prefix: '&'

# Hats Config
Hats:
  # Encable or disable this feature. Must be either true or false.
  Enabled: true

# Sudo config
Sudo:
  # Encable or disable this feature. Must be either true or false.
  Enabled: true

# Rules command config
Rules:
  # Encable or disable this feature. Must be either true or false.
  Enabled: true

# Kit Command config
Kit:
  # Encable or disable this feature. Must be either true or false.
  Enabled: true

# Heal command config
Heal:
  # Encable or disable this feature. Must be either true or false.
  Enabled: true

# Feed command config
Feed:
  # Encable or disable this feature. Must be either true or false.
  Enabled: true
```
