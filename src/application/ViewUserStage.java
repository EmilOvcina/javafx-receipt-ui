package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ViewUserStage extends Stage 
{
	//Dimensions of the pop-up window
	private static final int WIDTH = 240, HEIGHT = 250;
	//Table of users receipts
	private static TableView<Receipt> table;
	private int tableWidth = WIDTH;
	
	/**
	 * @param user - user to be viewed in pop-up window
	 */
	public ViewUserStage(ViewableUser user)
	{
		super();
		this.initModality(Modality.APPLICATION_MODAL);
		this.initOwner(Main.mainStage);
		this.setTitle("User #" + user.getId() + " - " + user.getName());
		this.setResizable(false);
		 
		BorderPane content = new BorderPane();
		content.setMaxWidth(tableWidth);
		
		table = new TableView<>();
		table.setMaxHeight(600 - (Main.NAVBAR_HEIGHT + 100));
		table.setMinHeight(600 - (Main.NAVBAR_HEIGHT + 100));
		table.setMaxWidth(tableWidth);
		table.setFocusTraversable(false);
		table.setEditable(false);
		
		TableColumn<Receipt, Integer> idColumn = new TableColumn<>("ID");
		idColumn.setCellValueFactory(new PropertyValueFactory<Receipt, Integer>("id"));
		
		TableColumn<Receipt, Double> amountColumn = new TableColumn<>("Amount");
		amountColumn.setCellValueFactory(new PropertyValueFactory<Receipt, Double>("value"));
		
		TableColumn<Receipt, String> statusColumn = new TableColumn<>("Status");
		statusColumn.setCellValueFactory(new PropertyValueFactory<Receipt, String>("status"));
		
		table.getColumns().add(idColumn);
		table.getColumns().add(amountColumn); 
		table.getColumns().add(statusColumn);
		
		loadTable(user);
		content.setCenter(table);
		
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
	 * Displays table items from the list of receipts from the user
	 * @param user - user which has receipts to be displayed.
	 */
	private static void loadTable(ViewableUser user)
	{
		ObservableList<Receipt> receipts = FXCollections.observableArrayList();
		for(Receipt r : user.getUser().getListOfReceipts()) {
			receipts.add(r);
		}
	
		table.setItems(receipts);	
	}
}
