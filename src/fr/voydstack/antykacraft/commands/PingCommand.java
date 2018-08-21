package fr.voydstack.antykacraft.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import fr.voydstack.antykacraft.utils.Constants;

public class PingCommand implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(args.length == 0) { // Ping personnel
				if(player.hasPermission("antykacraft.ping")) { // Test de permission
					int ping = ((CraftPlayer) player).getHandle().ping;
					player.sendMessage(Constants.PREFIX + "§aVotre ping est de " + ping + "ms");
				} else {
					player.sendMessage(Constants.NO_PERMISSION);
				}
			} else if(args.length == 1) { // Ping d'un autre joueur
				if(player.hasPermission("antykacraft.ping.others")) { // Test de permission
					Player target = null;
					// Parcourt des joueurs en ligne
					for(Player online : Bukkit.getOnlinePlayers()) { 
						if(online.getName().equalsIgnoreCase(args[0])) {
							target = online;
							break;
						}
					}
					try { // Si le joueur à été trouvé
						int ping = ((CraftPlayer) target).getHandle().ping;
						if(!target.equals(player)) { // Si le joueur ciblé n'est pas celui ayant exécuté la commande
							player.sendMessage(Constants.PREFIX + "§aLe ping de " + target.getName() + " est de " + ping + "ms");
						} else {
							player.sendMessage(Constants.PREFIX + "§aVotre ping est de " + ping + "ms");
						}
					} catch(NullPointerException npe) { // Le joueur est toujours nul (introuvable)
						player.sendMessage(Constants.PREFIX + "§c" + args[0] + " n'est pas connecté.");
					}
				} else {
					player.sendMessage(Constants.NO_PERMISSION);
				}
			}
		}
		return true;
	}
}
