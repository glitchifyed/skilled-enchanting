package net.glitchifyed.skilled_enchanting.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.glitchifyed.skilled_enchanting.SkilledEnchanting;
import net.glitchifyed.skilled_enchanting.block.entity.EnchanterBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;

public class EnchanterTakeC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        //BlockPos blockPos = buf.readBlockPos();
        ItemStack stackOne = buf.readItemStack();
        ItemStack stackTwo = buf.readItemStack();

        //EnchanterBlockEntity enchanter = (EnchanterBlockEntity) player.getWorld().getBlockEntity(blockPos);

        SkilledEnchanting.LOGGER.info("THING1");

        if (!stackOne.isEmpty() && !stackTwo.isEmpty()) {
            SkilledEnchanting.LOGGER.info("THING2");

            if (stackOne.isEnchantable() || stackOne.getEnchantments().size() != 0) {
                SkilledEnchanting.LOGGER.info("THING3");
                if (stackTwo.isOf(Items.NETHER_STAR)) {
                    SkilledEnchanting.LOGGER.info("THING4");
                    int level = (int)Math.floor(Math.log(stackTwo.getCount()) / Math.log(2));
                    int consume = (int)Math.pow(2, level);

                    if (level > 0 && stackTwo.getCount() >= consume) {
                        SkilledEnchanting.LOGGER.info("THING5");
                        ItemStack newStack = stackOne.copy();
                        newStack.addEnchantment(Enchantments.PROTECTION, level);

                        PlayerInventory inventory = player.getInventory();
                        inventory.selectedSlot = 2;
                        inventory.removeStack(0);
                        inventory.removeStack(1, consume);

                        /*inventory.removeStack(0);

                        inventory.removeStack(1, consume);

                        inventory.setStack(2, newStack);*/
                    }
                }
            }
        }
    }
}
