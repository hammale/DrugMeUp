package me.hammale.drugs;

import java.io.File;
import java.util.logging.Logger;
import java.util.*;

import net.minecraft.server.MobEffect;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class drugs extends JavaPlugin {
	
	  public FileConfiguration config;
	
	  Random gen = new Random();
	  
	  public ArrayList<String> drunk = new ArrayList<String>();	  
	  public ArrayList<String> onDrugs = new ArrayList<String>();

	  private final DrugPlayerListener playerListener = new DrugPlayerListener(this);
	  private final DrugEntityListener entityListener = new DrugEntityListener(this);
	  
	  Logger log = Logger.getLogger("Minecraft");
	
	@Override
	public void onEnable() {

		PluginDescriptionFile pdfFile = this.getDescription();
		
		log.info("[DrugMeUp] " + pdfFile.getVersion() + " Enabled!");
		
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvent(Event.Type.PLAYER_INTERACT, playerListener, Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_MOVE, playerListener, Priority.Normal, this);
		pm.registerEvent(Event.Type.ENTITY_DEATH, entityListener, Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_CHAT, playerListener, Priority.Normal, this);
		loadConfiguration();
	}
	
	@Override
	public void onDisable() {
		
		PluginDescriptionFile pdfFile = this.getDescription();
		
		log.info("[DrugMeUp] " + pdfFile.getVersion() + " Disabled!");
		
	}

	public void loadConfiguration(){
	    if(exists() == false){
		    config = getConfig();
		    config.options().copyDefaults(false);   
		    String path1 = "DrugIds.353.Effect";
		    String path2 = "DrugIds.351.Effect";
		    String path3 = "DrugIds.40.Effect";
		    
		    config.addDefault(path1, 9);
		    config.addDefault(path2, 9);
		    config.addDefault(path3, 9);
		    config.options().copyDefaults(true);
		    saveConfig();
	    }
	}
	
	private boolean exists() {	
			try{
			File file = new File("plugins/DrugMeUp/config.yml"); 
	        if (file.exists()) { 
	        	return true;
	        }else{
	        	return false;
	        }

			}catch (Exception e){
			  System.err.println("Error: " + e.getMessage());
			  return true;
			}
	}

	public int getEffect(int id){
	    config = getConfig();
	    int amnt = config.getInt("DrugIds." + id + "." + "Effect"); 
	    return amnt;
	}
	
	public int getId(int id){
	    config = getConfig();
	    int amnt = config.getInt("DrugIds." + id + ".Effect");
	    return amnt;
	}
	
	  public boolean onCommand(final CommandSender sender, Command cmd, String commandLabel, String[] args){
			if(cmd.getName().equalsIgnoreCase("drunk")){
					if(args.length == 0){
						return false;
					}
					if(args[0].equalsIgnoreCase("reload")){
						reloadConfig();
						if(sender instanceof Player){
							sender.sendMessage(ChatColor.GREEN + "DrugMeUp Reloaded!");
							return true;
						}else{
							sender.sendMessage("[DrugMeUp] Reloaded!");
							return true;
						}
					}
					return false;
			}
			return false;
		}			
	
	public void walkWeird(Player p) {
		
		drunk.add(p.getName());
		
	}
	
	public void walkSlow(final Player p) {
		
		CraftPlayer cp = (CraftPlayer) p;
		int power = gen.nextInt(100);
		if(power <= 10){
			power = 10;
		}
		int ran = gen.nextInt(1000);
		if(ran <= 300){
			ran = 300;
		}
		cp.getHandle().addEffect(new MobEffect(2, ran, power));
		getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {

		    public void run() {
				onDrugs.remove(p.getName());
		    }
		}, ran);
	}
	
	public void walkFast(final Player p) {
		
		CraftPlayer cp = (CraftPlayer) p;
		int power = gen.nextInt(100);
		if(power <= 10){
			power = 10;
		}
		int ran = gen.nextInt(1000);
		if(ran <= 300){
			ran = 300;
		}
		cp.getHandle().addEffect(new MobEffect(1, ran, power));
		getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {

		    public void run() {
				onDrugs.remove(p.getName());
		    }
		}, ran);
	}
	
	public void supaStrength(final Player p) {
		
		CraftPlayer cp = (CraftPlayer) p;
		int power = gen.nextInt(1000);
		if(power <= 100){
			power = 100;
		}
		int ran = gen.nextInt(1000);
		if(ran <= 300){
			ran = 300;
		}
		cp.getHandle().addEffect(new MobEffect(5, ran, power));
		cp.getHandle().addEffect(new MobEffect(3, ran, power));
		cp.getHandle().addEffect(new MobEffect(11, ran, power));
		getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {

		    public void run() {
				onDrugs.remove(p.getName());
		    }
		}, ran);
	}

	public void blindMe(final Player p) {
		
		CraftPlayer cp = (CraftPlayer) p;
		int power = gen.nextInt(1000);
		if(power <= 100){
			power = 100;
		}
		int ran = gen.nextInt(1000);
		if(ran <= 300){
			ran = 300;
		}
		cp.getHandle().addEffect(new MobEffect(15, ran, power));
		getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {

		    public void run() {
				onDrugs.remove(p.getName());
		    }
		}, ran);
	}
	
	public void soHungry(final Player p) {
		
		CraftPlayer cp = (CraftPlayer) p;
		int power = gen.nextInt(1000);
		if(power <= 100){
			power = 100;
		}
		int ran = gen.nextInt(1000);
		if(ran <= 300){
			ran = 300;
		}
		cp.getHandle().addEffect(new MobEffect(17, ran, power));
		getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {

		    public void run() {
				onDrugs.remove(p.getName());
		    }
		}, ran);
	}
	
	public void soSick(final Player p) {
		
		CraftPlayer cp = (CraftPlayer) p;
		int power = gen.nextInt(1000);
		if(power <= 100){
			power = 100;
		}
		int ran = gen.nextInt(1000);
		if(ran <= 300){
			ran = 300;
		}
		cp.getHandle().addEffect(new MobEffect(4, ran, power));
		cp.getHandle().addEffect(new MobEffect(18, ran, power));	
		cp.getHandle().addEffect(new MobEffect(19, ran, power));
		getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {

		    public void run() {
				onDrugs.remove(p.getName());
		    }
		}, ran);
	}
	
	public void feelingJumpy(final Player p) {
		
		CraftPlayer cp = (CraftPlayer) p;
		int power = gen.nextInt(15);
		int ran = gen.nextInt(1000);
		if(ran <= 300){
			ran = 300;
		}
		cp.getHandle().addEffect(new MobEffect(8, ran, power));
		
		getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {

		    public void run() {
				onDrugs.remove(p.getName());
		    }
		}, ran);
	}
	
	public void torchYa(Player p) {
		getServer().broadcastMessage(ChatColor.RED + "* " + p.getName() + " bursts into flames");
		final Block b = p.getLocation().getBlock();
		b.setTypeId(51);
		getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
		    public void run() {
				b.setTypeId(0);
		    }
		}, 5);
		
	}
	
	public void youOd(Player p) {
		p.damage(10000);
		getServer().broadcastMessage(ChatColor.GREEN + p.getName() + " OD'd! " + ChatColor.RED + "DON'T DO DRUGS KIDS!");
		onDrugs.remove(p.getName());
	}
	
	@SuppressWarnings("deprecation")
	public void pukeInv(Player p) {
		getServer().broadcastMessage(ChatColor.GREEN + "* " + p.getName() + " violently pukes his guts out");
		ItemStack[] i = p.getInventory().getContents();
		Location l = p.getLocation().getBlock().getRelative(BlockFace.NORTH, 3).getLocation();
		p.getInventory().clear();
		p.updateInventory();
		for(ItemStack item:i){
			if(item != null){
				p.getWorld().dropItemNaturally(l, item);
				p.updateInventory();
			}
		}
		p.updateInventory();
	}
	
	public void assignEffect(Player p, int i) {
		onDrugs.add(p.getName());
		p.sendMessage("Aww yeah buddy!");
		int effect = getEffect(i);
		if(effect == 1){
			walkWeird(p);
		}else if(effect == 2){
			walkSlow(p);
		}else if(effect == 3){
			walkFast(p);
		}else if(effect == 4){
			supaStrength(p);
		}else if(effect == 5){
			blindMe(p);
		}else if(effect == 6){
			soHungry(p);
		}else if(effect == 7){
			soSick(p);
		}else if(effect == 8){
			feelingJumpy(p);
		}else if(effect == 9){
			int ran = gen.nextInt(7);			
			if(ran == 0){
				walkWeird(p);
			}else if(ran == 1){
				walkSlow(p);
			}else if(ran == 2){
				walkFast(p);
			}else if(ran == 3){
				supaStrength(p);
			}else if(ran == 4){
				blindMe(p);
			}else if(ran == 5){
				soHungry(p);
			}else if(ran == 6){
				feelingJumpy(p);
			}else if(ran == 6){
				soSick(p);
			}
		}
		int ran1 = gen.nextInt(50);
		if(ran1 >= 45){
			torchYa(p);
		}
		if(ran1 <= 2){
			youOd(p);
		}
		if(ran1 >= 25){
			int power = gen.nextInt(15);
			p.damage(power);
		}
		if(ran1 >= 30 && ran1 < 35){
			pukeInv(p);
		}
	}

}