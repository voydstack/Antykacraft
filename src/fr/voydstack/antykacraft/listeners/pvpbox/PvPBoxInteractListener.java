package fr.voydstack.antykacraft.listeners.pvpbox;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupArrowEvent;

import fr.voydstack.antykacraft.pvpbox.PvPBoxCore;
import fr.voydstack.antykacraft.pvpbox.effect.Effect;
import fr.voydstack.antykacraft.pvpbox.effect.EffectType;
import fr.voydstack.antykacraft.utils.MiscellaneousUtils;

public class PvPBoxInteractListener implements Listener {
	private List<Material> allowClick = new ArrayList<Material>();
	public static List<Player> explosionImmunity = new ArrayList<Player>();
	
	public PvPBoxInteractListener() {
		allowClick.add(Material.STONE_BUTTON);
		allowClick.add(Material.WOOD_BUTTON);
		allowClick.add(Material.TRAP_DOOR);
		allowClick.add(Material.ACACIA_DOOR);
		allowClick.add(Material.BIRCH_DOOR);
		allowClick.add(Material.DARK_OAK_DOOR);
		allowClick.add(Material.JUNGLE_DOOR);
		allowClick.add(Material.SPRUCE_DOOR);
	}
	
	@EventHandler
	public void breakBlock(BlockBreakEvent e) {
		Player p = e.getPlayer();
		if(p.getWorld().getName().equals(PvPBoxCore.eventWorld)) {
			if(!p.hasPermission("antykacraft.event.break"))
				e.setCancelled(true);
			if(PvPBoxCore.players.containsKey(p))
				e.setCancelled(true);
		}
	}

	@EventHandler
	public void placeBlock(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		if(p.getWorld().getName().equals(PvPBoxCore.eventWorld)) {
			if(PvPBoxCore.players.containsKey(p))
				e.setCancelled(true);
			if(!p.hasPermission("antykacraft.event.break"))
				e.setCancelled(true);
		}
	}

	@EventHandler
	public void playerInteract(PlayerInteractEvent e) {
		try {
			Player p = e.getPlayer();
			if(p.getWorld().getName().equals(PvPBoxCore.eventWorld)) {
				if(PvPBoxCore.players.containsKey(p))
					if(!allowClick.contains(e.getClickedBlock().getType()))
						e.setCancelled(true);
				if(p.getInventory().getItemInMainHand().getType() == Material.POTION)
					e.setCancelled(false);
			}
		} catch(NullPointerException ex) {}
	}

	@EventHandler
	public void playerInteractEntity(PlayerInteractEntityEvent e) {
		if(PvPBoxCore.players.containsKey(e.getPlayer())) {
			if(e.getPlayer().getWorld().getName().equals(PvPBoxCore.eventWorld)) {
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void dropItem(PlayerDropItemEvent e) {
		if(PvPBoxCore.players.containsKey(e.getPlayer()))
			e.setCancelled(true);
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void playerPickupArrow(PlayerPickupArrowEvent e) {
		Player p = e.getPlayer();
		if(p.getWorld().getName().equalsIgnoreCase(PvPBoxCore.eventWorld)) {
			if(PvPBoxCore.players.containsKey(p)) {
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void fallingblock(EntityChangeBlockEvent e) {
		if(e.getEntity().getWorld().getName().equals(PvPBoxCore.eventWorld)) {
			if(e.getEntity() instanceof FallingBlock) {
				FallingBlock b = (FallingBlock) e.getEntity();
				if(b.getMaterial().equals(Material.COBBLESTONE)) {
					e.setCancelled(true);
				} else if(b.getMaterial().equals(Material.TNT)) {
					e.setCancelled(true);
				}
			}
		}
	}

	@EventHandler
	public void playerDamage(EntityDamageEvent e) {
		if(e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if(p.getWorld().getName().equals(PvPBoxCore.eventWorld)) {
				if(PvPBoxCore.players.containsKey(p)) {
					if(e.getCause() == DamageCause.BLOCK_EXPLOSION) {
						if(explosionImmunity.contains(p)) {
							e.setCancelled(true);
						} else {
							e.setDamage(6D);
						}
					} else if(e.getCause() == DamageCause.SUFFOCATION) {
						e.setCancelled(true);
						e.setDamage(0D);
					}
				}
			}
		}
	}
	
	@EventHandler
	public void snowballHitPlayer(EntityDamageByEntityEvent e) {
		if(e.getEntity() instanceof Player && e.getDamager() instanceof Snowball) {
			Player p = (Player) e.getEntity();
			if(p.getWorld().getName().equalsIgnoreCase(PvPBoxCore.eventWorld)) {
				if(PvPBoxCore.players.containsKey(p)) {
					e.setDamage(1D);
					e.setCancelled(false);
				}
			}
		}
	}

	@EventHandler
	public void fisherman(PlayerFishEvent e) {
		if(PvPBoxCore.players.containsKey(e.getPlayer())) {
			if(e.getCaught() == null) {
				List<Entity> nearby = e.getPlayer().getNearbyEntities(100,100,100);
				for (Entity en : nearby) {
					if (en.getType() == EntityType.FISHING_HOOK) {
						if(en.isOnGround()) {
							Location player = e.getPlayer().getLocation();
							Location hook = en.getLocation();
							e.getPlayer().setVelocity(MiscellaneousUtils.getVectorToLocationFast(player, hook, 2.75D));
							break;
						}
					}
				}
			} else {
				if(e.getCaught() instanceof Player) {
					if(e.getCaught() != e.getPlayer()) {
						Player fish = (Player) e.getCaught();
						if(PvPBoxCore.players.containsKey(fish)) {
							fish.setVelocity(MiscellaneousUtils.getVectorToLocationFast(fish.getLocation(), e.getPlayer().getLocation(), 2D));
						}
					}
				}
			}
		}
	}

	@EventHandler
	public void stunMoveCheck(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if(PvPBoxCore.players.containsKey(p)) {
			for(Effect effect : Effect.getPlayerEffects(p)) {
				if(effect.getEffectType() == EffectType.STUN) {
					e.setCancelled(true);
					e.setTo(e.getFrom());
				}
			}
		}
	}

	@EventHandler
	public void stunAttackCheck(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if(PvPBoxCore.players.containsKey(p)) {
			for(Effect effect : Effect.getPlayerEffects(p)) {
				if(effect.getEffectType() == EffectType.STUN) {
					e.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler
	public void itemDurability(PlayerItemDamageEvent e) {
		Player p = e.getPlayer();
		if(p.getWorld().getName().equalsIgnoreCase(PvPBoxCore.eventWorld)) {
			if(PvPBoxCore.players.containsKey(p)) {
				e.setDamage(0);
				e.setCancelled(true);
			}
		}
	}
}
