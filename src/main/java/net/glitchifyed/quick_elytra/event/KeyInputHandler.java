package net.glitchifyed.quick_elytra.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.glitchifyed.quick_elytra.QuickElytra;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.collection.DefaultedList;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {
    public static final String KEY_CATEGORY_EQUIP = "key.category.glitchifyed.quick_elytra";
    public static final String KEY_TOGGLE_EQUIP = "key.glitchifyed.quick_elytra.toggle_equip";

    public static KeyBinding toggleEquip;

    final static int ARMOUR_SLOT = 6;
    static boolean togglePressed;

    public static void registerKeyInputs() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            boolean togglePress = toggleEquip.isPressed();

            if (togglePress && !togglePressed) {
                togglePressed = true;

                MinecraftClient CLIENT = MinecraftClient.getInstance();

                int swapSlot = -1;

                DefaultedList<ItemStack> inventory = CLIENT.player.getInventory().main;

                ItemStack chestplate = CLIENT.player.getInventory().armor.get(2);
                boolean notWearing = chestplate.getItem() == Items.AIR;
                boolean wearingChestplate = isItemChestplate(chestplate);
                boolean wearingElytra = isItemElytra(chestplate);

                int highEnchantLevel = -1;
                int highestDurability = -1;

                for (int i = 0; i < inventory.size(); i++) {
                    ItemStack itemStack = inventory.get(i);
                    Item item = itemStack.getItem();

                    if (item == Items.AIR) {
                        continue;
                    }

                    if (notWearing || wearingChestplate && isItemElytra(itemStack) || wearingElytra && isItemChestplate(itemStack)) {
                        int durability = itemStack.getMaxDamage();

                        if (durability > highestDurability) {
                            highestDurability = durability;
                        }
                    }
                }

                for (int i = 0; i < inventory.size(); i++) {
                    ItemStack itemStack = inventory.get(i);
                    Item item = itemStack.getItem();

                    if (item == Items.AIR) {
                        continue;
                    }

                    if (notWearing || wearingChestplate && isItemElytra(itemStack) || wearingElytra && isItemChestplate(itemStack)) {
                        int level = getEnchantLevel(itemStack);
                        int durability = itemStack.getMaxDamage();

                        if (durability == highestDurability && level > highEnchantLevel) {
                            swapSlot = i;
                            highEnchantLevel = level;
                        }
                    }
                }

                if (swapSlot != -1) {
                    CLIENT.interactionManager.clickSlot(
                            CLIENT.player.playerScreenHandler.syncId,
                            ARMOUR_SLOT,
                            swapSlot,
                            SlotActionType.SWAP,
                            CLIENT.player
                    );
                }
            }
            else if (!togglePress && togglePressed) {
                togglePressed = false;
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

    static int getEnchantLevel(ItemStack itemStack) {
        int level = 0;
        NbtList enchants = itemStack.getEnchantments();

        if (enchants.size() > 0) {
            for (int i = 0; i < enchants.size(); i++) {
                NbtElement nbtEnchant = enchants.get(i);
                String strEnchant = nbtEnchant.asString();

                String lvlSeparate = strEnchant.replaceAll("\\D+", "");

                level += Integer.parseInt(lvlSeparate);
            }
        }

        return level;
    }

    static boolean isItemEquipChestplate(ItemStack itemStack) {
        return isItemElytra(itemStack) && isItemChestplate(itemStack);
    }

    static boolean isItemChestplate(ItemStack itemStack) {
        Item item = itemStack.getItem();
        String itemName = item.getName().getString();

        return MobEntity.getPreferredEquipmentSlot(itemStack).getType() == EquipmentSlot.Type.ARMOR && itemName.toLowerCase().contains("chestplate");
    }

    static boolean isItemElytra(ItemStack itemStack) {
        Item item = itemStack.getItem();

        return item == Items.ELYTRA;
    }
}
