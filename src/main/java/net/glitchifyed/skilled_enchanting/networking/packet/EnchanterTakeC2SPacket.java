package net.glitchifyed.skilled_enchanting.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.glitchifyed.skilled_enchanting.SkilledEnchanting;
import net.glitchifyed.skilled_enchanting.block.entity.EnchanterBlockEntity;
import net.glitchifyed.skilled_enchanting.block.entity.ModBlockEntities;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;

import java.util.Optional;

public class EnchanterTakeC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        BlockPos blockPos = buf.readBlockPos();

        server.execute(()-> { // GO TO MAIN THREAD (cant do world stuff)
            SkilledEnchanting.LOGGER.info(String.valueOf(blockPos));
            SkilledEnchanting.LOGGER.info(String.valueOf(player.getWorld()));

            Optional<EnchanterBlockEntity> enchanterEntity = player.getWorld().getBlockEntity(blockPos, ModBlockEntities.ENCHANTER_BLOCK_ENTITY);

            SkilledEnchanting.LOGGER.info(String.valueOf(enchanterEntity));

            SkilledEnchanting.LOGGER.info("THING1");

            if (enchanterEntity.isPresent()) {
                SkilledEnchanting.LOGGER.info("THING2");

                EnchanterBlockEntity enchanter = enchanterEntity.get();

                if (!enchanter.isEmpty()) {
                    SkilledEnchanting.LOGGER.info("THING2.5");

                    DefaultedList<ItemStack> items = enchanter.getItems();

                    ItemStack stackOne = items.get(0);
                    ItemStack stackTwo = items.get(1);

                    if (!stackOne.isEmpty() && !stackTwo.isEmpty() && (stackOne.isEnchantable() || stackOne.getEnchantments().size() != 0)) {
                        SkilledEnchanting.LOGGER.info("THING3");
                        if (stackTwo.isOf(Items.NETHER_STAR)) {
                            SkilledEnchanting.LOGGER.info("THING4");
                            int level = (int) Math.floor(Math.log(stackTwo.getCount()) / Math.log(2));
                            int consume = (int) Math.pow(2, level);

                            if (level > 0 && stackTwo.getCount() >= consume) {
                                SkilledEnchanting.LOGGER.info("THING5");
                                ItemStack newStack = stackOne.copy();
                                newStack.addEnchantment(Enchantments.PROTECTION, level);

                                stackTwo.decrement(consume);

                                items.set(0, ItemStack.EMPTY);

                                items.set(2, newStack);
                            }
                        }
                    }
                }
            }
        });
    }
}
