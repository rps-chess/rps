package de.finkbeiner.socketTest;

import java.io.Serializable;

import javafx.scene.image.Image;

public class Figure implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -4590886106930933834L;
	int posX;
	int posY;
	String carriedItem;
	int[] typSurroundingField = new int[9];
	
	public Figure(int posX, int posY, String carriedItem, int[] typSurroundingField) {
		super();
		this.posX = posX;
		this.posY = posY;
		this.carriedItem = carriedItem;
		this.typSurroundingField = typSurroundingField;
	}
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
