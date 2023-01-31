package application;

import java.util.List;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AddUserStage extends Stage
{
	//Width and height of the content pane
	private static final int WIDTH = 340, HEIGHT = 130;
	
	/**
	 * @param users - User list to add the new user to
	 */
	public AddUserStage(List<User> users)
	{
		super();
		this.initModality(Modality.APPLICATION_MODAL);
		this.initOwner(Main.mainStage);
		this.setTitle("Add User");
		this.setResizable(false);
		
		GridPane content = new GridPane();
		content.getStyleClass().add("viewReceiptContent");
		content.setMaxWidth(WIDTH);
		content.setMaxHeight(HEIGHT); 
		
		TextField name = new TextField();
		name.getStyleClass().add("addUserTF"); 
		  
		ChoiceBox<String> role = new ChoiceBox<>();  
		role.getStyleClass().add("addUserCB");
        role.setValue("Salesman");
        role.getItems().add("Salesman"); 
        role.getItems().add("Accountant");  
        role.getItems().add("Manager");   
		 
        Button addUser = new Button("Add User");
        addUser.getStyleClass().add("addUserTF");
        
        addUser.setOnAction(e -> {
        	String r = role.getValue();
        	if(r.equals("Salesman")) {
        		User u1 = new User(getNextID(users), name.getText());
        		((Manager) Main.userLoggedIn).addUser(u1);
        		Main.users.add(u1);
        	}
        	
        	if(r.equals("Accountant")) {
        		User u1 = new Accountant(getNextID(users), name.getText());
        		((Manager) Main.userLoggedIn).addUser(u1);
        		Main.users.add(u1);
        	}
        	
        	if(r.equals("Manager")) {
        		User u1 = new Manager(getNextID(users), name.getText(), null);
        		((Manager) Main.userLoggedIn).addUser(u1);
        		Main.users.add(u1);
        	}

			LogScreen.addToLog("Created user: " + name.getText() + " in the system.");
        	UserScreen.updateUserScreen();
        	this.close();
        });
        
        content.setVgap(15); 
		content.add(TextLabel.createTextLabel("Name: ", 16, 100), 0, 0);
		content.add(TextLabel.createTextLabel("Role: ", 16, 100), 0, 1);
		content.add(name, 1, 0);
		content.add(role, 1, 1);
		content.add(addUser, 2, 2);
		
		Scene dialogScene = new Scene(content, WIDTH, HEIGHT);
		dialogScene.addEventHandler(KeyEvent.KEY_RELEASED, (k)-> {
			if(k.getCode() == KeyCode.ESCAPE) //close functionality
				this.close();
		});
		
		dialogScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		this.setScene(dialogScene);
		this.show();
	} 
	
	/**
	 * @param users - List of all users in the system
	 * @return - Next ID a user should have
	 */
	private int getNextID(List<User> users)
	{
		int highestID = 0;
		for(User user : users) 
			if(user.getId() > highestID)
				highestID = user.getId();
		return highestID + 1;
	}
}
