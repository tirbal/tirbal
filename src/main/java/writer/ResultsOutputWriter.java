package main.java.writer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collection;

import org.apache.log4j.Logger;

import main.java.model.Ladder;
import main.java.parser.ScoreFileCsvParser;

/**
 * 
 * @author tbarthel
 *
 */
public class ResultsOutputWriter {
	//
	final static Logger logger = Logger.getLogger(ScoreFileCsvParser.class);
	//
	private String outputFilePath;
	//
	private Collection<Ladder> ladders;

	/**
	 * 
	 * @param outputfilepath
	 * @param ladders
	 */
	public ResultsOutputWriter(String outputfilepath, Collection<Ladder> ladders) {
		this.outputFilePath = outputfilepath;
		this.ladders = ladders;
	}

	/**
	 * @throws IOException
	 * 
	 */
	private void createParentFolder() throws IOException {
		logger.debug(String.format("Check if output parent folder needs to be created for file %s", outputFilePath));

		Path path = Paths.get(outputFilePath);

		if (path.getParent() != null && !Files.exists(path.getParent())) {
			logger.debug("Creating output parent folder");
			boolean parentCreation = new File(path.getParent().toString()).mkdirs();

			if (!parentCreation) {
				throw new IOException("Could not create required parent folder");
			}
		}
	}

	/**
	 * @throws IOException
	 * 
	 */
	private void writerLaddersInfo() throws IOException {
		//
		logger.debug("Write ladders info");

		Path path = Paths.get(outputFilePath);

		//
		int idx = 0;

		//
		for (Ladder ladder : ladders) {
			//
			try {
				byte[] strToBytes = ladder.toString().getBytes();
				if (idx != 0) {
					Files.write(path, strToBytes, StandardOpenOption.APPEND);
				} else {
					Files.write(path, strToBytes);
				}
			} catch (IOException e) {
				logger.error("Could not serialize ladder ID " + ladder.getId());
				throw e;
			}
		}
	}

	/**
	 * @throws IOException
	 * 
	 */
	public void writeModelInfo() {
		try {
			//
			createParentFolder();
			//
			writerLaddersInfo();
		} catch (IOException e) {
			logger.error("Could not write whole model info");
			logger.error(e.getMessage());
		}
	}

}
