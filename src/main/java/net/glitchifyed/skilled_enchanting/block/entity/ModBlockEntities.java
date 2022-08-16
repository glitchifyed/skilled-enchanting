package net.glitchifyed.skilled_enchanting.block.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.glitchifyed.skilled_enchanting.SkilledEnchanting;
import net.glitchifyed.skilled_enchanting.block.ModBlocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlockEntities {
    public static BlockEntityType<EnchanterBlockEntity> ENCHANTER_BLOCK_ENTITY;

    public static void registerModBlockEntities() {
        ENCHANTER_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE,
                new Identifier(SkilledEnchanting.MODID, "enchanter_block_entity"),
                FabricBlockEntityTypeBuilder.create(EnchanterBlockEntity::new, ModBlocks.ENCHANTER_BLOCK).build());
    }
}
