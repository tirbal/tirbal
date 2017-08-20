package main.java.model;

import org.apache.commons.lang3.StringUtils;

import main.java.exceptions.InstanciationException;

/**
 * 
 * @author tbarthel
 *
 */
public class Team extends IdentifiableObject implements Comparable<Team> {
	//
	private String player1;
	//
	private String player2;
	//
	private int currentElo;
	//
	private int nbMatchs;

	/**
	 * 
	 * @param player1
	 * @param player2
	 * @throws InstanciationException
	 */
	public Team(String player1, String player2) throws InstanciationException {
		super();
		setPlayers(player1, player2);
	}

	/**
	 * 
	 * @param player1
	 * @param player2
	 * @throws InstanciationException
	 */
	private void setPlayers(String player1, String player2) throws InstanciationException {
		if (StringUtils.isEmpty(player1) && StringUtils.isEmpty(player2)) {
			throw new InstanciationException("Players labels are blank");
		} else if (StringUtils.isEmpty(player1)) {
			this.player1 = player2;
			this.player2 = player2;
		} else if (StringUtils.isEmpty(player2)) {
			this.player1 = player1;
			this.player2 = player1;
		} else {
			if (player1.compareTo(player2) < 0) {
				this.player1 = player1;
				this.player2 = player2;
			} else {
				this.player1 = player2;
				this.player2 = player1;
			}
		}
	}

	/**
	 * 
	 * @return
	 */
	public String getPlayer1() {
		return player1;
	}

	/**
	 * 
	 * @param player1
	 */
	public void setPlayer1(String player1) {
		this.player1 = player1;
	}

	/**
	 * 
	 * @return
	 */
	public String getPlayer2() {
		return player2;
	}

	/**
	 * 
	 * @param player2
	 */
	public void setPlayer2(String player2) {
		this.player2 = player2;
	}

	/**
	 * 
	 * @return
	 */
	public int getCurrentElo() {
		return currentElo;
	}

	/**
	 * 
	 * @param currentElo
	 */
	public void setCurrentElo(int currentElo) {
		this.currentElo = currentElo;
	}

	/**
	 * 
	 */
	public String toString() {
		if (currentElo != 0) {
			return String.format("%s & %s [Elo %d] - Played matchs : %d", player1, player2, currentElo, nbMatchs);
		} else {
			return String.format("%s & %s - Played matchs : %d", player1, player2, nbMatchs);
		}
	}

	/**
	 * 
	 * @return
	 */
	public int getNbMatchs() {
		return nbMatchs;
	}

	/**
	 * 
	 * @param nbMatchs
	 */
	public void setNbMatchs(int nbMatchs) {
		this.nbMatchs = nbMatchs;
	}
	
	/**
	 * 
	 * @param team
	 * @return
	 */
	public boolean haveSamePlayers(Team team) {
		return this.player1.equals(team.getPlayer1()) && this.player2.equals(team.getPlayer2());
	}

	/**
	 * 
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + currentElo;
		result = prime * result + ((player1 == null) ? 0 : player1.hashCode());
		result = prime * result + ((player2 == null) ? 0 : player2.hashCode());
		return result;
	}

	/**
	 * 
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Team other = (Team) obj;
		if (currentElo != other.currentElo)
			return false;
		if (player1 == null) {
			if (other.player1 != null)
				return false;
		} else if (!player1.equals(other.player1))
			return false;
		if (player2 == null) {
			if (other.player2 != null)
				return false;
		} else if (!player2.equals(other.player2))
			return false;
		return true;
	}

	/**
	 * 
	 */
	@Override
	public int compareTo(Team team) {
		return team.getCurrentElo() - this.currentElo;
	}
}
