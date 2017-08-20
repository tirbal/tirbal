package main.java.launch;

import java.io.File;
import java.util.Collection;

import org.apache.log4j.Logger;

import main.java.model.Ladder;
import main.java.model.Tirbal;
import main.java.parser.ScoreFileCsvParser;
import main.java.writer.ResultsOutputWriter;

/**
 * 
 * @author tbarthel
 *
 */
public class TirbalLauncher {
	//
	final static Logger logger = Logger.getLogger(TirbalLauncher.class);
	//
	final String scoreFilePath;
	//
	final String outputFilePath;

	/**
	 * 
	 * @param optionValue
	 * @param optionValue2
	 */
	public TirbalLauncher(final String scoreFilePath, final String outputFilePath) {
		this.scoreFilePath = scoreFilePath;
		this.outputFilePath = outputFilePath;
	}

	/**
	 * 
	 * @param scoreFilePath
	 * @param outputFilePath
	 */
	public void launch() {
		//
		logger.info(String.format("Read input score CSV file '%s' ...", scoreFilePath));
		ScoreFileCsvParser csvParser = new ScoreFileCsvParser(scoreFilePath);
		//
		Collection<Ladder> ladders = csvParser.extractLadders();
		//
		logger.info("Tirbal execution ...");
		Tirbal tirbal = new Tirbal(ladders);
		tirbal.init();
		
		//
		logger.info(String.format("Write to output file '%s' ...", outputFilePath));
		ResultsOutputWriter resultsOutputWriter = new ResultsOutputWriter(outputFilePath, tirbal.getLadders());
		//
		resultsOutputWriter.writeModelInfo();
	}
}
