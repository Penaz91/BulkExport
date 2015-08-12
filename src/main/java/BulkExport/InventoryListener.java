package BulkExport;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

public final class InventoryListener implements Listener {
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event){
		try{
			String playername=event.getPlayer().getName();
			Inventory invo=BulkExport._chests.get(playername);
			String invoname=invo.getName();
			if (event.getInventory().getName()==invoname){	
				BulkExport.Handle((Player) event.getPlayer());
			}
		} catch (NullPointerException e){
			System.out.println("There has been an exception");
		}
	}
}
