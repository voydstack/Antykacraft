package fr.voydstack.antykacraft.listeners.pvpbox;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import fr.voydstack.antykacraft.Antykacraft;
import fr.voydstack.antykacraft.pvpbox.PvPBoxCore;
import fr.voydstack.antykacraft.pvpbox.ability.MainAbility;
import fr.voydstack.antykacraft.pvpbox.ability.MainAbility.HitAbility;
import fr.voydstack.antykacraft.pvpbox.ability.MainAbility.RightAbility;
import fr.voydstack.antykacraft.pvpbox.kit.Kit;
import fr.voydstack.antykacraft.utils.Constants;

public class PvPBoxAbilityListener implements Listener {
	@EventHandler
	public void rightAbility(PlayerInteractEvent e) {
		final Player p = e.getPlayer();
		if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(p.getWorld().getName().equals(PvPBoxCore.eventWorld)) {
				if(PvPBoxCore.players.containsKey(p)) {
					try {
						ItemStack iHand = e.getItem();
						Kit k = PvPBoxCore.players.get(p);
						final RightAbility ability = (RightAbility)k.rightAbilities.get(iHand);
						if(PvPBoxCore.cooldowns.containsKey(p)) {
							List<MainAbility> cooldowns = PvPBoxCore.cooldowns.get(p);
							if(!cooldowns.contains(ability)) {
								ability.run(p);
								cooldowns.add(ability);
								PvPBoxCore.cooldowns.remove(p);
								PvPBoxCore.cooldowns.put(p, cooldowns);
								Bukkit.getScheduler().runTaskLater(Antykacraft.instance, new Runnable() {
									public void run() {
										List<MainAbility> abilities = PvPBoxCore.cooldowns.get(p);
										abilities.remove(ability);
										PvPBoxCore.cooldowns.remove(p);
										PvPBoxCore.cooldowns.put(p, abilities);
									}
								}, k.rightAbilities.get(iHand).getCooldown());
							} else {
								p.sendMessage(Constants.PVPBOX_PREFIX + "§eSort en cours de rechargement");
							}
						} else {
							ability.run(p);
							List<MainAbility> cooldowns = new ArrayList<MainAbility>();
							cooldowns.add(ability);
							PvPBoxCore.cooldowns.put(p, cooldowns);
							Bukkit.getScheduler().runTaskLater(Antykacraft.instance, new Runnable() {
								public void run() {
									List<MainAbility> abilities = PvPBoxCore.cooldowns.get(p);
									abilities.remove(ability);
									PvPBoxCore.cooldowns.remove(p);
									PvPBoxCore.cooldowns.put(p, abilities);
								}
							}, k.rightAbilities.get(iHand).getCooldown());
						}
					} catch(NullPointerException ex) {}
				}
			}
		}
	}

	@EventHandler
	public void hitAbility(EntityDamageByEntityEvent e) {
		if(e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
			final Player p = (Player)e.getDamager();
			final Player d = (Player)e.getEntity();
			if(PvPBoxCore.players.containsKey(p) && PvPBoxCore.players.containsKey(d)) {
				try {
					ItemStack iHand = p.getInventory().getItemInMainHand();
					Kit k = PvPBoxCore.players.get(p);
					final HitAbility ability = (HitAbility)k.hitAbilities.get(iHand);
					if(PvPBoxCore.cooldowns.containsKey(p)) {
						List<MainAbility> cooldowns = PvPBoxCore.cooldowns.get(p);
						if(!cooldowns.contains(ability)) {
							ability.run(p, d, e.getDamage());
							cooldowns.add(ability);
							PvPBoxCore.cooldowns.remove(p);
							PvPBoxCore.cooldowns.put(p, cooldowns);
							Bukkit.getScheduler().scheduleSyncDelayedTask(Antykacraft.instance, new Runnable() {
								public void run() {
									List<MainAbility> abilities = PvPBoxCore.cooldowns.get(p);
									abilities.remove(ability);
									PvPBoxCore.cooldowns.remove(p);
									PvPBoxCore.cooldowns.put(p, abilities);
								}
							}, k.hitAbilities.get(iHand).getCooldown());
						} else {
							p.sendMessage(Constants.PVPBOX_PREFIX + "§eSort en cours de rechargement");
						}
					} else {
						List<MainAbility> cooldowns = new ArrayList<MainAbility>();
						ability.run(p, d, e.getDamage());
						cooldowns.add(ability);
						PvPBoxCore.cooldowns.put(p, cooldowns);
						Bukkit.getScheduler().scheduleSyncDelayedTask(Antykacraft.instance, new Runnable() {
							public void run() {
								List<MainAbility> abilities = PvPBoxCore.cooldowns.get(p);
								abilities.remove(ability);
								PvPBoxCore.cooldowns.remove(p);
								PvPBoxCore.cooldowns.put(p, abilities);
							}
						}, k.hitAbilities.get(iHand).getCooldown());
					}
				} catch(Exception ex) {}
				finally {
					if(PvPBoxCore.players.get(p).getName() == "Assassin") {
						if(p.getPotionEffect(PotionEffectType.INVISIBILITY) != null) {
							e.setDamage(e.getDamage() + 4D);
							p.removePotionEffect(PotionEffectType.INVISIBILITY);
						}
					}
				}
			}
		}
	}
}
