package fr.voydstack.antykacraft.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import fr.voydstack.antykacraft.Antykacraft;
import fr.voydstack.antykacraft.commands.FactionCommand;

public class MessageListener implements Listener {
	public static List<Player> factionChatLock = new ArrayList<Player>();

	/* Event qui traite le canal de faction automatique */
	@EventHandler
	public void onMessage(AsyncPlayerChatEvent e) { 
		if(Antykacraft.configHandler.isFactionChatEnabled()) {
			Player sender = e.getPlayer();
			if(factionChatLock.contains(sender)) {
				e.setCancelled(true);
				FactionCommand.sendFactionMessage(sender, e.getMessage());
			}
		}
	}
}