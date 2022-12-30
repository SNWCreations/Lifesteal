package snw.lifesteal;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.command.CommandExecutor;

public class DrawHeartCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) { // is Player
            // Variable declarations
            Player player = (Player) sender;
            int count;
            // Check arguments
            if (args.length != 1) {
                sender.sendMessage(Util.pluginMsg("参数不足。"));
                return false;
            }
            try {
                count = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                sender.sendMessage(Util.pluginMsg("参数不合法。需要整数。"));
                return false;
            }

            // Second stage - check if player's max health is OK
            AttributeInstance i = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
            if (count > ((i.getBaseValue() / 2) - 1)) { // not OK
                sender.sendMessage(Util.pluginMsg("提供的数值超出了你能承受的极限。你至少要保留 1 颗心。"));
                return true; // illegal argument, but syntax is OK
            }

            ItemStack h = Constants.HEART_ITEM.clone(); // clone, do not modify original version
            h.setAmount(count);
            i.setBaseValue(i.getBaseValue() - (count * 2));
            if (!player.getInventory().addItem(h).isEmpty()) { // if player's inventory is full?
                sender.sendMessage(Util.pluginMsg("警告: 你的背包似乎满了。物品已在你所在的位置掉落。请注意拾取。"));
                player.getLocation().getWorld().dropItem(player.getLocation(), h);
                // ensure the player won't lose the heart without the item
            }
            sender.sendMessage(Util.pluginMsg("操作成功。"));
        } else { // console?
            sender.sendMessage(Util.pluginMsg("不支持的执行者类型。期望执行者是玩家。"));
        }
        return true;
    }
    
}
