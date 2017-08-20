package main.java.launch;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.log4j.Logger;

/***
 * 
 * @author tbarthel
 *
 */
public class TirbalLaunch {
	//
	final static Logger logger = Logger.getLogger(TirbalLaunch.class);

	//
	private static final String jarName = new java.io.File(
			TirbalLaunch.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getName();

	private static final Options options = buildOptions();
	private static final HelpFormatter formatter = new HelpFormatter();
	//
	// final static String scoreFilePath =
	// "src/main/resources/babyfoot_results_18082017.csv";
	//
	// final static String outputFilePath =
	// "src/main/resources/output/babyfoot_results_18082017.txt";

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		logger.info("|-------------------------------------------------------------|");
		logger.info("|------------------------TIRBAL-------------------------------|");

		//
		try {
			//
			CommandLine command = buildCommandLine(args);
			//
			launch(command);
		} catch (Exception e) {
			logger.error(e.getMessage());
			logger.error("Tirbal did not complete execution.");
		}

		logger.info("|-------------------------------------------------------------|");
	}

	/**
	 * 
	 * @param command
	 */
	private static void launch(final CommandLine command) {
		//
		if (command.getOptionValue("help") != null
				|| (command.getOptionValue("input") == null || command.getOptionValue("output") == null)) {
			printHelp();
		} else {
			final TirbalLauncher tirbalLauncher = new TirbalLauncher(command.getOptionValue("input"),
					command.getOptionValue("output"));
			tirbalLauncher.launch();
		}
	}

	/**
	 * @throws ParseException
	 * 
	 */
	private static CommandLine buildCommandLine(String[] args) throws ParseException {
		//
		CommandLineParser parser = new DefaultParser();
		CommandLine cmd;

		//
		try {
			cmd = parser.parse(options, args);
		} catch (ParseException e) {
			logger.error(e.getMessage());
			printHelp();
			throw e;
		}

		return cmd;
	}

	/**
	 * 
	 */
	private static Options buildOptions() {
		Options options = new Options();

		Option input = new Option("i", "input", true, "input score file path");
		input.setRequired(false);
		options.addOption(input);

		Option output = new Option("o", "output", true, "output algorithm file path");
		output.setRequired(false);
		options.addOption(output);

		Option help = new Option("h", "help", false, "print this message");
		output.setRequired(false);
		options.addOption(help);

		return options;
	}

	/**
	 * 
	 */
	private static void printHelp() {
		formatter.printHelp(String.format("java -jar %s", jarName), options);
	}
}
