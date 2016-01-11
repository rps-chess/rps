package de.finkbeiner.socketTest;

import java.io.Serializable;

public class StreamPackage implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1160186762592927659L;
	Figure figure;
	String message;
	

	public StreamPackage() {
		// TODO Auto-generated constructor stub
	}
	public Figure getFigure() {
		return figure;
	}
	public void setFigure(Figure figure) {
		this.figure = figure;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	

}
