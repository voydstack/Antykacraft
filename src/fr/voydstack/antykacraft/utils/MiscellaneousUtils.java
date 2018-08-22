package fr.voydstack.antykacraft.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class MiscellaneousUtils {
	public static String wordMaj(String word) {
		return word.replaceFirst(".", (word.charAt(0) + "").toUpperCase());
	}
	public static int calculateTicks(int seconds) {
		return seconds * 20;
	}

	public static double round(double value, int places) {
		if (places < 0) throw new IllegalArgumentException();
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

	public static String replaceLast(String text, String regex, String replacement) {
		return text.replaceFirst("(?s)(.*)" + regex, "$1" + replacement);
	}

	public static Vector setAngle(Player p, float ang, double mul) {
		double pitch = ((p.getLocation().getPitch() + 90) * Math.PI) / 180;
		double yaw  = ((p.getLocation().getYaw() + ang)  * Math.PI) / 180;
		double x = Math.sin(pitch) * Math.cos(yaw);
		double y = Math.sin(pitch) * Math.sin(yaw);
		double z = Math.cos(pitch);
		return new Vector(x, z, y).multiply(mul);
	}
	
	public static Vector getVectorToLocationFast(Location a, Location b) {
		double dX = a.getX() - b.getX();
		double dY = a.getY() - b.getY();
		double dZ = a.getZ() - b.getZ();

		double yaw = Math.atan2(dZ, dX);
		double pitch = Math.atan2(Math.sqrt(dZ * dZ + dX * dX), dY) + Math.PI;

		double X = Math.sin(pitch) * Math.cos(yaw);
		double Y = Math.sin(pitch) * Math.sin(yaw);
		double Z = Math.cos(pitch);

		return new Vector(X, Z, Y);
	}
	
	public static Vector getVectorToLocationFast(Location a, Location b, double multiplicator) {
		return getVectorToLocationFast(a, b).multiply(multiplicator);
	}

	public static Vector getVectorToLocation(Location a, Location b, double multiplicator) {
		return a.subtract(b).toVector().multiply(multiplicator);
	}

	public static Vector getVectorToLocation(Location a, Location b) {
		return a.subtract(b).toVector();
	}
	
	public static boolean isOnSameBlock(Entity a, Entity b) {
		Location aloc = a.getLocation();
		Location bloc = b.getLocation();
		int alocx = aloc.getBlockX(),
			alocz = aloc.getBlockZ();
		int blocx = bloc.getBlockX(),
			blocz = bloc.getBlockZ();
		if(alocx == blocx && alocz == blocz) return true;
		else return false;
	}
}
