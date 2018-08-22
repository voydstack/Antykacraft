package fr.voydstack.antykacraft.pvpbox.kit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import fr.voydstack.antykacraft.config.pvpbox.PvPBoxConfig;
import fr.voydstack.antykacraft.pvpbox.PvPBoxCore;
import fr.voydstack.antykacraft.pvpbox.ability.KitAbility;
import fr.voydstack.antykacraft.pvpbox.ability.MainAbility;
import fr.voydstack.antykacraft.pvpbox.gui.PvPBoxKitIcon;
import fr.voydstack.antykacraft.utils.MiscellaneousUtils;
import fr.voydstack.libs.ItemLib;

public class Kit {
	public static List<Kit> kits = new ArrayList<Kit>();
	public static List<Kit> specialKits = new ArrayList<Kit>();
	public static boolean debug = false;
	private PotionEffect[] effects;
	private ItemStack[] armor, items;
	private ItemStack displayItem;
	private String name;
	private double maxLife;
	private byte attack, health, magic, difficulty;
	public HashMap<ItemStack, MainAbility> rightAbilities = new HashMap<ItemStack, MainAbility>();
	public HashMap<ItemStack, MainAbility> leftAbilities = new HashMap<ItemStack, MainAbility>();
	public HashMap<ItemStack, MainAbility> hitAbilities = new HashMap<ItemStack, MainAbility>();

	public Kit(String name, ItemStack[] items, ItemStack[] armor, PotionEffect[] effects, ItemStack displayItem, byte attack, byte health, byte magic, byte difficulty) {
		this.setName(name);
		this.setItems(items);
		this.setArmor(armor);
		this.setEffects(effects);
		this.setMaxLife(20D);
		this.setDisplayItem(displayItem);
		this.setDescAttack(attack);
		this.setDescHealth(health);
		this.setDescMagic(magic);
		this.setDescDifficulty(difficulty);
	}

	public Kit(String name, ItemStack[] items, ItemStack[] armor, PotionEffect[] effects, double maxLife, ItemStack displayItem, byte attack, byte health, byte magic, byte difficulty) {
		this.setName(name);
		this.setItems(items);
		this.setArmor(armor);
		this.setEffects(effects);
		this.setMaxLife(maxLife);
		this.setDisplayItem(displayItem);
		this.setDescAttack(attack);
		this.setDescHealth(health);
		this.setDescMagic(magic);
		this.setDescDifficulty(difficulty);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ItemStack[] getItems() {
		return items;
	}

	public void setItems(ItemStack[] items) {
		this.items = items;
	}

	public ItemStack[] getArmor() {
		return armor;
	}

	public void setArmor(ItemStack[] armor) {
		this.armor = armor;
	}

	public PotionEffect[] getEffects() {
		return effects;
	}

	public void setEffects(PotionEffect[] effects) {
		this.effects = effects;
	}

	public ItemStack getDisplayItem() {
		return displayItem;
	}

	public void setDisplayItem(ItemStack displayItem) {
		this.displayItem = displayItem;
	}

	public double getMaxLife() {
		return maxLife;
	}

	public void setMaxLife(double maxLife) {
		this.maxLife = maxLife;
	}

	public byte getDescAttack() {
		return this.attack;
	}

	public void setDescAttack(byte attack) {
		this.attack = attack;
	}

	public byte getDescHealth() {
		return this.health;
	}

	public void setDescHealth(byte health) {
		this.health = health;
	}

	public byte getDescMagic() {
		return this.magic;
	}

	public void setDescMagic(byte magic) {
		this.magic = magic;
	}

	public byte getDescDifficulty() {
		return this.difficulty;
	}

	public void setDescDifficulty(byte difficulty) {
		this.difficulty = difficulty;
	}

	public static void init() {
		ItemStack[] items, armor;
		ItemStack head;
		PotionEffect[] effects = new PotionEffect[] {};

		/* Guerrier */

		items = new ItemStack[] {new ItemStack(Material.IRON_SWORD)};
		head = ItemLib.addEnchantments(ItemLib.createItem(Material.SKULL_ITEM, 1, (byte) 2, "Warrior Head", null), 
				new Enchantment[] {Enchantment.PROTECTION_ENVIRONMENTAL}, new int[] {1});
		armor = new ItemStack[] {new ItemStack(Material.GOLD_BOOTS), new ItemStack(Material.IRON_LEGGINGS),
				new ItemStack(Material.IRON_CHESTPLATE), head};
		Kit warrior = new Kit("Guerrier", items, armor, effects, PvPBoxKitIcon.warriorKit, (byte)6, (byte)6, (byte)1, (byte)3);
		kits.add(warrior);

		/* Archer */

		items = new ItemStack[] {new ItemStack(Material.WOOD_SWORD), 
				ItemLib.addEnchantments(new ItemStack(Material.BOW), new Enchantment[] {Enchantment.ARROW_INFINITE, Enchantment.DURABILITY}, new int[] {1, 127}),
				ItemLib.addDisplayName(new ItemStack(Material.ARROW), "§aSalve")};
		head = ItemLib.addEnchantments(ItemLib.createItem(Material.SKULL_ITEM, 1, (byte) 0, "Archer Head", null),
				new Enchantment[] {Enchantment.PROTECTION_ENVIRONMENTAL}, new int[] {1});
		armor = new ItemStack[] {ItemLib.addEnchantments(new ItemStack(Material.GOLD_BOOTS), new Enchantment[] {Enchantment.DURABILITY}, new int[] {127}),
				ItemLib.addEnchantments(new ItemStack(Material.GOLD_LEGGINGS), new Enchantment[] {Enchantment.DURABILITY}, new int[] {127}),
				ItemLib.addEnchantments(new ItemStack(Material.GOLD_CHESTPLATE), new Enchantment[] {Enchantment.DURABILITY}, new int[] {127}),
				head};
		Kit archer = new Kit("Archer", items, armor, effects, PvPBoxKitIcon.archerKit, (byte)7, (byte)4, (byte)3, (byte)5);
		archer.rightAbilities.put(items[2], KitAbility.salve());
		kits.add(archer);

		/* Reaper */

		items = new ItemStack[] {ItemLib.addEnchantments(ItemLib.createItem(Material.IRON_HOE, 1, (short) 0, "§8Faux", null), new Enchantment[] {Enchantment.DAMAGE_ALL}, new int[] {4}),
				ItemLib.createPotion(PotionType.STRENGTH, false, true, 1), ItemLib.createSplashPotion(PotionType.POISON, false, true, 1)};
		head = ItemLib.createItem(Material.SKULL_ITEM, 1, (byte) 1, "Reaper Head", null);
		armor = new ItemStack[] {ItemLib.addEnchantments(ItemLib.colorLeatherArmor(new ItemStack(Material.LEATHER_BOOTS), Color.BLACK), new Enchantment[] {Enchantment.PROTECTION_ENVIRONMENTAL}, new int[] {1}),
				ItemLib.colorLeatherArmor(new ItemStack(Material.LEATHER_LEGGINGS), Color.BLACK), new ItemStack(Material.DIAMOND_CHESTPLATE), head};
		Kit reaper = new Kit("Reaper", items, armor, effects, PvPBoxKitIcon.reaperKit, (byte)6, (byte)3, (byte)4, (byte)5);
		kits.add(reaper);

		/* Wizard */

		items = new ItemStack[] {new ItemStack(Material.IRON_SPADE), ItemLib.createPotion(PotionType.STRENGTH, false, false, 1), ItemLib.createPotion(PotionType.SPEED, true, false, 1),
				ItemLib.createPotion(PotionType.INSTANT_HEAL, false, true, 1), ItemLib.createPotion(PotionType.REGEN, false, true, 1)};
		head = ItemLib.addEnchantments(ItemLib.createPlayerHead("MHF_Villager", "Wizard Head"), new Enchantment[] {Enchantment.PROTECTION_ENVIRONMENTAL}, new int[] {1});
		armor = new ItemStack[] {ItemLib.colorLeatherArmor(new ItemStack(Material.LEATHER_BOOTS), Color.YELLOW),
				ItemLib.colorLeatherArmor(new ItemStack(Material.LEATHER_LEGGINGS), Color.YELLOW), 
				ItemLib.colorLeatherArmor(new ItemStack(Material.LEATHER_CHESTPLATE), Color.YELLOW), head};
		Kit wizard = new Kit("Wizard", items, armor, effects, PvPBoxKitIcon.wizardKit, (byte)2, (byte)4, (byte)8, (byte)6);
		kits.add(wizard);

		/* Trolleur */

		ItemStack trollerStick = ItemLib.addEnchantments(ItemLib.createItem(Material.STICK, 1, (short) 0, "§dTroller Stick", null), 
				new Enchantment[] {Enchantment.KNOCKBACK, Enchantment.DAMAGE_ALL}, new int[] {3, 2});
		items = new ItemStack[] {new ItemStack(Material.SNOW_BALL, 64), new ItemStack(Material.SNOW_BALL, 64), trollerStick, 
				ItemLib.createSplashPotion(PotionType.WEAKNESS, false, false, 2)};
		head = ItemLib.addEnchantments(ItemLib.createItem(Material.SKULL_ITEM, 1, (short) 3, "Troller Head", null), 
				new Enchantment[] {Enchantment.PROTECTION_ENVIRONMENTAL}, new int[] {1});
		armor = new ItemStack[] {ItemLib.colorLeatherArmor(new ItemStack(Material.LEATHER_BOOTS), Color.BLACK),
				ItemLib.colorLeatherArmor(new ItemStack(Material.LEATHER_LEGGINGS), Color.BLACK), 
				ItemLib.colorLeatherArmor(new ItemStack(Material.LEATHER_CHESTPLATE), Color.BLACK), head};
		Kit troller = new Kit("Trolleur", items, armor, effects, PvPBoxKitIcon.trollerKit, (byte)1, (byte)3, (byte)6, (byte)8);
		kits.add(troller);

		/* Ninja */

		items = new ItemStack[] {ItemLib.addEnchantments(ItemLib.createItem(Material.STONE_SWORD, 1, (short) 0, "Katana", null), 
				new Enchantment[] {Enchantment.DAMAGE_ALL, Enchantment.KNOCKBACK}, new int[] {1, 1}),
				ItemLib.addDisplayName(new ItemStack(Material.NETHER_STAR, 1), "Shuriken")};
		armor = new ItemStack[] {ItemLib.colorLeatherArmor(new ItemStack(Material.LEATHER_BOOTS), Color.BLACK),
				ItemLib.colorLeatherArmor(new ItemStack(Material.LEATHER_LEGGINGS), Color.BLACK), 
				ItemLib.colorLeatherArmor(new ItemStack(Material.LEATHER_CHESTPLATE), Color.BLACK),
				ItemLib.colorLeatherArmor(new ItemStack(Material.LEATHER_HELMET), Color.BLACK)};
		effects = new PotionEffect[] {new PotionEffect(PotionEffectType.SPEED, (int) MiscellaneousUtils.calculateTicks(3600), 0)};
		Kit ninja = new Kit("Ninja", items, armor, effects, PvPBoxKitIcon.ninjaKit, (byte)7, (byte)4, (byte)4, (byte)7);
		ninja.rightAbilities.put(items[1], KitAbility.shuriken());
		kits.add(ninja);

		/* Tank */

		items = new ItemStack[] {ItemLib.createItem(Material.GOLD_AXE, 1, (short) 0, ChatColor.GOLD + "Hache de bourrin", null)};
		armor = new ItemStack[] {new ItemStack(Material.DIAMOND_BOOTS), 
				new ItemStack(Material.DIAMOND_LEGGINGS), 
				new ItemStack(Material.DIAMOND_CHESTPLATE), 
				ItemLib.colorLeatherArmor(new ItemStack(Material.LEATHER_HELMET), Color.BLUE)};
		effects = new PotionEffect[] {new PotionEffect(PotionEffectType.SLOW, (int) MiscellaneousUtils.calculateTicks(3600), 1),
				new PotionEffect(PotionEffectType.WEAKNESS, (int) MiscellaneousUtils.calculateTicks(3600), 0)};
		Kit tank = new Kit("Tank", items, armor, effects, 32D, PvPBoxKitIcon.tankKit, (byte)2, (byte)8, (byte)1, (byte)2);
		kits.add(tank);

		/* Rabbit */

		items = new ItemStack[] {ItemLib.addEnchantments(ItemLib.createItem(Material.CARROT_ITEM, 1, (short) 0, "§cFire §6Carrot", null), 
				new Enchantment[] {Enchantment.DAMAGE_ALL, Enchantment.FIRE_ASPECT}, new int[] {2, 2})};
		armor = new ItemStack[] {ItemLib.addEnchantments(ItemLib.colorLeatherArmor(new ItemStack(Material.LEATHER_BOOTS), Color.WHITE), 
				new Enchantment[] {Enchantment.PROTECTION_ENVIRONMENTAL}, new int[] {1}),
				ItemLib.addEnchantments(ItemLib.colorLeatherArmor(new ItemStack(Material.LEATHER_LEGGINGS), Color.WHITE), 
						new Enchantment[] {Enchantment.PROTECTION_ENVIRONMENTAL}, new int[] {1}), 
				ItemLib.addEnchantments(ItemLib.colorLeatherArmor(new ItemStack(Material.LEATHER_CHESTPLATE), Color.WHITE), 
						new Enchantment[] {Enchantment.PROTECTION_ENVIRONMENTAL}, new int[] {1}),
				ItemLib.addEnchantments(ItemLib.colorLeatherArmor(new ItemStack(Material.LEATHER_HELMET), Color.WHITE), 
						new Enchantment[] {Enchantment.PROTECTION_ENVIRONMENTAL}, new int[] {1})};
		effects = new PotionEffect[] {new PotionEffect(PotionEffectType.JUMP, (int) MiscellaneousUtils.calculateTicks(3600), 2),
				new PotionEffect(PotionEffectType.SPEED, (int) MiscellaneousUtils.calculateTicks(3600), 2)};
		Kit rabbit = new Kit("Rabbit", items, armor, effects, PvPBoxKitIcon.rabbitKit, (byte)4, (byte)4, (byte)4, (byte)6);
		kits.add(rabbit);

		/* Ghost */

		items = new ItemStack[] {ItemLib.addEnchantments(ItemLib.createItem(Material.BONE, 1, (short) 0, ChatColor.DARK_GRAY + "Os du spectre", null), 
				new Enchantment[] {Enchantment.DAMAGE_ALL}, new int[] {3}), ItemLib.createSplashPotion(PotionType.INSTANT_DAMAGE, false, false, 3)};
		armor = new ItemStack[] {};
		effects = new PotionEffect[] {new PotionEffect(PotionEffectType.INVISIBILITY, (int) MiscellaneousUtils.calculateTicks(3600), 0)};
		Kit ghost = new Kit("Fantôme", items, armor, effects, PvPBoxKitIcon.ghostKit, (byte)4, (byte)1, (byte)5, (byte)5);
		kits.add(ghost);

		/* Golem */

		items = new ItemStack[] {new ItemStack(Material.STONE_AXE), ItemLib.addDisplayName(new ItemStack(Material.COBBLESTONE), "Rocher")};
		armor = new ItemStack[] {null, new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.IRON_CHESTPLATE), null};
		effects = new PotionEffect[] {new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 0, MiscellaneousUtils.calculateTicks(10000))};
		Kit golem = new Kit("Golem", items, armor, effects, 24D, PvPBoxKitIcon.golemKit, (byte)5, (byte)7, (byte)4, (byte)7);
		golem.rightAbilities.put(items[1], KitAbility.golem());
		kits.add(golem);
		specialKits.add(golem);

		/* Bomberman */

		items = new ItemStack[] {new ItemStack(Material.WOOD_SWORD), ItemLib.addDisplayName(new ItemStack(Material.TNT), "§cBombe !")};
		armor = new ItemStack[] {ItemLib.colorLeatherArmor(new ItemStack(Material.LEATHER_BOOTS), Color.RED), null, 
				ItemLib.colorLeatherArmor(new ItemStack(Material.LEATHER_CHESTPLATE), Color.WHITE), 
				ItemLib.colorLeatherArmor(new ItemStack(Material.LEATHER_HELMET), Color.RED)};
		effects = new PotionEffect[] {};
		Kit bomberman = new Kit("Bomberman", items, armor, effects, PvPBoxKitIcon.bombermanKit, (byte)3, (byte)4, (byte)6, (byte)5);
		bomberman.rightAbilities.put(items[1], KitAbility.bomb());
		kits.add(bomberman);
		specialKits.add(bomberman);

		/* Pirate */

		items = new ItemStack[] {ItemLib.addDisplayName(new ItemStack(Material.STONE_SWORD), "Sabre"), 
				ItemLib.addDisplayName(new ItemStack(Material.GOLD_HOE), "Vieux Révolver"), 
				ItemLib.addDisplayName(ItemLib.createPotion(PotionType.INSTANT_HEAL, false, false, 2), "§aAntidote du scorbut")};
		armor = new ItemStack[] {new ItemStack(Material.AIR), ItemLib.colorLeatherArmor(new ItemStack(Material.LEATHER_LEGGINGS), Color.BLACK),
				ItemLib.colorLeatherArmor(new ItemStack(Material.LEATHER_CHESTPLATE), Color.RED),
				ItemLib.colorLeatherArmor(new ItemStack(Material.LEATHER_HELMET), Color.BLACK)};
		effects = new PotionEffect[] {};
		Kit pirate = new Kit("Pirate", items, armor, effects, PvPBoxKitIcon.pirateKit, (byte)5, (byte)4, (byte)3, (byte)5);
		pirate.rightAbilities.put(items[1], KitAbility.revolver());
		kits.add(pirate);
		specialKits.add(pirate);

		/* Marin */

		items = new ItemStack[] {ItemLib.addDisplayName(new ItemStack(Material.FISHING_ROD), "§eGrappin"), 
				ItemLib.addDisplayName(ItemLib.addEnchantments(new ItemStack(Material.RAW_FISH), new Enchantment[] {Enchantment.DAMAGE_ALL}, new int[] {2}), "§rPoisson saillant")};
		armor = new ItemStack[] {ItemLib.colorLeatherArmor(new ItemStack(Material.LEATHER_BOOTS), Color.YELLOW), ItemLib.colorLeatherArmor(new ItemStack(Material.LEATHER_LEGGINGS), Color.YELLOW),
				ItemLib.colorLeatherArmor(new ItemStack(Material.LEATHER_CHESTPLATE), Color.YELLOW),
				ItemLib.colorLeatherArmor(new ItemStack(Material.LEATHER_HELMET), Color.YELLOW)};
		effects = new PotionEffect[] {};
		Kit marin = new Kit("Marin", items, armor, effects, PvPBoxKitIcon.fisherKit, (byte)5, (byte)4, (byte)4, (byte)6);
		marin.rightAbilities.put(items[1], KitAbility.sharpFish());
		kits.add(marin);
		specialKits.add(marin);

		/* Enderman */

		items = new ItemStack[] {new ItemStack(Material.AIR), ItemLib.createItem(Material.MAGMA_CREAM, 1, (short) 0, "§5Teleporter", null)};
		armor = new ItemStack[] {ItemLib.colorLeatherArmor(new ItemStack(Material.LEATHER_BOOTS), Color.BLACK), ItemLib.colorLeatherArmor(new ItemStack(Material.LEATHER_LEGGINGS), Color.BLACK),
				ItemLib.colorLeatherArmor(new ItemStack(Material.LEATHER_CHESTPLATE), Color.BLACK),
				ItemLib.colorLeatherArmor(new ItemStack(Material.LEATHER_HELMET), Color.BLACK)};
		effects = new PotionEffect[] {new PotionEffect(PotionEffectType.INCREASE_DAMAGE,  (int) MiscellaneousUtils.calculateTicks(3600), 10), new PotionEffect(PotionEffectType.SPEED, MiscellaneousUtils.calculateTicks(3600), 1)};
		Kit enderman = new Kit("Enderman", items, armor, effects, PvPBoxKitIcon.endermanKit, (byte)6, (byte)4, (byte)5, (byte)7);
		enderman.rightAbilities.put(items[1], KitAbility.teleporter());
		kits.add(enderman);
		specialKits.add(enderman);
		
		/* Chasseur */
		
		items = new ItemStack[] {ItemLib.addEnchantments(ItemLib.addDisplayName(new ItemStack(Material.IRON_HOE), "Couteau de chasse"), new Enchantment[] {Enchantment.DAMAGE_ALL}, new int[] {2}),
				ItemLib.addDisplayName(new ItemStack(Material.TRAP_DOOR), "Piège à ours")};
		armor = new ItemStack[] {new ItemStack(Material.LEATHER_BOOTS), null, new ItemStack(Material.LEATHER_CHESTPLATE), null};
		effects = new PotionEffect[] {new PotionEffect(PotionEffectType.NIGHT_VISION, (int) MiscellaneousUtils.calculateTicks(3600), 2)};
		Kit chasseur = new Kit("Chasseur", items, armor, effects, PvPBoxKitIcon.hunterKit, (byte)6, (byte)3, (byte)4, (byte)5);
		chasseur.rightAbilities.put(items[1], KitAbility.trap());
		chasseur.rightAbilities.put(items[0], KitAbility.huntKnife());
		kits.add(chasseur);
		specialKits.add(chasseur);
		
		/* Assassin */
		
		items = new ItemStack[] {ItemLib.addEnchantments(ItemLib.addDisplayName(new ItemStack(Material.IRON_SWORD), "Dague tranchante"), new Enchantment[] {Enchantment.DAMAGE_ALL}, new int[] {3}),
				ItemLib.addDisplayName(new ItemStack(Material.FEATHER), "Lames de rasoir"),
				ItemLib.addDisplayName(new ItemStack(Material.SUGAR), "Poudre d'invisibilité")};
		armor = new ItemStack[] {null, null, null, null};
		effects = new PotionEffect[] {};
		Kit assassin = new Kit("Assassin", items, armor, effects, 16D, PvPBoxKitIcon.assassinKit, (byte)8, (byte)1, (byte)5, (byte)5);
		assassin.rightAbilities.put(items[1], KitAbility.razorBlade());
		assassin.rightAbilities.put(items[2], KitAbility.invisibility());
		kits.add(assassin);
		specialKits.add(assassin);
	}

	public static void resetPvPBoxPlayer(Player p) {
		p.setLevel(0);
		p.setExp(0F);
		for(PotionEffect effect : p.getActivePotionEffects()) 
			p.removePotionEffect(effect.getType());
		p.getInventory().clear();
		p.getInventory().setArmorContents(null);
		p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20D);
		p.setHealth(20D);
		PvPBoxCore.players.remove(p);
		p.teleport(PvPBoxConfig.getLobbyLocation());
	}

	public static Kit getKitByName(String kit) {
		for(Kit k : kits) {
			if(k.getName().equalsIgnoreCase(kit)) {
				return k;
			}
		}
		return null;
	}

	public static List<Kit> getSpecialKits() {
		List<Kit> specialKits = new ArrayList<Kit>();
		for(int i = 9; i < kits.size(); i++) {
			specialKits.add(kits.get(i));
		}
		return specialKits;
	}

	public static Kit getSpecialKitByName(String specialKit) {
		for(Kit k : getSpecialKits()) {
			if(k.getName().equalsIgnoreCase(specialKit)) {
				return k;
			}
		}
		return null;
	}
}