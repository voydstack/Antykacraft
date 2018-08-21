package fr.voydstack.antykacraft.pvpbox.gui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.voydstack.antykacraft.config.pvpbox.PvPBoxConfig;
import fr.voydstack.antykacraft.pvpbox.kit.Kit;
import fr.voydstack.libs.ItemLib;

public class PvPBoxGui {
	public static ItemStack glasspane = ItemLib.createItem(Material.STAINED_GLASS_PANE, 1, (short) 15, " ", null);
	public static Inventory pvpBoxKitSelector(Player p) {
		Inventory inv = Bukkit.createInventory(p, InventoryType.CHEST.getDefaultSize() * 2, "Sélection du kit PvPBox");
		int i = 0;
		for(Kit k : Kit.kits) {
			if(PvPBoxConfig.areKitsBuyable()) {
				if(!Kit.specialKits.contains(k)) {
					if(PvPBoxConfig.isEnabled(k)) {
						ItemStack it = k.getDisplayItem();
						ItemMeta iM = it.getItemMeta();
						iM.setLore(getLore(k));
						it.setItemMeta(iM);
						inv.setItem(18+i, it);
					} else i--;
				} else {
					try {
						if(PvPBoxConfig.getPlayerPurchasedKits(p).contains(k)) {
							if(PvPBoxConfig.isEnabled(k)) {
								ItemStack it = k.getDisplayItem();
								ItemMeta iM = it.getItemMeta();
								iM.setLore(getLore(k));
								it.setItemMeta(iM);
								inv.setItem(18+i, it);
							}
						}
					} catch(NullPointerException npe) {}
				}
			} else {
				ItemStack it = k.getDisplayItem();
				ItemMeta iM = it.getItemMeta();
				iM.setLore(getLore(k));
				it.setItemMeta(iM);
				inv.setItem(18+i, it);
			}
			i++;
		}
		fillEmpty(inv);
		return inv;
	}

	public static Inventory kitShopGUI(Player p) {
		Inventory inv = Bukkit.createInventory(p, 9, "Achat d'un kit PvPBox spécial");
		int points = PvPBoxConfig.getPlayerPoints(p);
		try {
			List<Kit> purchases = PvPBoxConfig.getPlayerPurchasedKits(p);
			for(Kit k : Kit.getSpecialKits()) {
				if(!purchases.contains(k)) {
					int price = PvPBoxConfig.getKitPrice(k);
					ChatColor color = points < price ? ChatColor.RED : ChatColor.GREEN;
					ItemStack kitShopItem = ItemLib.addLore(ItemLib.addDisplayName(k.getDisplayItem(), color + "" + ChatColor.BOLD + k.getName()), getShopKitLore(p, k));
					inv.addItem(kitShopItem);
				}
			}
		} catch(NullPointerException npe) {
			for(Kit k : Kit.getSpecialKits()) {
				int price = PvPBoxConfig.getKitPrice(k);
				ChatColor color = points < price ? ChatColor.RED : ChatColor.GREEN;
				ItemStack kitShopItem = ItemLib.addLore(ItemLib.addDisplayName(k.getDisplayItem(), color + "" + ChatColor.BOLD + k.getName()), getShopKitLore(p, k));
				inv.addItem(kitShopItem);
			}
		}
		inv.setItem(8, ItemLib.addLore(ItemLib.addDisplayName(new ItemStack(Material.GOLD_NUGGET), p.getDisplayName()), getPlayerPoints(p)));
		fillEmpty(inv);
		return inv;
	}

	public static void fillEmpty(Inventory inv) {
		for(int i = 0; i < inv.getSize(); i++) if(inv.getItem(i) == null) inv.setItem(i, glasspane);
	}

	public static List<String> getLore(Kit k) {
		List<String> lore = new ArrayList<String>();
		lore.add("§6---------------");
		lore.add("§cAttaque:   " + getRekt(k.getDescAttack()) + " ");
		lore.add("§aVie:         " + getRekt(k.getDescHealth()) + " ");
		lore.add("§bMagie:      " + getRekt(k.getDescMagic()) + " ");
		lore.add("§5Difficulté: " + getRekt(k.getDescDifficulty()) + " ");
		lore.add("§6---------------");
		return lore;
	}

	public static List<String> getShopKitLore(Player p, Kit k) {
		List<String> lore = new ArrayList<String>();
		lore.add("§ePrix: "+PvPBoxConfig.getKitPrice(k)+"pts");
		lore.add("§6---------------");
		lore.add("§cAttaque:   " + getRekt(k.getDescAttack()) + " ");
		lore.add("§aVie:         " + getRekt(k.getDescHealth()) + " ");
		lore.add("§bMagie:      " + getRekt(k.getDescMagic()) + " ");
		lore.add("§5Difficulté: " + getRekt(k.getDescDifficulty()) + " ");
		lore.add("§6---------------");
		return lore;
	}

	public static List<String> getPlayerPoints(Player p) {
		List<String> lore = new ArrayList<String>();
		lore.add("§6§l >> "+PvPBoxConfig.getPlayerPoints(p)+"pts");
		return lore;
	}

	static String getRekt(byte b) {
		String s = "";
		for(int i = 1; i <= b; i++) {
			s += "█";
		} return s;
	}
}
