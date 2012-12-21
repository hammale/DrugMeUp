package me.hammale.drugs;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Logger;

import net.minecraft.server.v1_4_5.MobEffect;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_4_5.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class drugs extends JavaPlugin {
	public FileConfiguration config;
	Random gen = new Random();

	public ArrayList<String> drunk = new ArrayList<String>();
	public static ArrayList<String> onDrugs = new ArrayList<String>();

	Logger log = Logger.getLogger("Minecraft");

	public void onEnable() {
		PluginDescriptionFile pdfFile = getDescription();

		log.info("[DrugMeUp] " + pdfFile.getVersion() + " Enabled!");

		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new DrugsPlayerListener(this), this);
		pm.registerEvents(new DrugsEntityListener(this), this);
		pm.registerEvents(new DrugsBlockListener(this), this);
		loadConfiguration();
	}

	public void onDisable() {
		PluginDescriptionFile pdfFile = getDescription();

		log.info("[DrugMeUp] " + pdfFile.getVersion() + " Disabled!");
	}

	public void loadConfiguration() {
		if (!exists()) {
			config = getConfig();
			config.options().copyDefaults(false);
			String path1 = "DrugIds.353.Effect";
			String path2 = "DrugIds.351:0.Effect";
			String path3 = "DrugIds.40.Effect";
			String path5 = "chat.broadcast.Burning";
			String path6 = "chat.broadcast.Death";
			String path7 = "chat.broadcast.Puke";
			String path8 = "chat.broadcast.OnDrugs";

			config.addDefault(path1, Integer.valueOf(8));
			config.addDefault(path2, Integer.valueOf(8));
			config.addDefault(path3, Integer.valueOf(8));
			config.addDefault(path5, "Bursts into flames");
			config.addDefault(path6, "OD'd - Don't do drugs kids!");
			config.addDefault(path7, "Violently pukes his guts out");
			config.addDefault(path8, "Aww yeah buddy!");

			config.options().copyDefaults(true);

			saveConfig();
		}
	}

	private boolean exists() {
		try {
			File file = new File("plugins/DrugMeUp/config.yml");

			return file.exists();
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
		return true;
	}

	public int getEffect(int id, int damage) {
		config = getConfig();
		int amnt;
		if (damage != 0) {
			amnt = config.getInt("DrugIds." + id + ":" + damage + ".Effect");
		} else {
			amnt = config.getInt("DrugIds." + id + ".Effect");
		}
		return amnt;
	}

	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		if (cmd.getName().equalsIgnoreCase("drugmeup")) {
			if (sender.hasPermission("drugs.reload")) {
				if (args.length == 1) {
					if (args[0].equalsIgnoreCase("reload")) {
						reloadConfig();
						if ((sender instanceof Player)) {
							sender.sendMessage(ChatColor.GREEN
									+ "DrugMeUp Reloaded!");
							return true;
						}
						sender.sendMessage("[DrugMeUp] Reloaded!");
						return true;
					}
				} else
					sender.sendMessage(ChatColor.DARK_RED
							+ "Invalid Arguments!");
				return false;
			} else
				sender.sendMessage(ChatColor.DARK_RED
						+ "You don't have permission!");
		}
		return false;
	}

	public void walkWeird(Player p) {
		drunk.add(p.getName());
	}

	public void walkSlow(final Player p) {
		CraftPlayer cp = (CraftPlayer) p;
		int power = gen.nextInt(100);
		if (power <= 10) {
			power = 10;
		}
		int ran = gen.nextInt(1000);
		if (ran <= 300) {
			ran = 300;
		}
		cp.getHandle().addEffect(new MobEffect(2, ran, power));
		getServer().getScheduler().scheduleSyncDelayedTask(this,
				new Runnable() {
					public void run() {
						drugs.onDrugs.remove(p.getName());
					}
				}, ran);
	}

	public void walkFast(final Player p) {
		CraftPlayer cp = (CraftPlayer) p;
		int power = gen.nextInt(100);
		if (power <= 10) {
			power = 10;
		}
		int ran = gen.nextInt(1000);
		if (ran <= 300) {
			ran = 300;
		}
		cp.getHandle().addEffect(new MobEffect(1, ran, power));
		getServer().getScheduler().scheduleSyncDelayedTask(this,
				new Runnable() {
					public void run() {
						drugs.onDrugs.remove(p.getName());
					}
				}, ran);
	}

	public void blindMe(final Player p) {
		CraftPlayer cp = (CraftPlayer) p;
		int power = gen.nextInt(1000);
		if (power <= 100) {
			power = 100;
		}
		int ran = gen.nextInt(1000);
		if (ran <= 300) {
			ran = 300;
		}
		cp.getHandle().addEffect(new MobEffect(15, ran, power));
		p.canSee(p);
		getServer().getScheduler().scheduleSyncDelayedTask(this,
				new Runnable() {
					public void run() {
						drugs.onDrugs.remove(p.getName());
					}
				}, ran);
	}

	public void soHungry(final Player p) {
		CraftPlayer cp = (CraftPlayer) p;
		int power = gen.nextInt(1000);
		if (power <= 100) {
			power = 100;
		}
		int ran = gen.nextInt(1000);
		if (ran <= 300) {
			ran = 300;
		}
		cp.getHandle().addEffect(new MobEffect(17, ran, power));
		getServer().getScheduler().scheduleSyncDelayedTask(this,
				new Runnable() {
					public void run() {
						drugs.onDrugs.remove(p.getName());
					}
				}, ran);
	}

	public void soSick(final Player p) {
		CraftPlayer cp = (CraftPlayer) p;
		int power = gen.nextInt(1000);
		if (power <= 100) {
			power = 100;
		}
		int ran = gen.nextInt(1000);
		if (ran <= 300) {
			ran = 300;
		}
		cp.getHandle().addEffect(new MobEffect(4, ran, power));
		cp.getHandle().addEffect(new MobEffect(18, ran, power));
		cp.getHandle().addEffect(new MobEffect(19, ran, power));
		getServer().getScheduler().scheduleSyncDelayedTask(this,
				new Runnable() {
					public void run() {
						drugs.onDrugs.remove(p.getName());
					}
				}, ran);
	}

	public void feelingJumpy(final Player p) {
		CraftPlayer cp = (CraftPlayer) p;
		int power = gen.nextInt(15);
		int ran = gen.nextInt(1000);
		if (ran <= 300) {
			ran = 300;
		}
		cp.getHandle().addEffect(new MobEffect(8, ran, power));

		getServer().getScheduler().scheduleSyncDelayedTask(this,
				new Runnable() {
					public void run() {
						drugs.onDrugs.remove(p.getName());
					}
				}, ran);
	}

	public void torchYa(Player p) {
		String hot = config.getString("chat.broadcast.Burning");
		getServer().broadcastMessage(ChatColor.RED + p.getName() + " " + hot);
		final Block b = p.getLocation().getBlock();
		b.setTypeId(51);
		getServer().getScheduler().scheduleSyncDelayedTask(this,
				new Runnable() {
					public void run() {
						b.setTypeId(0);
					}
				}, 5L);
	}

	public void youOd(Player p) {
		String death = config.getString("chat.broadcast.Death");
		p.damage(10000);
		getServer().broadcastMessage(ChatColor.RED + p.getName() + " " + death);
		onDrugs.remove(p.getName());
	}

	@SuppressWarnings("deprecation")
	public void pukeInv(Player p) {
		String puke = config.getString("chat.broadcast.Puke");
		getServer().broadcastMessage(
				ChatColor.GREEN + "* " + p.getName() + " " + puke);
		ItemStack[] i = p.getInventory().getContents();
		Location l = p.getLocation().getBlock().getRelative(BlockFace.NORTH, 3)
				.getLocation();
		p.getInventory().clear();
		for (ItemStack item : i) {
			if (item != null) {
				p.getWorld().dropItemNaturally(l, item);
				p.updateInventory();
			}
		}
		p.updateInventory();
	}

	public void assignEffect(Player p, int i, int dmg) {
		String ond = config.getString("chat.broadcast.OnDrugs");
		onDrugs.add(p.getName());
		p.sendMessage(ond);
		int effect = getEffect(i, dmg);
		if (effect == 1) {
			walkWeird(p);
		} else if (effect == 2) {
			walkSlow(p);
		} else if (effect == 3) {
			walkFast(p);
		} else if (effect == 4) {
			blindMe(p);
		} else if (effect == 5) {
			soHungry(p);
		} else if (effect == 6) {
			soSick(p);
		} else if (effect == 7) {
			feelingJumpy(p);
		} else if (effect == 8) {
			int ran = gen.nextInt(6);
			if (ran == 0)
				walkWeird(p);
			else if (ran == 1)
				walkSlow(p);
			else if (ran == 2)
				walkFast(p);
			else if (ran == 3)
				blindMe(p);
			else if (ran == 4)
				soHungry(p);
			else if (ran == 5)
				feelingJumpy(p);
			else if (ran == 6) {
				soSick(p);
			}
		}
		int ran1 = gen.nextInt(50);
		if (ran1 >= 45) {
			torchYa(p);
		}
		if (ran1 <= 2) {
			youOd(p);
		}
		if (ran1 >= 25) {
			int power = gen.nextInt(15);
			p.damage(power);
		}
		if ((ran1 >= 30) && (ran1 < 35))
			pukeInv(p);
	}
}