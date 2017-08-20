package main.java.elo;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import main.java.model.MatchResult;
import main.java.model.Player;
import main.java.model.Team;

/**
 * 
 * @author tbarthel
 *
 */
public class EloService {
	//
	private EloIndividualResolver eloIndividualResolver;
	//
	private EloTeamCalculator eloTeamCalculator;

	/**
	 * 
	 */
	public EloService() {
		this.eloIndividualResolver = new EloIndividualResolver();
		this.eloTeamCalculator = new EloTeamCalculator();
	}

	/**
	 * 
	 * @param matchResults
	 */
	public void calculateTeamsElo(Collection<MatchResult> matchResults) {
		this.eloTeamCalculator.calculateTeamsElo(matchResults);
	}

	/**
	 * 
	 * @param teams
	 * @param players
	 */
	public void guessIndividualPlayerElo(Set<Team> teams, Set<Player> players) {
		//
		List<String> playerNames = players.stream().map(Player::getPlayer).collect(Collectors.toList());

		//
		double[] elo = this.eloIndividualResolver.guessIndividualElo(teams, playerNames).toArray();

		int idx = 0;
		
		//
		for (Player player : players) {
			player.setElo(elo[idx]);
			idx++;
		}
	}
}
