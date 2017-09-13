package main.java.elo;

import java.util.List;
import java.util.Set;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.DecompositionSolver;
import org.apache.commons.math3.linear.QRDecomposition;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.apache.log4j.Logger;

import main.java.model.Team;

/**
 * 
 * @author tbarthel
 *
 */
public class EloIndividualResolver {
	//
	final static Logger logger = Logger.getLogger(EloIndividualResolver.class);
	//
	final double logitFactor = 4.0;

	/**
	 * 
	 * @param teams
	 * @param players
	 * @return
	 */
	public RealVector guessIndividualElo(Set<Team> teams, List<String> players) {
		//
		logger.debug("Guess individual Elo");
		logger.debug("...");

		//
		double[][] matrixCoef = new double[teams.size()][players.size()];
		double[] teamsElo = new double[teams.size()];

		//
		int i = 0;

		//
		for (Team team : teams) {
			double[] vector = buildPlayerVector(players, team);

			teamsElo[i] = team.getCurrentElo() * this.getNbMatchFactor(team.getNbMatchs());
			matrixCoef[i] = vector;

			i++;
		}
		//
		RealMatrix coefficients = new Array2DRowRealMatrix(matrixCoef, false);

		DecompositionSolver solver = new QRDecomposition(coefficients).getSolver();

		RealVector constants = new ArrayRealVector(teamsElo, false);
		RealVector solution = solver.solve(constants);
		logger.debug(String.format("Individual Elo solution %s", solution.toString()));

		return solution;
	}

	/**
	 * 
	 * @param players
	 * @param team
	 * @return
	 */
	private double[] buildPlayerVector(List<String> players, Team team) {
		//
		double[] vector = new double[players.size()];

		int nbPlayer1 = players.indexOf(team.getPlayer1());
		int nbPlayer2 = players.indexOf(team.getPlayer2());

		//
		if (nbPlayer1 != nbPlayer2) {
			vector[nbPlayer1] = 0.5 * this.getNbMatchFactor(team.getNbMatchs());
			vector[nbPlayer2] = 0.5 * this.getNbMatchFactor(team.getNbMatchs());
		} else {
			vector[nbPlayer1] = 1 * this.getNbMatchFactor(team.getNbMatchs());
		}

		return vector;
	}

	/**
	 * logit * ln(x)
	 * 
	 */
	private double getNbMatchFactor(int matchNb) {
		return (1 / (1 + Math.exp(this.logitFactor - ((1.0 / this.logitFactor) * matchNb)))) * Math.log(1 + matchNb);
	}
}
