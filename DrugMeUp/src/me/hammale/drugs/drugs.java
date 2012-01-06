package me.hammale.drugs;

import java.util.logging.Logger;
import java.util.*;

import net.minecraft.server.MobEffectList;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class drugs extends JavaPlugin {
	
	  public FileConfiguration config;
	
	  public ArrayList<String> drunk = new ArrayList<String>();

	  private final DrugPlayerListener playerListener = new DrugPlayerListener(this);
	  
	  Logger log = Logger.getLogger("Minecraft");
	
	@Override
	public void onEnable() {

		PluginDescriptionFile pdfFile = this.getDescription();
		
		log.info("[DrugMeUp] " + pdfFile.getVersion() + " Enabled!");
		
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvent(Event.Type.PLAYER_INTERACT, playerListener, Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_MOVE, (Listener) playerListener, Priority.Normal, this);
		
		int id = getId(2);
		
		System.out.println(id);
		
		loadConfiguration();
	}
	
	@Override
	public void onDisable() {
		
		PluginDescriptionFile pdfFile = this.getDescription();
		
		log.info("[DrugMeUp] " + pdfFile.getVersion() + " Disabled!");
		
	}

	public void loadConfiguration(){
	    config = getConfig();
	    config.options().copyDefaults(true);   
	    String path1 = "DrugIds.353.Effect";
	    String path2 = "DrugIds.351.Effect";
	    String path3 = "DrugIds.40.Effect";
	    
	    config.addDefault(path1, 9);
	    config.addDefault(path2, 9);
	    config.addDefault(path3, 9);
	    config.options().copyDefaults(true);
	    saveConfig();
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
				if(sender instanceof Player){
					Player p = (Player) sender;
					if(drunk.contains(p.getName())){
						drunk.remove(p.getName());
					}else{
						drunk.add(p.getName());
					}
					return true;
				}
				return false;
			}
			return false;
		}			
	
	public void walkWeird(Player p) {
		
		drunk.add(p.getName());
		
	}

	public void assignEffect(Player p) {
		
		walkWeird(p);
		
	}

}