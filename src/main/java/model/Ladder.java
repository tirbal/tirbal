package main.java.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.math3.linear.SingularMatrixException;
import org.apache.log4j.Logger;

import main.java.elo.EloService;

/**
 * 
 * @author tbarthel
 *
 */
public class Ladder extends IdentifiableObject {
	//
	final static Logger logger = Logger.getLogger(Ladder.class);

	//
	private List<MatchResult> matchResults;
	//
	private Set<Team> teams;
	//
	private Set<Player> players;
	//
	private EloService eloService = new EloService();

	/**
	 * 
	 */
	public Ladder() {
		super();
		this.teams = new HashSet<Team>();
		this.players = new HashSet<Player>();
	}

	/**
	 * 
	 * @param matchResults
	 */
	public Ladder(List<MatchResult> matchResults) {
		super();
		this.matchResults = matchResults;
		this.teams = new HashSet<Team>();
		this.players = new HashSet<Player>();
	}

	/**
	 * 
	 */
	public void print() {
		logger.info("-------------------------------------------------------------");
		logger.info("------------------------LADDER-------------------------------");

		List<Team> teams = new ArrayList<Team>(this.teams);
		Collections.sort(teams);

		logger.info("-------------------------------------------------------------");
		logger.info("-- >> Teams");

		//
		for (Team team : teams) {
			logger.info(team.toString());
		}

		logger.info("-------------------------------------------------------------");
		logger.info("-- >> Players");
		List<Player> players = new ArrayList<Player>(this.players);
		Collections.sort(players);

		//
		for (Player player : players) {
			logger.info(player.toString());
		}

		logger.info("-------------------------------------------------------------\n\n");
	}

	/**
	 * 
	 */
	public void init() {
		this.calculateTeamsElo();
		this.initTeams();
		this.initPlayers();
		this.guessIndividualPlayerElo();
	}

	/**
	 * 
	 */
	private void calculateTeamsElo() {
		this.eloService.calculateTeamsElo(matchResults);
	}

	/**
	 * 
	 */
	private void initTeams() {
		//
		for (MatchResult matchResult : matchResults) {
			this.teams.add(matchResult.getTeam1());
			this.teams.add(matchResult.getTeam2());
		}

		// makes the program crash (sometimes) for some reason
		// if (logger.isDebugEnabled()) {
		for (Team team : this.teams) {
			logger.debug("Teams : " + team.toString());
		}
		// }
	}

	/**
	 * 
	 */
	private void initPlayers() {
		//
		Set<String> players = new TreeSet<String>();

		//
		for (Team team : teams) {
			players.add(team.getPlayer1());
			players.add(team.getPlayer2());
		}

		// makes the program crash (sometimes) for some reason
		// if (logger.isDebugEnabled()) {
		for (String player : players) {
			this.players.add(new Player(player));
			logger.debug("Player : " + player);
		}
		// }
	}

	/**
	 * 
	 */
	private void guessIndividualPlayerElo() {
		//
		try {
			this.eloService.guessIndividualPlayerElo(teams, players);
		} catch (SingularMatrixException ex) {
			logger.error("No individual ELO could be computed for the ladder because it is too small");
			logger.error(ex.getMessage());
		}
	}

	/**
	 * 
	 */
	@Override
	public String toString() {
		StringBuilder sbModel = new StringBuilder();
		sbModel.append("-------------------------------------------------------------\n");
		sbModel.append("------------------------LADDER-------------------------------\n");

		List<Team> teams = new ArrayList<Team>(this.teams);
		Collections.sort(teams);

		sbModel.append("-------------------------------------------------------------\n");
		sbModel.append("-- >> Teams\n");

		//
		for (Team team : teams) {
			sbModel.append(team.toString() + "\n");
		}

		sbModel.append("-------------------------------------------------------------\n");
		sbModel.append("-- >> Players\n");
		List<Player> players = new ArrayList<Player>(this.players);
		Collections.sort(players);

		//
		for (Player player : players) {
			sbModel.append(player.toString() + "\n");
		}

		sbModel.append("-------------------------------------------------------------\n\n");

		return sbModel.toString();
	}
}
