package net.glitchifyed.skilled_enchanting.screen;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.glitchifyed.skilled_enchanting.SkilledEnchanting;
import net.glitchifyed.skilled_enchanting.networking.ModMessages;
import net.minecraft.client.MinecraftClient;
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
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class EnchanterScreenHandler extends ScreenHandler {
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
            inventory = new SimpleInventory(3) {
                public void markDirty() {
                    super.markDirty();
                    EnchanterScreenHandler.this.onContentChanged(this);
                }
            };
        }

        checkSize(inventory, 3);
        this.inventory = inventory;
        inventory.onOpen(playerInventory.player);

        int m;
        int l;

        this.addSlot(new Slot(inventory, 0, 24, 38) {
            public boolean canInsert(ItemStack stack) {
                return stack.isEnchantable() || stack.getEnchantments().size() != 0;
            }

            public int getMaxItemCount() {
                return 1;
            }
        });
        this.addSlot(new Slot(inventory, 1, 60, 38) {
            public int getMaxItemCount() {
                return 64;
            }
        });
        this.addSlot(new Slot(inventory, 2, 134, 38) {
            public boolean canInsert(ItemStack stack) {
                return false;
            }

            public int getMaxItemCount() {
                return 1;
            }

            @Override
            public void onTakeItem(PlayerEntity player, ItemStack stack) {
                SkilledEnchanting.LOGGER.info("THING!");

                ClientPlayNetworking.send(ModMessages.ENCHANTER_TAKE_ID, PacketByteBufs.create().writeBlockPos(pos));//.writeItemStack(inventory.getStack(0)).writeItemStack(inventory.getStack(1)));

                /*ItemStack stackTwo = inventory.getStack(1);

                inventory.removeStack(0);
                //inventory.removeStack(1, 1);

                if (stackTwo.isOf(Items.NETHER_STAR)) {
                    int level = (int)Math.floor(Math.log(stackTwo.getCount()) / Math.log(2));
                    int consume = (int)Math.pow(2, level);

                    inventory.removeStack(1, consume);
                }*/
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
                boolean can_make = false;

                if (stackTwo.isOf(Items.NETHER_STAR)) {
                    int level = (int)Math.floor(Math.log(stackTwo.getCount()) / Math.log(2));
                    int consume = (int)Math.pow(2, level);

                    if (level > 0 && consume > 0 && stackTwo.getCount() >= consume) {
                        justChanged = true;
                        can_make = true;

                        inventory.removeStack(2);

                        ItemStack newStack = stackOne.copy();
                        newStack.addEnchantment(Enchantments.PROTECTION, level);

                        inventory.setStack(2, newStack);
                    }
                }

                if (!can_make) {
                    if (!inventory.getStack(2).isEmpty()) {
                        inventory.removeStack(2);
                    }
                }
            }
            else {
                if (!inventory.getStack(2).isEmpty()) {
                    inventory.removeStack(2);
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
