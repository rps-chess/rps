package de.finkbeiner.rps.server;

import java.io.Serializable;

import javafx.scene.image.Image;

public class Figure implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8701324038595745167L;
	int posX;
	int posY;
	String carriedItem;
	int[] typSurroundingField = new int[9];
	
	public int getPosX() {
		return posX;
	}
	public void setPosX(int posX) {
		this.posX = posX;
	}
	public int getPosY() {
		return posY;
	}
	public void setPosY(int posY) {
		this.posY = posY;
	}
	public String getCarriedItem() {
		return carriedItem;
	}
	public void setCarriedItem(String carriedItem) {
		this.carriedItem = carriedItem;
	}
	public int[] getTypSurroundingField() {
		return typSurroundingField;
	}
	public void setTypSurroundingField(int[] typSurroundingField) {
		this.typSurroundingField = typSurroundingField;
	}

	


}
