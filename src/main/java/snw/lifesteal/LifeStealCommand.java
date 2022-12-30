package snw.lifesteal;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;

public class LifeStealCommand implements CommandExecutor {
    private final Main main;

    public LifeStealCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        main.work = !main.work;
        sender.sendMessage(Util.pluginMsg("插件已" + ((main.work) ? (ChatColor.GREEN + "启用") : (ChatColor.RED + "禁用"))));
        return true;
    }
    
}
