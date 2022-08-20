package net.glitchifyed.skilled_enchanting;

import net.fabricmc.api.ModInitializer;
import net.glitchifyed.skilled_enchanting.block.ModBlocks;
import net.glitchifyed.skilled_enchanting.block.entity.ModBlockEntities;
import net.glitchifyed.skilled_enchanting.networking.ModMessages;
import net.glitchifyed.skilled_enchanting.screen.ModScreens;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.MendingEnchantment;
import net.minecraft.entity.EquipmentSlot;
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
		ModResources.registerServer();

		ModBlocks.registerModBlocks();
		ModBlockEntities.registerModBlockEntities();

		ModScreens.registerScreenHandlers();

		ModMessages.registerC2SPackets();
	}
}
