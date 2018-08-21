package fr.voydstack.antykacraft.commands.pvpbox;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.voydstack.antykacraft.Antykacraft;
import fr.voydstack.antykacraft.config.pvpbox.PvPBoxConfig;
import fr.voydstack.antykacraft.pvpbox.PvPBoxCore;
import fr.voydstack.antykacraft.pvpbox.gui.PvPBoxGui;
import fr.voydstack.antykacraft.pvpbox.kit.Kit;
import fr.voydstack.antykacraft.utils.Constants;
import fr.voydstack.antykacraft.utils.MiscellaneousUtils;

public class PvPBoxCommand implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			if(p.getWorld().getName().equalsIgnoreCase(PvPBoxCore.eventWorld)) {
				if(args.length == 1) { // 1 Argument
					if(args[0].equalsIgnoreCase("list")) { // Liste des arènes
						if(p.hasPermission("antykacraft.pvpbox.list")) {
							p.sendMessage(PvPBoxConfig.getPvPBoxArenaList());
						} else p.sendMessage(Constants.NO_PERMISSION);
					} else if(args[0].equalsIgnoreCase("reload")) { // Reload dynamique de la config
						if(p.hasPermission("antykacraft.pvpbox.reload")) {
							Antykacraft.instance.reloadConfig();
							p.sendMessage(Constants.PVPBOX_PREFIX + "§aConfig redemarrée avec succés !");
						} else p.sendMessage(Constants.NO_PERMISSION);
					} else if(args[0].equalsIgnoreCase("stats")) { // Stats personnelles
						if(p.hasPermission("antykacraft.pvpbox.stats")) {
							p.sendMessage(PvPBoxConfig.getOfflinePlayerPvPBoxStats(p.getName(), true));
						} else p.sendMessage(Constants.NO_PERMISSION);
					} else if(args[0].equalsIgnoreCase("rank")) { // Rang sur le serveur (par nombre de kills) (5 affichés)
						if(p.hasPermission("antykacraft.pvpbox.rank")) {
							p.sendMessage(PvPBoxConfig.getPvPBoxRank(5));
						} else p.sendMessage(Constants.NO_PERMISSION);
					} else if(args[0].equalsIgnoreCase("trank")) { // Rang sur le serveur (par ratio kills/morts) (5 affichés)
						p.sendMessage(PvPBoxConfig.getRatioPvPBoxRank(5));
					} else if(args[0].equalsIgnoreCase("debug")) { // Active / Désactive le mode debug
						if(p.hasPermission("antykacraft.pvpbox.debug")) {
							Kit.debug = Kit.debug == false ? true : false;
							p.sendMessage(Kit.debug == true ? "§aDebug Mode ON !" : "§cDebug Mode OFF !");
						} else p.sendMessage(Constants.NO_PERMISSION);
					} else if(args[0].equalsIgnoreCase("leave")) { // Quitte la partie de PvPBox en cours
						if(p.hasPermission("antykacraft.pvpbox.leave")) {
							if(PvPBoxCore.players.containsKey(p)) {
								Kit.resetPvPBoxPlayer(p);
								p.teleport(PvPBoxConfig.getLobbyLocation());
							} else p.sendMessage(Constants.PVPBOX_PREFIX + "§cNope.");
						}
					} else if(args[0].equalsIgnoreCase("lobby")) { // Permet de se téléporter au lobby PvPBox
						if(p.hasPermission("antykacraft.pvpbox.lobby.teleport")) {
							if(PvPBoxConfig.isLobbyDefined()) {
								p.teleport(PvPBoxConfig.getLobbyLocation());
							} else p.sendMessage(Constants.PVPBOX_PREFIX+"§cLe lobby n'a pas encore été défini");
						}
					} else if(args[0].equalsIgnoreCase("points")) { // Affiche le nombre de points de victoire
						if(p.hasPermission("antykacraft.pvpbox.points")) {
							p.sendMessage(Constants.PVPBOX_PREFIX+"§aVous avez actuellement "+PvPBoxConfig.getPlayerPoints(p)+" points de victoire.");
						}
					} else if(args[0].equalsIgnoreCase("multiplier")) { // Affiche le multiplicateur de points de victoire
						if(p.hasPermission("antykacraft.pvpbox.multiplier.get")) {
							p.sendMessage(Constants.PVPBOX_PREFIX+"§aLe multiplicateur de points est fixé à "+PvPBoxConfig.getPointsMultiplier());
						}
					} else if(args[0].equalsIgnoreCase("mostplayed")) { // Affiche le kit le plus joué
						if(p.hasPermission("antykacraft.pvpbox.kit.mostplayed")) {
							try {
								p.sendMessage(Constants.PVPBOX_PREFIX+"§aLe kit le plus joué est le kit \""+PvPBoxConfig.getMostPlayedKit().getName()+"\"");
							} catch(NullPointerException npe) {
								p.sendMessage(Constants.PVPBOX_PREFIX+"§eAucun kit n'a encore été joué.");
							}
						} else p.sendMessage(Constants.NO_PERMISSION);
					} else if(args[0].equalsIgnoreCase("lessplayed")) { // Affiche le kit le moins joué
						if(p.hasPermission("antykacraft.pvpbox.kit.lessplayed")) {
							try {
								p.sendMessage(Constants.PVPBOX_PREFIX+"§aLe kit le moins joué est le kit \""+PvPBoxConfig.getLessPlayedKit().getName()+"\"");
							} catch(NullPointerException npe) {
								p.sendMessage(Constants.PVPBOX_PREFIX+"§eAucun kit n'a encore été joué.");
							}
						} else p.sendMessage(Constants.NO_PERMISSION);
					} else p.sendMessage(Constants.PVPBOX_PREFIX + "§cArgument inconnu.");
				} else if(args.length == 2) { // 2 Arguments
					if(args[0].equalsIgnoreCase("create")) { // Permet de créer une arène PvPBox (à la position de l'opérateur)
						if(p.hasPermission("antykacraft.pvpbox.create")) {
							PvPBoxConfig.createNewPvPBoxArena(p, args[1]);
							p.sendMessage(Constants.PVPBOX_PREFIX + "§aL'arène PvPBox \"" + MiscellaneousUtils.wordMaj(args[1]) + "\" a bien été crée !");
						} else p.sendMessage(Constants.NO_PERMISSION);
					} else if(args[0].equalsIgnoreCase("setdefault")) { // Permet de définir l'arène PvPBox par défaut
						if(p.hasPermission("antykacraft.pvpbox.setdefault")) {
							if(PvPBoxConfig.pvpBoxArenaExist(args[1])) {
								PvPBoxConfig.setDefaultPvPBoxArena(args[1]);
								p.sendMessage(Constants.PVPBOX_PREFIX + "§aL'arène \"" + MiscellaneousUtils.wordMaj(args[1]) + "\" a bien été définie par défaut !");
							} else if(args[1].equalsIgnoreCase("none")) {
								Antykacraft.configHandler.getConfig().set("antykacraft.pvpbox.defaultArena", "none");
								Antykacraft.instance.saveConfig();
								p.sendMessage(Constants.PVPBOX_PREFIX + "§aAucune arène n'a été sélectionnée.");
							} else p.sendMessage("§cL'arene spécifiée n'existe pas !");
						} else p.sendMessage(Constants.NO_PERMISSION);
					} else if(args[0].equalsIgnoreCase("remove")) { // Permet de supprimer une arène PvPBox
						if(p.hasPermission("antykacraft.pvpbox.remove")) {
							if(PvPBoxConfig.pvpBoxArenaExist(args[1])) {
								PvPBoxConfig.removePvPBoxArena(args[1]);
								p.sendMessage(Constants.PVPBOX_PREFIX + "§aL'arène \"" + MiscellaneousUtils.wordMaj(args[1]) + "\" a bien été supprimée.");
							} else p.sendMessage(Constants.PVPBOX_PREFIX + "§cL'arène spécifiée n'existe pas.");
						} else p.sendMessage(Constants.NO_PERMISSION);
					} else if(args[0].equalsIgnoreCase("stats")) { // Permet d'obtenir les stats d'autres joueurs
						if(p.hasPermission("antykacraft.pvpbox.stats.others")) {
							if(args[1] != "") 
								sender.sendMessage(PvPBoxConfig.getOfflinePlayerPvPBoxStats(args[1], false));
						} else p.sendMessage(Constants.NO_PERMISSION);
					} else if(args[0].equalsIgnoreCase("rank")) { // Rang sur le serveur (par nombre de kills) (nombre spécifié >= 5)
						int i = 0;
						try {i = Integer.valueOf(args[1]);}
						catch(Exception e) {sender.sendMessage(Constants.PVPBOX_PREFIX + "§cVous devez indiquer un nombre en 2è argument !");}
						if(i >= 5) {
							sender.sendMessage(PvPBoxConfig.getPvPBoxRank(i));
						} else sender.sendMessage(Constants.PVPBOX_PREFIX + "§cLa limite du classement doit être supérieure ou égale à  5.");
					} else if(args[0].equalsIgnoreCase("trank")) { // Rang sur le serveur (par ration kills / morts) (nombre spécifié >= 5)
						int i = 0;
						try {i = Integer.valueOf(args[1]);}
						catch(Exception e) {sender.sendMessage(Constants.PVPBOX_PREFIX + "§cVous devez indiquer un nombre en 2è argument !");}
						if(i >= 5) {
							sender.sendMessage(PvPBoxConfig.getRatioPvPBoxRank(i));
						} else sender.sendMessage(Constants.PVPBOX_PREFIX + "§cLa limite du classement doit être supérieure ou égale à  5.");
					} else if(args[0].equalsIgnoreCase("lobby")) { // Configuration du lobby PvPBox
						if(args[1].equalsIgnoreCase("set")) { // Permet de définir le lobby PvPBox (à la position de l'opérateur)
							if(p.hasPermission("antykacraft.pvpbox.lobby.set")) {
								PvPBoxConfig.setLobby(p);
								p.sendMessage(Constants.PVPBOX_PREFIX+"§aLe lobby PvPBox a bien été défini!");
							}
						} else p.sendMessage(Constants.PVPBOX_PREFIX+"§cArgument inconnu.");
					} else if(args[0].equalsIgnoreCase("multiplier")) { // Permet de définir un multiplicateur de points
						if(p.hasPermission("antykacraft.pvpbox.multiplier.set")) {
							try {
								double multiplier = Double.parseDouble(args[1]);
								PvPBoxConfig.setPointsMultiplier(multiplier);
								p.sendMessage(Constants.PVPBOX_PREFIX+"§aLe multiplicateur de points a été fixé à "+multiplier);
							} catch(Exception ex) {
								p.sendMessage(Constants.PVPBOX_PREFIX + "§cVeuillez entrer un nombre valide");
							}
						} else p.sendMessage(Constants.NO_PERMISSION);
					} else if(args[0].equalsIgnoreCase("killreward")) { // Permet de définir le nombres de points gagnés par kill
						if(p.hasPermission("antykacraft.pvpbox.points.amount")) {
							try {
								int amount = Integer.parseInt(args[1]);
								PvPBoxConfig.setPointsPerKill(amount);
								p.sendMessage(Constants.PVPBOX_PREFIX+"§aLe nombre de points par kills a été fixé à "+amount);
							} catch(Exception ex) {
								p.sendMessage(Constants.PVPBOX_PREFIX + "§cVeuillez entrer un nombre valide");
							}
						} else p.sendMessage(Constants.NO_PERMISSION);
					} else if(args[0].equalsIgnoreCase("kits")) { // Configuration des kits
						if(args[1].equalsIgnoreCase("buyable")) { // Active / Désactive la gratuité des kits spéciaux.
							if(p.hasPermission("antykacraft.pvpbox.kit.buyable")) {
								if(PvPBoxConfig.areKitsBuyable()) {
									PvPBoxConfig.setKitsBuyable(false);
									p.sendMessage(Constants.PVPBOX_PREFIX+"§aTous les kits spéciaux sont désormais gratuits !");
								} else {
									PvPBoxConfig.setKitsBuyable(true);
									p.sendMessage(Constants.PVPBOX_PREFIX+"§aLes kits spéciaux sont redevenus payants.");
								}
							} else p.sendMessage(Constants.NO_PERMISSION);
						} else if(args[1].equalsIgnoreCase("buy")) { // Ouvre le panneau d'achat de kits
							if(p.hasPermission("antykacraft.pvpbox.kit.buy")) {
								p.openInventory(PvPBoxGui.kitShopGUI(p));
							} else p.sendMessage(Constants.NO_PERMISSION);
						} else p.sendMessage(Constants.PVPBOX_PREFIX+"§cArgument inconnu.");
					} else p.sendMessage(Constants.PVPBOX_PREFIX+"§cArgument inconnu.");
				} else if(args.length == 3) { // 3 Arguments
					if(args[0].equalsIgnoreCase("kits")) { // Configuration des kits
						if(args[1].equalsIgnoreCase("enable")) { // Permet d'activer un kit
							if(p.hasPermission("antykacraft.pvpbox.kit.enable")) {
								try {
									Kit k = Kit.getKitByName(args[2]);
									if(!PvPBoxConfig.isEnabled(k)) {
										PvPBoxConfig.removeDisabledKit(k);
										p.sendMessage(Constants.PVPBOX_PREFIX + "§aLe kit \""+k.getName()+"\" a bien été activé.");
									} else p.sendMessage(Constants.PVPBOX_PREFIX + "§eLe kit \""+k.getName()+"\" est déjà activé.");
								} catch(NullPointerException npe) {
									p.sendMessage(Constants.PVPBOX_PREFIX + "§cCe kit n'existe pas.");
								}
							} else p.sendMessage(Constants.NO_PERMISSION);
						} else if(args[1].equalsIgnoreCase("disable")) { // Permet de désactiver un kit
							if(p.hasPermission("antykacraft.pvpbox.kit.disable")) {
								try {
									Kit k = Kit.getKitByName(args[2]);
									if(PvPBoxConfig.isEnabled(k)) {
										PvPBoxConfig.addDisabledKit(k);
										p.sendMessage(Constants.PVPBOX_PREFIX + "§aLe kit \""+k.getName()+"\" a bien été désactivé.");
									} else p.sendMessage(Constants.PVPBOX_PREFIX + "§eLe kit \""+k.getName()+"\" est déjà désactivé.");
								} catch(NullPointerException npe) {
									p.sendMessage(Constants.PVPBOX_PREFIX + "§cCe kit n'existe pas.");
								}
							} else p.sendMessage(Constants.NO_PERMISSION);
						} else p.sendMessage(Constants.PVPBOX_PREFIX + "§cArgument inconnu.");
					}
				} else if(args.length == 4) {
					if(args[0].equalsIgnoreCase("kits")) { // Permet de définir le prix d'un kit
						if(args[1].equalsIgnoreCase("price")) {
							if(p.hasPermission("antykacraft.pvpbox.kit.price")) {
								try {
									Kit k = Kit.getSpecialKitByName(args[2]);
									int price = Integer.parseInt(args[3]);
									PvPBoxConfig.setKitPrice(k, price);
									p.sendMessage(Constants.PVPBOX_PREFIX + "§aLe prix du kit \""+k.getName()+"\" est de maintenant "+price+"pts");
								} catch(NullPointerException npe) {
									p.sendMessage(Constants.PVPBOX_PREFIX + "§cCe kit n'existe pas !");
								} catch(Exception e) {
									p.sendMessage(Constants.PVPBOX_PREFIX + "§cVeuillez indiquer un prix correct!");
								}
							} else p.sendMessage(Constants.NO_PERMISSION);
						} else p.sendMessage(Constants.PVPBOX_PREFIX+"§cArgument inconnu.");
					} else if(args[0].equalsIgnoreCase("points")) { // Gère la gestion des points
						if(args[1].equalsIgnoreCase("give")) { // Permet de donner des points
							if(p.hasPermission("antykacraft.pvpbox.points.give")) {
								try {
									Player to = Bukkit.getPlayer(args[2]);
									int amount = Integer.parseInt(args[3]);
									PvPBoxConfig.addPlayerPoints(to, amount);
									p.sendMessage(Constants.PVPBOX_PREFIX+"§aVous avez donné "+amount+" points de victoire à "+to.getDisplayName());
									to.sendMessage(Constants.PVPBOX_PREFIX+to.getDisplayName()+" §aVous a donné "+amount+" points de victoire");
								} catch(Exception ex) {
									p.sendMessage(Constants.PVPBOX_PREFIX + "§cVeuillez entrer des données correctes");
								}
							} else p.sendMessage(Constants.NO_PERMISSION);
						} else p.sendMessage(Constants.PVPBOX_PREFIX+"§cArgument inconnu.");
					} else p.sendMessage(Constants.PVPBOX_PREFIX+"§cArgument inconnu.");
				} else if(args.length == 0) { // Page d'aide
					p.sendMessage(Constants.PVPBOX_PREFIX + "§eAide en cours d'édition ...");
				} else return false; 
			} else p.sendMessage(Constants.PVPBOX_PREFIX + "§cVous devez être dans la map event pour effectuer cette commande.");
		} else {
			if(args.length == 1) { // Permet d'ouvrir la sélection de kits à un joueur
				Player p = Bukkit.getServer().getPlayer(args[0]);
				if(p != null) {
					if(PvPBoxConfig.getDefaultPvPBoxArena() != "none") {
						p.openInventory(PvPBoxGui.pvpBoxKitSelector(p));
					} else p.sendMessage(Constants.PVPBOX_PREFIX + "§cIl n'y a aucune arène PvPBox de séléctionnée ...");
				}
			} else if(args.length == 2) { // Permet de téléporter un joueur au lobby PvPBox
				if(args[0].equalsIgnoreCase("lobby")) {
					String playerName = args[1];
					try {
						Player pl = Bukkit.getPlayer(playerName);
						pl.teleport(PvPBoxConfig.getLobbyLocation());
					} catch(Exception e) {
						sender.sendMessage(Constants.PVPBOX_PREFIX+"§cLe joueur n'est pas en ligne");
					}
				}
			} else sender.sendMessage(Constants.PVPBOX_PREFIX + "§cCommande inconnue.");
		}
		return true;
	}
}
