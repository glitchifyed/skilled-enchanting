package net.glitchifyed.skilled_enchanting;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.glitchifyed.skilled_enchanting.config.EnchanterRecipe;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Map;

public class ModResources {
    public static void registerServer() {
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new SimpleSynchronousResourceReloadListener() {
            @Override
            public Identifier getFabricId() {
                return new Identifier(SkilledEnchanting.MODID, "enchanter");
            }

            @Override
            public void reload(ResourceManager manager) {
                EnchanterRecipe.enchantments.clear();

                Map<Identifier, Resource> something = manager.findResources("enchanter", path -> path.getPath().endsWith(".json"));

                for (Map.Entry<Identifier, Resource> entry : something.entrySet()) {
                    Identifier id = entry.getKey();

                    try(InputStream stream = manager.getResource(id).get().getInputStream()) {
                        String content = new String(stream.readAllBytes());

                        JsonObject json = new JsonParser().parse(content).getAsJsonObject();

                        String enchant = json.get("enchantment").getAsString();
                        String item = json.get("material").getAsString();
                        int amount = json.get("amount").getAsInt();
                        float increase = json.get("increase").getAsFloat();
                        int maxLevel = json.get("max").getAsInt();
                        int xpIncrease = json.get("xp").getAsInt();

                        JsonElement dislikeElement = json.get("dislikes");

                        JsonArray dislikes = new JsonArray();

                        if (dislikeElement != null) {
                            dislikes = dislikeElement.getAsJsonArray();

                            for (JsonElement enchJson : dislikes) {
                                String ench = enchJson.getAsString();

                                if (Registry.ENCHANTMENT.get(new Identifier(ench)) == null) {
                                    SkilledEnchanting.LOGGER.warn("Enchantment '" + ench + "' doesn't exist! (Dislike inside Enchantment '" + enchant + "')");
                                }
                            }
                        }

                        EnchanterRecipe.enchantments.add(new EnchanterRecipe(enchant, item, amount, increase, maxLevel, xpIncrease, dislikes));

                        if (Registry.ENCHANTMENT.get(new Identifier(enchant)) == null) {
                            SkilledEnchanting.LOGGER.warn("Enchantment '" + enchant + "' doesn't exist!");
                        }

                        if (Registry.ITEM.get(new Identifier(item)) == Items.AIR) {
                            SkilledEnchanting.LOGGER.warn("Item '" + item + "' doesn't exist! (Enchantment '" + enchant +"')");
                        }
                    } catch(Exception e) {
                        SkilledEnchanting.LOGGER.error("Error occurred while loading resource json " + id.toString(), e);
                    }
                }
            }
        });
    }
}
