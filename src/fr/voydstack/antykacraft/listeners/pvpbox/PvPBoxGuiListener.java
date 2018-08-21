package fr.voydstack.antykacraft.listeners.pvpbox;

import java.util.ArrayList;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;

import fr.voydstack.antykacraft.config.pvpbox.PvPBoxConfig;
import fr.voydstack.antykacraft.pvpbox.PvPBoxCore;
import fr.voydstack.antykacraft.pvpbox.effect.Effect;
import fr.voydstack.antykacraft.pvpbox.gui.PvPBoxGui;
import fr.voydstack.antykacraft.pvpbox.kit.Kit;
import fr.voydstack.antykacraft.utils.Constants;

public class PvPBoxGuiListener implements Listener {
	@EventHandler
	public void selectKit(InventoryClickEvent e) {
		Inventory inv = e.getInventory();
		Player p = (Player) e.getWhoClicked();
		ItemStack i = e.getCurrentItem();
		if(p.getWorld().getName().equals(PvPBoxCore.eventWorld)) {
			try {
				if(inv.getName().equals(PvPBoxGui.pvpBoxKitSelector(p).getName())) {
					if(!i.getType().equals(Material.STAINED_GLASS_PANE)) {
						PlayerInventory pInv = p.getInventory();
						for(PotionEffect effect : p.getActivePotionEffects()) p.removePotionEffect(effect.getType());
						pInv.clear();
						pInv.setArmorContents(null);
						Kit kit = null;
						for(Kit k : Kit.kits) {
							try {
								if(k.getDisplayItem().equals(i)) {
									for(ItemStack it : k.getItems())
										pInv.addItem(it);
									pInv.setArmorContents(k.getArmor());
									for(PotionEffect ef : k.getEffects())
										p.addPotionEffect(ef);
									p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(k.getMaxLife());
									p.setHealth(k.getMaxLife());
									p.setGameMode(GameMode.SURVIVAL);
									PvPBoxConfig.playKit(p, k);
									p.sendMessage(Constants.PVPBOX_PREFIX + "§eTu as choisi le kit \"" + k.getName() + "§e\"");
									kit = k;
									Effect.getEffectsMap().put(p, new ArrayList<Effect>());
									break;
								}
							} catch(NullPointerException ex) {}
						}
						PvPBoxCore.players.put(p, kit);
						PvPBoxCore.teleportToPvPBoxArena(p);
					} e.setCancelled(true);
				} else if(inv.getName().equals(PvPBoxGui.kitShopGUI(p).getName())) {
					if(i.getType() != Material.GOLD_NUGGET) {
						for(Kit k : Kit.getSpecialKits()) {
							if(i.getItemMeta().getDisplayName().contains(k.getName())) {
								int points = PvPBoxConfig.getPlayerPoints(p);
								int price = PvPBoxConfig.getKitPrice(k);
								if(points < price) {
									p.sendMessage(Constants.PVPBOX_PREFIX+"§cVous n'avez pas assez de points pour acheter ce kit");
								} else {
									PvPBoxConfig.purchaseKit(p, k);
									p.sendMessage(Constants.PVPBOX_PREFIX+"§aVous avez acheté le kit \""+k.getName()+"\" !");
									p.closeInventory();
								}
							}
						}
					} e.setCancelled(true);
				}
			} catch(NullPointerException npe) {}
		}
	}

	@EventHandler
	public void inventoryMove(InventoryMoveItemEvent e) {
		if(e.getSource().getName().equals("Achat d'un kit PvPBox spécial")) {
			e.setCancelled(true);
		}
	}
}
