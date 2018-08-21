package fr.voydstack.antykacraft.scoreboard;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class TeamsHandler {
	private List<String> teamsName = new ArrayList<String>();
	private ScoreboardHandler scoreboardHandler;
	
	public TeamsHandler(ScoreboardHandler scoreboardHandler) {
		setScoreboardHandler(scoreboardHandler);
		Scoreboard scoreboard = scoreboardHandler.getScoreboardManager().getMainScoreboard();
		for(Team team : scoreboard.getTeams()) {
			teamsName.add(team.getName());
		}
	}
	
	public ScoreboardHandler getScoreboardHandler() {
		return scoreboardHandler;
	}
	
	public void setScoreboardHandler(ScoreboardHandler scoreboardHandler) {
		this.scoreboardHandler = scoreboardHandler;
	}
	
	public List<String> getTeamsName() {
		return teamsName;
	}
	
	public void setTeamsName(List<String> teamsName) {
		this.teamsName = teamsName;
	}
	
	public Team getPlayerTeam(Player player) {
		Scoreboard scoreboard = scoreboardHandler.getScoreboardManager().getMainScoreboard();
		return scoreboard.getEntryTeam(player.getName());
	}
}