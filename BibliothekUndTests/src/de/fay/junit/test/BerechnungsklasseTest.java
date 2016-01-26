package de.fay.junit.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class BerechnungsklasseTest {

	Berechnungsklasse testKlasse = null;

	@Before
	public void setUp() throws Exception {
		testKlasse = Berechnungsklasse.getInstance();
	}

	@Test
	public final void testAddiere() {
		int ergebnis = testKlasse.addiere(1, 2);
		assertEquals(3, ergebnis);
	}

	@Test
	public final void testSubtrahiere() {
		int ergebnis = testKlasse.subtrahiere(1, 2);
		assertEquals(-1, ergebnis);
	}

}
