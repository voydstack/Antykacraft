package fr.voydstack.antykacraft.pvpbox.ability;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import fr.voydstack.antykacraft.Antykacraft;
import fr.voydstack.antykacraft.listeners.pvpbox.PvPBoxInteractListener;
import fr.voydstack.antykacraft.pvpbox.PvPBoxCore;
import fr.voydstack.antykacraft.pvpbox.ability.MainAbility.RightAbility;
import fr.voydstack.antykacraft.pvpbox.effect.Effect;
import fr.voydstack.antykacraft.pvpbox.effect.EffectType;
import fr.voydstack.antykacraft.utils.Constants;
import fr.voydstack.antykacraft.utils.MiscellaneousUtils;

public class KitAbility {

	/* Ninja */

	public static RightAbility shuriken() {
		return new RightAbility(6) {
			public void run(final Player p) {
				final Item i = p.getWorld().dropItem(p.getEyeLocation(), new ItemStack(Material.NETHER_STAR));
				i.setPickupDelay(9999);
				i.setVelocity(p.getLocation().getDirection().multiply(2.0D));
				new BukkitRunnable() {
					public void run() {
						for (Entity pl : i.getNearbyEntities(2D, 2D, 2D)) {
							if (pl instanceof Player && !p.equals(pl)) {
								if (!i.isDead()) {
									if (i.getLocation().distanceSquared(pl.getLocation()) <= 1.65D) {
										pl.getWorld().playSound(i.getLocation(), Sound.ENTITY_SILVERFISH_HURT, 1.0F, 1.2F);
										((LivingEntity)pl).damage(7D);
										i.remove();
										cancel();
									}
								} else cancel();
							}
						}
						if(i.isOnGround()) {
							i.remove();
							cancel();
						}
					}
				}.runTaskTimer(Antykacraft.instance, 0L, 1L);
				Bukkit.getScheduler().runTaskLater(Antykacraft.instance, new Runnable() {
					public void run() {
						i.remove();
					}
				}, 60L);
			}
		};
	}

	/* Archer */

	public static RightAbility salve() {
		return new RightAbility(10) {
			public void run(Player p) {
				float ang = 70F;
				for(int i = 0; i < 6; i++) {
					ang += 5;
					Arrow a = (Arrow) p.launchProjectile(Arrow.class);
					a.setShooter(p);
					a.setFireTicks(20);
					a.setBounce(true);
					a.setVelocity(MiscellaneousUtils.setAngle(p, ang, 2D));
				}
			}
		};
	}

	/* Golem */

	public static RightAbility golem() {
		return new RightAbility(6) {
			public void run(final Player p) {				
				final FallingBlock f = p.getWorld().spawnFallingBlock(p.getEyeLocation(), new MaterialData(Material.COBBLESTONE));
				f.setVelocity(p.getLocation().getDirection().multiply(1.75D));
				new BukkitRunnable() {
					public void run() {
						for (Entity pl : f.getNearbyEntities(4D, 4D, 4D)) {
							if (pl instanceof Player && !p.equals(pl)) {
								if (!f.isDead()) {
									if (f.getLocation().distanceSquared(pl.getLocation()) <= 2.5D) {
										((LivingEntity)pl).damage(5D);
										pl.getWorld().playSound(f.getLocation(), Sound.BLOCK_STONE_BREAK, 2.0F, .5F);
										((Player)pl).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 1, 30));
										cancel();
									}
								} else cancel();
							}
						}
					}
				}.runTaskTimer(Antykacraft.instance, 0L, 1L);
				Bukkit.getScheduler().runTaskLater(Antykacraft.instance, new Runnable() {
					public void run() {
						f.remove();
					}
				}, 60L);
			}
		};
	}

	/* Bomberman */

	public static RightAbility bomb() {
		return new RightAbility(3) {
			public void run(final Player p) {
				final FallingBlock tnt = p.getWorld().spawnFallingBlock(p.getEyeLocation(), new MaterialData(Material.TNT));
				tnt.setVelocity(p.getLocation().getDirection().multiply(1.35D));
				tnt.setDropItem(false);
				PvPBoxInteractListener.explosionImmunity.add(p);
				Bukkit.getScheduler().runTaskLater(Antykacraft.instance, new Runnable() {
					public void run() {
						Location l = tnt.getLocation();
						tnt.remove();
						p.getWorld().createExplosion(l.getX(), l.getY(), l.getZ(), 3.5F, false, false);
						Bukkit.getScheduler().runTaskLater(Antykacraft.instance, new Runnable() {
							public void run() {
								PvPBoxInteractListener.explosionImmunity.remove(p);
							}
						}, 10L);
					}
				}, 20L);
			}
		};
	}

	/* Pirate */

	public static RightAbility revolver() {
		return new RightAbility(5) {
			public void run(final Player p) {
				final Item it = p.getWorld().dropItem(p.getEyeLocation(), new ItemStack(Material.COAL_BLOCK));
				it.setPickupDelay(9999);
				p.getWorld().playSound(p.getEyeLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1.0F, 1.0F);
				it.setVelocity(p.getLocation().getDirection().multiply(1.85D));
				new BukkitRunnable() {
					public void run() {
						for (Entity pl : it.getNearbyEntities(2D, 2D, 2D)) {
							if (pl instanceof Player && !p.equals(pl)) {
								if (!it.isDead()) {
									if (it.getLocation().distanceSquared(pl.getLocation()) <= 1.65D) {
										((LivingEntity)pl).damage(8D);
										it.remove();
										cancel();
									}
								} else cancel();
							}
						}
						if(it.isOnGround()) {
							it.remove();
							cancel();
						}
					}
				}.runTaskTimer(Antykacraft.instance, 0L, 1L);
				Bukkit.getScheduler().runTaskLater(Antykacraft.instance, new Runnable() {
					public void run() {
						it.remove();
					}
				}, 60L);
			}
		};
	}

	/* Pêcheur */

	public static RightAbility sharpFish() {
		return new RightAbility(5) {
			public void run(final Player p) {
				final Item i = p.getWorld().dropItem(p.getEyeLocation(), new ItemStack(Material.RAW_FISH));
				i.setPickupDelay(9999);
				i.setVelocity(p.getLocation().getDirection().multiply(1.65D));
				new BukkitRunnable() {
					public void run() {
						for (Entity pl : i.getNearbyEntities(2D, 2D, 2D)) {
							if (pl instanceof Player && !p.equals(pl)) {
								if (!i.isDead()) {
									if (i.getLocation().distanceSquared(pl.getLocation()) <= 1.75D) {
										pl.getWorld().playSound(i.getLocation(), Sound.ENTITY_SLIME_SQUISH, 1.0F, 1.2F);
										((LivingEntity)pl).damage(4D);
										i.remove();
										cancel();
									}
								} else cancel();
							}
						}
						if(i.isOnGround()) {
							p.getWorld().playSound(i.getLocation(), Sound.ENTITY_SLIME_SQUISH, 1.0F, 1.2F);
							i.remove();
							cancel();
						}
					}
				}.runTaskTimer(Antykacraft.instance, 0L, 1L);
				Bukkit.getScheduler().runTaskLater(Antykacraft.instance, new Runnable() {
					public void run() {
						i.remove();
					}
				}, 60L);
			}
		};
	}

	/* Enderman */

	public static RightAbility teleporter() {
		return new RightAbility(5) {
			public void run(final Player p) {
				final Item it = p.getWorld().dropItem(p.getEyeLocation(), new ItemStack(Material.MAGMA_CREAM));
				it.setPickupDelay(9999);
				p.getWorld().playSound(p.getEyeLocation(), Sound.ENTITY_SNOWBALL_THROW, 1.0F, 1.0F);
				it.setVelocity(p.getLocation().getDirection().multiply(2D));
				new BukkitRunnable() {
					public void run() {
						for (Entity pl : it.getNearbyEntities(2D, 2D, 2D)) {
							if (pl instanceof Player && !p.equals(pl)) {
								if (!it.isDead()) {
									if (it.getLocation().distanceSquared(pl.getLocation()) <= 1.75D) {
										((LivingEntity)pl).damage(2D);
										Location a = p.getLocation();
										Location b = pl.getLocation();
										p.teleport(b);
										pl.teleport(a);
										p.getWorld().playSound(p.getEyeLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 1.0F, 1.0F);
										p.getWorld().playSound(((Player)pl).getEyeLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 1.0F, 1.0F);
										it.remove();
										cancel();
									} 
								} else cancel();
							}
						}  
						if(it.isOnGround()) {
							p.teleport(it);
							p.getWorld().playSound(p.getEyeLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 1.0F, 1.0F);
							it.remove();
							cancel();
						}
					}
				}.runTaskTimer(Antykacraft.instance, 0L, 1L);
				Bukkit.getScheduler().runTaskLater(Antykacraft.instance, new Runnable() {
					public void run() {
						it.remove();
					}
				}, 100L);
			}
		};
	}

	/* Chasseur */

	public static RightAbility huntKnife() {
		return new RightAbility(8) {
			public void run(final Player p) {
				final Item i = p.getWorld().dropItem(p.getEyeLocation(), new ItemStack(Material.IRON_HOE));
				i.setPickupDelay(9999);
				i.setVelocity(p.getLocation().getDirection().multiply(2D));
				new BukkitRunnable() {
					public void run() {
						for (Entity pl : i.getNearbyEntities(2D, 2D, 2D)) {
							if (pl instanceof Player && !p.equals(pl)) {
								if (!i.isDead()) {
									if (i.getLocation().distanceSquared(((Player)pl).getEyeLocation()) <= 1D) {
										p.sendMessage(Constants.PVPBOX_PREFIX + "§e§lHEADSHOT sur §c" + pl.getName());
										((LivingEntity)pl).damage(8D);
										i.remove();
										cancel();
									} else {
										if(i.getLocation().distanceSquared(pl.getLocation()) <= 1.65D) {
											((LivingEntity)pl).damage(4D);
											pl.getWorld().playSound(i.getLocation(), Sound.BLOCK_SLIME_BREAK, 1.0F, 1.2F);
											i.remove();
											cancel();
										}
									}
								} else cancel();
							}
						}
						if(i.isOnGround()) {
							i.remove();
							cancel();
						}
					}
				}.runTaskTimer(Antykacraft.instance, 0L, 1L);
				Bukkit.getScheduler().runTaskLater(Antykacraft.instance, new Runnable() {
					public void run() {
						i.remove();
					}
				}, 60L);
			}
		};
	}

	public static RightAbility trap() {
		return new RightAbility(6) {
			public void run(final Player p) {
				final Item i = p.getWorld().dropItem(p.getEyeLocation(), new ItemStack(Material.TRAP_DOOR));
				i.setPickupDelay(9999);
				i.setVelocity(p.getLocation().getDirection());
				new BukkitRunnable() {
					public void run() {
						for (Entity pl : i.getNearbyEntities(4D, 4D, 4D)) {
							if (pl instanceof Player && !p.equals(pl)) {
								if(!i.isDead()) {
									if (MiscellaneousUtils.isOnSameBlock(i, pl)) {
										pl.getWorld().playSound(i.getLocation(), Sound.BLOCK_WOODEN_TRAPDOOR_CLOSE, 1.0F, 1.2F);
										((LivingEntity)pl).damage(4D);
										new Effect(EffectType.STUN).active((Player) pl, 1);
										pl.sendMessage(Constants.PVPBOX_PREFIX + "§c§lSTUN (1s)");
										i.remove();
										cancel();
									}
								}
							} else cancel();
						}
					}
				}.runTaskTimer(Antykacraft.instance, 0L, 1L);
				Bukkit.getScheduler().runTaskLater(Antykacraft.instance, new Runnable() {
					public void run() {
						i.remove();
					}
				}, 100L);
			}
		};
	}

	/* Assassin */

	public static RightAbility razorBlade() {
		return new RightAbility(6) {
			public void run(final Player p) {
				p.removePotionEffect(PotionEffectType.INVISIBILITY);
				new BukkitRunnable() {
					int i = 0;
					float angle = 60F;
					public void run() {
						i++;
						if(i <= 6) {
							final Item it = p.getWorld().dropItem(p.getEyeLocation(), new ItemStack(Material.FEATHER));
							it.setPickupDelay(9999);
							it.setVelocity(MiscellaneousUtils.setAngle(p, angle, 2.5D));
							new BukkitRunnable() {
								public void run() {
									for (Entity pl : it.getNearbyEntities(2D, 2D, 2D)) {
										if (pl instanceof Player && !p.equals(pl)) {
											if (!it.isDead()) {
												if (it.getLocation().distanceSquared(pl.getLocation()) <= 1.75D) {
													pl.getWorld().playSound(it.getLocation(), Sound.BLOCK_SLIME_BREAK, 1.0F, 1.2F);
													((LivingEntity)pl).damage(4D);
													it.remove();
													cancel();
												}
											} else cancel();
										}
									}
									if(it.isOnGround()) {
										p.getWorld().playSound(it.getLocation(), Sound.ITEM_HOE_TILL, 1.0F, 1.2F);
										it.remove();
										cancel();
									}
								}
							}.runTaskTimer(Antykacraft.instance, 0L, 1L);
							angle += 10F;
							Bukkit.getScheduler().runTaskLater(Antykacraft.instance, new Runnable() {
								public void run() {
									it.remove();
								}
							}, 100L);
						}
					}
				}.runTaskTimer(Antykacraft.instance, 0L, 2L);
			}
		};
	}

	public static RightAbility invisibility() {
		return new RightAbility(8) {
			public void run(final Player p) {
				p.sendMessage(Constants.PVPBOX_PREFIX + "§eLa poudre fera effet dans 2.5s");
				Bukkit.getScheduler().runTaskLater(Antykacraft.instance, new Runnable() {
					public void run() {
						if(PvPBoxCore.players.containsKey(p)) {
							p.playSound(p.getLocation(), Sound.BLOCK_CLOTH_BREAK, 1.0F, 1.2F);							
							p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, MiscellaneousUtils.calculateTicks(15), 1));
						}
					}
				}, 50L);
			}
		};
	}
}
