package fr.voydstack.libs;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

/**
 * ItemLib utility library
 * @author MrAzErTy31
 * @version 1.0
 */

public class ItemLib extends JavaPlugin {
	/* Item Manipulation */

	public static ItemStack createItem(Material material, int amount, short data, String name, List<String> lore) {
		ItemStack item = new ItemStack(material, amount, data);
		ItemMeta MetaItem = item.getItemMeta();
		if(name != null) MetaItem.setDisplayName(name);
		if(lore != null) MetaItem.setLore(null); MetaItem.setLore(lore);
		item.setItemMeta(MetaItem);

		return item;
	}

	public static ItemStack addDisplayName(ItemStack item, String name) {
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(name);
		item.setItemMeta(itemMeta);
		return item;
	}

	public static ItemStack addLore(ItemStack item, List<String> lore) {
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setLore(lore);
		item.setItemMeta(itemMeta);
		return item;
	}

	public static ItemStack addEnchantments(ItemStack item, Enchantment[] enchants, int[] levels) {
		ItemMeta MetaItem = item.getItemMeta();
		for(int i = 0; i <= enchants.length - 1; i++) {
			item.addUnsafeEnchantment(enchants[i], levels[i]);
			MetaItem.addEnchant(enchants[i], levels[i], true);
		}	
		return item;
	}

	public static ItemStack colorLeatherArmor(ItemStack armor, Color color) {
		LeatherArmorMeta metaArmor = (LeatherArmorMeta) armor.getItemMeta();
		metaArmor.setColor(color);
		armor.setItemMeta(metaArmor);
		return armor;
	}

	public static ItemStack createPotion(PotionType type, boolean extend, boolean upgraded, int amount) {
		ItemStack potion = new ItemStack(Material.POTION, amount);
		PotionMeta meta = (PotionMeta) potion.getItemMeta();
		meta.setBasePotionData(new PotionData(type, extend, upgraded));
		potion.setItemMeta(meta);
		return potion;
	}

	public static ItemStack createSplashPotion(PotionType type, boolean extend, boolean upgraded, int amount) {
		ItemStack potion = new ItemStack(Material.SPLASH_POTION, amount);
		PotionMeta meta = (PotionMeta) potion.getItemMeta();
		meta.setBasePotionData(new PotionData(type, extend, upgraded));
		potion.setItemMeta(meta);
		return potion;
	}

	@SuppressWarnings("deprecation")
	public static ItemStack createPlayerHead(String playerName, String display) {
		ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
		SkullMeta headMeta = (SkullMeta) head.getItemMeta();
		headMeta.setOwningPlayer(Bukkit.getOfflinePlayer(playerName));
		if(display != null) headMeta.setDisplayName(display);
		head.setItemMeta(headMeta);
		return head;
	}

	public static ItemStack addCustomPotionEffect(PotionEffectType p, int d, int a, boolean s) {
		Material son = s ? Material.SPLASH_POTION : Material.POTION;
		ItemStack i = new ItemStack(son);
		PotionMeta pM = (PotionMeta) i.getItemMeta();
		pM.addCustomEffect(new PotionEffect(p, d, a), true);
		i.setItemMeta(pM);
		return i;
	}

	public static ItemStack setCustomPotionEffect(PotionEffectType p, int d, int a, PotionType pT, boolean s) {
		Material son = s ? Material.SPLASH_POTION : Material.POTION;
		ItemStack i = new ItemStack(son);
		PotionMeta pM = (PotionMeta) i.getItemMeta();
		pM.setBasePotionData(new PotionData(pT));
		pM.addCustomEffect(new PotionEffect(p, d, a), true);
		i.setItemMeta(pM);	
		return i;
	}

	public static ItemStack[] getFullColoredArmor(Color c) {
		String[] s = new String[] {"BOOTS", "LEATHER", "CHESTPLATE", "HELMET"};
		ItemStack[] it = new ItemStack[4];
		for(int i = 0; i < 4; i++)
			it[i] = colorLeatherArmor(new ItemStack(Material.getMaterial("LEATHER_" + s[i])), c);
		return it;
	}

	public static ItemStack[] getFullColoredArmor(Color c, Enchantment[] e, int[] l) {
		String[] s = new String[] {"BOOTS", "LEATHER", "CHESTPLATE", "HELMET"};
		ItemStack[] it = new ItemStack[4];
		for(int i = 0; i < 4; i++)
			it[i] = addEnchantments(colorLeatherArmor(new ItemStack(Material.getMaterial("LEATHER_" + s[i])), c), e, l);
		return it;
	}

	public static ItemStack setUnbreakable(ItemStack item) {
		ItemMeta iM = item.getItemMeta();
		iM.setUnbreakable(true);
		iM.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		item.setItemMeta(iM);
		return item;
	}

	/* Formatting Utils */

	public static List<String> createLore(String[] lore) {
		List<String> itemLore = new ArrayList<String>();
		for(String lorePart : lore) itemLore.add(lorePart);
		return itemLore;
	}
}
