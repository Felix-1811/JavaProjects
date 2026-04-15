package dev.felix;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.NamespacedKey;

public class MainPlugin extends JavaPlugin {

    public static MainPlugin INSTANCE;
    public static String PREFIX;


    @Override
    public void onEnable() {
        INSTANCE = this;
        PREFIX = "§1Plugin §7";
        getLogger().info("Plugin enabled! #1");
        registerRecipes();
        Bukkit.getConsoleSender().sendMessage(PREFIX + "Plugin enabled! #2");
    }

    public void registerRecipes(){
        ItemStack result = new ItemStack(Material.DIAMOND);
        NamespacedKey key = new NamespacedKey(this, "Gold_Zu_Diamond");
        ShapedRecipe recipe = new ShapedRecipe(key, result);
        recipe.shape("AAA", "BBB", "AAA");
        recipe.setIngredient('A', Material.IRON_INGOT);
        recipe.setIngredient('B', Material.GOLD_INGOT);
        Bukkit.addRecipe(recipe);

    }


    @Override
    public void onDisable() {
        getLogger().info("Plugin disabled!");
        Bukkit.getConsoleSender().sendMessage(PREFIX + "Plugin disabled! #2 ");

    }
}