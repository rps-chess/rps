package de.fay.junit.test;

public class Berechnungsklasse {

	/*
	 * Singleton-Klasse, weil nur ein Objekt der Klasse benötigt wird.
	 */
	private static Berechnungsklasse instance = null;

	protected Berechnungsklasse() {

	}

	public static Berechnungsklasse getInstance() {
		if (instance == null) {
			instance = new Berechnungsklasse();
		}
		return instance;
	}

	public int addiere(int a, int b) {
		return a + b;
	}

	public int subtrahiere(int a, int b) {
		return a - b;
	}

}
