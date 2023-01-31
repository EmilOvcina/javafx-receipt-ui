package application;
import java.io.File;
import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;

public class UploadReceiptScreen extends GridPane
{
	private String outFilePath = "";
	public UploadReceiptScreen() {
		super();
		
		this.setMinHeight(600);
		this.setMinWidth(800);
		
		int fontSize = 16;
		
		//The "amount" vbox
		Label amountLabel = TextLabel.createTextLabel("Amount:", fontSize);
		TextField amountField = new TextField();
		
		VBox amountVBox = new VBox(amountLabel, amountField);
		amountVBox.setSpacing(5);
		amountVBox.setPadding(new Insets(15));
		amountVBox.setPrefWidth(200);
		
		
		//The "Picture of the recipts" vbox
		Label pictureLabel = TextLabel.createTextLabel("Picture of reciept:", fontSize);
		FileChooser pictureFileChooser = new FileChooser();
		Button pictureButton = new Button("Choose file");
		pictureButton.setOnAction(e -> {
            File tmp = pictureFileChooser.showOpenDialog(new Stage());
            setFileString(tmp.getPath());
        });
		VBox pictureVBox = new VBox(pictureLabel, pictureButton);
		pictureVBox.setPadding(new Insets(15));
		pictureVBox.setSpacing(5);
		pictureVBox.setPrefWidth(200);
		
		//The "Send receipt" button vbox
		Button sendButton = new Button("Send receipt");
		Label sendLabel = new Label();
		sendButton.setOnAction(e -> {
			try
			{
				double value = Double.parseDouble(amountField.getText());
				int receiptID = getNextID(Main.userLoggedIn.getListOfReceipts());
				Receipt tmp = new Receipt(receiptID, value, "Pending", outFilePath);
				Main.userLoggedIn.addReceipt(tmp);
				LogScreen.addToLog("Receipt added");
				sendLabel.setText("Receipt sent");
				
			}
			catch(NumberFormatException exception)
			{
			  sendLabel.setText("Please use numbers only and \n\".\" as decimal point");
			}
		});
		
		VBox sendVBox = new VBox(sendButton, sendLabel);
		sendVBox.setSpacing(5);
		sendVBox.setPadding(new Insets(15));
		sendVBox.setPrefWidth(200);
		
		this.add(amountVBox, 0, 0);
		this.add(pictureVBox, 0, 1);
		this.add(sendVBox, 0, 2);
		
	}
	/**
	 * @param s - path to the image chosen when uploading the receipt
	 */
	private void setFileString(String s) {
		outFilePath = s;
	}
	
	/**
	 * @param receipts - list of all receipts in the system
	 * @return next ID a receipt should have
	 */
	private int getNextID(List<Receipt> receipts)
	{
		int highestID = 0;
		for(Receipt receipt : receipts) 
			if(receipt.getId() > highestID)
				highestID = receipt.getId();
		return highestID + 1;
	}
}