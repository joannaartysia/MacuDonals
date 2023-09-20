package main;

import javafx.application.Application; 
import javafx.scene.Scene;
import javafx.stage.Stage;
import pages.Login;

public class Main extends Application {
	
	private static Stage mainStage;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		mainStage = primaryStage;
		
		Login loginPg = new Login();
		changeScene(loginPg.showLoginPage());
		mainStage.setResizable(false);
		mainStage.setTitle("MacuDonals");
		mainStage.show();
	}
	
	public static void changeScene (Scene scene) {
		mainStage.setScene(scene);
	}

}
