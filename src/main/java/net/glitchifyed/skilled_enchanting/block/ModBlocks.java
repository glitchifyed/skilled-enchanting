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
    public static final Block ENCHANTER_BLOCK = new EnchanterBlock(FabricBlockSettings.copyOf(Blocks.ENCHANTING_TABLE).luminance(1).hardness(5.0f).strength(4.0f).requiresTool());

    private static Block registerBlock(String name, Block block, ItemGroup tab) {
        registerBlockItem("enchanter", ENCHANTER_BLOCK, tab);
        return Registry.register(Registry.BLOCK, new Identifier(SkilledEnchanting.MODID, name), block);
    }

    private static Item registerBlockItem(String name, Block block, ItemGroup tab) {
        return Registry.register(Registry.ITEM, new Identifier(SkilledEnchanting.MODID, name),
                new BlockItem(block, new FabricItemSettings().group(tab)));
    }

    public static void registerModBlocks() {
        //Registry.register(Registry.BLOCK, new Identifier(SkilledEnchanting.MODID, "enchanter"), ENCHANTER_BLOCK);
        //Registry.register(Registry.ITEM, new Identifier(SkilledEnchanting.MODID, "enchanter"), new BlockItem(ENCHANTER_BLOCK, new FabricItemSettings().group(ItemGroup.DECORATIONS)));
        registerBlock("enchanter", ENCHANTER_BLOCK, ItemGroup.DECORATIONS);
    }
}
