package net.glitchifyed.skilled_enchanting.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.glitchifyed.skilled_enchanting.SkilledEnchanting;
import net.glitchifyed.skilled_enchanting.block.entity.EnchanterBlockEntity;
import net.glitchifyed.skilled_enchanting.block.entity.ModBlockEntities;
import net.glitchifyed.skilled_enchanting.config.EnchanterRecipe;
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
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Optional;

public class EnchanterTakeC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        BlockPos blockPos = buf.readBlockPos();

        server.execute(()-> { // GO TO MAIN THREAD (cant do world stuff otherwise)
            ServerWorld world = player.getWorld();

            Optional<EnchanterBlockEntity> enchanterEntity = world.getBlockEntity(blockPos, ModBlockEntities.ENCHANTER_BLOCK_ENTITY);

            if (enchanterEntity.isPresent()) {
                EnchanterBlockEntity enchanter = enchanterEntity.get();

                if (!enchanter.isEmpty()) {
                    DefaultedList<ItemStack> items = enchanter.getItems();

                    ItemStack stackOne = items.get(0);
                    ItemStack stackTwo = items.get(1);

                    if (!stackOne.isEmpty() && !stackTwo.isEmpty() && (stackOne.isEnchantable() || stackOne.getEnchantments().size() != 0)) {
                        ItemStack newStack = EnchanterRecipe.getOutputStack(stackOne, stackTwo);

                        if (!newStack.isEmpty()) {
                            int[] details = EnchanterRecipe.getDetails(stackTwo);

                            if (details != null) {
                                int consume = details[1];
                                int xp = EnchanterRecipe.getXPWithBookshelves(details[2], items.get(3));

                                boolean creative = player.getAbilities().creativeMode;

                                if (creative || stackTwo.getCount() >= consume && player.experienceLevel >= xp) {
                                    items.set(0, ItemStack.EMPTY);

                                    if (!creative) {
                                        stackTwo.decrement(consume);

                                        player.addExperienceLevels(-xp);
                                    }

                                    items.set(2, newStack);

                                    world.playSound(null, blockPos, SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 1.0F, world.random.nextFloat() * 0.1F + 0.9F);
                                }
                            }
                        }
                    }
                }
            }
        });
    }
}
