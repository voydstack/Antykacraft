package fr.voydstack.antykacraft;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

import fr.voydstack.antykacraft.commands.FactionCommand;
import fr.voydstack.antykacraft.config.ConfigHandler;
import fr.voydstack.antykacraft.listeners.MessageListener;
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

		/* Enregistrement des listeners */

		this.getServer().getPluginManager().registerEvents(new MessageListener(), this);
	}

	@Override
	public void onDisable() {
		/* Annulation des Timers */
		this.getServer().getScheduler().cancelAllTasks();
	}
}
