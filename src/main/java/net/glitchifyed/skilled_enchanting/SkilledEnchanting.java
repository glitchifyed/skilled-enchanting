package net.glitchifyed.skilled_enchanting;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.glitchifyed.skilled_enchanting.block.EnchanterBlock;
import net.glitchifyed.skilled_enchanting.block.ModBlocks;
import net.glitchifyed.skilled_enchanting.block.entity.ModBlockEntities;
import net.glitchifyed.skilled_enchanting.config.ModConfigs;
import net.glitchifyed.skilled_enchanting.screen.ModScreens;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SkilledEnchanting implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MODID = "skilled_enchanting";

	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	@Override
	public void onInitialize() {
		ModBlocks.registerModBlocks();
		ModBlockEntities.registerModBlockEntities();

		ModScreens.registerScreenHandlers();

		ModConfigs.registerConfigs();
	}
}
