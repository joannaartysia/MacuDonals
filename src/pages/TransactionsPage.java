package pages;

import java.sql.ResultSet; 
import java.sql.SQLException;
import java.util.Arrays;

import database.Connect;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import jfxtras.labs.scene.control.window.Window;
import main.Main;
import model.TransactionDetail;
import model.TransactionHeader;
import model.User;

public class TransactionsPage{
	Scene scene;
	BorderPane bPane; 
	GridPane gPane; 
	FlowPane fPane;
	MenuBar menuBar;
	Menu menu, account;
	MenuItem menu1, menu3, account1;
	HBox btnHBox;
	VBox vbox;
	Window transactions;
	int select = -1;
	
	ObservableList<TransactionDetail> carts;
	TableView<TransactionDetail> cartTable;
	
	ObservableList<TransactionHeader> user;
	TableView<TransactionHeader> userTable;
	
	public void initialize() {
		bPane = new BorderPane();
		gPane = new GridPane();
		
		fPane = new FlowPane();
		
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
		
		cartTable = new TableView<>();
		userTable = new TableView<>();
		
		user = FXCollections.observableArrayList();
		carts = FXCollections.observableArrayList();
		
		transactions = new Window("Transactions");
		transactions.setMaxSize(900, 640);
		
		vbox = new VBox();
		vbox.getChildren().addAll(transactions, userTable);
		
		scene = new Scene(bPane, 900, 640);
	}
	
	public void setLayout() {
		bPane.setTop(menuBar);
		
		bPane.setCenter(vbox);
		vbox.setAlignment(Pos.CENTER);
		
		bPane.setBottom(cartTable);
		
	}
	
	public void setEvent() {
		userTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TransactionHeader>() {

			@Override
			public void changed(ObservableValue<? extends TransactionHeader> observable, TransactionHeader oldValue, TransactionHeader newValue) {
				// TODO Auto-generated method stub
				if(newValue != null) {
					select = newValue.getTransaction_id();
				
					Connect connect = Connect.getConnection();
					String query = String.format("SELECT * FROM `transaction_detail` WHERE transaction_id = '%d'", select);
					ResultSet rs = connect.executeQuery(query);
					
					try {
						carts.clear();
						
						while(rs.next()) {
							
							int transaction_id = rs.getInt("transaction_id");
							int food_id = rs.getInt("food_id");
							int quantity = rs.getInt("quantity");
							int total_price = rs.getInt("total_price");
						
							TransactionDetail td = new TransactionDetail(transaction_id, food_id, quantity, total_price);
							carts.add(td);
							cartTable.setItems(carts);	
						}
						
						refreshCartTable();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
				}
			}
		});
	}
		
	
	
	public void setUser() {
		TableColumn<TransactionHeader, Integer> idColumn = new TableColumn<TransactionHeader, Integer>("ID");
		TableColumn<TransactionHeader, Integer> userIdColumn = new TableColumn<TransactionHeader, Integer>("User ID");
		TableColumn<TransactionHeader, String> nameColumn = new TableColumn<TransactionHeader, String>("User name");
		TableColumn<TransactionHeader, String> roleColumn = new TableColumn<TransactionHeader, String>("User role");
		TableColumn<TransactionHeader, String> dateColumn = new TableColumn<TransactionHeader, String>("Date");
		
		
		idColumn.setCellValueFactory(new PropertyValueFactory<TransactionHeader, Integer>("transaction_id"));
		idColumn.setMinWidth(100);
		userIdColumn.setCellValueFactory(new PropertyValueFactory<TransactionHeader, Integer>("user_id"));
		userIdColumn.setMinWidth(200);
		nameColumn.setCellValueFactory(new PropertyValueFactory<TransactionHeader, String>("usernameTH"));
		nameColumn.setMinWidth(200);
		roleColumn.setCellValueFactory(new PropertyValueFactory<TransactionHeader, String>("roleTH"));
		roleColumn.setMinWidth(200);
		dateColumn.setCellValueFactory(new PropertyValueFactory<TransactionHeader, String>("date"));
		dateColumn.setMinWidth(200);
		
		userTable.getColumns().addAll(Arrays.asList(idColumn, userIdColumn, nameColumn , roleColumn, dateColumn));
	}
	
	@SuppressWarnings("unchecked")
	public void setCart() {
		TableColumn<TransactionDetail, Integer> foodIdColumn = new TableColumn<TransactionDetail, Integer>("ID");
		TableColumn<TransactionDetail, String> foodNameColumn = new TableColumn<TransactionDetail, String>("Food Name");
		TableColumn<TransactionDetail, String> foodTypeColumn = new TableColumn<TransactionDetail, String>("Food Type");
		TableColumn<TransactionDetail, Integer> foodPriceColumn = new TableColumn<TransactionDetail, Integer>("Food Price");
		TableColumn<TransactionDetail, Integer> qtyColumn = new TableColumn<TransactionDetail, Integer>("Quantity");
		TableColumn<TransactionDetail, Integer> totalPriceColumn = new TableColumn<TransactionDetail, Integer>("Total Price");

		foodIdColumn.setCellValueFactory(new PropertyValueFactory<TransactionDetail, Integer>("transaction_id"));
		foodIdColumn.setMinWidth(100);
		foodNameColumn.setCellValueFactory(new PropertyValueFactory<TransactionDetail, String>("foodName"));
		foodNameColumn.setMinWidth(200);
		foodTypeColumn.setCellValueFactory(new PropertyValueFactory<TransactionDetail, String>("foodType"));
		foodTypeColumn.setMinWidth(150);
		foodPriceColumn.setCellValueFactory(new PropertyValueFactory<TransactionDetail, Integer>("foodPrice"));
		foodPriceColumn.setMinWidth(150);
		qtyColumn.setCellValueFactory(new PropertyValueFactory<TransactionDetail, Integer>("quantity"));
		qtyColumn.setMinWidth(100);
		totalPriceColumn.setCellValueFactory(new PropertyValueFactory<TransactionDetail, Integer>("total_price"));
		totalPriceColumn.setMinWidth(200);

		cartTable.getColumns().addAll(foodIdColumn, foodNameColumn, foodTypeColumn , foodPriceColumn, qtyColumn, totalPriceColumn);
	}
	
	public void getUser() {
		Connect connect = Connect.getConnection();
		String query = String.format("SELECT * FROM `transaction_header` where user_id = '%d'", User.currentUser.getId());
		ResultSet rs = connect.executeQuery(query);
		
		try {
			while(rs.next()) {
				
				int transaction_id = rs.getInt("transaction_id");
				int user_id = rs.getInt("user_id");
				String date = rs.getString("date");
			
				
				TransactionHeader th = new TransactionHeader(transaction_id, user_id,date);
				user.add(th);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void getCart() {
		Connect connect = Connect.getConnection();
		String query = "SELECT * FROM `transaction_detail`";
		ResultSet rs = connect.executeQuery(query);
		
		try {
			while(rs.next()) {
				
				int transaction_id = rs.getInt("transaction_id");
				int food_id = rs.getInt("food_id");
				int quantity = rs.getInt("quantity");
				int total_price = rs.getInt("total_price");
			
				TransactionDetail td = new TransactionDetail(transaction_id, food_id, quantity, total_price);
				carts.add(td);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void refreshUserTable() {
		user.clear();
		getUser();
		ObservableList<TransactionHeader> ths = FXCollections.observableArrayList(user);
		userTable.setItems(ths);
	} 
	
	public void refreshCartTable() {

		ObservableList<TransactionDetail> tds = FXCollections.observableArrayList(carts);
		cartTable.setItems(tds);
	} 
	
	public void setEventMenu() {
		menu1.setOnAction(e -> {
			goToBuyFoodPage();
		});
		
		
		account1.setOnAction(e -> {
			goToLogOut();
		});
	}

	public void goToBuyFoodPage() {
		BuyFoodPage buyPage = new BuyFoodPage();
		Main.changeScene(buyPage.showBuyFoodPage());
	}
	
	
	public void goToLogOut() {
		Login logOut = new Login();
		Main.changeScene(logOut.showLoginPage());
	}
	
	public Scene showTransactionsPage() {
		initialize();
		setLayout();
		setEventMenu();
		setEvent();
		setUser();
		setCart();
		refreshUserTable();
		refreshCartTable();
		return scene;
	}

	
}