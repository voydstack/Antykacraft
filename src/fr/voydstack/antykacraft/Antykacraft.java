package fr.voydstack.antykacraft;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

import fr.voydstack.antykacraft.commands.FactionCommand;
import fr.voydstack.antykacraft.commands.PingCommand;
import fr.voydstack.antykacraft.commands.pvpbox.PvPBoxCommand;
import fr.voydstack.antykacraft.config.ConfigHandler;
import fr.voydstack.antykacraft.listeners.MessageListener;
import fr.voydstack.antykacraft.listeners.pvpbox.PvPBoxAbilityListener;
import fr.voydstack.antykacraft.listeners.pvpbox.PvPBoxConnectionListener;
import fr.voydstack.antykacraft.listeners.pvpbox.PvPBoxGuiListener;
import fr.voydstack.antykacraft.listeners.pvpbox.PvPBoxInteractListener;
import fr.voydstack.antykacraft.listeners.pvpbox.PvPBoxPlayerStatusListener;
import fr.voydstack.antykacraft.pvpbox.PvPBoxCore;
import fr.voydstack.antykacraft.scoreboard.ScoreboardHandler;
import fr.voydstack.antykacraft.utils.LogHandler;

public class Antykacraft extends JavaPlugin {
	public static Antykacraft instance;
	
	public static ConfigHandler configHandler;
	public static LogHandler logHandler;
	public static ScoreboardHandler scoreboardHandler;
	
	@Override
	public void onEnable() {
		/* Annulation des Timers */
		this.getServer().getScheduler().cancelAllTasks();

		/* Initialisation de l'instance du plugin */
		instance = this;

		/* Mise en place des handlers */

		// Configuration
		saveDefaultConfig();
		configHandler = new ConfigHandler(this.getConfig());
		
		// Logger 
		logHandler = new LogHandler(Logger.getLogger("Minecraft"));

		// Scoreboard
		scoreboardHandler = new ScoreboardHandler(this.getServer().getScoreboardManager());

		/* Enregistrement des commandes */

		this.getCommand("faction").setExecutor(new FactionCommand());
		this.getCommand("ping").setExecutor(new PingCommand());
		
		

		/* Enregistrement des listeners */

		this.getServer().getPluginManager().registerEvents(new MessageListener(), this);
		
		/* PvPBox */
		PvPBoxCore.init();
		
		this.getCommand("pvpbox").setExecutor(new PvPBoxCommand());
		
		this.getServer().getPluginManager().registerEvents(new PvPBoxAbilityListener(), this);
		this.getServer().getPluginManager().registerEvents(new PvPBoxConnectionListener(), this);
		this.getServer().getPluginManager().registerEvents(new PvPBoxGuiListener(), this);
		this.getServer().getPluginManager().registerEvents(new PvPBoxInteractListener(), this);
		this.getServer().getPluginManager().registerEvents(new PvPBoxPlayerStatusListener(), this);
		
	}

	@Override
	public void onDisable() {
		/* Annulation des Timers */
		this.getServer().getScheduler().cancelAllTasks();
	}
}
