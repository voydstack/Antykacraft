package fr.voydstack.antykacraft.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import fr.voydstack.antykacraft.Antykacraft;
import fr.voydstack.antykacraft.listeners.MessageListener;
import fr.voydstack.antykacraft.utils.Constants;

public class FactionCommand implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(player.hasPermission("antykacraft.faction")) { // Test de permission
				try {
					if(args.length < 1) { // Toggle du chat faction
						String status;
						if(!MessageListener.factionChatLock.contains(player)) {
							MessageListener.factionChatLock.add(player);
							status = "activé";
						} else {
							MessageListener.factionChatLock.remove(player);
							status = "désactivé";
						}
						player.sendMessage(Constants.PREFIX + "§aLe canal de faction automatique a été " + status);
						Antykacraft.logHandler.getLogger().info(Constants.PREFIX_RAW + player + " a " + status + " le canal de faction automatique.");
					} else { // Usage normal
						// Si le chat faction est activé dans la configuration
						if(Antykacraft.configHandler.isFactionChatEnabled()) {
							String message = String.join(" ", args);
							if(MessageListener.factionChatLock.contains(player)) {
								player.sendMessage(Constants.PREFIX + "§eLe canal de faction automatique est activé, inutile de taper la commande.");
							}
							sendFactionMessage(player, message);
						} else {
							player.sendMessage(Constants.PREFIX + "§eLe canal de faction est temporairement désactivé.");
						}
					}
				} catch(NullPointerException npe) {
					/*player.sendMessage(Constants.PREFIX + "§cVous n'appartenez à aucune faction.");*/
					npe.printStackTrace();
				}
			} else {
				player.sendMessage(Constants.NO_PERMISSION);
			}
		}
		return true;
	}

	/* player: Expéditeur du message
	 * message: Message de l'expéditeur
	 * Description: Envoie un message à tous les joueurs en ligne de la faction
	 */
	public static void sendFactionMessage(Player player, String message) {
		Team playerTeam = Antykacraft.scoreboardHandler.getTeamsHandler().getPlayerTeam(player);
		for(String name : playerTeam.getEntries()) {
			Player dest = Bukkit.getPlayer(name);
			if(dest.isOnline()) {
				String dispName = player.getName();
				try {
					dispName = player.getDisplayName();
				} catch(NullPointerException npe) {
					dispName = player.getName();
				} finally {
					dest.sendMessage("§6["+playerTeam.getDisplayName()+"] §r" + dispName + " > "+message);
				}
			}
		}
	}
}
