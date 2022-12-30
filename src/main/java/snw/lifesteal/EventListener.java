package snw.lifesteal;

import org.bukkit.BanList.Type;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class EventListener implements Listener {
    private final Main main;

    public EventListener(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        if (!main.work) return;
        e.getEntity().spigot().respawn();
        main.getServer().getScheduler().runTaskLater(main, () -> {
            Player killer = e.getEntity().getKiller();
            if (killer != null) { // or a mob killed this player?
                AttributeInstance k = killer.getAttribute(Attribute.GENERIC_MAX_HEALTH); // killer's max health attribute instance
                k.setBaseValue(k.getBaseValue() + 2.0); // 2 -> 1 heart
                AttributeInstance d = e.getEntity().getAttribute(Attribute.GENERIC_MAX_HEALTH);
                if (d.getBaseValue() - 2.0 <= 0) {
                    d.setBaseValue(20.0); // reset
                    main.getServer().getBanList(Type.NAME).addBan(e.getEntity().getName(), "你失败了！游戏结束了！", null, null);
                } else {
                    d.setBaseValue(d.getBaseValue() - 2.0); // remove a heart
                }
            }
        }, 1L);
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
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
}
