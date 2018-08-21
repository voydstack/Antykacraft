package fr.voydstack.antykacraft.pvpbox.effect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.voydstack.antykacraft.Antykacraft;
import fr.voydstack.antykacraft.utils.MiscellaneousUtils;

public class Effect {
	private static HashMap<Player, List<Effect>> effects = new HashMap<Player, List<Effect>>();

	private EffectType effectType;

	public Effect(EffectType effectType) {
		this.effectType = effectType;
	}
	
	public EffectType getEffectType() {
		return effectType;
	}

	public void setEffectType(EffectType effectType) {
		this.effectType = effectType;
	}
	
	private void add(Player p) {
		if(effects.containsKey(p)) {
			for(Player pl : effects.keySet()) {
				if(p.equals(pl)) {
					List<Effect> playerEffects = effects.get(pl);
					if(!playerEffects.contains(this)) {
						playerEffects.add(this);
						effects.remove(pl);
						effects.put(pl, playerEffects);
					}
				}
			}
		}
	}

	private void remove(Player p) {
		if(effects.containsKey(p)) {
			for(Player pl : effects.keySet()) {
				if(p.equals(pl)) {
					List<Effect> playerEffects = effects.get(pl);
					if(playerEffects.contains(this)) {
						playerEffects.remove(this);
						effects.remove(pl);
						effects.put(pl, playerEffects);
					}
				}
			}
		}
	}
	
	public void active(Player p, int seconds) {
		this.add(p);
		final Effect instance = this;
		Bukkit.getScheduler().runTaskLater(Antykacraft.instance, new Runnable() {
			public void run() {
				instance.remove(p);
			}
		}, MiscellaneousUtils.calculateTicks(seconds));
	}
	
	public static HashMap<Player, List<Effect>> getEffectsMap() {
		return effects;
	}
	
	public static boolean hasEffects(Player p) {
		return !getPlayerEffects(p).isEmpty();
	}
	
	public static List<Effect> getPlayerEffects(Player p) {
		if(effects.containsKey(p)) {
			return effects.get(p);
		} else {
			return new ArrayList<Effect>();
		}
	}
	
	public static void setPlayerEffects(Player p, List<Effect> newEffects) {
		if(effects.containsKey(p)) effects.remove(p);
		effects.put(p, newEffects);
	}
	
	public static void clearAllEffects(Player p) {
		if(hasEffects(p)) {
			setPlayerEffects(p, new ArrayList<Effect>());
		}
	}
}
