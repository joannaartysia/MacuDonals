package pages;


import java.sql.ResultSet;
import java.sql.SQLException;

import database.Connect;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import jfxtras.labs.scene.control.window.Window;
import main.Main;
import model.Food;
import model.TransactionDetail;

public class ManageFood {

	Scene scene; 
	BorderPane bPane; 
	GridPane gPane; 
	MenuBar menuBar;
	Menu menu, account;
	MenuItem menu2, menu3, account1;
	Image img;
	BackgroundImage bgm;
	Background bg2;
	TableView<Food> marketPlaceTable;
	TableView<TransactionDetail> cartTable;
	ObservableList<Food> foodList;
	Text iDT, nameT, typeT, priceT, stockT;
	TextField iDTF, nameTF, priceTF;
	ComboBox<String> typeCB;
	Spinner<Integer> stockSpinner;
	Button insertBtn, updateBtn, deleteBtn;
	HBox iDHBox, nameHBox, typeHBox, priceHBox, stockHBox;
	VBox listVBox;
	Background bg;
	VBox vbox;
	int selectedFood =-1;
	Window manageFood;

	
	
	public void init() {
		bPane = new BorderPane();
		gPane = new GridPane();
		
		menuBar = new MenuBar();
		
		menu = new Menu("Menu");
		menuBar.getMenus().add(menu);		

		menu2 = new MenuItem("Manage Food");
		menu3 = new MenuItem("Transactions");

		menu.getItems().add(menu2);
		menu.getItems().add(menu3);
		
		account = new Menu("Account");
		menuBar.getMenus().add(account);
		account1 = new MenuItem("Log Out");
		account.getItems().add(account1);
		
		manageFood = new Window("Manage Food");
		manageFood.setMaxSize(900, 640);
		
		marketPlaceTable = new TableView<>();
		
		vbox = new VBox();
		vbox.getChildren().addAll(manageFood, marketPlaceTable);
		
		foodList = FXCollections.observableArrayList();
		
		iDT = new Text("ID");
		nameT = new Text("Name");
		typeT = new Text("Type");
		priceT = new Text("Price");
		stockT = new Text("Stock");
		
		iDTF = new TextField();
		iDTF.setEditable(false);
		iDTF.setStyle("-fx-background-color: transparent");
		
		nameTF = new TextField();
		priceTF = new TextField();
		
		typeCB = new ComboBox<>();
		typeCB.getItems().add("Main Dish");
		typeCB.getItems().add("Appetizer");
		typeCB.getItems().add("Drink");
		typeCB.getItems().add("Dessert");
		typeCB.getItems().add("Side Dish");
		typeCB.setPrefWidth(200);
		
		stockSpinner = new Spinner<>(0, 970, 0, 1);
		stockSpinner.setPrefWidth(200);
		
		insertBtn = new Button("Insert");
		updateBtn = new Button("Update");
		deleteBtn = new Button("Delete");
		insertBtn.setPrefWidth(120);
		updateBtn.setPrefWidth(120);
		deleteBtn.setPrefWidth(120);
	}
	
	public void setLayout() {
		
		bPane.setTop(menuBar);
		
		bPane.setCenter(vbox);
		vbox.setAlignment(Pos.CENTER);
		
		bPane.setBottom(gPane);
		gPane.setAlignment(Pos.BOTTOM_CENTER);
		
		gPane.add(iDT, 0, 0);
		gPane.add(nameT, 0, 1);
		gPane.add(typeT, 0, 2);
		gPane.add(priceT, 0, 3);
		gPane.add(stockT, 0, 4);
		
		gPane.add(iDTF, 1, 0);
		gPane.add(nameTF, 1, 1);
		gPane.add(typeCB, 1, 2);
		gPane.add(priceTF, 1, 3);
		gPane.add(stockSpinner, 1, 4);
		
		gPane.add(insertBtn, 2, 1);
		gPane.add(updateBtn, 2, 2);
		gPane.add(deleteBtn, 2, 4);
		
		gPane.setPadding(new Insets(20));
		gPane.setHgap(20);
		gPane.setVgap(20);

		bPane.setStyle("-fx-background-color: lightsteelblue");
		
		
		scene = new Scene(bPane, 900, 640);
	}
	
	
	public void setEvent() {
	
	insertBtn.setOnMouseClicked(e -> {
		String name = nameTF.getText();
		String type = typeCB.getSelectionModel().getSelectedItem();
		String price = priceTF.getText();
		
		
		int stock = stockSpinner.getValue();
		String s = Integer.toString(stock);
		
		
		if (name.isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Error");
			alert.setContentText("Name must not empty");
			alert.showAndWait();
		} else if (type == null || type.isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Error");
			alert.setContentText("Type must not empty");
			alert.showAndWait();
		} else if (price.isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Error");
			alert.setContentText("Price must not empty");
			alert.showAndWait();
		} else if (s.isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Error");
			alert.setContentText("Stock must not empty");
			alert.showAndWait();
		} else if (name.length() < 5 || name.length() > 30) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Error");
			alert.setContentText("Name must be between 5-30");
			alert.showAndWait();
		} else if (stock <= 0) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Error");
			alert.setContentText("Stock must be more than 0");
			alert.showAndWait();
		} else {
			int p = Integer.parseInt(price);
			if (p <= 0) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Error");
				alert.setContentText("Price must be more than 0");
				alert.showAndWait();
			}
			Food id = foodList.get(foodList.size()-1) ;
			int idFood = id.getId();
			idFood ++;
			Connect connect = Connect.getConnection();
			
			String query = String.format("INSERT INTO `food`(`id`, `name`, `type`, `price`, `stock`) VALUES ('%d','%s','%s','%d','%s')", idFood, name, type, p, stock);
			connect.executeUpdate(query);
				foodList.add(new Food(idFood, name, type, p, stock));
				marketPlaceTable.setItems(foodList);	
				
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Message");
				alert.setHeaderText("Message");
				alert.setContentText("Food added successfully");
				alert.showAndWait();
				
				refreshLayout();
				refreshFoodTable();
				
		}
	});
//	}
	
	marketPlaceTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Food>() {

		@Override
		public void changed(ObservableValue<? extends Food> observable, Food oldValue, Food newValue) {
			// TODO Auto-generated method stub
			if(newValue != null) {
				selectedFood = newValue.getId();
				
			}
		}
	});
	updateBtn.setOnMouseClicked(e -> {
		Food select = marketPlaceTable.getSelectionModel().getSelectedItem();
		
		String name = nameTF.getText();
		String type = typeCB.getSelectionModel().getSelectedItem();
		String price = priceTF.getText();
		
		
		int stock = stockSpinner.getValue();
		
		if(select == null) {
			
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Error");
			alert.setContentText("You must choose a food first!");
			alert.showAndWait();
		
			return;
		}
		if (name.length() < 5 || name.length() > 30) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Error");
			alert.setContentText("Name must be between 5-30");
			alert.showAndWait();
		} else if (stock <= 0) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Error");
			alert.setContentText("Stock must be more than 0");
			alert.showAndWait();
		} else {
			int p = Integer.parseInt(price);
			if (p <= 0) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Error");
				alert.setContentText("Price must be more than 0");
				alert.showAndWait();
			}
			Connect connect = Connect.getConnection();
			String query = String.format("UPDATE `food` SET `name`='%s',`type`='%s',`price`='%d',`stock`='%d' WHERE `id`='%d'" , name, type,p, stock,selectedFood);
			connect.executeUpdate(query);
			
						select.setName(name);
						select.setType(type);
						select.setPrice(p);
						select.setStock(stock);
						marketPlaceTable.getItems().set(marketPlaceTable.getSelectionModel().getSelectedIndex(), select);
						
						Alert alert = new Alert(AlertType.CONFIRMATION);
						alert.setTitle("Message");
						alert.setHeaderText("Message");
						alert.setContentText("Food updated successfully");
						alert.showAndWait();
						
						refreshFoodTable();
						refreshLayout();
		}
		 });
	
	
	deleteBtn.setOnMouseClicked(e -> {
		Food selectRemove = marketPlaceTable.getSelectionModel().getSelectedItem();
		
		if(selectRemove == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Error");
			alert.setContentText("You must choose a food first!");
			alert.showAndWait();
		} else {
			foodList.remove(selectRemove);
			Connect connect = Connect.getConnection();
			String query = String.format("DELETE FROM `food` WHERE id = '%d'", selectRemove.getId());
			connect.executeUpdate(query);
			
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Message");
			alert.setHeaderText("Message");
			alert.setContentText("Food deleted successfully");
			alert.showAndWait();
			
		refreshFoodTable();
		refreshLayout();
		}	
		});
}

@SuppressWarnings("unchecked")
public void setTableFood() {
	
	TableColumn<Food, Integer> idColumn = new TableColumn<Food, Integer>("ID");
	TableColumn<Food, String> nameColumn = new TableColumn<Food, String>("Name");
	TableColumn<Food, String> typeColumn = new TableColumn<Food, String>("Type");
	TableColumn<Food, Integer> priceColumn = new TableColumn<Food, Integer>("Price");
	TableColumn<Food, Integer> stockColumn = new TableColumn<Food, Integer>("Stock");

	idColumn.setCellValueFactory(new PropertyValueFactory<Food, Integer>("id"));
	idColumn.setMinWidth(100);
	nameColumn.setCellValueFactory(new PropertyValueFactory<Food, String>("name"));
	nameColumn.setMinWidth(200);
	typeColumn.setCellValueFactory(new PropertyValueFactory<Food, String>("type"));
	typeColumn.setMinWidth(200);
	priceColumn.setCellValueFactory(new PropertyValueFactory<Food, Integer>("price"));
	priceColumn.setMinWidth(200);
	stockColumn.setCellValueFactory(new PropertyValueFactory<Food, Integer>("stock"));
	stockColumn.setMinWidth(200);

	marketPlaceTable.getColumns().addAll(idColumn, nameColumn, typeColumn, priceColumn, stockColumn);
	
	refreshFoodTable();
}
	
	public void getFood() {
		Connect connect = Connect.getConnection();
		String query = "SELECT * FROM `food`";
		ResultSet rs = connect.executeQuery(query);
		
		try {
			while(rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String type = rs.getString("type");
				int price = rs.getInt("price");
				int stock = rs.getInt("stock");
				
				Food food = new Food(id, name, type, price, stock);
				foodList.add(food);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void refreshFoodTable() {
		foodList.clear();
		getFood();
		ObservableList<Food> foodObs = FXCollections.observableArrayList(foodList);
		marketPlaceTable.setItems(foodObs);
	}
	
	public void refreshLayout() {
		iDTF.setText("");
		nameTF.setText("");
		typeCB.setPromptText("");
		priceTF.setText("");
		stockSpinner.setPromptText("");
	}
	
	
	public void setEventMenu() {
		
		
		menu3.setOnAction(e -> {
			goToTransactionsPage();
		});
		
		account1.setOnAction(e -> {
			goToLogOut();
		});
	}

	public void goToTransactionsPage() {
		TransactionAdmin transPage = new TransactionAdmin();
		Main.changeScene(transPage.showTransactionAdminPage());
	}

	public void goToLogOut() {
		Login logOut = new Login();
		Main.changeScene(logOut.showLoginPage());
	}
	
	public Scene showManageFoodPage() {
		init();
		setLayout();
		setEventMenu();
		setTableFood();
		refreshFoodTable();
		setEvent();
		return scene;
	}

}