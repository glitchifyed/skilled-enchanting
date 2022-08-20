# Skilled Enchanting
## Summary
Skilled Enchanting is a Minecraft mod that improves the vanilla enchanting system by adding a new block called the Enchanter.
The enchanter works similarly to an Enchanting Table, except instead of having random enchantments, you use certain materials to 'craft' certain enchantments.
You can also insert bookshelves into the first slot of the Enchanter, which will decrease the amount of levels that are consumed when enchanting items.

### Offical downloads
https://modrinth.com/mod/skilled-enchanting
https://www.curseforge.com/minecraft/mc-mods/skilled-enchanting/

## Crafting
The Enchanter is crafted with a few items. One of these is a new item called Condensed Obsidian.
\
2 Condensed Obsidian can be crafted with 6 Obsidian and 3 Crying Obsidian in any order.

The Enchanter is crafted in this exact order:
\
AAA
\
BCB
\
BDB
\
A: Amethyst Block
\
B: Condensed Obsidian
\
C: Diamond Block
\
D: Enchanting Table

## Datapacks
The Enchanter is fully customisable using datapacks (which should theoretically allow modded enchantments and items too!):
\
Datapack structure: DATAPACK_NAME\data\skilled_enchanting\enchanter\ENCHANTMENT.json
\
To override a default recipe, name the .json file with the enchantment id (eg respiration.json). This will replace the default recipe.
\
JSON structure:
```
{
    "enchantment": "MODID OR minecraft:ENCHANTMENT_ID",
    "material": "MODID OR minecraft:ITEM_ID",
    "amount": AMOUNT TO CRAFT LEVEL ONE (eg 2),
    "increase": AMOUNT MUTLIPLIER PER ENCHANTMENT LEVEL (>= 1.0f),
    "max": MAX ENCHANTMENT LEVEL (-1 for no max),
    "xp": XP LEVELS CONSUMED PER ENCHANTMENT LEVEL (eg 10 per level),
    "dislikes": [ LEAVE BLANK FOR NO EXCLUSIVITY
        "MODID OR minecraft:ENCHANTMENT_ID"
    ]
}
```


## Default Enchanter Recipes:
* Enchantment: Aqua Affinity
\
Item material: Sea Lantern


* Enchantment: Bane of Arthropods
\
Item material: Fermented Spider Eye


* Enchantment: Blast Protection
\
Item material: Obsidian


* Enchantment: Channeling
\
Item material: Lightning Rod


* Enchantment: Depth Strider
\
Item material: Sponge


* Enchantment: Efficiency
\
Item material: Diamond


* Enchantment: Feather Falling
\
Item material: Slime Block


* Enchantment: Fire Aspect
\
Item material: Magma Cream


* Enchantment: Fire Protection
\
Item material: Blaze Rod


* Enchantment: Flame
\
Item material: Lava Bucket


* Enchantment: Fortune
\
Item material: Netherite Scrap


* Enchantment: Frost Walker
\
Item material: Blue Ice


* Enchantment: Impaling
\
Item material: Prismarine Shard


* Enchantment: Infinity
\
Item material: Arrow


* Enchantment: Knockback
\
Item material: Slimeball


* Enchantment: Looting
\
Item material: Block of Lapis Lazuli


* Enchantment: Loyalty
\
Item material: Lead


* Enchantment: Luck of the Sea
\
Item material: Heart of the Sea


* Enchantment: Lure
\
Item material: Tropical Fish


* Enchantment: Mending
\
Item material: Bottle o' Enchanting


* Enchantment: Multishot
\
Item material: Arrow


* Enchantment: Piercing
\
Item material: Spectral Arrow


* Enchantment: Power
\
Item material: Blaze Powder


* Enchantment: Projectile Protection
\
Item material: Block of Copper


* Enchantment: Protection
\
Item material: Block of Iron


* Enchantment: Punch
\
Item material: Slimeball


* Enchantment: Quick Charge
\
Item material: String


* Enchantment: Respiration
\
Item material: Pufferfish


* Enchantment: Riptide
\
Item material: Dark Prismarine


* Enchantment: Sharpness
\
Item material: Block of Quartz


* Enchantment: Silk Touch
\
Item material: Cobweb


* Enchantment: Smite
\
Item material: Bone Block


* Enchantment: Sweeping Edge
\
Item material: Ender Pearl


* Enchantment: Swift Sneak
\
Item material: Echo Shard


* Enchantment: Thorns
\
Item material: Prismarine Shard


* Enchantment: Unbreaking
\
Item material: Iron Ingot
