package test.java;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.DecompositionSolver;
import org.apache.commons.math3.linear.QRDecomposition;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.junit.Test;

public class testApacheMath3MMSE {
	
	private double affinityScore(double maxScore, double k) {
		return 1 / (1 + Math.exp(-1 * (k * 10 / maxScore)));
	}
	
	@Test
	public void testMMSEConvergence() {
		//
		Instant start = Instant.now();
		
		//
		RealMatrix coefficients = new Array2DRowRealMatrix(new double[][] { { 1, 1, 0, 0, 0 }, { 0, 1, 1, 0, 0 },
				{ 0, 1, 0, 1, 0 }, { 1, 0, 0, 1, 0 }, { 0, 0, 1, 0, 1 }, { 1, 0, 0, 0, 1 }, {0, 1, 0, 0, 1} }, false);

		DecompositionSolver solver = new QRDecomposition(coefficients).getSolver();

		RealVector constants = new ArrayRealVector(new double[] { 1000, 1100, 800, 650, 700, 675, 700 }, false);
		RealVector solution = solver.solve(constants);
		
		Instant end = Instant.now();

		//
		System.out.println(Duration.between(start, end)); // prints PT1M3.553S
		
		
		// calculate affinity
		double k[] = new double[7];
		k[0] = constants.getEntry(0) - (solution.getEntry(0) + solution.getEntry(1));
		k[1] = constants.getEntry(1) - (solution.getEntry(1) + solution.getEntry(2));
		k[2] = constants.getEntry(2) - (solution.getEntry(1) + solution.getEntry(3));
		k[3] = constants.getEntry(3) - (solution.getEntry(0) + solution.getEntry(3));
		k[4] = constants.getEntry(4) - (solution.getEntry(2) + solution.getEntry(4));
		k[5] = constants.getEntry(5) - (solution.getEntry(0) + solution.getEntry(4));
		k[6] = constants.getEntry(6) - (solution.getEntry(1) + solution.getEntry(4));
		
		//
		double affinity[] = new double[7];
		affinity[0] = this.affinityScore(constants.getEntry(0), k[0]);
		affinity[1] = this.affinityScore(constants.getEntry(1), k[1]);
		affinity[2] = this.affinityScore(constants.getEntry(2), k[2]);
		affinity[3] = this.affinityScore(constants.getEntry(3), k[3]);
		affinity[4] = this.affinityScore(constants.getEntry(4), k[4]);
		affinity[5] = this.affinityScore(constants.getEntry(5), k[5]);
		affinity[6] = this.affinityScore(constants.getEntry(6), k[6]);
		
		System.out.println("solution : " + solution.toString());
		System.out.println("k : " + Arrays.toString(k));
		System.out.println("affinity : " + Arrays.toString(affinity));
	}

}
