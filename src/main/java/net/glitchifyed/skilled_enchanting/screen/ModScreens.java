package net.glitchifyed.skilled_enchanting.screen;

import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.glitchifyed.skilled_enchanting.SkilledEnchanting;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreens {
    public static ScreenHandlerType<EnchanterScreenHandler> ENCHANTER_SCREEN_HANDLER;

    public static void registerScreenHandlers() {
        ENCHANTER_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(new Identifier(SkilledEnchanting.MODID, "enchanter"), EnchanterScreenHandler::new);
    }
}
