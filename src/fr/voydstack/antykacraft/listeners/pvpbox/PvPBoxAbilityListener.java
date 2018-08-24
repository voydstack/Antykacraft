package fr.voydstack.antykacraft.listeners.pvpbox;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;

import fr.voydstack.antykacraft.Antykacraft;
import fr.voydstack.antykacraft.pvpbox.PvPBoxCore;
import fr.voydstack.antykacraft.pvpbox.ability.MainAbility;
import fr.voydstack.antykacraft.pvpbox.ability.MainAbility.HitAbility;
import fr.voydstack.antykacraft.pvpbox.ability.MainAbility.LeftAbility;
import fr.voydstack.antykacraft.pvpbox.ability.MainAbility.RightAbility;
import fr.voydstack.antykacraft.pvpbox.kit.Kit;
import fr.voydstack.antykacraft.pvpbox.runnable.PvPBoxCooldownRunnable;
import fr.voydstack.antykacraft.utils.Constants;

public class PvPBoxAbilityListener implements Listener {
	@EventHandler
	public void rightAbility(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(p.getWorld().getName().equals(PvPBoxCore.eventWorld)) {
				if(PvPBoxCore.players.containsKey(p)) {
					try {
						ItemStack iHand = e.getItem();
						Kit k = PvPBoxCore.players.get(p);
						if(k.abilities.get(iHand) instanceof RightAbility) {
							RightAbility ability = (RightAbility)k.abilities.get(iHand);
							if(ability.getTime() == 0L && !ability.isBusy()) { // Si le sort est disponible
								ability.initCooldown();
								ability.run(p);
								ability.setBusy(true);
								new PvPBoxCooldownRunnable(p, iHand).runTaskTimer(Antykacraft.instance, 0L, 1L);
							} else {
								p.sendMessage(Constants.PVPBOX_PREFIX + "§eLe sort est en cours de rechargement ... (§l"+ability.getTimeSeconds()+"§r§es)");
							}
						}
					} catch(NullPointerException npe) {}
				}
			}
		}
	}

	@EventHandler
	public void leftAbility(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if(e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
			if(p.getWorld().getName().equals(PvPBoxCore.eventWorld)) {
				if(PvPBoxCore.players.containsKey(p)) {
					try {
						ItemStack iHand = e.getItem();
						Kit k = PvPBoxCore.players.get(p);
						if(k.abilities.get(iHand) instanceof LeftAbility) {
							LeftAbility ability = (LeftAbility)k.abilities.get(iHand);
							if(ability.getTime() == 0L && !ability.isBusy()) { // Si le sort est disponible
								ability.initCooldown();
								ability.run(p);
								ability.setBusy(true);
								new PvPBoxCooldownRunnable(p, iHand).runTaskTimer(Antykacraft.instance, 0L, 1L);
							} else {
								p.sendMessage(Constants.PVPBOX_PREFIX + "§eLe sort est en cours de rechargement ... (§l"+ability.getTimeSeconds()+"§r§es)");
							}
						}
					} catch(NullPointerException npe) {}
				}
			}
		}
	}

	@EventHandler
	public void hitAbility(EntityDamageByEntityEvent e) {
		if(e.getEntity() instanceof Player && e.getEntity() instanceof Player) {
			Player entity = (Player) e.getEntity();
			Player damager = (Player) e.getDamager();
			if(entity.getWorld().getName().equalsIgnoreCase(PvPBoxCore.eventWorld) && 
					damager.getWorld().getName().equalsIgnoreCase(PvPBoxCore.eventWorld)) {
				if(PvPBoxCore.players.containsKey(damager) && PvPBoxCore.players.containsKey(entity)) {
					try {
						ItemStack iHand = damager.getInventory().getContents()[damager.getInventory().getHeldItemSlot()];
						Kit k = PvPBoxCore.players.get(damager);
						if(k.abilities.get(iHand) instanceof HitAbility) {
							HitAbility ability = (HitAbility) k.abilities.get(iHand);
							if(ability.getTime() == 0L && !ability.isBusy()) {
								ability.initCooldown();
								ability.run(damager, entity);
								ability.setBusy(true);
								new PvPBoxCooldownRunnable(damager, iHand).runTaskTimer(Antykacraft.instance, 0L, 1L);
							}
						}
					} catch(NullPointerException npe) {}
				}
			}
		}
	}

	@EventHandler
	public void playerHeldItem(PlayerItemHeldEvent e) {
		if(e.getPlayer().getWorld().getName().equalsIgnoreCase(PvPBoxCore.eventWorld)) {
			Player p = e.getPlayer();
			if(PvPBoxCore.players.containsKey(p)) {
				ItemStack heldItem = p.getInventory().getContents()[e.getNewSlot()];
				if(PvPBoxCore.players.get(p).abilities.containsKey(heldItem)) {
					MainAbility ability = PvPBoxCore.players.get(p).abilities.get(heldItem);
					if(ability.getTimeSeconds() > 0 && ability.isBusy()) {
						new PvPBoxCooldownRunnable.WatchRunnable(p, heldItem).runTaskTimer(Antykacraft.instance, 0L, 1L);
					} else {
						p.setLevel(0);
					}
				} else {
					p.setLevel(0);
				}
			}
		}
	}
}
