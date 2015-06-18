package BulkExport;
import java.io.File;
import java.util.HashMap;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
public class BulkExport extends JavaPlugin{
	static Inventory exportchest=null;
	File Datafolder = getDataFolder();
	@Override
    public void onEnable() {
		getLogger().info("BulkExport has been enabled");
		exportchest=Bukkit.getServer().createInventory(null, 54);
		getServer().getPluginManager().registerEvents(new InventoryListener(), this);
    }
 
    @Override
    public void onDisable() {
    	getLogger().info("BulkExport has been Disabled");
    	HandlerList.unregisterAll(this);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    	if (cmd.getName().equalsIgnoreCase("export")) { // If the player typed /basic then do the following...
    		if (!(sender instanceof Player)) {
    			sender.sendMessage("This command can only be run by a player.");
    		} else {
    			Player player = (Player) sender;
    			player.openInventory(exportchest);
    		}
    		return true;
    	} //If this has happened the function will return true. 
            // If this hasn't happened the value of false will be returned.
    	return false;
    }
    public static void Handle(Player player){
    	int full=0;
    	HashMap<Integer, ? extends ItemStack> mats=exportchest.all(Material.STONE);
    	Set<Integer> keys= mats.keySet();
    	HashMap<Integer, ItemStack> toreturn=new HashMap<Integer, ItemStack>();
    	for (Integer key:keys){
    		if (mats.get(key).getAmount()==64){
    			full=full+1;
    		}else{
    			toreturn.put(key, mats.get(key));
    		}
    	}
    	PlayerInventory pi=player.getInventory();
    	if (!(toreturn.isEmpty())){
    		Set<Integer> keys1=toreturn.keySet();
    		for (Integer key:keys1){
    			pi.addItem(toreturn.get(key));
    		}
    	}
    	pi.addItem(new ItemStack(Material.PAPER,full));
    	exportchest.clear();
    }
}
