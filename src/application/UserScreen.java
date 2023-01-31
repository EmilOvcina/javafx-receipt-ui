package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class UserScreen extends GridPane
{
	//Table view of users in the system
	private static TableView<ViewableUser> table;
	//Width of table, used for centering
	private int contentWidth = 360;
	
	public UserScreen() 
	{
		super();
		this.setMaxHeight(600 - Main.NAVBAR_HEIGHT);
		this.setMaxWidth(800 - Main.SIDEBAR_WIDTH);
		
		table = new TableView<>();
		table.setMaxHeight(600 - (Main.NAVBAR_HEIGHT + 100));
		table.setMinHeight(600 - (Main.NAVBAR_HEIGHT + 100));
		table.setMaxWidth(contentWidth);
		table.setFocusTraversable(false);
		table.setEditable(false);
		
		TableColumn<ViewableUser, Integer> idColumn = new TableColumn<>("ID");
		idColumn.setCellValueFactory(new PropertyValueFactory<ViewableUser, Integer>("id"));
		
		TableColumn<ViewableUser, String> nameColumn = new TableColumn<>("User");
		nameColumn.setCellValueFactory(new PropertyValueFactory<ViewableUser, String>("name"));
		nameColumn.setMinWidth(100);
		
		TableColumn<ViewableUser, String> roleColumn = new TableColumn<>("Role");
		roleColumn.setCellValueFactory(new PropertyValueFactory<ViewableUser, String>("Role"));
		
		TableColumn<ViewableUser, Button> viewColumn = new TableColumn<>("View Receipts");
		viewColumn.setCellValueFactory(new PropertyValueFactory<ViewableUser, Button>("button"));
		viewColumn.setMinWidth(130);
		
		table.getColumns().add(idColumn);
		table.getColumns().add(nameColumn);
		table.getColumns().add(roleColumn);
		table.getColumns().add(viewColumn);
		
		Button addUserBtn = new Button("+");
		addUserBtn.getStyleClass().add("addUserBtn");
		addUserBtn.setMaxSize(30, 30);
		addUserBtn.setTooltip(new Tooltip("Add User"));
		
		addUserBtn.setOnAction(e -> {
			new AddUserStage(Main.users);
			updateUserScreen();
		});
		
		HBox hFiller = new HBox();
		hFiller.getChildren().add(TextLabel.createTextLabel("Manage Users", 16, Color.BLACK, "title"));
		hFiller.getStyleClass().add("title");
		
		VBox vFiller = new VBox();
		vFiller.setMinSize(((Main.WIDTH - Main.SIDEBAR_WIDTH - 30) - contentWidth) / 2, 600);
		vFiller.getChildren().add(addUserBtn);
		vFiller.setStyle("-fx-padding: 50px 0 0 50px");
		
		this.add(hFiller, 1, 1);
		this.add(table, 1, 1);
		this.add(vFiller, 0, 1);
		
		updateUserScreen();
	}
	/**
	 * Loops through the list of Users and updates the table with the up to date list of users.
	 */
	public static void updateUserScreen()
	{
		ObservableList<ViewableUser> viewableUsers = FXCollections.observableArrayList();
		for(User user : Main.users)
		{
			if(user instanceof Manager)
				viewableUsers.add(new ViewableUser(user.getId(), user.getName(), "Manager", new Button("View"), user));
			else if (user instanceof Accountant)
				viewableUsers.add(new ViewableUser(user.getId(), user.getName(), "Accountant", new Button("View"), user));
			else
				viewableUsers.add(new ViewableUser(user.getId(), user.getName(), "Salesman", new Button("View"), user));
		}
		
		table.setItems(viewableUsers);
	}
}