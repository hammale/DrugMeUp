package me.hammale.drugs;

import java.util.Random;

import net.minecraft.server.MobEffect;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class DrugsPlayerListener implements Listener {
	Random gen = new Random();
	public final drugs plugin;

	public DrugsPlayerListener(drugs plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerChat(PlayerChatEvent e) {
		if (drugs.onDrugs.contains(e.getPlayer().getName())) {
			String initial = e.getMessage();
			String end = scramble(initial);
			e.setMessage(end);
		}
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (player.hasPermission("drugs.use")) {
			ItemStack stack = event.getItem();
			if (stack != null) {
				short data = stack.getDurability();
				if ((event.getAction() == Action.RIGHT_CLICK_AIR || event
						.getAction() == Action.RIGHT_CLICK_BLOCK)
						&& (player.isSneaking())
						&& (this.plugin.getEffect(stack.getTypeId(), data) != 0)) {
					player.getInventory().removeItem(
							new ItemStack(stack.getType(), 1, data));
					this.plugin.assignEffect(player, stack.getTypeId(), data);
				}
			}
		}
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		if (this.plugin.drunk.contains(e.getPlayer().getName())) {
			int speed = this.gen.nextInt(2);
			int ran = this.gen.nextInt(3);
			if (ran != 2) {
				Vector dir = e.getPlayer().getLocation().getDirection();
				double val = speed * 0.1D;
				Vector v = new Vector(dir.getX() * val, 0.0D, 0.0D);
				e.getPlayer().setVelocity(v);
				doSlow(e.getPlayer());
				doPortal(e.getPlayer());
			}
		}
	}

	public void doPortal(final Player p) {
		CraftPlayer cp = (CraftPlayer) p;
		int ran = this.gen.nextInt(1000);
		if (ran <= 300) {
			ran = 300;
		}
		int power = this.gen.nextInt(1000);
		if (power <= 100) {
			power = 100;
		}
		cp.getHandle().addEffect(new MobEffect(9, ran, power));
		this.plugin.getServer().getScheduler()
				.scheduleSyncDelayedTask(this.plugin, new Runnable() {
					public void run() {
						DrugsPlayerListener.this.plugin.drunk.remove(p
								.getName());
						drugs.onDrugs.remove(p.getName());
					}
				}, ran);
	}

	public void doSlow(Player p) {
		int speed = 5;
		int ran = this.gen.nextInt(3);
		if (ran != 2) {
			int rblock = this.gen.nextInt(4);
			Block b = null;
			if (rblock == 0)
				b = p.getLocation().getBlock()
						.getRelative(BlockFace.NORTH, speed);
			else if (rblock == 1)
				b = p.getLocation().getBlock()
						.getRelative(BlockFace.SOUTH, speed);
			else if (rblock == 2)
				b = p.getLocation().getBlock()
						.getRelative(BlockFace.EAST, speed);
			else if (rblock == 3) {
				b = p.getLocation().getBlock()
						.getRelative(BlockFace.WEST, speed);
			}
			double val = 0.1D;
			Vector v = new Vector(b.getLocation().getX() * val, 0.0D, 0.0D);
			p.setVelocity(v);
		}
	}

	public String scramble(String word) {
		StringBuilder builder = new StringBuilder(word.length());
		boolean[] used = new boolean[word.length()];

		for (int i = 0; i < word.length(); i++) {
			int rndIndex;
			do
				rndIndex = new Random().nextInt(word.length());
			while (used[rndIndex] != false);
			used[rndIndex] = true;

			builder.append(word.charAt(rndIndex));
		}
		return builder.toString();
	}
}