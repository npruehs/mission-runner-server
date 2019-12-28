package de.npruehs.missionrunner.server.account;

public class Account {
	private final String id;
	private final int level;
	private final int score;

	public Account(String id, int level, int score) {
		this.id = id;
		this.level = level;
		this.score = score;
	}

	public String getId() {
		return id;
	}

	public int getLevel() {
		return level;
	}

	public int getScore() {
		return score;
	}
}
