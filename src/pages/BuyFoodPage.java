package pages;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;


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
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import jfxtras.labs.scene.control.window.Window;
import main.Main;
import model.Food;
import model.TransactionDetail;
import model.User;

public class BuyFoodPage {

	Scene scene; 
	BorderPane bPane; 
	GridPane gPane; 
	FlowPane fPane;
	MenuBar menuBar;
	Menu menu, account;
	MenuItem menu1, menu3, account1;
	HBox btnHBox, hbox;
	VBox vbox;
	Window buyFood;
	
	ObservableList<Food> foodList;
	ObservableList<TransactionDetail> carts;
	ObservableList<TransactionDetail> transaction;
	
	TableView<Food> marketPlace;
	TableView<TransactionDetail> cartTable;
	Text id, name, type, price, stock, quantity;
	TextField nameTF, typeTF, priceTF, stockTF, iDTF;

	Spinner<Integer> qtySpinner;
	Button addBtn, removeBtn ,checkOutBtn;
	int selectedFoodId = -1;
	
	int transactionId = 0;
	
	public void initialize() {
		bPane = new BorderPane();
		gPane = new GridPane();
		
		foodList = FXCollections.observableArrayList();
		carts = FXCollections.observableArrayList();
		transaction = FXCollections.observableArrayList();
		
		menuBar = new MenuBar();
		menu = new Menu("Menu");		
		menuBar.getMenus().add(menu);
		menu1 = new MenuItem("Buy Food");
	
		menu3 = new MenuItem("Transactions");
		menu.getItems().add(menu1);
		
		menu.getItems().add(menu3);
		
		account = new Menu("Account");
		menuBar.getMenus().add(account);
		account1 = new MenuItem("Log Out");
		account.getItems().add(account1);
		
		buyFood = new Window("Buy Food");
		buyFood.setMaxSize(900, 640);
		
		marketPlace = new TableView<>();
		marketPlace.setPrefHeight(500);
		
		cartTable = new TableView<>();
		cartTable.setPrefHeight(200);
		
		vbox = new VBox();
		vbox.getChildren().addAll(buyFood, marketPlace);
		
		id = new Text("ID");
		name = new Text("Name");
		type = new Text("Type");
		price = new Text("Price");
		stock = new Text("Stock");
		quantity = new Text("Quantity:");
		
		iDTF = new TextField();
		iDTF.setStyle("-fx-background-color: transparent");
		iDTF.setEditable(false);
		
		nameTF = new TextField();
		nameTF.setStyle("-fx-background-color: transparent");
		nameTF.setEditable(false);
		
		typeTF = new TextField();
		typeTF.setStyle("-fx-background-color: transparent");
		typeTF.setEditable(false);
		
		priceTF = new TextField();
		priceTF.setStyle("-fx-background-color: transparent");
		priceTF.setEditable(false);
		
		stockTF = new TextField();
		stockTF.setStyle("-fx-background-color: transparent");
		stockTF.setEditable(false);
		
		qtySpinner = new Spinner<>(0, 10, 0);
		
		addBtn = new Button("Add to Cart");
		removeBtn = new Button("Remove from Cart");
		checkOutBtn = new Button("Checkout");
		addBtn.setPrefWidth(100);
		removeBtn.setPrefWidth(100);
		checkOutBtn.setPrefWidth(100);
		
		hbox = new HBox(20);
		hbox.getChildren().addAll(gPane, cartTable);
		hbox.setPadding(new Insets(20));
		
		scene = new Scene(bPane, 900, 640);
	}

	public void setLayout() {
		gPane.add(id, 0, 0);
		gPane.add(name, 0, 1);
		gPane.add(type, 0, 2);
		gPane.add(price, 0, 3);
		gPane.add(stock, 0, 4);
		
		gPane.add(iDTF, 1, 0);
		gPane.add(nameTF, 1, 1);
		gPane.add(typeTF, 1, 2);
		gPane.add(priceTF, 1, 3);
		gPane.add(stockTF, 1, 4);
		
		gPane.add(quantity, 2, 0);
		gPane.add(qtySpinner, 2, 1);
		gPane.add(addBtn, 2, 2);
		gPane.add(removeBtn, 2, 3);
		gPane.add(checkOutBtn, 2, 4);
		
		gPane.setPadding(new Insets(20));

		gPane.setHgap(20);
		gPane.setVgap(10);
		
		bPane.setTop(menuBar);
		
		bPane.setCenter(vbox);
		vbox.setAlignment(Pos.CENTER);
		
		bPane.setBottom(hbox);
		
		bPane.setStyle("-fx-background-color: lightsteelblue");
		
	}
	
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

		marketPlace.getColumns().addAll(Arrays.asList(idColumn,nameColumn, typeColumn, priceColumn, stockColumn));
		
		refreshTable();
	}
	
	public void setCart() {
		TableColumn<TransactionDetail, Integer> foodIdColumn = new TableColumn<TransactionDetail, Integer>("Food Id");
		TableColumn<TransactionDetail, String> foodNameColumn = new TableColumn<TransactionDetail, String>("Food Name");
		TableColumn<TransactionDetail, Integer> foodPriceColumn = new TableColumn<TransactionDetail, Integer>("Food Price");
		TableColumn<TransactionDetail, Integer> qtyColumn = new TableColumn<TransactionDetail, Integer>("Quantity");
		TableColumn<TransactionDetail, Integer> totalPriceColumn = new TableColumn<TransactionDetail, Integer>("Total Price");

		foodIdColumn.setCellValueFactory(new PropertyValueFactory<TransactionDetail, Integer>("food_id"));
		foodIdColumn.setMinWidth(50);
		foodNameColumn.setCellValueFactory(new PropertyValueFactory<TransactionDetail, String>("foodName"));
		foodNameColumn.setMinWidth(100);
		foodPriceColumn.setCellValueFactory(new PropertyValueFactory<TransactionDetail, Integer>("foodPrice"));
		foodPriceColumn.setMinWidth(75);
		qtyColumn.setCellValueFactory(new PropertyValueFactory<TransactionDetail, Integer>("quantity"));
		qtyColumn.setMinWidth(50);
		totalPriceColumn.setCellValueFactory(new PropertyValueFactory<TransactionDetail, Integer>("total_price"));
		totalPriceColumn.setMinWidth(75);

		cartTable.getColumns().addAll(Arrays.asList(foodIdColumn, foodNameColumn, foodPriceColumn, qtyColumn, totalPriceColumn));
	
		
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
		
		marketPlace.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Food>() {

			@Override
			public void changed(ObservableValue<? extends Food> observable, Food oldValue, Food newValue) {
				// TODO Auto-generated method stub
				if(newValue != null) {
					selectedFoodId = newValue.getId();
					
					iDTF.setText(newValue.getId() + "");
					nameTF.setText(newValue.getName());
					typeTF.setText(newValue.getType());
					priceTF.setText(newValue.getPrice() + "");
					stockTF.setText(newValue.getStock() + "");
				}
			}
		});
	}
	
	public void setEventButton() {
		// 2 TODO: add button
		addBtn.setOnMouseClicked(e -> {
			Food select = marketPlace.getSelectionModel().getSelectedItem();
			
			if(select == null) {
				
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Error");
				alert.setContentText("You must choose a food first!");
				alert.showAndWait();
			
				return;
			}
			
			int quantity = qtySpinner.getValue();
			int totalPrice = quantity * select.getPrice();
			String q = Integer.toString(quantity);
			
			
				if(q.isEmpty()) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Error");
				alert.setContentText("Quantity must not empty");
				alert.showAndWait();
			} else if (quantity > select.getStock()) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Error");
				alert.setContentText("Quantity must less than stock product");
				alert.showAndWait();
			} else if (quantity == 0) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Error");
				alert.setContentText("Quantity must be more than 0");
				alert.showAndWait();
			} else {
				TransactionDetail trdtl = new TransactionDetail(transactionId, select.getId(), quantity, totalPrice);
				carts.add(trdtl);
				cartTable.setItems(carts);	
				
				int st = marketPlace.getSelectionModel().getSelectedItem().getStock();
				
				select.setStock(st-quantity);
				marketPlace.getItems().set(marketPlace.getSelectionModel().getSelectedIndex(), select);
				
				
				
			refreshTable();
			refreshLayout();
			}
			
			
		});
		
		
		removeBtn.setOnMouseClicked(e -> {
			TransactionDetail selectCart = cartTable.getSelectionModel().getSelectedItem();
			
			if (cartTable.getItems().isEmpty()) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Error");
				alert.setContentText("Cart is empty");
				alert.showAndWait();
				
			} else if(selectCart == null) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Error");
				alert.setContentText("You must choose a food first!");
				alert.showAndWait();
			} else {
					carts.remove(selectCart);
					
				refreshTable();
				refreshLayout();
			}
			}
			);
		
			checkOutBtn.setOnMouseClicked(e -> {
				if (cartTable.getItems().isEmpty()) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("Error");
					alert.setContentText("Cart is empty");
					alert.showAndWait();
				}
				else {
					transaction = carts ;
					Timestamp instant = Timestamp.from(Instant.now());
					Connect connect = Connect.getConnection();
						String query = String.format("INSERT INTO `transaction_header`(`transaction_id`, `user_id`, `date`) VALUES ('%d','%d','%s')", 
								0, User.currentUser.getId(), instant);
						connect.executeUpdate(query);
					
					for(TransactionDetail transactionDetail : transaction) {
						String querys = String.format("INSERT INTO `transaction_detail`(`transaction_id`, `food_id`, `quantity`, `total_price`) VALUES ('%d','%d','%d','%d')", 
								transactionDetail.getTransaction_id(), transactionDetail.getFood_id(), transactionDetail.getQuantity(), transactionDetail.getTotal_price());
						connect.executeUpdate(querys);
					}
					
					cartTable.getItems().clear();
					carts.clear();
					
					refreshTable();
					refreshLayout();
				}
			});
	}
	
	public void setTransactionId() {
		Connect connect = Connect.getConnection();
		String query = "SELECT * FROM `transaction_detail` ORDER BY transaction_id DESC LIMIT 1";
		ResultSet rs = connect.executeQuery(query);
		
		try {
			while(rs.next()) {
				transactionId = rs.getInt("transaction_id") + 1;
			}
			
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	
	}
	

	public void refreshTable() {
		foodList.clear();
		getFood();
		setTransactionId();
		ObservableList<Food> foods = FXCollections.observableArrayList(foodList);
		marketPlace.setItems(foods);

	}
	
	public void refreshLayout() {
		iDTF.setText("");
		nameTF.setText("");
		typeTF.setText("");
		priceTF.setText("");
		stockTF.setText("");
		qtySpinner.setPromptText(null);
		selectedFoodId = -1;
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
		TransactionsPage transPage = new TransactionsPage();
		Main.changeScene(transPage.showTransactionsPage());
	}
	
	public void goToLogOut() {
		Login logOut = new Login();
		Main.changeScene(logOut.showLoginPage());
	}
	
	public Scene showBuyFoodPage() {
		initialize();
		setLayout();
		setTableFood();
		setCart();
		refreshTable();
		setEventButton();
		setEventMenu();
		return scene;
	}

	
}