package snw.lifesteal;

import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    boolean work = false;

    @Override
    public void onEnable() {
        // Plugin startup logic
        getCommand("lifesteal").setExecutor(new LifeStealCommand(this));
        getServer().getPluginManager().registerEvents(new EventListener(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
