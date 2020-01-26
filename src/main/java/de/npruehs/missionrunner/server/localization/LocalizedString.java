package de.npruehs.missionrunner.server.localization;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.google.common.base.Charsets;
import com.google.common.hash.Funnel;
import com.google.common.hash.PrimitiveSink;

@Entity
public class LocalizedString {
	@Id
	private String id;
	private String en;
	private String de;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getEn() {
		return en;
	}
	
	public void setEn(String en) {
		this.en = en;
	}
	
	public String getDe() {
		return de;
	}
	
	public void setDe(String de) {
		this.de = de;
	}
	
	public static class LocalizedStringFunnel implements Funnel<LocalizedString> {
		private static final long serialVersionUID = -5330502644385219512L;

		@Override
		  public void funnel(LocalizedString string, PrimitiveSink into) {
		    into
		        .putString(string.en, Charsets.UTF_8)
		        .putString(string.de, Charsets.UTF_8);
		};
	}
}
