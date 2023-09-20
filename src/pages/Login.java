package pages;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.Connect;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import main.Main;
import model.User;

public class Login {
	Scene scene;
	BorderPane bp;
	GridPane gp;
	Text login, email,  password;
	TextField emailTF;
	PasswordField passwordF;
	Button loginBtn;
	Hyperlink loginHL;
	ArrayList<User> userList;
	VBox vbox;
	

	public void init() {
		bp = new BorderPane();
		gp = new GridPane();
		vbox = new VBox(20);
		
		login = new Text("LOGIN");

		email = new Text("Email");
		password = new Text("Password");

		emailTF = new TextField();
		passwordF = new PasswordField();
		
		loginBtn = new Button("Login");
		
		loginHL = new Hyperlink("Don't have account? Register here!");
		
		userList = new ArrayList<>();
		
		scene = new Scene(bp, 500, 250);
	}

	@SuppressWarnings("static-access")
	public void setLayout() {
		
		gp.add(email, 0, 1);
		gp.add(password, 0, 2);

		gp.add(emailTF, 1, 1);
		gp.add(passwordF, 1, 2);
		
		gp.add(loginBtn, 1, 3);
		loginBtn.setStyle("-fx-font: bold 20px 'arial'");
		loginBtn.setStyle("-fx-background-color: red; -fx-text-fill:white");
		
		bp.setTop(login);
		bp.setMargin(login, new Insets(25,0,0,0));
		bp.setAlignment(login, Pos.CENTER);
		login.setStyle("-fx-font: bold 20px 'arial'");
		
		bp.setBottom(loginHL);
	
		gp.setHgap(20);
		gp.setVgap(20);
		bp.setCenter(gp);
		gp.setAlignment(Pos.CENTER);
		
		vbox.getChildren().addAll(loginBtn,loginHL);
		bp.setBottom(vbox);
		vbox.setAlignment(Pos.CENTER);
		
		bp.setStyle("-fx-background-color: gold");
	}


//	@SuppressWarnings("unlikely-arg-type")
	public void setEvent() {
		//Hyperlink To Register
		loginHL.setOnMouseClicked(e -> {
			goToRegisterPage();
		});
		
		//Empty email & password
		loginBtn.setOnMouseClicked(e -> {
			if (passwordF.getText().isEmpty() || emailTF.getText().isEmpty()) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Error");
				alert.setContentText("Email/Password cannot be empty!");
				alert.showAndWait();
				
				return;
			} else {
				userList.clear();
				Connect connect = Connect.getConnection();
				String query = String.format("select * from `user`");
				ResultSet rs = connect.executeQuery(query);
				
				try {
					while(rs.next()) {
						int idrs = rs.getInt("id");
						String usernamers = rs.getString("username");
						String passwordrs = rs.getString("password");
						String emailrs = rs.getString("email");
						String phoners = rs.getString("phone_number");
						String addressrs = rs.getString("address");
						String genderrs = rs.getString("gender");
						String rolers = rs.getString("role");
						String nationalityrs = rs.getString("nationality");
						
						User users = new User(idrs, usernamers, passwordrs, emailrs, phoners, addressrs, genderrs, rolers, nationalityrs);
						userList.add(users);
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					
				}
				String check = "";
				int index = 0;
				for (int i = 0; i < userList.size(); i++) {
					if(emailTF.getText().equals(userList.get(i).getEmail()) && passwordF.getText().equals(userList.get(i).getPassword())) {
						check = "yes";
						index = i;
					}
				}
				
				if (check.equals("yes")) {
					if (userList.get(index).getRole().equalsIgnoreCase("user")) {
						User.currentUser = userList.get(index);
						goToMainFormUser();
					} else if (userList.get(index).getRole().equalsIgnoreCase("administrator")) {
						goToMainFormAdmin();
					}
				} else {
					Alert alertInvalidEmail = new Alert(AlertType.ERROR);
					alertInvalidEmail.setTitle("Error");
					alertInvalidEmail.setHeaderText("Error");
					alertInvalidEmail.setContentText("Invalid email/password!");
					alertInvalidEmail.showAndWait();
				}
			}
		});
	}
		
	public void goToRegisterPage() {
		Register registerPage = new Register();
		Main.changeScene(registerPage.showRegisterPage());
	}
	
	public void goToMainFormUser() {
		MainFormUser mainUserPage = new MainFormUser();
		Main.changeScene(mainUserPage.showMainUserPage());
	}
	
	public void goToMainFormAdmin() {
		MainFormAdmin mainAdminPage = new MainFormAdmin();
		Main.changeScene(mainAdminPage.showMainAdminPage());
	}

	public Scene showLoginPage() {
		init();
		setLayout();
		setEvent();
		return scene;
	}
	
}