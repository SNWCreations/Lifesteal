package snw.lifesteal;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    boolean work = false;

    @Override
    public void onEnable() {
        // Plugin startup logic
        getCommand("lifesteal").setExecutor(new LifeStealCommand(this));
        getCommand("drawheart").setExecutor(new DrawHeartCommand());
        getCommand("transferheart").setExecutor(new TransferHeartCommand(this));
        getServer().getPluginManager().registerEvents(new EventListener(this), this);
        initRecipe();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getServer().resetRecipes();
    }

    private void initRecipe() {
        // idi
        // dad
        // idi
        // i - Netherite Ingot
        // d - Diamond Block
        // a - Totem Of Undying
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(this, "heart"), Constants.HEART_ITEM)
                                    .shape("idi", "dad", "idi")
                                    .setIngredient('i', Material.NETHERITE_INGOT)
                                    .setIngredient('d', Material.DIAMOND_BLOCK)
                                    .setIngredient('a', Material.TOTEM_OF_UNDYING);
        getServer().addRecipe(recipe);
    }
}
