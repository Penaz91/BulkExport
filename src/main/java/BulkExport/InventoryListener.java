package BulkExport;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public final class InventoryListener implements Listener {
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event){
		if (event.getInventory().getName()==BulkExport.exportchest.getName()){
			BulkExport.Handle((Player) event.getPlayer());
		}
	}
}
