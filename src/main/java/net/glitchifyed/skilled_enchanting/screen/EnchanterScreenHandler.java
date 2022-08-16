package net.glitchifyed.skilled_enchanting.screen;

import net.glitchifyed.skilled_enchanting.SkilledEnchanting;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import org.jetbrains.annotations.Nullable;

public class EnchanterScreenHandler extends ScreenHandler {
    private final Inventory inventory;

    public EnchanterScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(3));
    }

    public EnchanterScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(ModScreens.ENCHANTER_SCREEN_HANDLER, syncId);
        checkSize(inventory, 3);
        this.inventory = inventory;
        inventory.onOpen(playerInventory.player);

        int m;
        int l;
        //Our inventory
        /*for (m = 0; m < 3; ++m) {
            for (l = 0; l < 3; ++l) {
                this.addSlot(new Slot(inventory, l + m * 3, 62 + l * 18, 17 + m * 18));
            }
        }*/

        this.addSlot(new Slot(inventory, 0, 24, 38) {
            public boolean canInsert(ItemStack stack) {
                //return stack.isEnchantable();
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
                super.onTakeItem(player, stack);

                inventory.removeStack(0);
                inventory.removeStack(1, 1);
            }
        });

        //The player inventory
        for (m = 0; m < 3; ++m) {
            for (l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + m * 9 + 9, 8 + l * 18, 84 + m * 18));
            }
        }

        //The player Hotbar
        for (m = 0; m < 9; ++m) {
            this.addSlot(new Slot(playerInventory, m, 8 + m * 18, 142));
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
}
