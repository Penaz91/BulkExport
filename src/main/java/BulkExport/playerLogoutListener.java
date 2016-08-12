package BulkExport;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class playerLogoutListener implements Listener{
	@EventHandler
	public void onPlayerLogout(PlayerQuitEvent event){
		BulkExport._chests.remove(event.getPlayer().getName());
		Bukkit.getLogger().info("[BulkExport] Removed player from the list of inventories for exporting");
	}
}
