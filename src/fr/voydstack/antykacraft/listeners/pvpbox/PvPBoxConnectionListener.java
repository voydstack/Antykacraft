package fr.voydstack.antykacraft.listeners.pvpbox;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;

import fr.voydstack.antykacraft.Antykacraft;
import fr.voydstack.antykacraft.pvpbox.PvPBoxCore;

public class PvPBoxConnectionListener implements Listener {
	@EventHandler
	public void playerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if(p.getWorld().getName().equalsIgnoreCase(Antykacraft.configHandler.getEventWorldName())) {
			if(PvPBoxCore.players.containsKey(p)) PvPBoxCore.players.remove(p);
			p.getInventory().clear();
			p.getInventory().setArmorContents(null);
			p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20D);
			for(PotionEffect ef : p.getActivePotionEffects())
				p.removePotionEffect(ef.getType());
			p.teleport(Bukkit.getWorld(PvPBoxCore.eventWorld).getSpawnLocation());
		} 
	}

	@EventHandler
	public void playerQuit(PlayerQuitEvent e) {
		if(PvPBoxCore.players.containsKey(e.getPlayer())) {
			PvPBoxCore.players.remove(e.getPlayer());
		}
	}
}
