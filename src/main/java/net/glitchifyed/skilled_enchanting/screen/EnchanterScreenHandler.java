package net.glitchifyed.skilled_enchanting.screen;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.glitchifyed.skilled_enchanting.SkilledEnchanting;
import net.glitchifyed.skilled_enchanting.config.EnchanterRecipe;
import net.glitchifyed.skilled_enchanting.networking.ModMessages;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.EnchantmentScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class EnchanterScreenHandler extends ScreenHandler {
    public int xp;

    private final Inventory inventory;
    private boolean justChanged = false;
    private BlockPos pos;

    public EnchanterScreenHandler(int syncId, PlayerInventory playerInventory, PacketByteBuf buf) {
        this(syncId, playerInventory, (Inventory) null);
        pos = buf.readBlockPos();
    }

    public EnchanterScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(ModScreens.ENCHANTER_SCREEN_HANDLER, syncId);

        pos = BlockPos.ORIGIN;

        if (inventory == null) {
            inventory = new SimpleInventory(4) {
                public void markDirty() {
                    super.markDirty();
                    EnchanterScreenHandler.this.onContentChanged(this);
                }
            };
        }

        checkSize(inventory, 4);
        this.inventory = inventory;
        inventory.onOpen(playerInventory.player);

        int m;
        int l;

        this.addSlot(new Slot(inventory, 0, 34, 38) {
            public boolean canInsert(ItemStack stack) {
                return stack.isOf(Items.ENCHANTED_BOOK) || stack.isEnchantable() || stack.getEnchantments().size() != 0;
            }

            public int getMaxItemCount() {
                return 1;
            }
        });
        this.addSlot(new Slot(inventory, 1, 70, 38) {
            public int getMaxItemCount() {
                return 64;
            }
        });
        this.addSlot(new Slot(inventory, 2, 144, 38) {
            public boolean canInsert(ItemStack stack) {
                return false;
            }

            @Override
            public boolean canTakeItems(PlayerEntity playerEntity) {
                ItemStack stackTwo = inventory.getStack(1);

                if (playerEntity.getAbilities().creativeMode || !playerEntity.getWorld().isClient()) {
                    return true;
                }

                int[] details = EnchanterRecipe.getDetails(stackTwo); //EnchanterRecipe.getDetails(EnchanterRecipe.getRecipe(stackTwo), stackTwo);

                if (details != null) {
                    if (playerEntity.experienceLevel >= details[2]) {
                        return true;
                    }
                }

                return false;
            }

            public int getMaxItemCount() {
                return 1;
            }

            @Override
            public void onTakeItem(PlayerEntity player, ItemStack stack) {
                if (player.getWorld().isClient) {
                    ClientPlayNetworking.send(ModMessages.ENCHANTER_TAKE_ID, PacketByteBufs.create().writeBlockPos(pos));
                }
            }
        });
        this.addSlot(new Slot(inventory, 3, 15, 38) {
            public boolean canInsert(ItemStack stack) {
                return stack.isOf(Items.BOOKSHELF);
            }
        });

        for (m = 0; m < 3; ++m) {
            for (l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + m * 9 + 9, 8 + l * 18, 84 + m * 18));
            }
        }

        for (m = 0; m < 9; ++m) {
            this.addSlot(new Slot(playerInventory, m, 8 + m * 18, 142));
        }
    }

    public BlockPos getPos() {
        return pos;
    }

    @Override
    public void onContentChanged(Inventory inventory) {
        if (justChanged) {
            justChanged = false;

            return;
        }

        super.onContentChanged(inventory);

        if (inventory == this.inventory) {
            ItemStack stackOne = inventory.getStack(0);
            ItemStack stackTwo = inventory.getStack(1);

            if (!stackOne.isEmpty() && !stackTwo.isEmpty()) {
                ItemStack newStack = EnchanterRecipe.getOutputStack(stackOne, stackTwo);

                if (newStack.isEmpty()) {
                    if (!inventory.getStack(2).isEmpty()) {
                        inventory.removeStack(2);
                    }

                    xp = -1;
                }
                else {
                    justChanged = true;

                    inventory.setStack(2, newStack);

                    //xp = (int)Math.floor(EnchanterRecipe.getDetails(stackTwo)[2] * EnchanterRecipe.getLevelMultiplier(inventory.getStack(3))); //EnchanterRecipe.getDetails(EnchanterRecipe.getRecipe(stackTwo), stackTwo)[2];
                    xp = EnchanterRecipe.getXPWithBookshelves(EnchanterRecipe.getDetails(stackTwo)[2], inventory.getStack(3));
                }
            }
            else {
                if (!inventory.getStack(2).isEmpty()) {
                    inventory.removeStack(2);

                    xp = -1;
                }
            }
        }
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    public void close(PlayerEntity player) {
        super.close(player);
        //this.dropInventory(player, this.inventory);
    }
}
