package de.npruehs.missionrunner.server.account;

public class Account {
	private final String id;
	private final String name;
	private final int level;
	private final int score;

	public Account(String id, String name, int level, int score) {
		this.id = id;
		this.name = name;
		this.level = level;
		this.score = score;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public int getLevel() {
		return level;
	}

	public int getScore() {
		return score;
	}
}
