package fr.voydstack.antykacraft.scoreboard;

import org.bukkit.scoreboard.ScoreboardManager;

public class ScoreboardHandler {
	private ScoreboardManager scoreboardManager;
	private TeamsHandler teamsHandler;
	
	public ScoreboardHandler(ScoreboardManager scoreboardManager) {
		setScoreboardManager(scoreboardManager);
		setTeamsHandler(new TeamsHandler(this));
	}
	
	public ScoreboardManager getScoreboardManager() {
		return scoreboardManager;
	}
	
	public void setScoreboardManager(ScoreboardManager scoreboardManager) {
		this.scoreboardManager = scoreboardManager;
	}
	
	public TeamsHandler getTeamsHandler() {
		return teamsHandler;
	}
	
	public void setTeamsHandler(TeamsHandler teamsHandler) {
		this.teamsHandler = teamsHandler;
	}
}
