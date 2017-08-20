package main.java.filter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import main.java.model.MatchResult;
import main.java.model.Team;

/**
 * 
 * @author tbarthel
 *
 */
public class FilterMatchResultsService {
	//
	final static Logger logger = Logger.getLogger(FilterMatchResultsService.class);

	/**
	 * 
	 */
	public FilterMatchResultsService() {
	}

	/**
	 * 
	 * @param matchResults
	 * @return
	 */
	public List<MatchResult> filterMatchResults(Collection<MatchResult> matchResults) {
		//
		logger.info("Filter match results ...");

		//
		boolean init = true;
		int nbMatchResultsRemoved = 0;
		List<MatchResult> currentMatchResults = new ArrayList<MatchResult>(matchResults);
		List<MatchResult> filteredMatchResults = null;

		//
		while (nbMatchResultsRemoved != 0 || init) {
			logger.debug("Filter results iteration ...");
			init = false;

			Map<String, Set<String>> playerTeams = buildPlayersCoplayersMap(currentMatchResults);
			Set<String> invalidPlayers = findInvalidPlayers(playerTeams);
			printInvalidPlayers(invalidPlayers);
			filteredMatchResults = filterInvalidPlayersResults(invalidPlayers, currentMatchResults);
			nbMatchResultsRemoved = (currentMatchResults.size() - filteredMatchResults.size());
			currentMatchResults = filteredMatchResults;
			logger.debug(String.format("Filter %d match results...", nbMatchResultsRemoved));
		}

		return filteredMatchResults;
	}

	/**
	 * 
	 * @param playerTeams
	 * @param matchResults
	 */
	private Map<String, Set<String>> buildPlayersCoplayersMap(Collection<MatchResult> matchResults) {
		//
		Map<String, Set<String>> playerCoplayers = new HashMap<String, Set<String>>();

		//
		initMap(playerCoplayers, matchResults);

		//
		for (MatchResult matchResult : matchResults) {
			addTeamToMap(playerCoplayers, matchResult.getTeam1());
			addTeamToMap(playerCoplayers, matchResult.getTeam2());
		}

		return playerCoplayers;
	}

	/**
	 * 
	 * @param playerTeams
	 * @param matchResults
	 */
	private void initMap(Map<String, Set<String>> playerTeams, Collection<MatchResult> matchResults) {
		//
		for (MatchResult matchResult : matchResults) {
			playerTeams.put(matchResult.getTeam1().getPlayer1(), new HashSet<String>());
			playerTeams.put(matchResult.getTeam1().getPlayer2(), new HashSet<String>());
			playerTeams.put(matchResult.getTeam2().getPlayer1(), new HashSet<String>());
			playerTeams.put(matchResult.getTeam2().getPlayer2(), new HashSet<String>());
		}
	}

	/**
	 * 
	 * @param playerTeams
	 * @param team
	 */
	private void addTeamToMap(Map<String, Set<String>> playerTeams, Team team) {
		//
		// if (!team.getPlayer1().equals(team.getPlayer2())) {
		playerTeams.get(team.getPlayer1()).add(team.getPlayer2());
		playerTeams.get(team.getPlayer2()).add(team.getPlayer1());
		// }
	}

	/**
	 * Find all players having plaid in one single team
	 * 
	 * @param playerTeams
	 * @return
	 */
	private Set<String> findInvalidPlayers(Map<String, Set<String>> playerTeams) {
		Set<String> invalidPlayers = playerTeams.entrySet().stream().filter(a -> a.getValue().size() == 1)
				.collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue())).keySet();

		return invalidPlayers;
	}

	/**
	 * 
	 * @param playersToRemove
	 * @param matchResults
	 * @return
	 */
	private List<MatchResult> filterInvalidPlayersResults(Set<String> playersToRemove,
			Collection<MatchResult> matchResults) {
		// TODO Auto-generated method stub
		List<MatchResult> filteredMatchResults = new ArrayList<MatchResult>();
		String invalidPlayer = null;

		//
		for (MatchResult matchResult : matchResults) {
			boolean removeMatchResult = false;
			List<String> players = matchResult.getAllPlayers();

			//
			for (String player : players) {
				if (playersToRemove.contains(player)) {
					invalidPlayer = player;
					removeMatchResult = true;
				}
			}

			//
			if (!removeMatchResult) {
				filteredMatchResults.add(matchResult);
			} else {
				logger.warn(String.format("Filter match result '%s' because of invalid player '%s'",
						matchResult.toString(), invalidPlayer));
			}
		}

		return filteredMatchResults;
	}

	/**
	 * 
	 */
	private void printInvalidPlayers(Set<String> invalidPlayers) {
		for (String invalidPlayer : invalidPlayers) {
			logger.warn(String.format(
					"'%s' needs to play a new match with a different team in order for his match results to be considered (see README.md)",
					invalidPlayer));
		}
	}
}
