package fr.voydstack.antykacraft.pvpbox.runnable;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import fr.voydstack.antykacraft.pvpbox.PvPBoxCore;
import fr.voydstack.antykacraft.pvpbox.ability.MainAbility;
import fr.voydstack.antykacraft.utils.Constants;

public class PvPBoxCooldownRunnable extends BukkitRunnable {
	protected Player player;
	protected ItemStack trigger;
	protected MainAbility ability = null;

	public PvPBoxCooldownRunnable(Player player, ItemStack trigger) {
		this.player = player;
		this.trigger = trigger;
		if(PvPBoxCore.players.get(player).abilities.containsKey(trigger)) {
			this.ability = PvPBoxCore.players.get(player).abilities.get(trigger);
		}
	}

	public ItemStack getTrigger() {
		return trigger;
	}

	public void setTrigger(ItemStack trigger) {
		this.trigger = trigger;
	}

	public MainAbility getAbility() {
		return ability;
	}

	public void setAbility(MainAbility ability) {
		this.ability = ability;
	}

	@Override
	public void run() {
		try {
			if(player.getInventory().getContents()[player.getInventory().getHeldItemSlot()].isSimilar(trigger)) {
				player.setLevel(ability.getTimeSeconds());
			}
		} catch(NullPointerException npe) {
			player.setLevel(0);
		} finally {
			if(ability.getTime() > 0L) {
				ability.removeTime(1L);
			} else {
				player.playSound(player.getEyeLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
				player.sendMessage(Constants.PVPBOX_PREFIX + "§aLe sort \"§l"+trigger.getItemMeta().getDisplayName()+"§r§a\" est prêt!");
				ability.setBusy(false);
				cancel();
			}
		}
	}

	public static class WatchRunnable extends PvPBoxCooldownRunnable {
		public WatchRunnable(Player player, ItemStack trigger) {
			super(player, trigger);
		}
		
		@Override
		public void run() {
			try {
				if(player.getInventory().getContents()[player.getInventory().getHeldItemSlot()].isSimilar(trigger)) {
					player.setLevel(ability.getTimeSeconds());
				}
			} catch(NullPointerException npe) {
				player.setLevel(0);
			}
		}
	}
}
