package de.npruehs.missionrunner.server.localization;

public class LocalizationData {
	private String hash;
	
	private LocalizedString[] strings;
	
	public LocalizationData() {
	}

	public LocalizationData(String hash, LocalizedString[] strings) {
		this.hash = hash;
		this.strings = strings;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public LocalizedString[] getStrings() {
		return strings;
	}

	public void setStrings(LocalizedString[] strings) {
		this.strings = strings;
	}
}
