package main.java.parser;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;

import com.opencsv.CSVReader;

import main.java.exceptions.InstanciationException;
import main.java.exceptions.TirbalException;
import main.java.model.Ladder;
import main.java.model.MatchResult;
import main.java.model.Team;
import main.java.model.group.MatchResultGroup;
import main.java.model.group.MatchResultsGroups;

/**
 * 
 * @author tbarthel
 *
 */
public class ScoreFileCsvParser {
	//
	final static Logger logger = Logger.getLogger(ScoreFileCsvParser.class);
	//
	public String scoreFilePath;
	//
	private List<Team> teams;

	/**
	 * 
	 * @param scoreFilePath
	 */
	public ScoreFileCsvParser(String scoreFilePath) {
		this.scoreFilePath = scoreFilePath;
		this.teams = new ArrayList<Team>();
	}

	/**
	 * 
	 * @return
	 */
	public Collection<Ladder> extractLadders() {
		Collection<MatchResult> matchResults = this.readMatchResults();

		return buildLadders(matchResults);
	}

	/**
	 * 
	 * @return
	 */
	private Collection<MatchResult> readMatchResults() {
		//
		List<MatchResult> matchResults = new ArrayList<MatchResult>();

		//
		try (CSVReader reader = new CSVReader(new FileReader(this.scoreFilePath), ',', '\'', 2)) {
			// read line by line
			String[] record = null;

			//
			while ((record = reader.readNext()) != null) {
				//
				if (isDataLine(record) && isValidDataLine(record)) {
					try {
						matchResults.add(buildMatchResult(record));
					} catch (TirbalException ex) {
						logger.warn(ex.getMessage());
					}
				} else {
					logger.warn("Line rejected : " + Arrays.toString(record));
				}
			}
		} catch (IOException e) {
			logger.error("Could not read input CSV file");
			logger.error(e.getMessage());
		}

		return matchResults;
	}

	/**
	 * 
	 */
	private Collection<Ladder> buildLadders(Collection<MatchResult> matchResults) {
		final Collection<Ladder> allLadders = new ArrayList<Ladder>();
		MatchResultsGroups matchResultGroups = calculateMatchResultGroups(matchResults);

		for (MatchResultGroup matchResultGroup : matchResultGroups.getMatchResultsGroups()) {
			allLadders.add(new Ladder(matchResultGroup.getMatchResults()));
		}
		return allLadders;
	}

	/**
	 * 
	 * @param matchResults
	 * @return
	 */
	private MatchResultsGroups calculateMatchResultGroups(Collection<MatchResult> matchResults) {
		MatchResultsGroups matchResultGroups = new MatchResultsGroups();

		for (MatchResult matchResult : matchResults) {
			matchResultGroups.addMatchResultGroup(matchResult);
		}

		return matchResultGroups;
	}

	/**
	 * 
	 * @param record
	 * @return
	 * @throws InstanciationException
	 */
	private MatchResult buildMatchResult(String[] record) throws InstanciationException {
		//
		MatchResult matchResult = new MatchResult();

		//
		matchResult.setScoreTeam1(Integer.parseInt(record[2]));
		matchResult.setScoreTeam2(Integer.parseInt(record[3]));

		//
		matchResult.setTeam1(this.getOrBuildTeam(record[0], record[1]));
		matchResult.getTeam1().setNbMatchs(matchResult.getTeam1().getNbMatchs() + 1);
		matchResult.setTeam2(this.getOrBuildTeam(record[4], record[5]));
		matchResult.getTeam2().setNbMatchs(matchResult.getTeam2().getNbMatchs() + 1);

		return matchResult;
	}

	/**
	 * 
	 * @param player1
	 * @param player2
	 * @return
	 * @throws InstanciationException
	 */
	private Team findTeam(String player1, String player2) throws InstanciationException {
		//
		Team foundTeam = null;
		Team newTeam = new Team(player1, player2);

		//
		for (Team team : teams) {
			if (team.haveSamePlayers(newTeam)) {
				foundTeam = team;
				break;
			}
		}

		return foundTeam;
	}

	/**
	 * 
	 * @param player1
	 * @param player2
	 * @return
	 * @throws InstanciationException
	 */
	private Team getOrBuildTeam(String player1, String player2) throws InstanciationException {
		//
		Team returnedTeam = null;
		Team existingTeam = findTeam(player1, player2);

		//
		if (existingTeam != null) {
			returnedTeam = existingTeam;
		} else {
			returnedTeam = new Team(player1, player2);
			this.teams.add(returnedTeam);
		}

		return returnedTeam;
	}

	/**
	 * 
	 * @param record
	 * @return
	 */
	private boolean isDataLine(String[] record) {
		return record[0] != "Team 1" && record[0] != "Joueur 1" && record.length >= 6;
	}

	/**
	 * 
	 * @param record
	 * @return
	 */
	private boolean isValidDataLine(String[] record) {
		// hard to grasp ? just don't forget I wrote this program for fun ^^ !
		boolean fullTeams = (!record[0].isEmpty() && !record[1].isEmpty() && !record[0].equals(record[1]))
				&& (!record[4].isEmpty() && !record[5].isEmpty() && !record[4].equals(record[5]));
		boolean singleTeams = ((!record[0].isEmpty() ^ !record[1].isEmpty()) || (record[0].equals(record[1])))
				&& ((!record[4].isEmpty() ^ !record[5].isEmpty()) || (record[4].equals(record[5])));

		return fullTeams || singleTeams;
	}
}
