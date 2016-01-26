package de.fay.serialisierung.test;

import java.io.Serializable;

public class Objekt implements Serializable {

	private int nummer;
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNummer() {
		return nummer;
	}

	public void setNummer(int nummer) {
		this.nummer = nummer;
	}

}
