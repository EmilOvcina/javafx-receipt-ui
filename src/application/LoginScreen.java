package application;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginScreen extends GridPane
{
	private RadioButton rb;
	/**
	 * @param stage - the stage object which the login screen is on
	 * @param redirect - the scene which the login screen should redirect to when the login button is pressed
	 */
	public LoginScreen(Stage stage, Scene redirect)
	{
		super();
        this.setAlignment(Pos.CENTER);
        this.setHgap(10);
        this.setVgap(10);
        
        this.setStyle("-fx-background-color: #333;");
        
        Text title = new Text("EMO Receipt Management System");
        title.setFont(Font.font("", FontWeight.NORMAL, 20)); 
        title.setFill(Color.WHITE);
        this.add(title, 0, 0, 2,1); 

        Label emailLabel = new Label("Email:");
        emailLabel.setTextFill(Color.WHITE);
        this.add(emailLabel, 0, 1);
 
        TextField emailField = new TextField();
        this.add(emailField, 1, 1);
 
        Label passwordLabel = new Label("Password:");
        passwordLabel.setTextFill(Color.WHITE); 
        this.add(passwordLabel, 0, 2);   
  
        PasswordField passwordField = new PasswordField();
        this.add(passwordField, 1, 2);      
  
        Text errorMessageOnClick = new Text();   
        this.add(errorMessageOnClick, 1,4);   
      
        ChoiceBox<String> role = new ChoiceBox<>();      
        role.setValue("Salesman");
        role.getItems().add("Salesman");
        role.getItems().add("Accountant");  
        role.getItems().add("Manager"); 
        
        rb = new RadioButton("Load API");
        rb.setText("Load API");
        rb.setTextFill(Color.WHITE);
        
        Button loginButton = new Button("Login");
        loginButton.setOnAction(event -> {
            if(emailField.getText().length() == 0) {
                errorMessageOnClick.setFill(Color.RED);
                errorMessageOnClick.setText("Invalid email!");
            }
            else {
            	Main.loadAPI = rb.isSelected();
                Main.NAME = emailField.getText(); 
                Main.ROLE = role.getValue();
                Main.loadMainScreen();
                stage.setScene(redirect); 
            }
        });
        
        this.add(rb, 2, 3);
        this.add(loginButton, 0, 3);
        this.add(role, 1, 3);
    }
}