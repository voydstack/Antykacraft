package fr.voydstack.antykacraft.listeners.pvpbox;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.potion.PotionEffect;

import fr.voydstack.antykacraft.Antykacraft;
import fr.voydstack.antykacraft.config.pvpbox.PvPBoxConfig;
import fr.voydstack.antykacraft.pvpbox.PvPBoxCore;
import fr.voydstack.antykacraft.pvpbox.kit.Kit;

public class PvPBoxPlayerStatusListener implements Listener {
	@EventHandler
	public void changeWorld(PlayerChangedWorldEvent e) {
		final Player p = e.getPlayer();
		if(p.getWorld().getName().equals(PvPBoxCore.eventWorld)) {
			p.getInventory().clear();
			p.getInventory().setArmorContents(null);
			for(PotionEffect ef : p.getActivePotionEffects())
				p.removePotionEffect(ef.getType());
			p.setLevel(0);
			p.setExp(0F);
		}
		try{PvPBoxCore.players.remove(p);}
		catch(Exception ex){};
		p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20D);
		p.setScoreboard(Antykacraft.scoreboardHandler.getScoreboardManager().getMainScoreboard());
	}

	@EventHandler
	public void playerDeath(PlayerDeathEvent e) {
		if(e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if(p.getWorld().getName().equals(PvPBoxCore.eventWorld)) {
				if(PvPBoxCore.players.containsKey(p)) {
					PvPBoxCore.playerDeath(p);	
					p.teleport(PvPBoxConfig.getLobbyLocation());
					try {
						PvPBoxCore.playerKill(p.getKiller());
					} catch(NullPointerException npe) {}
					finally {
						String deathMessage = e.getDeathMessage();
						e.setDeathMessage("");
						for(Player pl : Bukkit.getWorld(PvPBoxCore.eventWorld).getPlayers())
							pl.sendMessage(deathMessage);
						Kit.resetPvPBoxPlayer(p);
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerLossFood(FoodLevelChangeEvent e) {
		if ((e.getEntity() instanceof Player)) {
			Player p = (Player)e.getEntity();
			if (p.getWorld().getName().equalsIgnoreCase(PvPBoxCore.eventWorld))
				e.setCancelled(true);
		}
	}
}
