package BulkExport;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.yaml.snakeyaml.Yaml;

public class BulkExport extends JavaPlugin{
	static Inventory exportchest=null;
	File Datafolder = getDataFolder();
	static ArrayList<Exportable> items=new ArrayList<Exportable>();
	@Override
    public void onEnable() {
		getLogger().info("BulkExport has been enabled");
		ConfigurationSerialization.registerClass(Exportable.class);
		exportchest=Bukkit.getServer().createInventory(null, 54);
		getServer().getPluginManager().registerEvents(new InventoryListener(), this);
		/*items.add(new Exportable(1,new ItemStack(Material.STONE),64,1,new ItemStack(Material.PAPER),1));
		items.add(new Exportable(2,new ItemStack(Material.CARROT_ITEM),64,1,new ItemStack(Material.PAPER),1));*/
		/*Yaml yaml=new Yaml();
		FileInputStream customfile=null;
		try {
			customfile = new FileInputStream(new File(getDataFolder(),"items.yml"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		items=new ArrayList((yaml.loadAs(customfile,java.util.LinkedHashMap.class)).keySet());*/
		File customfile = new File(getDataFolder(),"items.yml");
		FileConfiguration customyml=YamlConfiguration.loadConfiguration(customfile);
		items=(ArrayList<Exportable>) customyml.get("0");
		getLogger().info("Items: " +items);
    }
    @Override
    public void onDisable() {
    	getLogger().info("BulkExport has been Disabled");
    	HandlerList.unregisterAll(this);
    	File customfile = new File(getDataFolder(),"items.yml");
		FileConfiguration customyml=YamlConfiguration.loadConfiguration(customfile);
		/*Integer i=0;
		for (Exportable item:items){
			customyml.createSection(i.toString());
			customyml.set(i.toString(),item);
			i=i+1;
		}*/
		customyml.createSection("0");
		customyml.set("0",items);
		try{
			customyml.save(customfile);
		} catch (IOException e){
			getLogger().info("Unable to save items.yml");
		}
		/*Map<String, Object> map=customyml.getValues(false);
		if (map.get(args[0])==null){
			customyml.createSection(args[0]);
			customyml.set(args[0],i);
		}else{
			customyml.set(args[0], i);
		}
		try {
			customyml.save(customfile);
		} catch (IOException e) {
			sender.sendMessage("Unable to save item, items.yml not found");*/
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
    	ItemStack[] contents=exportchest.getContents();
    	HashMap<Integer, ItemStack> toreturn=new HashMap<Integer, ItemStack>();
    	boolean dirty=false;
    	for (int i=0;i<contents.length;i++){
    		if ((contents[i]!=null) && (!(contents[i].isSimilar(contents[0])))){
    			PlayerInventory pi=player.getInventory();
    			for (ItemStack item:contents){
    				if (item!=null){
    					pi.addItem(item);
    				}
    			}
    			exportchest.clear();
    			dirty=true;
    			player.sendMessage("All the items must be of the same Type");
    			break;
    		}
    	}
    	if (!dirty){
    		Exportable itemfound=null;
    		for (Exportable item:items){
    			if (item.getTrade().isSimilar(contents[0])){
    				itemfound=item;
    			}
    		}
    		HashMap<Integer, ? extends ItemStack> mats=exportchest.all(itemfound.getTrade().getType());
    		Set<Integer> keys= mats.keySet();
    		for (Integer key:keys){
    			if ((mats.get(key).isSimilar(itemfound.getTrade()))&&(mats.get(key).getAmount()==itemfound.getStackSize())){
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
    		pi.addItem(new ItemStack(itemfound.getTraded().getType(),full));
    	}
    	exportchest.clear();
    }
}
