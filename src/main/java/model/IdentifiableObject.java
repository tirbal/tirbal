package main.java.model;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * @author tbarthel
 *
 */
public class IdentifiableObject {
	//
	private static AtomicInteger idSequence = new AtomicInteger(0);
	//
	private final int id;

	/**
	 * 
	 */
	public IdentifiableObject() {
		this.id = idSequence.getAndIncrement();
	}

	public int getId() {
		return id;
	}
}
