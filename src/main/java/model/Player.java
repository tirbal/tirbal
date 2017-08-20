package main.java.model;

/**
 * 
 * @author tbarthel
 *
 */
public class Player extends IdentifiableObject implements Comparable<Player> {
	//
	private String player;
	//
	private double elo;

	/**
	 * 
	 * @param player
	 */
	public Player(String player) {
		super();
		this.player = player;
	}
	
	/**
	 * 
	 * @param player
	 * @param elo
	 */
	public Player(String player, double elo) {
		super();
		this.player = player;
		this.elo = elo;
	}

	/**
	 * 
	 * @return
	 */
	public String getPlayer() {
		return player;
	}

	/**
	 * 
	 * @param player
	 */
	public void setPlayer(String player) {
		this.player = player;
	}

	/**
	 * 
	 * @return
	 */
	public double getElo() {
		return elo;
	}

	/**
	 * 
	 * @param elo
	 */
	public void setElo(double elo) {
		this.elo = elo;
	}

	/**
	 * 
	 */
	@Override
	public int compareTo(Player player) {
		// TODO Auto-generated method stub
		return (int) (player.getElo() - this.getElo());
	}

	/**
	 * 
	 */
	@Override
	public String toString() {
		if (this.getElo() != 0) {
			return String.format("%s [Elo %d]", this.getPlayer(), (int) this.getElo());
		} else {
			return String.format("%s - could not estimate Elo", this.getPlayer());
		}
	}
}
