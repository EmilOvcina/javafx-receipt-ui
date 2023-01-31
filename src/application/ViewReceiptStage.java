package application;
import java.io.FileNotFoundException;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ViewReceiptStage extends Stage
{
	//Dimensions of the pop-up window 
	private static final int WIDTH = 400, HEIGHT = 250;
	/**
	 * @param receipt - receipt to be viewed
	 * @throws FileNotFoundException
	 */
	public ViewReceiptStage(ViewableReceipt receipt) throws FileNotFoundException
	{
		super();
		this.initModality(Modality.APPLICATION_MODAL);
		this.initOwner(Main.mainStage);
		this.setTitle("Receipt #" + receipt.getId() + " - " + receipt.getName());
		this.setResizable(false);
		BorderPane content = new BorderPane();
		content.getStyleClass().add("viewReceiptContent");
		content.setMaxWidth(WIDTH);
		content.setMaxHeight(HEIGHT); 
		  
		VBox textBox = new VBox(); 
		textBox.setMaxWidth(WIDTH / 2); 
		textBox.getChildren().add(TextLabel.createTextLabel("Receipt #" + receipt.getId(), 20));
		textBox.getChildren().add(TextLabel.createTextLabel("Owner: " + receipt.getName(), 18));
		textBox.getChildren().add(TextLabel.createTextLabel("Amount: " + receipt.getValue(), 18));
		textBox.getChildren().add(TextLabel.createTextLabel("Status: " + receipt.getStatus(), 18));
		textBox.getChildren().add(TextLabel.createTextLabel("Image Path: " + receipt.getFilePath(), 12));
		 
		HBox buttonBox = new HBox();
		buttonBox.getStyleClass().add("buttonBox");
		Button approveButton = new Button("Approve"); 
		approveButton.setFocusTraversable(false);
		approveButton.setPrefWidth(WIDTH/4);
		approveButton.setPrefHeight(80);
		approveButton.getStyleClass().add("approveButton");
		//Marks the receipt as "approved"
		approveButton.setOnAction(e-> {
			updateReceipt(receipt, "Approved");
			if(Main.loadAPI)
				BankAPIUtils.updateReceipt(receipt, "Approved");
			ApproveReceiptScreen.updateScreen();
			LogScreen.addToLog("Approved Receipt #" + receipt.getId());
			this.close();
		});
		
		Button denyButton = new Button("Fraud");
		denyButton.setPrefWidth(WIDTH/4);
		denyButton.setFocusTraversable(false);
		denyButton.setPrefHeight(80);
		denyButton.getStyleClass().add("denyButton");
		
		//Marks the receipt as "fraud"
		denyButton.setOnAction(e-> {
			updateReceipt(receipt, "Fraud"); 
			if(Main.loadAPI)
				BankAPIUtils.updateReceipt(receipt, "Fraud");
			ApproveReceiptScreen.updateScreen();
			LogScreen.addToLog("Denied Receipt #" + receipt.getId());
			this.close();
		});
		
		if(receipt.getStatus().toLowerCase().equals("pending")) {
			buttonBox.getChildren().add(approveButton);
			buttonBox.getChildren().add(denyButton);
		}
		
		Button pendingButton = new Button("Update");
		pendingButton.setPrefWidth(WIDTH/4);
		pendingButton.setFocusTraversable(false);
		pendingButton.setPrefHeight(80);
		pendingButton.getStyleClass().add("pendingButton");
		
		//Marks the receipt as "pending"
		pendingButton.setOnAction(e-> {
			updateReceipt(receipt, "Pending");
			if(Main.loadAPI)
				BankAPIUtils.updateReceipt(receipt, "Pending");
			ApproveReceiptScreen.updateScreen();
			LogScreen.addToLog("Set Receipt #" + receipt.getId() + " to pending");
			this.close();
		});
		
		if(!receipt.getStatus().toLowerCase().equals("pending"))
			buttonBox.getChildren().add(pendingButton);
			
		textBox.getChildren().add(buttonBox);
		
		content.setLeft(textBox);
		Image image = new Image(Main.class.getResourceAsStream("../../res/test.png"));
		ImageView imageView = new ImageView(image);
		imageView.getStyleClass().add("receiptImage");
		imageView.setFitWidth(150);
		imageView.setFitHeight(HEIGHT - 20);
		
		content.setRight(imageView);
		
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
	 * @param receipt - receipt to update
	 * @param status - new status of the receipt to be updated
	 */
	private void updateReceipt(ViewableReceipt receipt, String status)
	{
		for(Receipt r : receipt.getUser().getListOfReceipts())
			if(r.getId() == receipt.getId())
			{
				Receipt nr = new Receipt(r.getId(), r.getValue(), status, r.getFilePath());
				receipt.getUser().updateReceipt(r, nr);
				return;
			}
	}
}
