package main.java.model.group;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import main.java.model.MatchResult;

/**
 * 
 * @author tbarthel
 *
 */
public class MatchResultGroup {
	//
	private List<MatchResult> matchResults;
	
	/**
	 * 
	 */
	public MatchResultGroup() {
		this.matchResults = new ArrayList<MatchResult> ();
	}
	
	/**
	 * 
	 * @return
	 */
	public List<MatchResult> getMatchResults() {
		return matchResults;
	}

	/**
	 * 
	 * @param matchResults
	 */
	public void setMatchResults(List<MatchResult> matchResults) {
		this.matchResults = matchResults;
	}

	/**
	 * 
	 * @param testedMatchResult
	 * @return
	 */
	public boolean sharePlayer(MatchResult testedMatchResult) {
		//
		for (MatchResult matchResult : matchResults) {
			//
			if (!Collections.disjoint(testedMatchResult.getAllPlayers(), matchResult.getAllPlayers())) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 
	 * @param matchResult
	 * @return
	 */
	public boolean addMatchResult(MatchResult matchResult) {
		return this.getMatchResults().add(matchResult);
	}
	
	/**
	 * 
	 * @param matchResults
	 * @return
	 */
	public boolean addAllMatchResults(List<MatchResult> matchResults) {
		return this.getMatchResults().addAll(matchResults);
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isEmpty() {
		return this.getMatchResults().isEmpty();
	}
	
	/**
	 * 
	 */
	public void clearMatchResults() {
		this.getMatchResults().clear();
	}
}
