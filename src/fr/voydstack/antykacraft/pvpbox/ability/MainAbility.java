package fr.voydstack.antykacraft.pvpbox.ability;

import org.bukkit.entity.Player;

interface IRightAbility {
	public void run(Player p);
} 

interface IHitAbility {
	public void run(Player p, Player d);
}

interface ILeftAbility {
	public void run(Player p);
}

public abstract class MainAbility {
	protected long cooldown;
	protected long time;
	protected boolean busy = false;

	public MainAbility(long cooldown) {
		this.cooldown = cooldown;
		this.time = 0;
	}

	public MainAbility(int seconds) {
		this(seconds*20L);
	}

	public MainAbility() {
		this(0);
	}

	public long getCooldown() {
		return cooldown;
	}

	public void setCooldown(long cooldown) {
		this.cooldown = cooldown;
	}
	
	public long getTime() {
		return time;
	}
	
	public int getTimeSeconds() {
		return (int) Math.ceil(time / 20D);
	}
	
	public void setTime(long time) {
		this.time = time;
	}
	
	public void addTime(long time) {
		setTime(getTime() + time);
	}
	
	public void addTime(int time) {
		addTime(time*20L);
	}
	
	public void removeTime(long time) {
		setTime(getTime() - time);
	}
	
	public void removeTime(int time) {
		setTime(getTime() - time*20L);
	}
	
	public boolean isBusy() {
		return busy;
	}
	
	public void setBusy(boolean busy) {
		this.busy = busy;
	}
	
	public void initCooldown() {
		setTime(getCooldown());
	}
	
	public static abstract class RightAbility extends MainAbility implements IRightAbility {
		public RightAbility(long cooldown) {
			super(cooldown);
		}
		public RightAbility(int cooldown) {
			super(cooldown);
		}
		public RightAbility() {
			super();
		}
	} 
	
	public static abstract class HitAbility extends MainAbility implements IHitAbility {
		public HitAbility(long cooldown) {
			super(cooldown);
		}
		public HitAbility(int cooldown) {
			super(cooldown);
		}
		public HitAbility() {
			super();
		}
	} 
	
	public static abstract class LeftAbility extends MainAbility implements ILeftAbility {
		public LeftAbility(long cooldown) {
			super(cooldown);
		}
		public LeftAbility(int cooldown) {
			super(cooldown);
		}
		public LeftAbility() {
			super();
		}
	}
}
