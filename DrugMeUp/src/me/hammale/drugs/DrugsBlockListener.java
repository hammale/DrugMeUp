package me.hammale.drugs;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class DrugsBlockListener implements Listener {
	public final drugs plugin;

	public DrugsBlockListener(drugs plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		Player p = (Player) event.getPlayer();
		if (plugin.getEffect(event.getBlockPlaced().getTypeId(), 0) != 0) {
			if (drugs.onDrugs.contains(p.getName())) {
				event.setCancelled(true);
			}
		}
	}
}
