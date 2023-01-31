package application;
	
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class Main extends Application 
{
	//Dimension of elements on the screen
	protected static final int WIDTH = 800, HEIGHT = 600, SIDEBAR_WIDTH = 150, NAVBAR_HEIGHT = 40;
	//Set during the login phase.
	public static String NAME = "";
	public static String ROLE = "";
	
	private static HBox navbar;
	private static VBox sidebar;
	private static SidebarButton b,b2,b3,b4, b5;
	public static Stage mainStage;
	
	//User instance for the logged in user. The user using the program.
	public static User userLoggedIn;
	//List of users in the system which a manager or accountant should use.
	public static List<User> users = new ArrayList<>();
	
	private static OverviewScreen overviewScreen;
	//true = load data from the bank API
	public static boolean loadAPI = false;
	
	//Salesman API token from the bank API.
	public static String salesManToken = "cZWVAG44OPS8Vlo6EgL1695z4cbcJ3317es7kNQp";
	
	/**
	 * First method called. Sets up entire screen and loads in all components.
	 */
	@Override
	public void start(Stage primaryStage) 
	{
		mainStage = primaryStage;
		try {
			primaryStage.setTitle("EMO RSM");
			primaryStage.setResizable(false);
			
			BorderPane root = new BorderPane();
			Scene mainScene = new Scene(root, Main.WIDTH, Main.HEIGHT);
			LoginScreen loginScreen = new LoginScreen(primaryStage, mainScene);
			
			overviewScreen = new OverviewScreen();
			UploadReceiptScreen uploadReceiptScreen = new UploadReceiptScreen();
			ApproveReceiptScreen approveReceiptScreen = new ApproveReceiptScreen();
			LogScreen logScreen = new LogScreen();
			UserScreen userScreen = new UserScreen();
			
			sidebar = new VBox();
			sidebar.setMinWidth(SIDEBAR_WIDTH);
			sidebar.minHeight(HEIGHT);
			sidebar.setStyle("-fx-background-color: #555;");
			
			b = new SidebarButton("Submit Receipt");
			b.setOnAction(event -> {
				root.setCenter(uploadReceiptScreen);
			});
			
			b2 = new SidebarButton("Manage Receipts");
			b2.setOnAction(event -> {
				ApproveReceiptScreen.updateScreen();
				root.setCenter(approveReceiptScreen);
			});
			
			b3 = new SidebarButton("Your Receipts");
			b3.setOnAction(event -> {
				overviewScreen.updateOverview();
				root.setCenter(overviewScreen);
			});
			
			b4 = new SidebarButton("Application Log");
			b4.setOnAction(event -> {
				root.setCenter(logScreen);
			});
			
			b5 = new SidebarButton("Manage Users");
			b5.setOnAction(event -> {
				UserScreen.updateUserScreen();
				root.setCenter(userScreen);
			});
			
			navbar = new HBox();
			navbar.setMinHeight(NAVBAR_HEIGHT);
			navbar.setMinWidth(WIDTH);
			navbar.setStyle("-fx-background-color: #333333;");
			
			root.setTop(navbar);
			root.setLeft(sidebar);
			root.setCenter(overviewScreen);
			
			Scene loginScene = new Scene(loginScreen,WIDTH,HEIGHT);
			mainScene.addEventHandler(KeyEvent.KEY_RELEASED, (k)-> {
				if(k.getCode() == KeyCode.ESCAPE) //logout functionality
				{
					Stage newStage = primaryStage;
					primaryStage.close();
					start(newStage);
				}
			});
			
			loginScene.getStylesheets().add(getClass().getResource("login.css").toExternalForm());
			mainScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(loginScene);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Is run to update the elements on the screen according to the role of the user logged in.
	 */
	public static void loadMainScreen()
	{
		navbar.getChildren().add(TextLabel.createTextLabel("EMO Receipt Management System - " + ROLE + ":", 20, javafx.scene.paint.Color.WHITE, "headerText"));
		navbar.getChildren().add(TextLabel.createTextLabel(Main.NAME, TextAlignment.RIGHT, 18, 450, Color.WHITE,
				"headerName"));
		
		switch(ROLE) {
		case "Salesman":
			sidebar.getChildren().addAll(b3, b);
			userLoggedIn = new User(-1, NAME);
			break;
		case "Accountant":
			sidebar.getChildren().addAll(b3, b, b2);
			userLoggedIn = new Accountant(-1, NAME);
			break;
		case "Manager":
			sidebar.getChildren().addAll(b3, b, b2, b4, b5);
			userLoggedIn = new Manager(-1, NAME, null);
			break;
		default:
			sidebar.getChildren().addAll(b3, b);
			userLoggedIn = new User(-1, NAME);
			break;
		}
		overviewScreen.updateOverview();
		if(Main.loadAPI)
			Main.users = BankAPIUtils.getBankUserList();
		else
			addUsersForTest();
	}
	/**
	 * If data from the banks API is not loaded, add some test users to the program for testing.
	 */
	private static void addUsersForTest()
	{
		if(Main.users.size() > 0)
			return;
		
		User u2 = new User(1, "Markus");
		u2.addReceipt(new Receipt(3, 325.42, "Fraud", "/receipts/markus/0.png"));
		User u3 = new Accountant(2, "Ogi");
		u3.addReceipt(new Receipt(4, 245.42, "Pending", "/receipts/ogi/0.png"));
		User u1 = new Manager(0, "Emil", (Accountant) u3);
		u1.addReceipt(new Receipt(0, 25.42, "Pending", "/receipts/emil/0.png"));
		u1.addReceipt(new Receipt(1, 143.99, "Pending", "/receipts/emil/1.png"));
		u1.addReceipt(new Receipt(2, 225.42, "Approved", "/receipts/emil/2.png"));
		
		Main.users.add(u1);
		Main.users.add(u2);
		Main.users.add(u3);
	}
	
	/**
	 * Launches the application and calls 'start'
	 * @param args - should be empty
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
