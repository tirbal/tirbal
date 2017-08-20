package main.java.model;

import java.util.ArrayList;
import java.util.List;

import main.java.exceptions.InstanciationException;

/**
 * 
 * @author tbarthel
 *
 */
public class MatchResult extends IdentifiableObject {
	//
	private Team team1;
	//
	private int scoreTeam1;
	//
	private Team team2;
	//
	private int scoreTeam2;

	/**
	 * 
	 */
	public MatchResult() {
		super();
	}

	/**
	 * 
	 * @param player1
	 * @param player2
	 * @param scoreTeam1
	 * @param scoreTeam2
	 * @param player3
	 * @param player4
	 * @throws InstanciationException
	 */
	public MatchResult(String player1, String player2, int scoreTeam1, int scoreTeam2, String player3, String player4)
			throws InstanciationException {
		super();
		this.team1 = new Team(player1, player2);
		this.scoreTeam1 = scoreTeam1;

		this.team2 = new Team(player3, player4);
		this.scoreTeam2 = scoreTeam2;
	}
	
	/**
	 * 
	 * @return
	 */
	public Team getTeam1() {
		return team1;
	}

	/**
	 * 
	 * @param team1
	 */
	public void setTeam1(Team team1) {
		this.team1 = team1;
	}

	/**
	 * 
	 * @return
	 */
	public int getScoreTeam1() {
		return scoreTeam1;
	}

	/**
	 * 
	 * @param scoreTeam1
	 */
	public void setScoreTeam1(int scoreTeam1) {
		this.scoreTeam1 = scoreTeam1;
	}

	/**
	 * 
	 * @return
	 */
	public Team getTeam2() {
		return team2;
	}

	/**
	 * 
	 * @param team2
	 */
	public void setTeam2(Team team2) {
		this.team2 = team2;
	}

	/**
	 * 
	 * @return
	 */
	public int getScoreTeam2() {
		return scoreTeam2;
	}

	/**
	 * 
	 * @param scoreTeam2
	 */
	public void setScoreTeam2(int scoreTeam2) {
		this.scoreTeam2 = scoreTeam2;
	}

	/**
	 * 
	 * @return
	 */
	public List<String> getAllPlayers() {
		List<String> players = new ArrayList<String>();
		players.add(this.team1.getPlayer1());
		players.add(this.team1.getPlayer2());
		players.add(this.team2.getPlayer1());
		players.add(this.team2.getPlayer2());

		return players;
	}

	/**
	 * 
	 */
	public String toString() {
		return String.format("%s || %d vs %d || %s", team1.toString(), scoreTeam1, scoreTeam2, team2.toString());
	}
}
