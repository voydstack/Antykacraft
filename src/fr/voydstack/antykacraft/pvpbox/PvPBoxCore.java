package fr.voydstack.antykacraft.pvpbox;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.voydstack.antykacraft.Antykacraft;
import fr.voydstack.antykacraft.config.pvpbox.PvPBoxConfig;
import fr.voydstack.antykacraft.pvpbox.ability.MainAbility;
import fr.voydstack.antykacraft.pvpbox.gui.PvPBoxKitIcon;
import fr.voydstack.antykacraft.pvpbox.kit.Kit;
import fr.voydstack.libs.ParticleEffect;

public class PvPBoxCore {
	public static HashMap<Player, Kit> players = new HashMap<Player, Kit>();
	public static HashMap<Player, List<MainAbility>> cooldowns = new HashMap<Player, List<MainAbility>>();
	public static String eventWorld;

	public static void init() {
		eventWorld = Antykacraft.configHandler.getEventWorldName();
		PvPBoxConfig.init();
		PvPBoxKitIcon.init();
		Kit.init();
		
		new BukkitRunnable() {
			@Override
			public void run() {
				try {
					for(Player p : Bukkit.getWorld(eventWorld).getPlayers()) {
						if(players.containsKey(p)) {
							if(players.get(p).getName().equals("Fantôme")) {
								ParticleEffect ghost = new ParticleEffect(ParticleEffect.ParticleType.SPELL, 0, 0, 0);
								Location l = p.getLocation();
								l.add(0, 1, 0);
								ghost.sendToLocation(l);
							}
						}
					}
				} catch(NullPointerException npe) {}
			}
		}.runTaskTimer(Antykacraft.instance, 0L, 30L);
	}
	
	public static void teleportToPvPBoxArena(Player p) {
		p.teleport(PvPBoxConfig.getPvPBoxArenaLocation(PvPBoxConfig.getDefaultPvPBoxArena()));
		p.closeInventory();
	}
	
	public static void playerDeath(Player p) {
		try {
			if(p.getWorld().getName().equals(eventWorld)) {
				PvPBoxConfig.addDeath(p);
				try {players.remove(p);}
				catch(Exception ex) {}
			}
		} catch(NullPointerException npe) {}
	}

	public static void playerKill(Player p) {
		try {
			if(p.getWorld().getName().equals(eventWorld)) {
				PvPBoxConfig.addKill(p);
			}
		} catch(NullPointerException npe) {}
	}
}