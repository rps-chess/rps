package de.fay.serialisierung.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializingObject {

	private static SerializingObject instance = null;

	/*
	 * Erstellung als Singleton-Klasse, da nur ein Objekt von dieser Klasse
	 * benötigt wird
	 */
	protected SerializingObject() {

	}

	public static SerializingObject getInstance() {
		if (instance == null) {
			instance = new SerializingObject();
		}

		return instance;
	}

	public void serialize(Object object, String path) {

		/*
		 * Serialisierung über einen Anschlussstrom (FileOutputStream) auf
		 * niedriger Ebene und einem darüberliegenden Verkettungsstrom auf
		 * höherer Ebene. Verkettungsstrom wird nicht benötigt wenn zum Beispiel
		 * nur Bytecode geschrieben wird.
		 */
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(path);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(object);
			objectOutputStream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Object deSerialize(String path) {

		/*
		 * Deserialisierung über einen Anschlussstrom (FileInputStream) auf
		 * niedriger Ebene und einem darüberliegenden Verkettungsstrom auf
		 * höherer Ebene. Verkettungsstrom wird nicht benötigt wenn zum Beispiel
		 * nur Bytecode geschrieben wird.
		 */

		Object returnObject = null;
		try {
			FileInputStream fileInputStream = new FileInputStream(path);
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
			returnObject = objectInputStream.readObject();
			objectInputStream.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnObject;
	}

}
