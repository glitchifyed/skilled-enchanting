package net.glitchifyed.skilled_enchanting.config;

import com.mojang.datafixers.util.Pair;
import net.glitchifyed.skilled_enchanting.SkilledEnchanting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModConfigs {
    public static SimpleConfig CONFIG;
    private static ModConfigProvider configs;

    public static void registerConfigs() {
        configs = new ModConfigProvider();
        createConfigs();

        CONFIG = SimpleConfig.of(SkilledEnchanting.MODID + "_config").provider(configs).request();

        assignConfigs();
    }

    private static void createConfigs() {
        configs.addKeyValuePair(new Pair<>("key.test.value1", "Just a Testing string!"), "String");
    }

    private static void assignConfigs() {
        //enchanterRecipes = CONFIG.getOrDefault("key.test.value1", new ArrayList<EnchanterRecipe>().toString());

        System.out.println("All " + configs.getConfigsList().size() + " have been set properly");
    }
}