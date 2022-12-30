package snw.lifesteal;

import java.util.Arrays;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Constants {
    private Constants() {}
    public static final ItemStack HEART_ITEM;

    static {
        ItemStack heart = new ItemStack(Material.NETHER_STAR);
        ItemMeta meta = heart.getItemMeta();
        meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "心");
        meta.setLore(Arrays.asList(ChatColor.RED + "右键使用为自己增加一颗心的生命上限！", ChatColor.RED + "可以通过 /heart 命令生成。"));
        heart.setItemMeta(meta);
        HEART_ITEM = heart;
    }
}
