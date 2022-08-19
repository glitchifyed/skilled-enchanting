package net.glitchifyed.skilled_enchanting.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import net.glitchifyed.skilled_enchanting.SkilledEnchanting;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.io.DataOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class EnchanterRecipe {
    public static final List<EnchanterRecipe> enchantments = new ArrayList<>();

    public String enchantment;
    public String item;
    public int amount;
    public float increase;
    public int maxLevel;
    public int xpIncrease;
    public JsonArray dislikes;

    public EnchanterRecipe(String enchantment, String item, int amount, float increase, int maxLevel, int xpIncrease, JsonArray dislikes) {
        this.enchantment = enchantment;
        this.item = item;
        this.amount = amount;
        this.increase = increase;
        this.maxLevel = maxLevel;
        this.xpIncrease = xpIncrease;
        this.dislikes = dislikes;
    }

    public static boolean isEnchantable(ItemStack itemStack) {
        return itemStack.isEnchantable() || itemStack.getEnchantments().size() != 0;
    }

    public static boolean getCompatible(ItemStack itemStack, List<Enchantment> enchs) {
        if (!isEnchantable(itemStack)) {
            return false;
        }

        if (enchs.size() == 0) {
            return true;
        }

        for (Enchantment ench : enchs) {
            if (!getCompatible(itemStack, ench)) {
                return false;
            }
        }

        return true;
    }

    public static boolean getCompatible(ItemStack itemStack, Enchantment enchantment) {
        if (enchantments.size() == 0) {
            return true;
        }

        if (!isEnchantable(itemStack)) {
            return false;
        }

        if (!itemStack.isOf(Items.BOOK) && !itemStack.isOf(Items.ENCHANTED_BOOK) && !enchantment.isAcceptableItem(itemStack)) {
            return false;
        }

        String checkId = String.valueOf(EnchantmentHelper.getEnchantmentId(enchantment));

        Map<Enchantment, Integer> enchList = EnchantmentHelper.get(itemStack);

        if (enchList.size() == 0) {
            return true;
        }

        for (Map.Entry<Enchantment, Integer> entry : enchList.entrySet()) {
            Enchantment ench = entry.getKey();
            String id = String.valueOf(EnchantmentHelper.getEnchantmentId(ench));

            if (Objects.equals(id, checkId)) {
                return false;
            }

            for (EnchanterRecipe enchanterRecipe : enchantments) {
                if (Objects.equals(checkId, enchanterRecipe.enchantment)) {
                    if (enchanterRecipe.dislikes.size() == 0) {
                        break;
                    }

                    for (JsonElement jsonId : enchanterRecipe.dislikes) {
                        String id2 = jsonId.getAsString();

                        if (Objects.equals(id, id2)) {
                            return false;
                        }
                    }

                    break;
                }
            }
        }

        return true;
    }

    public static int[] getDetails(ItemStack stack) {
        EnchanterRecipe recipe = getRecipe(stack);

        return getDetails(recipe, stack);
    }

    public static int[] getDetails(EnchanterRecipe recipe, ItemStack stack) {
        if (recipe == null) {
            return null;
        }

        int level = (int) Math.floor(Math.log(stack.getCount() / (recipe.amount / recipe.increase)) / Math.log(recipe.increase));

        if (recipe.maxLevel > 0) {
            level = Math.min(level, recipe.maxLevel);
        }

        int consume = recipe.amount * (int) Math.pow(recipe.increase, (level - 1));
        int xp = recipe.xpIncrease * level;

        return new int[]{level, consume, xp};
    }

    public static EnchanterRecipe getRecipe(ItemStack stack) {
        for (EnchanterRecipe recipe : enchantments) {
            if (Objects.equals(recipe.item, String.valueOf(Registry.ITEM.getId(stack.getItem())))) {
                return recipe;
            }
        }

        return null;
    }

    public static ItemStack getOutputStack(ItemStack stackOne, ItemStack stackTwo) {
        EnchanterRecipe recipe = getRecipe(stackTwo);

        if (recipe == null) {
            return ItemStack.EMPTY;
        }

        int[] details = getDetails(recipe, stackTwo);

        int level = details[0];
        int consume = details[1];

        if (level > 0 && consume > 0 && stackTwo.getCount() >= consume) {
            Enchantment enchantment = Registry.ENCHANTMENT.get(Identifier.tryParse(recipe.enchantment));

            if (getCompatible(stackOne, enchantment)) {
                ItemStack newStack;

                if (stackOne.isOf(Items.BOOK) || stackOne.isOf(Items.ENCHANTED_BOOK)) {
                    newStack = new ItemStack(Items.ENCHANTED_BOOK);

                    ItemStack otherStack = stackOne.copy();

                    NbtCompound ogNbt = otherStack.getNbt();

                    NbtList list = new NbtList();

                    if (ogNbt != null) {
                        list = ogNbt.getList("StoredEnchantments", 7);
                    }

                    list.addElement(list.size(), EnchantmentHelper.createNbt(Identifier.tryParse(recipe.enchantment), level));

                    otherStack.setSubNbt("StoredEnchantments", list);

                    NbtCompound nbt = otherStack.getNbt();

                    if (nbt != null) {
                        newStack.setNbt(nbt.copy());
                    }
                } else {
                    newStack = stackOne.copy();
                    newStack.addEnchantment(enchantment, level);
                }

                return newStack;
            }
        }

        return ItemStack.EMPTY;
    }

    public static int getXPWithBookshelves(int xp, ItemStack stack) {
        if (stack.isEmpty() || !stack.isOf(Items.BOOKSHELF)) {
            return xp;
        }

        return (int)Math.ceil(xp * (1f - stack.getCount() / (stack.getMaxCount() * 2f)));
    }
}
