package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class ApproveReceiptScreen extends GridPane
{
	//Table for all the receipts.
	private static TableView<ViewableReceipt> table;
	//Width of the table, used for centering.
	private final int tableWidth = 450;
	
	public ApproveReceiptScreen()
	{
		super();
		table = new TableView<>();
		this.setMaxHeight(600 - Main.NAVBAR_HEIGHT);
		this.setMaxWidth(800 - Main.SIDEBAR_WIDTH);
		
		table.setMaxHeight(600 - (Main.NAVBAR_HEIGHT + 100));
		table.setMinHeight(600 - (Main.NAVBAR_HEIGHT + 100));
		table.setMaxWidth(tableWidth);
		table.setFocusTraversable(false);
		table.setEditable(false);
		
		TableColumn<ViewableReceipt, Integer> idColumn = new TableColumn<>("ID");
		idColumn.setCellValueFactory(new PropertyValueFactory<ViewableReceipt, Integer>("id"));
		
		TableColumn<ViewableReceipt, String> nameColumn = new TableColumn<>("User");
		nameColumn.setCellValueFactory(new PropertyValueFactory<ViewableReceipt, String>("name"));
		nameColumn.setMinWidth(100);
		
		TableColumn<ViewableReceipt, Double> amountColumn = new TableColumn<>("Amount");
		amountColumn.setCellValueFactory(new PropertyValueFactory<ViewableReceipt, Double>("value"));
		
		TableColumn<ViewableReceipt, String> statusColumn = new TableColumn<>("Status");
		statusColumn.setCellValueFactory(new PropertyValueFactory<ViewableReceipt, String>("status"));
		
		TableColumn<ViewableReceipt, Button> viewColumn = new TableColumn<>(" ");
		viewColumn.setCellValueFactory(new PropertyValueFactory<ViewableReceipt, Button>("viewButton"));
		viewColumn.setMinWidth(130);
		
		table.getColumns().add(idColumn);
		table.getColumns().add(nameColumn);
		table.getColumns().add(amountColumn); 
		table.getColumns().add(statusColumn);
		table.getColumns().add(viewColumn);
		
		VBox vFiller = new VBox();
		vFiller.setMinSize(((Main.WIDTH - Main.SIDEBAR_WIDTH) - tableWidth) / 2, 600);
		
		HBox hFiller = new HBox();
		hFiller.getChildren().add(TextLabel.createTextLabel("Manage Receipts", 16, Color.BLACK, "title"));
		hFiller.getStyleClass().add("title");
		
		this.add(hFiller, 1, 1);
		this.add(table, 1, 1);
		this.add(vFiller, 0, 1);
		updateScreen();
	}
	
	/**
	 * Goes through all users list of receipts and updates the table view with the up to date list of receipts
	 */
	public static void updateScreen()
	{
		ObservableList<ViewableReceipt> receipts = FXCollections.observableArrayList();
		for(User user : Main.users)
			for(Receipt r : user.getListOfReceipts()) {
				receipts.add(new ViewableReceipt(r.getId(), r.getValue(), r.getStatus(), r.getFilePath(), 
						new Button("View"), user.getName(), user));
			}
		
		table.setItems(receipts);		
	}
}
