package BulkExport;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class playerLoginListener implements Listener{
	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent event){
		Player pl = event.getPlayer();
		BulkExport._chests.put(pl.getName(), Bukkit.getServer().createInventory(pl, 54));
		Bukkit.getLogger().info("[BulkExport] Added player to the list of inventories for exporting");
	}
}
