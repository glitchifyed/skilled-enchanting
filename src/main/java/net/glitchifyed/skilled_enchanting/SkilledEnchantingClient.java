package net.glitchifyed.skilled_enchanting;

import me.lortseam.completeconfig.gui.ConfigScreenBuilder;
import me.lortseam.completeconfig.gui.coat.CoatScreenBuilder;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.glitchifyed.skilled_enchanting.screen.EnchanterScreen;
import net.glitchifyed.skilled_enchanting.screen.ModScreens;

@Environment(EnvType.CLIENT)
public class SkilledEnchantingClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ScreenRegistry.register(ModScreens.ENCHANTER_SCREEN_HANDLER, EnchanterScreen::new);
	}
}
