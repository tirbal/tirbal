package main.java.model;

import java.util.Collection;

/**
 * 
 * @author tbarthel
 *
 */
public class Tirbal {
	//
	private Collection<Ladder> ladders;

	/**
	 * 
	 * @param ladders
	 */
	public Tirbal(Collection<Ladder> ladders) {
		this.ladders = ladders;
	}

	/**
	 * 
	 */
	public void init() {
		//
		for (Ladder ladder : ladders) {
			ladder.init();
			ladder.print();
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public Collection<Ladder> getLadders() {
		return ladders;
	}
}
