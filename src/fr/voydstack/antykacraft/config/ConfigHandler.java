package fr.voydstack.antykacraft.config;

import org.bukkit.configuration.file.FileConfiguration;

import fr.voydstack.antykacraft.Antykacraft;

public class ConfigHandler {
	private FileConfiguration config;
	
	public ConfigHandler(FileConfiguration config) {
		setConfig(config);
	}
	
	public FileConfiguration getConfig() {
		return config;
	}
	
	public void setConfig(FileConfiguration config) {
		this.config = config;
	}
	
	public boolean isFactionChatEnabled() {
		return config.getBoolean("antykacraft.factionChat");
	}
	
	public void setFactionChatEnabled(boolean enabled) {
		config.set("antykacraft.factionChat", enabled);
		Antykacraft.instance.saveConfig();
	}
	
	public String getEventWorldName() {
		return config.getString("antykacraft.event_world");
	}
	
	public void setEventWorldName(String worldName) {
		config.set("antykacraft.world_event", worldName);
		Antykacraft.instance.saveConfig();
	}
}
