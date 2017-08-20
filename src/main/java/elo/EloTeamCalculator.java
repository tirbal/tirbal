package main.java.elo;

import java.util.Collection;

import org.apache.log4j.Logger;

import main.java.model.MatchResult;
import main.java.model.Team;

/**
 * 
 * @author tbarthel
 *
 */
public class EloTeamCalculator {
	//
	final static Logger logger = Logger.getLogger(EloTeamCalculator.class);
	//
	private int defaultPlayerElo = 1500;
	//
	private int basicKFactor = 30;

	/**
	 * 
	 * @param matchResults
	 */
	public void calculateTeamsElo(Collection<MatchResult> matchResults) {
		logger.debug("Calculate teams Elo");
		logger.debug("...");
		
		// foreach match
		//
		for (MatchResult matchResult : matchResults) {
			fixNewTeamElo(matchResult);
			updateTeamEloRanking(matchResult);
		}
	}

	/**
	 * 
	 * @param matchResult
	 */
	private void fixNewTeamElo(MatchResult matchResult) {
		if (matchResult.getTeam1().getCurrentElo() == 0) {
			matchResult.getTeam1().setCurrentElo(defaultPlayerElo);
		}

		if (matchResult.getTeam2().getCurrentElo() == 0) {
			matchResult.getTeam2().setCurrentElo(defaultPlayerElo);
		}
	}

	/**
	 * 
	 * @param matchResult
	 */
	private void updateTeamEloRanking(MatchResult matchResult) {

		double[] E = calculateTeamExpectedValue(matchResult.getTeam1(), matchResult.getTeam2());

		int[] S = calculateS(matchResult.getScoreTeam1(), matchResult.getScoreTeam2());

		double k = calculateKFactor(matchResult);

		double newEloTeam1 = matchResult.getTeam1().getCurrentElo() + (k * (S[0] - E[0]));
		double newEloTeam2 = matchResult.getTeam2().getCurrentElo() + (k * (S[1] - E[1]));

		logger.debug("-------");
		logger.debug("k factor : " + k);
		logger.debug("e1 : " + E[0]);
		logger.debug("e2 : " + E[1]);
		logger.debug(matchResult.getTeam1().toString());
		logger.debug("ELO " + matchResult.getTeam1().getCurrentElo() + " -> " + (int) newEloTeam1);

		logger.debug(matchResult.getTeam2().toString());
		logger.debug("ELO " + matchResult.getTeam2().getCurrentElo() + " -> " + (int) newEloTeam2);
		logger.debug("-------");

		matchResult.getTeam1().setCurrentElo((int) newEloTeam1);
		matchResult.getTeam2().setCurrentElo((int) newEloTeam2);
	}
	
	/**
	 * 
	 * @param team1
	 * @param team2
	 * @return
	 */
	private double[] calculateTeamExpectedValue(Team team1, Team team2) {
		double[] expectedValues = new double[2];
		double[] expR = new double[2];
		double[] R = new double[2];
		
		expR[0] = (double) ((double) team1.getCurrentElo() / 400.0);
		R[0] = Math.pow(10, expR[0]);

		expR[1] = (double) ((double) team2.getCurrentElo() / 400.0);
		R[1] = Math.pow(10, expR[1]);

		expectedValues[0] = (R[0]) / (R[0] + R[1]);
		expectedValues[1] = (R[1]) / (R[0] + R[1]);
		
		return expectedValues;
	}
	
	/**
	 * 
	 * @param scoreTeam1
	 * @param scoreTeam2
	 * @return
	 */
	private int[] calculateS(int scoreTeam1, int scoreTeam2) {
		int[] S = new int[2];

		if (scoreTeam1 > scoreTeam2) {
			S[0] = 1;
			S[1] = 0;
		} else {
			S[0] = 0;
			S[1] = 1;
		}
		
		return S;
	}

	/**
	 * 
	 * @param matchResult
	 * @return
	 */
	private double calculateKFactor(MatchResult matchResult) {
		int maxScore = Math.max(matchResult.getScoreTeam1(), matchResult.getScoreTeam2());
		int minScore = Math.min(matchResult.getScoreTeam1(), matchResult.getScoreTeam2());

		double kFactor = Math.log(1 + maxScore - minScore) * basicKFactor;

		return kFactor;

	}
}
