package snw.lifesteal;

import java.util.List;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.command.TabExecutor;

public class TransferHeartCommand implements TabExecutor {
    private final Main main;

    public TransferHeartCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) { // is Player
            // Variable declarations
            Player player = (Player) sender;
            Player target;
            int count;
            // Check arguments
            if (args.length != 2) {
                sender.sendMessage(Util.pluginMsg("参数不足。"));
                return false;
            }
            target = main.getServer().getPlayerExact(args[0]);
            if (target == null) {
                sender.sendMessage(Util.pluginMsg("目标玩家不存在或不在线。"));
                return true;
            }
            try {
                count = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                sender.sendMessage(Util.pluginMsg("参数不合法。需要整数。"));
                return true;
            }

            // Second stage - check if player's max health is OK
            AttributeInstance i = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
            if (count > ((i.getBaseValue() / 2) - 1)) { // not OK
                sender.sendMessage(Util.pluginMsg("提供的数值超出了你能承受的极限。你至少要保留 1 颗心。"));
                return true; // illegal argument, but syntax is OK
            }

            i.setBaseValue(i.getBaseValue() - (count * 2));
            AttributeInstance t = target.getAttribute(Attribute.GENERIC_MAX_HEALTH); // target's max health
            t.setBaseValue(t.getBaseValue() + (count * 2));
            sender.sendMessage(Util.pluginMsg("操作成功。"));
        } else { // console?
            sender.sendMessage(Util.pluginMsg("不支持的执行者类型。期望执行者是玩家。"));
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
