package me.hammale.drugs;

import java.util.Random;

import net.minecraft.server.MobEffect;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class DrugPlayerListener extends PlayerListener {

	Random gen = new Random();
	
	public final drugs plugin;
	
    public DrugPlayerListener(drugs plugin)
    {
      this.plugin = plugin;
    }
    
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		ItemStack stack = event.getItem();
		if(event.getAction() == Action.RIGHT_CLICK_AIR){
		if (stack != null)
		{
			if (stack.getTypeId() == 353)
			{
					plugin.assignEffect(player);
					doPortal(event.getPlayer());
			}
	}
}		
}
	public void onPlayerMove(final PlayerMoveEvent e) {
//		if(plugin.drunk.contains(e.getPlayer().getName())){
//			//Vector dir1 = Direction.NORTH;
//			int gap = gen.nextInt(5);
//			int rdir = gen.nextInt(3);
//			if(rdir == 1){
//				  Vector dir = e.getPlayer().getLocation().getDirection();
//				  double val = gap * .1;
//			      Vector v = new Vector(dir.getX() * val, 0D, dir.getZ() * val);
//			      e.getPlayer().setVelocity(v);
//			}else if(rdir == 0){
//				  Vector dir = e.getPlayer().getLocation().getDirection();
//				  Vector backward = dir.multiply(-5);
//				  double val = gap * .1;
//			      Vector v = new Vector(backward.getX() * val, 0D, backward.getZ() * 0);
//			      e.getPlayer().setVelocity(v);
//			}
//		      		      
//		}
			if(plugin.drunk.contains(e.getPlayer().getName())){
				
				int speed = gen.nextInt(2);
				int ran = gen.nextInt(3);
				if(ran != 2){
					  Vector dir = e.getPlayer().getLocation().getDirection();
					  double val = speed * .1;
				      Vector v = new Vector(dir.getX() * val, 0D, 0D);
				      e.getPlayer().setVelocity(v);
				      doSlow(e.getPlayer());
				}     
			}
	}
	
	public void doPortal(final Player p){
		CraftPlayer cp = (CraftPlayer) p;
		int ran = gen.nextInt(1000);
		if(ran <= 300){
			ran = 300;
		}
		cp.getHandle().addEffect(new MobEffect(9, ran, 20));
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

		    public void run() {
		        plugin.drunk.remove(p.getName());
		    }
		}, ran);
	}
	
	public void doSlow(Player p){
		int speed = 5;
		int ran = gen.nextInt(3);
		if(ran != 2){
			int rblock = gen.nextInt(4);
			Block b = null;
			if(rblock == 0){
				b = p.getLocation().getBlock().getRelative(BlockFace.NORTH, speed);
			}else if(rblock == 1){
				b = p.getLocation().getBlock().getRelative(BlockFace.SOUTH, speed);
			}else if(rblock == 2){
				b = p.getLocation().getBlock().getRelative(BlockFace.EAST, speed);
			}else if(rblock == 3){
				b = p.getLocation().getBlock().getRelative(BlockFace.WEST, speed);
			}
			  double val = .1;
		      Vector v = new Vector(b.getLocation().getX() * val, 0D, 0D);
		      p.setVelocity(v);
		} 
	}
	
}
