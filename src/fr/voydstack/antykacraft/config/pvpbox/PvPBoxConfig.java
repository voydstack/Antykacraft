package fr.voydstack.antykacraft.config.pvpbox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.google.common.collect.Multimap;
import com.google.common.collect.TreeMultimap;

import fr.voydstack.antykacraft.Antykacraft;
import fr.voydstack.antykacraft.pvpbox.PvPBoxCore;
import fr.voydstack.antykacraft.pvpbox.kit.Kit;
import fr.voydstack.antykacraft.utils.Constants;
import fr.voydstack.antykacraft.utils.MiscellaneousUtils;

public class PvPBoxConfig {
	private static FileConfiguration config;
	
	public static void init() {
		config = Antykacraft.configHandler.getConfig();
	}
	
	/** GETTERS **/

	public static String getDefaultPvPBoxArena() {
		return config.getString("antykacraft.pvpbox.defaultArena");
	}

	public static Location getPvPBoxArenaLocation(String arena) {
		double x = config.getDouble("antykacraft.pvpbox.arenas." + arena + ".location.x");
		double y = config.getDouble("antykacraft.pvpbox.arenas." + arena + ".location.y");
		double z = config.getDouble("antykacraft.pvpbox.arenas." + arena + ".location.z");
		float yaw = (float) config.getDouble("antykacraft.pvpbox.arenas." + arena + ".location.yaw");
		float pitch = (float) config.getDouble("antykacraft.pvpbox.arenas." + arena + ".location.pitch");
		return new Location(Bukkit.getWorld(PvPBoxCore.eventWorld), x, y, z, yaw, pitch);
	}

	public static boolean pvpBoxArenaExist(String arena) {
		return config.getConfigurationSection("antykacraft.pvpbox.arenas").getKeys(false).contains(arena);
	}

	public static String getPvPBoxArenaList() {
		Set<String> configSecList = config.getConfigurationSection("antykacraft.pvpbox.arenas").getKeys(false);
		String message = "";
		if(configSecList.size() != 0) {
			message = "§6Liste des arènes PvPBox (§b" + configSecList.size() + "§6) : \n";
			for(String sec : configSecList) {
				String current = sec.equalsIgnoreCase(getDefaultPvPBoxArena()) ? "§6<=" : "";
				message += "§6- §b" + MiscellaneousUtils.wordMaj(sec) + " " + current + "\n";
			}
		} else {
			message = "§cIl n'y a aucune arène PvPBox pour le moment ...";
		}
		return message;
	}

	public static int getPvPBoxArenaTotalNumber() {
		return config.getConfigurationSection("antykacraft.pvpbox.arenas").getKeys(false).size();
	}

	public static int getPvPBoxKills(Player p) {
		return config.getInt("antykacraft.pvpbox.players." + p.getUniqueId() + ".stats.kills");
	}

	public static int getPvPBoxDeaths(Player p) {
		return config.getInt("antykacraft.pvpbox.players." + p.getUniqueId() + ".stats.deaths");
	}

	public static int getPlayerPoints(Player p) {
		return config.getInt("antykacraft.pvpbox.players."+p.getUniqueId() + ".points");
	}

	public static double getPointsMultiplier() {
		return config.getDouble("antykacraft.pvpbox.points.multiplier");
	}

	public static int getPointsPerKill() {
		return config.getInt("antykacraft.pvpbox.points.amount");
	}

	public static boolean areKitsBuyable() {
		return config.getBoolean("antykacraft.pvpbox.kitsBuyable");
	}

	public static String getRatioPvPBoxRank(int limit) {
		if(limit != 0) {
			String message = "§6[PvPBox] §a" + "Voici le classement PvPBox (ratio) : \n" + "§6--------------------------------\n";
			ConfigurationSection configsec = config.getConfigurationSection("antykacraft.pvpbox.players");
			Set<String> uuids = configsec.getKeys(false);
			List<Double> pvpboxPlayerRatio = new ArrayList<Double>();
			Multimap<Double, String> pK = TreeMultimap.create();
			if(!(limit <= uuids.size())) limit = uuids.size();
			for(String s : uuids) {
				String p = config.getString("antykacraft.pvpbox.players." + s + ".name");
				int k = config.getInt("antykacraft.pvpbox.players." + s + ".stats.kills"),
						d = config.getInt("antykacraft.pvpbox.players." + s + ".stats.deaths");
				double r = getFreeRatio(k, d);
				pK.put(r, p);
				pvpboxPlayerRatio.add(r);
			}
			Collections.sort(pvpboxPlayerRatio);
			Collections.reverse(pvpboxPlayerRatio);
			int pseudo = 0;
			for(int i = 0; i < limit; i++) {
				Object[] f = (Object[]) pK.get(pvpboxPlayerRatio.get(i)).toArray();
				if(((String) f[pseudo]).contains("_")) MiscellaneousUtils.replaceLast(((String) f[pseudo]), "_", "");
				message += "§6" + (i + 1) + ". " + f[pseudo] + " : §b" + pvpboxPlayerRatio.get(i) + " (" + getOfflinePlayerPvPBoxKills((String) f[pseudo]) + " Kills & " + getOfflinePlayerPvPBoxDeaths((String) f[pseudo]) + " Morts).\n";
				if(pK.get(pvpboxPlayerRatio.get(i)).size() > 1) {
					if(pseudo == pK.get(pvpboxPlayerRatio.get(i)).size() - 1) pseudo = 0;
					else pseudo ++;
				}
			}
			return message;
		} else return "";
	}

	public static String getPvPBoxRank(int limit) {
		if(limit != 0) {
			String message = "§6[PvPBox] §a" + "Voici le classement PvPBox (kills) : \n" + "§6--------------------------------\n";
			ConfigurationSection configsec = config.getConfigurationSection("antykacraft.pvpbox.players");
			Set<String> uuids = configsec.getKeys(false);
			List<Integer> pvpboxPlayerKills = new ArrayList<Integer>();
			Multimap<Integer, String> pK = TreeMultimap.create();
			if(!(limit <= uuids.size())) limit = uuids.size();
			for(String s : uuids) {
				String p = config.getString("antykacraft.pvpbox.players." + s + ".name");
				Integer k = config.getInt("antykacraft.pvpbox.players." + s + ".stats.kills");
				pK.put(k, p);
				pvpboxPlayerKills.add(k);
			}
			Collections.sort(pvpboxPlayerKills);
			Collections.reverse(pvpboxPlayerKills);
			int pseudo = 0;
			for(int i = 0; i < limit; i++) {
				Object[] f = (Object[]) pK.get(pvpboxPlayerKills.get(i)).toArray();
				if(((String) f[pseudo]).contains("_")) MiscellaneousUtils.replaceLast(((String) f[pseudo]), "_", "");
				message += "§6" + (i + 1) + ". " + f[pseudo] + " : §b" + pvpboxPlayerKills.get(i) + " Kills\n";
				if(pK.get(pvpboxPlayerKills.get(i)).size() > 1) {
					if(pseudo == pK.get(pvpboxPlayerKills.get(i)).size() - 1) pseudo = 0;
					else pseudo ++;
				}
			}
			return message;
		} else return "";
	}

	public static int getPlayerKitPlays(Player p, Kit k) {
		return config.getInt("antykacraft.pvpbox.players."+p.getUniqueId()+".stats.kits."+k.getName());
	}

	public static Kit getPlayerMostPlayedKit(Player p) {
		ConfigurationSection kits = config.getConfigurationSection("antykacraft.pvpbox.players."+p.getUniqueId()+".stats.kits");
		Kit k = null;
		int i = 0;
		for(String kitName : kits.getKeys(false)) {
			int plays = config.getInt("antykacraft.pvpbox.players."+p.getUniqueId()+".stats.kits."+kitName);
			if(plays > i) {
				i = plays;
				k = Kit.getKitByName(kitName);
			}
		}
		return k;
	}

	public static Kit getPlayerMostPlayedKit(String uuid) {
		ConfigurationSection kits = config.getConfigurationSection("antykacraft.pvpbox.players."+uuid+".stats.kits");
		Kit k = null;
		int i = 0;
		for(String kitName : kits.getKeys(false)) {
			int plays = config.getInt("antykacraft.pvpbox.players."+uuid+".stats.kits."+kitName);
			if(plays > i) {
				i = plays;
				k = Kit.getKitByName(kitName);
			}
		}
		return k;
	}

	public static Kit getMostPlayedKit() {
		Kit mostPlayed = null;
		ConfigurationSection players = config.getConfigurationSection("antykacraft.pvpbox.players");
		HashMap<Kit, Integer> total = new HashMap<Kit, Integer>();
		for(Kit k : Kit.kits) total.put(k, 0);
		for(String uuid : players.getKeys(false)) {
			ConfigurationSection kits = config.getConfigurationSection("antykacraft.pvpbox.players."+uuid+".stats.kits");
			for(String kitName : kits.getKeys(false)) {
				Kit k = Kit.getKitByName(kitName);
				int plays = config.getInt("antykacraft.pvpbox.players."+uuid+".stats.kits."+kitName);
				int currentPlays = total.get(k);
				total.remove(k);
				total.put(k, currentPlays + plays);
			}
		}
		int i = 0;
		for(Kit k : total.keySet()) {
			if(total.get(k) > i) {
				i = total.get(k);
				mostPlayed = k;
			}
		}
		return mostPlayed;
	}

	public static Kit getLessPlayedKit() {
		Kit lessPlayed = null;
		ConfigurationSection players = config.getConfigurationSection("antykacraft.pvpbox.players");
		HashMap<Kit, Integer> total = new HashMap<Kit, Integer>();
		for(Kit k : Kit.kits) total.put(k, 0);
		for(String uuid : players.getKeys(false)) {
			ConfigurationSection kits = config.getConfigurationSection("antykacraft.pvpbox.players."+uuid+".stats.kits");
			for(String kitName : kits.getKeys(false)) {
				Kit k = Kit.getKitByName(kitName);
				int plays = config.getInt("antykacraft.pvpbox.players."+uuid+".stats.kits."+kitName);
				int currentPlays = total.get(k);
				total.remove(k);
				total.put(k, currentPlays + plays);
			}
		}
		int i = 0;
		for(Kit k : total.keySet()) {
			if(total.get(k) < i || i == 0) {
				i = total.get(k);
				lessPlayed = k;
			}
		}
		return lessPlayed;
	}

	public static int getTotalPlayerPlays(Player p) {
		return config.getInt("antykacraft.pvpbox.players."+p.getUniqueId()+".stats.play");
	}

	public static int getTotalPlayerPlays(String uuid) {
		return config.getInt("antykacraft.pvpbox.players."+uuid+".stats.play");
	}

	public static double getPlayerRatio(Player p) {
		double ratio = 0;
		if(!(getPvPBoxKills(p) == 0) && !(getPvPBoxDeaths(p) == 0)) 
			ratio = (double) (((double) getPvPBoxKills(p)) / ((double) getPvPBoxDeaths(p)));
		return MiscellaneousUtils.round(ratio, 3);
	}

	public static double getFreeRatio(int kills, int deaths) {
		double ratio = 0;
		if(!(kills == 0) && !(deaths == 0)) 
			ratio = (double) ((double) kills / ((double) deaths));
		return MiscellaneousUtils.round(ratio, 3);
	}

	public static String getOfflinePlayerPvPBoxStats(String p, boolean himself) {
		String message = "", namePl = "", uuid = "";
		ConfigurationSection configsec = config.getConfigurationSection("antykacraft.pvpbox.players");
		try {
			Set<String> uuids = configsec.getKeys(false);
			boolean exist = false;
			int kills = 0, death = 0;
			for(String s : uuids) {
				namePl = config.getString("antykacraft.pvpbox.players." + s + ".name");
				if(namePl.equalsIgnoreCase(p)) {
					kills = config.getInt("antykacraft.pvpbox.players." + s + ".stats.kills");
					death = config.getInt("antykacraft.pvpbox.players." + s + ".stats.deaths");
					uuid = s;
					exist = true;
					break;
				}
			}
			if(exist == true) {
				if(himself == false) 	
					message += Constants.PVPBOX_PREFIX + "§aVoici les stats PvPBox de " + namePl + " : \n§6--------------------------------\n";
				else 
					message = Constants.PVPBOX_PREFIX + "§aVoici vos stats PvPBox : \n§6--------------------------------\n";
				message += "§6- Parties: §b" + getTotalPlayerPlays(uuid) + "\n";
				message += "§6- Kills: §b" + kills + "\n";
				message += "§6- Morts: §b" + death + "\n";
				message += "§6- Ratio: §b" + getFreeRatio(kills, death) + "\n";
				try {
					message += "§6- Kit préféré: §b" + getPlayerMostPlayedKit(uuid).getName();
				} catch(NullPointerException npe) {
					message += "§6- Kit préféré: §bAucun";
				}
			} else message = Constants.PVPBOX_PREFIX+"§cLe joueur spécifié n'existe pas ou n'a pas encore fait de PvPBox.";
		} catch(NullPointerException npe) {
			message = Constants.PVPBOX_PREFIX + "§aVoici vos stats PvPBox : \n§6--------------------------------\n";
			message += "§6- Parties: §b0\n";
			message += "§6- Kills: §b0\n";
			message += "§6- Morts: §b0\n";
			message += "§6- Ratio: §b0\n";
			message += "§6- Kit préféré: §bAucun";
		}
		return message;
	}

	public static int getOfflinePlayerPvPBoxKills(String p) {
		int k = 0;
		String namePl;
		ConfigurationSection configsec = config.getConfigurationSection("antykacraft.pvpbox.players");
		Set<String> uuids = configsec.getKeys(false);
		for(String s : uuids) {
			namePl = config.getString("antykacraft.pvpbox.players." + s + ".name");
			if(namePl.equalsIgnoreCase(p)) {
				k = config.getInt("antykacraft.pvpbox.players." + s + ".stats.kills");
				break;
			}
		}
		return k;
	}

	public static int getOfflinePlayerPvPBoxDeaths(String p) {
		int d = 0;
		String namePl;
		ConfigurationSection configsec = config.getConfigurationSection("antykacraft.pvpbox.players");
		Set<String> uuids = configsec.getKeys(false);
		for(String s : uuids) {
			namePl = config.getString("antykacraft.pvpbox.players." + s + ".name");
			if(namePl.equalsIgnoreCase(p)) {
				d = config.getInt("antykacraft.pvpbox.players." + s + ".stats.deaths");
				break;
			}
		}
		return d;
	}

	public static double getOfflinePlayerPvPBoxRatio(String p) {
		return getFreeRatio(getOfflinePlayerPvPBoxKills(p), getOfflinePlayerPvPBoxDeaths(p));
	}

	public static Location getLobbyLocation() {
		double x = config.getDouble("antykacraft.pvpbox.lobby.location.x");
		double y = config.getDouble("antykacraft.pvpbox.lobby.location.y");
		double z = config.getDouble("antykacraft.pvpbox.lobby.location.z");
		float yaw = (float) config.getDouble("antykacraft.pvpbox.lobby.location.yaw");
		float pitch = (float) config.getDouble("antykacraft.pvpbox.lobby.location.pitch");
		return new Location(Bukkit.getWorld(PvPBoxCore.eventWorld), x, y, z, yaw, pitch);
	}

	public static boolean isLobbyDefined() {
		return config.isConfigurationSection("antykacraft.pvpbox.lobby");
	}

	public static List<Kit> getPlayerPurchasedKits(Player p) {
		return getPlayerPurchasedKits(p.getUniqueId().toString());
	}

	public static List<Kit> getPlayerPurchasedKits(String uuid) {
		ArrayList<Kit> kits = new ArrayList<Kit>();
		ConfigurationSection purchases = config.getConfigurationSection("antykacraft.pvpbox.players."+uuid+".purchases.kits");
		for(String kitName : purchases.getKeys(false)) {
			try {
				Kit k = Kit.getKitByName(kitName);
				kits.add(k);
			} catch(NullPointerException npe) {}
		}
		return kits;
	}

	public static int getKitPrice(Kit k) {
		return config.getInt("antykacraft.pvpbox.shop.kits."+k.getName());
	}

	public static List<String> getDisabledKits() {
		if(config.getStringList("antykacraft.pvpbox.kits.disabled").isEmpty()) {
			return new ArrayList<String>();
		} else {
			return config.getStringList("antykacraft.pvpbox.kits.disabled");
		}
	}
	
	public static boolean isEnabled(Kit k) {
		List<String> disabledKits = getDisabledKits();
		return !disabledKits.contains(k.getName());
	}

	/** SETTERS **/

	public static void createNewPvPBoxArena(Player p, String name) {
		config.set("antykacraft.pvpbox.arenas." + name + ".location.x", p.getLocation().getBlockX());
		config.set("antykacraft.pvpbox.arenas." + name + ".location.y", p.getLocation().getBlockY());
		config.set("antykacraft.pvpbox.arenas." + name + ".location.z", p.getLocation().getBlockZ());
		config.set("antykacraft.pvpbox.arenas." + name + ".location.yaw", p.getLocation().getYaw());
		config.set("antykacraft.pvpbox.arenas." + name + ".location.pitch", p.getLocation().getPitch());

		Antykacraft.instance.saveConfig();
	}

	public static void setDefaultPvPBoxArena(String name) {
		config.set("antykacraft.pvpbox.defaultArena", name);
		if(config.getConfigurationSection("antykacraft.pvpbox.arenas").getKeys(false).size() == 0) 
			config.set("antykacraft.pvpbox.arenas.defaultArena", "none");
		Antykacraft.instance.saveConfig();
	}

	public static void removePvPBoxArena(String name) {
		config.set("antykacraft.pvpbox.arenas." + name, null);
		if(config.getConfigurationSection("antykacraft.pvpbox.arenas").getKeys(false).contains(name)) 
			config.set("antykacraft.pvpbox.arenas.defaultArena", "none");
		Antykacraft.instance.saveConfig();
	}

	public static void addKill(Player p) {
		int newKills = getPvPBoxKills(p) + 1;
		setPlayerPoints(p, getPlayerPoints(p)+(int) (getPointsPerKill()*getPointsMultiplier()));
		if(config.getString("antykacraft.pvpbox.players." + p.getUniqueId() + ".name") == null) 
			config.set("antykacraft.pvpbox.players." + p.getUniqueId() + ".name", p.getName());
		config.set("antykacraft.pvpbox.players." + p.getUniqueId() + ".stats.kills", newKills);
		Antykacraft.instance.saveConfig();
	}

	public static void addDeath(Player p) {
		int newDeaths = getPvPBoxDeaths(p) + 1;
		if(config.getString("antykacraft.pvpbox.players." + p.getUniqueId() + ".name") == null) 
			config.set("antykacraft.pvpbox.players." + p.getUniqueId() + ".name", p.getName());
		config.set("antykacraft.pvpbox.players." + p.getUniqueId() + ".stats.deaths", newDeaths);
		Antykacraft.instance.saveConfig();
	}

	public static void setPlayerPoints(Player p, int points) {
		config.set("antykacraft.pvpbox.players." + p.getUniqueId() + ".points", points);
		Antykacraft.instance.saveConfig();
	}

	public static void addPlayerPoints(Player p, int amount) {
		setPlayerPoints(p, getPlayerPoints(p) + amount);
	}

	public static void setPointsMultiplier(double multiplier) {
		config.set("antykacraft.pvpbox.points.multiplier", multiplier);
		Antykacraft.instance.saveConfig();
	}

	public static void setPointsPerKill(int amount) {
		config.set("antykacraft.pvpbox.points.amount", amount);
		Antykacraft.instance.saveConfig();
	}

	public static void setKitsBuyable(boolean buyable) {
		config.set("antykacraft.pvpbox.kitsBuyable", buyable);
		Antykacraft.instance.saveConfig();
	}

	public static void playKit(Player p, Kit k) {
		config.set("antykacraft.pvpbox.players."+p.getUniqueId()+".name", p.getName());
		int totalPlays = config.getInt("antykacraft.pvpbox.players."+p.getUniqueId()+".stats.play");
		config.set("antykacraft.pvpbox.players."+p.getUniqueId()+".stats.play", totalPlays+1);
		int kitPlays = config.getInt("antykacraft.pvpbox.players."+p.getUniqueId()+".stats.kits."+k.getName());
		config.set("antykacraft.pvpbox.players."+p.getUniqueId()+".stats.kits."+k.getName(), kitPlays+1);
		Antykacraft.instance.saveConfig();
	}

	public static void setLobby(Player p) {
		config.set("antykacraft.pvpbox.lobby.location.x", p.getLocation().getBlockX());
		config.set("antykacraft.pvpbox.lobby.location.y", p.getLocation().getBlockY());
		config.set("antykacraft.pvpbox.lobby.location.z", p.getLocation().getBlockZ());
		config.set("antykacraft.pvpbox.lobby.location.yaw", p.getLocation().getYaw());
		config.set("antykacraft.pvpbox.lobby.location.pitch", p.getLocation().getPitch());
		Antykacraft.instance.saveConfig();
	}

	public static void setKitPrice(Kit k, int price) {
		config.set("antykacraft.pvpbox.shop.kits."+k.getName(), price);
		Antykacraft.instance.saveConfig();
	}

	public static void purchaseKit(Player p, Kit k) {
		int price = getKitPrice(k);
		int points = getPlayerPoints(p);
		setPlayerPoints(p, points-price);
		List<Kit> currentKits = null;
		try {
			currentKits = getPlayerPurchasedKits(p);
		} catch(NullPointerException npe) {
			currentKits = new ArrayList<Kit>();
		} finally {
			currentKits.add(k);
			for(Kit kitFinal : currentKits) {
				config.set("antykacraft.pvpbox.players."+p.getUniqueId()+".purchases.kits."+kitFinal.getName(), true);
			}
			Antykacraft.instance.saveConfig();
		}
	}

	public static void addDisabledKit(Kit k) {
		List<String> disabled = getDisabledKits();
		if(isEnabled(k)) {
			disabled.add(k.getName());
			config.set("antykacraft.pvpbox.kits.disabled", disabled);
			Antykacraft.instance.saveConfig();
		}
	}

	public static void removeDisabledKit(Kit k) {
		List<String> disabled = getDisabledKits();
		if(!isEnabled(k)) {
			disabled.remove(k.getName());
			config.set("antykacraft.pvpbox.kits.disabled", disabled);
			Antykacraft.instance.saveConfig();
		}
	}
}
