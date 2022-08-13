package net.glitchifyed.quick_elytra.mixin;

import net.glitchifyed.quick_elytra.QuickElytra;
import net.minecraft.client.gui.screen.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class QuickElytraMixin {
	@Inject(at = @At("HEAD"), method = "init()V")
	private void init(CallbackInfo info) {
		QuickElytra.LOGGER.info("This line is printed by a QuickElytra mixin!");
	}
}
