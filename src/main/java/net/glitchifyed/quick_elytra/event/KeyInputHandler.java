package net.glitchifyed.quick_elytra.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {
    public static final String KEY_CATEGORY_EQUIP = "key.category.glitchifyed.quick_elytra";
    public static final String KEY_TOGGLE_EQUIP = "key.glitchifyed.quick_elytra.toggle_equip";

    public static KeyBinding toggleEquip;

    public static void registerKeyInputs() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (toggleEquip.wasPressed()) {
                client.player.sendChatMessage("TOGGLE EQUIP PRESSED", Text.literal("your amongus"));
            }
        });
    }

    public static void register() {
        toggleEquip = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_TOGGLE_EQUIP,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_G,
                KEY_CATEGORY_EQUIP
        ));

        registerKeyInputs();
    }
}
