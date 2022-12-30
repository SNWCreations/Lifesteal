package snw.lifesteal;

import org.bukkit.ChatColor;

public class Util {
    private Util() {} // cannot be constructed

    public static String pluginMsg(String raw) {
        return ChatColor.GRAY + "[" + ChatColor.RED + "LifeSteal" + ChatColor.GRAY + "]" + ChatColor.RESET + " " + raw;
    }
}
