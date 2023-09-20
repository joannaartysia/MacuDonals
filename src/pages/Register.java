package pages;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


import database.Connect;
import main.Main;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.User;

public class Register {
	Scene scene;
	BorderPane bp;
	GridPane gp;
	VBox vbox;
	VBox vbox1;
	FlowPane fpGender;
	Text register, username, email,  password, confirm, gender, address, phone_number, nationality;
	TextField usernameTF, emailTF, phone_numberTF;
	PasswordField passwordF, confirmF;
	TextArea addressTA;
	RadioButton maleRB, femaleRB;
	ToggleGroup genderTG;
	ComboBox<String> nationalityChoose;
	Button registerBtn;
	Hyperlink registerHL;
	ArrayList<User> userList;
	

	@SuppressWarnings("static-access")
	public void init() {
		bp = new BorderPane();
		gp = new GridPane();
		vbox = new VBox(20);
		
		register = new Text("REGISTER");
		bp.setTop(register);
		bp.setMargin(register, new Insets(25,0,0,0));
		bp.setAlignment(register, Pos.CENTER);
		register.setStyle("-fx-font: bold 20px 'arial'");

		username = new Text("Name");
		email = new Text("Email");
		password = new Text("Password");
		confirm = new Text("Confirm");
		gender = new Text("Gender");
		address = new Text("Address");
		phone_number = new Text("Phone Number");
		nationality = new Text("Nationality");

		usernameTF = new TextField();
		emailTF = new TextField();
		passwordF = new PasswordField();
		confirmF = new PasswordField();
		addressTA = new TextArea();
		phone_numberTF = new TextField();
		
		maleRB = new RadioButton("Male");
		femaleRB = new RadioButton("Female");
		
		registerBtn = new Button("Register");
		
		registerHL = new Hyperlink("Already have account? Login here!");
		
		userList = new ArrayList<>();
		
		scene = new Scene(bp, 900, 640);
	}

	public void setLayout() {
		gp.add(username, 0, 0);
		gp.add(email, 0, 1);
		gp.add(password, 0, 2);
		gp.add(confirm, 0, 3);
		gp.add(gender, 0, 4);
		gp.add(phone_number, 0, 5);
		gp.add(address, 0, 6);
		
		
		gp.add(nationality, 0, 7);

		gp.add(usernameTF, 1, 0);
		gp.add(emailTF, 1, 1);
		gp.add(passwordF, 1, 2);
		gp.add(confirmF, 1, 3);
		gp.add(phone_numberTF, 1, 5);
		gp.add(addressTA, 1, 6);
		
		genderTG = new ToggleGroup();
		maleRB.setToggleGroup(genderTG);
		femaleRB.setToggleGroup(genderTG);
		fpGender = new FlowPane();
		fpGender.getChildren().addAll(maleRB, femaleRB);
		fpGender.setHgap(20);
		fpGender.setAlignment(Pos.CENTER);
		gp.add(fpGender, 1, 4);
		
		nationalityChoose = new ComboBox<String>();
		nationalityChoose.getItems().add("Indonesia");
		nationalityChoose.getItems().add("Malysia");
		nationalityChoose.getItems().add("Singapore");
		nationalityChoose.getItems().add("Laos");
		nationalityChoose.getItems().add("Kamboja");
		nationalityChoose.setPrefWidth(500);
		gp.add(nationalityChoose, 1, 7);
		
		gp.add(registerBtn, 1, 8);
		registerBtn.setStyle("-fx-font: bold 20px 'arial'");
		registerBtn.setStyle("-fx-background-color: red; -fx-text-fill:white");
		
		gp.setPadding(new Insets(20, 20, 20, 20));
		gp.setHgap(20);
		gp.setVgap(20);
		bp.setCenter(gp);
		gp.setAlignment(Pos.CENTER);
		
		bp.setBottom(vbox);
		vbox.getChildren().addAll(registerBtn,registerHL);
		vbox.setAlignment(Pos.CENTER);

		bp.setStyle("-fx-background-color: gold");
		gp.setPadding(new Insets(20));
		
	}

	public void setEvent() {
		registerHL.setOnMouseClicked(e -> {
			goToLoginPage();
		});
		
		registerBtn.setOnMouseClicked(e -> {
				String username = usernameTF.getText();
				String email = emailTF.getText();
				String password = passwordF.getText();
				String address = addressTA.getText();
				String phone_number = phone_numberTF.getText();
				String nationality = nationalityChoose.getValue();
				String role = "user";
				String genderUser = "";
				if (maleRB.isSelected()) {
					genderUser = "male";
				} else if (femaleRB.isSelected()) {
					genderUser = "female";
				}

			if (usernameTF.getText().isEmpty() || 
					emailTF.getText().isEmpty() ||
					passwordF.getText().isEmpty() ||
					confirmF.getText().isEmpty() ||
					phone_numberTF.getText().isEmpty() ||
					addressTA.getText().isEmpty() ) {
				Alert alertEmpty = new Alert(AlertType.ERROR);
				alertEmpty.setTitle("Error");
				alertEmpty.setHeaderText("Error");
				alertEmpty.setContentText("Please fill all the column!");
				alertEmpty.showAndWait();
				
				return;
				
			} else if (usernameTF.getText().length() < 5 || usernameTF.getText().length() > 20){
				Alert alertName = new Alert(AlertType.ERROR);
				alertName.setTitle("Error");
				alertName.setHeaderText("Error");
				alertName.setContentText("Name must be between 5 - 20 characters");
				alertName.showAndWait();
				
				return;
			}
			
			//Register error password
			else if (passwordF.getText().length() < 5 || passwordF.getText().length() > 20 ) {
				Alert alertPass = new Alert(AlertType.ERROR);
				alertPass.setTitle("Error");
				alertPass.setHeaderText("Error");
				alertPass.setContentText("Password must be between 5 - 20 characters");
				alertPass.showAndWait();
				
				return;
				
			} else if (!isAlphaNumeric(passwordF.getText())) {
				Alert alertPass = new Alert(AlertType.ERROR);
				alertPass.setTitle("Error");
				alertPass.setHeaderText("Error");
				alertPass.setContentText("Password must be alpha numeric");
				alertPass.showAndWait();
				
				return;
			}
	
			//Register error confirm password
			else if (!confirmF.getText().equals(passwordF.getText())) {
				Alert alertCPass = new Alert(AlertType.ERROR);
				alertCPass.setTitle("Error");
				alertCPass.setHeaderText("Error");
				alertCPass.setContentText("Confirm password must same with password");
				alertCPass.showAndWait();
				
				return;
			}
			
			//Register error email
			else if (!emailTF.getText().contains("@") || !emailTF.getText().endsWith(".com")) {
				Alert alertEmail = new Alert(AlertType.ERROR);
				alertEmail.setTitle("Error");
				alertEmail.setHeaderText("Error");
				alertEmail.setContentText("Email must in email format (@domain.com)");
				alertEmail.showAndWait();
				
				return;
			}//Register error phone number
			else if (!isNumeric(phone_numberTF.getText())) {
				Alert alertPhone = new Alert(AlertType.ERROR);
				alertPhone.setTitle("Error");
				alertPhone.setHeaderText("Error");
				alertPhone.setContentText("Phone Number must be numeric");
				alertPhone.showAndWait();
				
				return;
			}
			
			else if (!addressTA.getText().endsWith("street")) {
				Alert alertAddress = new Alert(AlertType.ERROR);
				alertAddress.setTitle("Error");
				alertAddress.setHeaderText("Error");
				alertAddress.setContentText("Address must end with ‘street’");
				alertAddress.showAndWait();
				
				return;
			}
			
			else if (!maleRB.isSelected() && !femaleRB.isSelected()) {
				Alert alertGender = new Alert(AlertType.ERROR);
				alertGender.setTitle("Error");
				alertGender.setHeaderText("Error");
				alertGender.setContentText("Gender must be selected, either ‘Male’ or ‘Female’");
				alertGender.showAndWait();
				
				return;
			}
			
			String n = nationalityChoose.getSelectionModel().getSelectedItem();
			
			if (n == null || n.isEmpty()) {
				Alert alertNationality = new Alert(AlertType.ERROR);
				alertNationality.setTitle("Error");
				alertNationality.setHeaderText("Error");
				alertNationality.setContentText("Nationality must be chosen");
				alertNationality.showAndWait();
				
				return;
			}
			
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
			
			//Register error uniq email
			for (int i = 0; i < userList.size(); i++) {
				if (emailTF.getText().equals(userList.get(i).getEmail())) {
					Alert alertEmail = new Alert(AlertType.ERROR);
					alertEmail.setTitle("Error");
					alertEmail.setHeaderText("Error");
					alertEmail.setContentText("This email already registered");
					alertEmail.showAndWait();
					
					return ;
				} 
			}
			
			@SuppressWarnings("unused")
			Connect connects = Connect.getConnection();
			String querys = String.format("INSERT INTO `user`(`id`, `username`, `password`, `email`, `phone_number`, `address`, `gender`, `role`, `nationality`) VALUES ('%d','%s','%s','%s','%s','%s','%s','%s','%s')", 0, username, password, email, phone_number, address, genderUser, role, nationality);
			connect.executeUpdate(querys);
			
				Alert success = new Alert(AlertType.INFORMATION);
				success.setTitle("Information");
				success.setHeaderText("Information");
				success.setContentText("Your account is sucessfully registered!");
				success.showAndWait();
				
			goToLoginPage();	
		});
		
	}

	private boolean isNumeric(String str) {
		for(Character c : str.toCharArray()) {
			if(!Character.isDigit(c)) {
				return false;
			}
		}
		return true;
	}
	
	private boolean isAlphaNumeric(String str) {
		for(Character c : str.toCharArray()) {
			if(!Character.isAlphabetic(c) && !Character.isDigit(c)) {
				return false;
			}
		}
		return true;
	}
	
	
	public void goToLoginPage() {
		Login loginPage = new Login();
		Main.changeScene(loginPage.showLoginPage());
	}
	
	public Scene showRegisterPage() {
		init();
		setLayout();
		setEvent();
		return scene;
	}
	
}