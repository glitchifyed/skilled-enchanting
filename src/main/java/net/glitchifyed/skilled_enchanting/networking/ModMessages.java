package net.glitchifyed.skilled_enchanting.networking;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.glitchifyed.skilled_enchanting.SkilledEnchanting;
import net.glitchifyed.skilled_enchanting.networking.packet.EnchanterTakeC2SPacket;
import net.minecraft.util.Identifier;

public class ModMessages {
    public static final Identifier ENCHANTER_TAKE_ID = new Identifier(SkilledEnchanting.MODID, "enchanter_take");

    public static void registerC2SPackets() {
        ServerPlayNetworking.registerGlobalReceiver(ENCHANTER_TAKE_ID, EnchanterTakeC2SPacket::receive);
    }

    public static void registerS2CPackets() {

    }
}
