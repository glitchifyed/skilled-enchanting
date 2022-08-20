package net.glitchifyed.skilled_enchanting.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.glitchifyed.skilled_enchanting.SkilledEnchanting;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlocks {
    public static final Block ENCHANTER_BLOCK = new EnchanterBlock(FabricBlockSettings.copyOf(Blocks.ENCHANTING_TABLE).requiresTool());
    public static final Block CONDENSED_OBSIDIAN = new CondensedObsidianBlock(FabricBlockSettings.copyOf(Blocks.OBSIDIAN).hardness(150f).resistance(3500f).requiresTool());

    private static Block registerBlock(String name, Block block, ItemGroup tab) {
        registerBlockItem(name, block, tab);
        return Registry.register(Registry.BLOCK, new Identifier(SkilledEnchanting.MODID, name), block);
    }

    private static Item registerBlockItem(String name, Block block, ItemGroup tab) {
        return Registry.register(Registry.ITEM, new Identifier(SkilledEnchanting.MODID, name),
                new BlockItem(block, new FabricItemSettings().group(tab)));
    }

    public static void registerModBlocks() {
        registerBlock("enchanter", ENCHANTER_BLOCK, ItemGroup.DECORATIONS);
        registerBlock("condensed_obsidian", CONDENSED_OBSIDIAN, ItemGroup.BUILDING_BLOCKS);
    }
}
