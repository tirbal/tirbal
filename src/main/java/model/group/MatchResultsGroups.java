package main.java.model.group;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import main.java.model.MatchResult;
import main.java.parser.ScoreFileCsvParser;

/**
 * 
 * @author tbarthel
 *
 */
public class MatchResultsGroups {
	//
	final static Logger logger = Logger.getLogger(ScoreFileCsvParser.class);
	//
	private List<MatchResultGroup> matchResultsGroups;
	
	/**
	 * 
	 */
	public MatchResultsGroups() {
		this.matchResultsGroups = new ArrayList<MatchResultGroup> ();
	}

	/**
	 * 
	 * @return
	 */
	public List<MatchResultGroup> getMatchResultsGroups() {
		return matchResultsGroups;
	}

	/**
	 * 
	 * @param matchResultsGroups
	 */
	public void setMatchResultsGroups(List<MatchResultGroup> matchResultsGroups) {
		this.matchResultsGroups = matchResultsGroups;
	}

	/**
	 * 
	 */
	private void removeEmptyMatchResultGroups() {
		//
		this.matchResultsGroups = this.matchResultsGroups
                .stream()
                .filter(matchResultGroup -> !matchResultGroup.isEmpty())
                .collect(Collectors.toList());
	}

	/**
	 * 
	 * @param newMatchResult
	 */
	public void addMatchResultGroup(MatchResult newMatchResult) {
		//
		boolean groupsWereRearranged = reaarangeMatchResultGroups(newMatchResult);

		//
		if (!groupsWereRearranged) {
			createNewMatchResultGroup(newMatchResult);
		} else {
			removeEmptyMatchResultGroups();
		}
	}

	/**
	 * 
	 * @param newMatchResult
	 * @return
	 */
	private boolean reaarangeMatchResultGroups(MatchResult newMatchResult) {
		Integer idxLoopFound = null;
		int idx = 0;
		
		//
		for (MatchResultGroup matchResultGroup : matchResultsGroups) {
			if (matchResultGroup.sharePlayer(newMatchResult)) {
				if (idxLoopFound == null) {
					matchResultGroup.addMatchResult(newMatchResult);
					idxLoopFound = idx;
				} else {
					matchResultsGroups.get(idxLoopFound).addAllMatchResults(matchResultGroup.getMatchResults());
					matchResultGroup.clearMatchResults();
				}
			}

			idx++;
		}

		return idxLoopFound != null;
	}

	/**
	 * 
	 * @param newMatchResult
	 */
	private void createNewMatchResultGroup(MatchResult newMatchResult) {
		logger.debug("Create match result groups for : " + newMatchResult.toString());
		MatchResultGroup newMatchResultGroup = new MatchResultGroup();
		newMatchResultGroup.addMatchResult(newMatchResult);
		matchResultsGroups.add(newMatchResultGroup);
	}
}
