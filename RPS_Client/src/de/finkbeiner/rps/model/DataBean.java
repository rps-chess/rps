package de.finkbeiner.rps.model;

import javafx.stage.Stage;

public class DataBean {   
		   private Stage primaryStage = null;   
		   
		    
		   public DataBean(Stage primaryStage) {
		      this.primaryStage = primaryStage;

		   }
		 

		   public Stage getPrimaryStage() {
		      return primaryStage;
		   }
		}

