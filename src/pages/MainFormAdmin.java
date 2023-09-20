package pages;

import java.io.FileInputStream;  
import java.io.FileNotFoundException;

import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import main.Main;

public class MainFormAdmin{

	Scene scene; 
	BorderPane bPane; 
	GridPane gPane; 
	MenuBar menuBar;
	Menu menu, account;
	MenuItem menu1, menu2, account1;
	Background bg2;
	BackgroundImage bgm;
	
	public void initialize() {
		bPane = new BorderPane();
		gPane = new GridPane();
		
		menuBar = new MenuBar();
		
		menu = new Menu("Menu");
		menuBar.getMenus().add(menu);		
		menu1 = new MenuItem("Manage Food");
		menu2 = new MenuItem("Transactions");
		menu.getItems().add(menu1);
		menu.getItems().add(menu2);
		
		account = new Menu("Account");
		menuBar.getMenus().add(account);
		account1 = new MenuItem("Log Out");
		account.getItems().add(account1);
		
		scene = new Scene(bPane, 900, 640);
	}
	
	private void setLayout() {
		bPane.setTop(menuBar);
		
		bPane.setCenter(gPane); 
		
		Image img = null;
		try {
			img = new Image(new FileInputStream("C:\\Angeline\\Semester 3\\BAD\\\\LAB\\PROJECT\\Logo.jpg"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		bgm = new BackgroundImage(img,null, null, null, null);
		bg2 = new Background(bgm);
		bPane.setBackground(bg2);
		
	}
	
	private void setEvent() {
		menu1.setOnAction(e -> {
			goToManageFoodPage();
		});
		menu2.setOnAction(e -> {
			goToTransactionAdminPage();
		});
		
		account1.setOnAction(e -> {
			goToLogOut();
		});
	}

	public void goToManageFoodPage() {
		ManageFood managePage = new ManageFood();
		Main.changeScene(managePage.showManageFoodPage());
	}
	
	public void goToTransactionAdminPage() {
		TransactionAdmin transPage = new TransactionAdmin();
		Main.changeScene(transPage.showTransactionAdminPage());
	}
	
	public void goToLogOut() {
		Login logOut = new Login();
		Main.changeScene(logOut.showLoginPage());
	}
	
	
	public Scene showMainAdminPage() {
		initialize();
		setLayout();
		setEvent();
		return scene;
	}

}