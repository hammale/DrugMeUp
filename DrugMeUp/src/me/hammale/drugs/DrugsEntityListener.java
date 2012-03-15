package me.hammale.drugs;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class DrugsEntityListener implements Listener {
	public final drugs plugin;

	public DrugsEntityListener(drugs plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void EntityDeath(EntityDeathEvent e) {
		if ((e.getEntity() instanceof Player)) {
			Player p = (Player) e.getEntity();
			if (plugin.drunk.contains(p.getName())) {
				plugin.drunk.remove(p.getName());
			}
			if (drugs.onDrugs.contains(p.getName()))
				drugs.onDrugs.remove(p.getName());
		}
	}
}