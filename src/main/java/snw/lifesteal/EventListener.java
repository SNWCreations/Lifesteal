package snw.lifesteal;

import org.bukkit.BanList.Type;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class EventListener implements Listener {
    private final Main main;

    public EventListener(Main main) {
        this.main = main;
    }

    @EventHandler(ignoreCancelled = true)
    public void onDeath(EntityDamageByEntityEvent e) {
        if (!main.work) return;
        if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
            Player damaged = ((Player) e.getEntity());
            Player damager = (Player) e.getDamager();

            if (e.getFinalDamage() >= damaged.getHealth()) { // will die
                AttributeInstance k = damager.getAttribute(Attribute.GENERIC_MAX_HEALTH); // killer's max health attribute instance
                k.setBaseValue(k.getBaseValue() + 2.0); // 2 -> 1 heart
                AttributeInstance d = damaged.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                if (d.getBaseValue() - 2.0 <= 0) {
                    d.setBaseValue(20.0); // reset
                    main.getServer().getBanList(Type.NAME).addBan(
                            e.getEntity().getName(),
                            "你失败了！游戏结束了！",
                            null,
                            null
                    );
                } else {
                    d.setBaseValue(d.getBaseValue() - 2.0); // remove a heart
                    damaged.sendMessage(Util.pluginMsg("你失去了一颗心！"));
                }
            }
        }
    }


    @EventHandler(priority = EventPriority.LOWEST)
    public void interact(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            ItemStack handItem = e.getPlayer().getInventory().getItemInMainHand();
            if (Constants.HEART_ITEM.isSimilar(handItem)) {
                AttributeInstance i = e.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH);
                i.setBaseValue(i.getBaseValue() + 2.0);
                handItem.setAmount(handItem.getAmount() - 1);
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        e.getPlayer().sendMessage(Util.pluginMsg("插件已" + ((main.work) ? (ChatColor.GREEN + "启用") : (ChatColor.RED + "禁用"))));
    }
}
