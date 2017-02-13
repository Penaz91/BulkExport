package BulkExport;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import net.milkbowl.vault.item.*;

import org.bukkit.Bukkit;
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

public class BulkExport extends JavaPlugin{
	static HashMap<String,Inventory> _chests; 
	//static Inventory exportchest=null;
	File Datafolder = getDataFolder();
	static ArrayList<Exportable> items=new ArrayList<Exportable>();
	static Exportable temp=new Exportable();
	@SuppressWarnings("unchecked")
	@Override
    public void onEnable() {
		getLogger().info("BulkExport has been enabled");
		_chests=new HashMap<String,Inventory>();
		File f = getDataFolder();
		if (!f.exists()){
			f.mkdir();
			saveResource("config.yml", false);
			saveResource("items.yml",false);
		}
		ConfigurationSerialization.registerClass(Exportable.class);
		//exportchest=Bukkit.getServer().createInventory(null, 54);
		getServer().getPluginManager().registerEvents(new InventoryListener(), this);
		getServer().getPluginManager().registerEvents(new playerLoginListener(), this);
		getServer().getPluginManager().registerEvents(new playerLogoutListener(), this);
		File customfile = new File(getDataFolder(),"items.yml");
		FileConfiguration customyml=YamlConfiguration.loadConfiguration(customfile);
		items=(ArrayList<Exportable>) customyml.get("0");
		if (items==null){
			items=new ArrayList<Exportable>();
		}
		//getLogger().info("Items:" +items);
    }
    @Override
    public void onDisable() {
    	getLogger().info("BulkExport has been Disabled");
    	HandlerList.unregisterAll(this);
    	File customfile = new File(getDataFolder(),"items.yml");
		FileConfiguration customyml=YamlConfiguration.loadConfiguration(customfile);
		customyml.createSection("0");
		customyml.set("0",items);
		try{
			customyml.save(customfile);
		} catch (IOException e){
			getLogger().info("Unable to save items.yml");
		}
		_chests=null;
    }
    @SuppressWarnings("unchecked")
	@Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    	if (cmd.getName().equalsIgnoreCase("export")) { // If the player typed /basic then do the following...
    		if (!(sender instanceof Player)) {
    			sender.sendMessage("This command can only be run by a player.");
    		} else {
    			Player player = (Player) sender;
    			_chests.put(player.getName(), Bukkit.getServer().createInventory(player, 54));
    			player.openInventory(_chests.get(player.getName()));
    		}
    		return true;
    	} else{
    		if (cmd.getName().equalsIgnoreCase("CreateTrade")) {
    			if (!(sender instanceof Player)) {
        			sender.sendMessage("This command can only be run by a player.");
        		} else {
        			temp.setNumStacks(Integer.parseInt(args[0]));
        			temp.setNumTraded(Integer.parseInt(args[1]));
        			Player player=(Player) sender;
        			PlayerInventory pi=player.getInventory();
        			temp.setStackSize(pi.getItem(pi.getHeldItemSlot()).getMaxStackSize());
        			temp.setTrade(pi.getItem(pi.getHeldItemSlot()));
        			sender.sendMessage("Select the item to trade to and then run /finishtrade");
        		}
    			return true;
    		}else{
    			
    		}if (cmd.getName().equalsIgnoreCase("FinishTrade")) {
    			if (!(sender instanceof Player)) {
    				sender.sendMessage("This command can only be run by a player.");
        		} else {
        			Player player=(Player) sender;
        			PlayerInventory pi=player.getInventory();
        			temp.setTraded(pi.getItem(pi.getHeldItemSlot()));
        			getLogger().info(temp.toString());
        			getLogger().info(items.toString());
        			items.add(temp);
        			sender.sendMessage("Saving trades.");
        			File customfile = new File(getDataFolder(),"items.yml");
        			FileConfiguration customyml=YamlConfiguration.loadConfiguration(customfile);
        			customyml.createSection("0");
        			customyml.set("0",items);
        			try{
        				customyml.save(customfile);
        			} catch (IOException e){
        				getLogger().info("Unable to save items.yml");
        			}
        			sender.sendMessage("Reloading trades.");
        			customfile = new File(getDataFolder(),"items.yml");
        			customyml=YamlConfiguration.loadConfiguration(customfile);
        			items=(ArrayList<Exportable>) customyml.get("0");
        			if (items==null){
        				items=new ArrayList<Exportable>();
        			}
        			sender.sendMessage("Trade creation finished.");
        		}
    			return true;
    		} else {
    			if (cmd.getName().equalsIgnoreCase("Trades")) {
    				if (!(sender instanceof Player)){
    					sender.sendMessage("This command can only be run by a player.");
    				}else{
    					sender.sendMessage("These are the trades available:");
    					//String toret="";
    					for (Exportable trade:items){
    						if (trade.getTrade().hasItemMeta()){
    							if (trade.getTraded().hasItemMeta()){
    								// Doppio Nome Custom
    								sender.sendMessage("Trade " + trade.getNumStacks() + "x" + trade.getStackSize() + " " + trade.getTrade().getItemMeta().getDisplayName() + " for " + trade.getNumTraded() + " " + trade.getTraded().getItemMeta().getDisplayName());
    							}else{
    								// Nome custom solo Trade
    								String tradedname;
    								try{
    									tradedname = Items.itemByStack(trade.getTraded()).getName();
    								}catch (NullPointerException e){
    									tradedname = "null";
    								}
    								if (tradedname=="null"){
    									tradedname = trade.getTraded().getItemMeta().getDisplayName();
    								}
    								sender.sendMessage("Trade " + trade.getNumStacks() + "x" + trade.getStackSize() + " " + trade.getTrade().getItemMeta().getDisplayName() + " for " + trade.getNumTraded() + " " + tradedname);
    							}
    						}else{
    							if (trade.getTraded().hasItemMeta()){
    								// Nome Custom Solo Traded
    								String tradename;
    								try{
    									tradename = Items.itemByStack(trade.getTrade()).getName();
    								}catch (NullPointerException e){
    									tradename="null";
    								}
									if (tradename=="null"){
										tradename = trade.getTrade().getItemMeta().getDisplayName();
									}
    								sender.sendMessage("Trade " + trade.getNumStacks() + "x" + trade.getStackSize() + " " + tradename + " for " + trade.getNumTraded() + " " + trade.getTraded().getItemMeta().getDisplayName());
    							}else{
    								// No Nome Custom
    								String tradedname;
    								try{
    									tradedname = Items.itemByStack(trade.getTraded()).getName();
    								}catch(NullPointerException e){
    									tradedname = "null";
    								}
    								if (tradedname=="null"){
    									tradedname = trade.getTraded().getType().toString();
    								}
    								String tradename;
    								try{
    									tradename = Items.itemByStack(trade.getTrade()).getName();
    								}catch(NullPointerException e){
    									tradename = "null";
    								}
    								if (tradename=="null"){
    									tradename = trade.getTrade().getType().toString();
    								}
    								sender.sendMessage("Trade " + trade.getNumStacks() + "x" + trade.getStackSize() + " " + tradename + " for " + trade.getNumTraded() + " " + tradedname);
    							}
    						}
    					}
    				} return true;
    			}
    		}
    		//If this has happened the function will return true. 
    	}
            // If this hasn't happened the value of false will be returned.
    	return false;
    }
    public static void Handle(Player player){
    	int full=0;
    	ItemStack[] contents=_chests.get(player.getName()).getContents();
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
    			_chests.get(player.getName()).clear();
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
    		if (itemfound==null){
				for (ItemStack itm:contents){
					PlayerInventory pi=player.getInventory();
					if (itm!=null){
						pi.addItem(itm);
					}
				}
				player.sendMessage("Sorry, but no trades are available for these items, see /trades");
			}else{
				HashMap<Integer, ? extends ItemStack> mats=_chests.get(player.getName()).all(itemfound.getTrade().getType());
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
				if (full%itemfound.getNumStacks()==0){
					Bukkit.getServer().getLogger().info("Entro1");
					if (full!= 0){
						itemfound.getTraded().setAmount((full*itemfound.getNumTraded()/itemfound.getNumStacks()));
						pi.addItem(itemfound.getTraded());
					}
				}else{
					Bukkit.getServer().getLogger().info("Entro2");
					itemfound.getTraded().setAmount(((full-1)*itemfound.getNumTraded()/itemfound.getNumStacks()));
					pi.addItem(itemfound.getTraded());
					itemfound.getTrade().setAmount(64*(full%itemfound.getNumStacks()));
					pi.addItem(itemfound.getTrade());
				}
			}
    		_chests.get(player.getName()).clear();
    	}
    }
}
