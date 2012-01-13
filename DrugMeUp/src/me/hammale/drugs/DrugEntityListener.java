package me.hammale.drugs;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityListener;

public class DrugEntityListener extends EntityListener {

	public final drugs plugin;
	
    public DrugEntityListener(drugs plugin)
    {
      this.plugin = plugin;
    }
	
	public void onEntityDeath(EntityDeathEvent e) {
		if(e.getEntity() instanceof Player){
		Player p = (Player) e.getEntity();
		if(plugin.drunk.contains(p.getName())){
			plugin.drunk.remove(p.getName());
		}
		if(plugin.onDrugs.contains(p.getName())){
			plugin.onDrugs.remove(p.getName());
		}
	}
	}
}
