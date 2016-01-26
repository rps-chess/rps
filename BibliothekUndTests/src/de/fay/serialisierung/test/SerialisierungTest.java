package de.fay.serialisierung.test;

public class SerialisierungTest {

	public static void main(String[] args) {

		Objekt myObject = new Objekt();
		myObject.setName("TestObjekt");
		myObject.setNummer(1);

		SerializingObject mySerializingObject = SerializingObject.getInstance();
		mySerializingObject.serialize(myObject, "SerialisierungsTest.ser");

		Objekt mySerializedObject = (Objekt) mySerializingObject.deSerialize("SerialisierungsTest.ser");

		System.out.println("Der Name des Objekts lautet " + mySerializedObject.getName());
		System.out.println("Die Nummer des Objekts lautet " + mySerializedObject.getNummer());
	}

}
