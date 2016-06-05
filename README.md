# aTilionUtilities

The purpose of this plugin is to add some extra functionality and fun features to your minecraft server.
This is a utilitiy plugin and has some of the features essentials have. But it is not created to take essentials place.
Any features in this mod can be disabled in the config.

## Features:

###### Current Features:
    **Fly** - Do /fly [time] [player name] to fly or disable flying.
    **Sudo** - You can can override anyones chat and make them do commands.
    **Path block** - You walk faster on path blocks
    **Stair Chairs** - You can sit on chairs.


######  Upcomming features:
    __Kit__ - You can give players the ability to /kit [kit name] as long as they have the atu.kit.[kit name} permission.
    **Locked Chests Signs** - Use signs to lock a chest to you.
    **Elevator Signs** - Use signs to teleport a player up or down.
    **Hat Command** - Use /hat to place the block in your hand as your helmet. Some blocks may have effects...
    **Special items and armor** - Special tools and armor that give special gameplay effects.
    **Bridge dispenser/Sign** - Place a sign on dispencers to create a retracting/extracting bridge.


## Config:

// Version of config. Do NOT change this
Version: 0.0.2

// Path Config
Path:
  // Enable or disable this feature. Must be either true or false.
  Enabled: true
  // Speed between 0 and 1.
  // 0.2 is minecraft walk speed. 0.3 is config default
  Speed: 0.3

// Stair Chairs Config
Chair:
 // Encable or disbable this feature. Must be either true or false.
 Enabled: true
 // Maximum distance you can be from the stair block
 // Default config value is 2.5
 Distance: 2.5
 // Do you require signs on both sides of the stair?
 // Default config value is false. Can only be true or false.
 Require-Signs: false
 // Do you require to have nothing in your hand when you are trying to sit?
 // Default config values is true. Can only be true or false.
 Require-empty-hand: true

// Fly command config
Fly:
 // Encable or disbable this feature. Must be either true or false.
 Enabled: true
 // Take no fall damage when you are falling if the time runs out. Must be either true or false.
 // Default config value is true
 Take-no-falldamage: true
