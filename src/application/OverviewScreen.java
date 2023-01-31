package application;

import java.util.function.Predicate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class OverviewScreen extends GridPane 
{
	//List of receipts which are displayed in the screen
	private static ObservableList<Receipt> receipts;
	//Dimension of the tab view component 
	private final int tabWidth = 410;
	private Tab tab1, tab2, tab3, tab4;
	//Each table containing filtered receipts
	private static TableView<Receipt> t1, t2, t3, t4;
	
	public OverviewScreen() {
		super();
		receipts = FXCollections.observableArrayList();
		
		t1 = createTable(0);
		t2 = createTable(1);
		t3 = createTable(2);
		t4 = createTable(3);
		
		TabPane tabPane = new TabPane();
		tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		tab1 = new Tab("All receipts", t1);
		tab2 = new Tab("Approved", t2);
		tab3 = new Tab("Pending", t3);
		tab4 = new Tab("Declined", t4);
		tabPane.getTabs().addAll(tab1, tab2, tab3, tab4);
		tabPane.setMaxWidth(tabWidth);
		tabPane.setMaxHeight(600 - (Main.NAVBAR_HEIGHT + 100));
		tabPane.setMinHeight(600 - (Main.NAVBAR_HEIGHT + 100));
		VBox vFiller = new VBox();
		vFiller.setMinSize(((Main.WIDTH - Main.SIDEBAR_WIDTH) - tabWidth) / 2, 600);
		
		HBox hFiller = new HBox();
		hFiller.getChildren().add(TextLabel.createTextLabel("Your Receipts", 16, Color.BLACK, "title"));
		hFiller.getStyleClass().add("title");
		
		this.add(vFiller, 0, 1);
		this.add(hFiller, 1, 1);
		this.add(tabPane, 1, 1);
	}
	
	/**
	 * Goes through list of receipts and updates the table view to get the up to date list of receipts
	 */
	public void updateOverview()
	{
		ObservableList<Receipt> updatedReceipts = FXCollections.observableArrayList();
		for(Receipt r : Main.userLoggedIn.getListOfReceipts()) {
			updatedReceipts.add(r);
		}
		
		t1.setItems(updatedReceipts);
		FilteredList<Receipt> tabApproved = new FilteredList<>(FXCollections.observableArrayList(updatedReceipts), getApproved());
		t2.setItems(tabApproved);
		FilteredList<Receipt> tabPending = new FilteredList<>(FXCollections.observableArrayList(updatedReceipts), getPending());
		t3.setItems(tabPending);
		FilteredList<Receipt> tabDeclined = new FilteredList<>(FXCollections.observableArrayList(updatedReceipts), getDeclined());
		t4.setItems(tabDeclined);
	}
	
	/**
	 * Used to create the filtered tables
	 * @param filter - specifies which status the receipt has. 0 = unfiltered, 1 = approved, 2 = pending, 3 = declined
	 * @return a table view containing receipts depending on the filtering.
	 */
	private TableView<Receipt> createTable(int filter) {
		TableView<Receipt> tableView = new TableView<>();
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		TableColumn<Receipt, Integer> column1 = new TableColumn<>("Id"); 
		column1.setCellValueFactory(new PropertyValueFactory<Receipt, Integer>("id"));
		TableColumn<Receipt, Double> column2 = new TableColumn<>("Amount");
		column2.setCellValueFactory(new PropertyValueFactory<Receipt, Double>("value"));
		TableColumn<Receipt, String> column3 = new TableColumn<>("Status");
		column3.setCellValueFactory(new PropertyValueFactory<Receipt, String>("status"));
		tableView.getColumns().add(column1);
		tableView.getColumns().add(column2);
		tableView.getColumns().add(column3);
		
		switch(filter) {
			case 0:
				tableView.setItems(FXCollections.observableArrayList(receipts));
				break;
			case 1:
				FilteredList<Receipt> tabApproved = new FilteredList<>(FXCollections.observableArrayList(receipts), getApproved());
				tableView.setItems(tabApproved);
				break;
			case 2:
				FilteredList<Receipt> tabPending = new FilteredList<>(FXCollections.observableArrayList(receipts), getPending());
				tableView.setItems(tabPending);
				break;
			case 3:
				FilteredList<Receipt> tabDeclined = new FilteredList<>(FXCollections.observableArrayList(receipts), getDeclined());
				tableView.setItems(tabDeclined);
				break;
		}
		tableView.setFocusTraversable(false);
		tableView.setEditable(false);
		return tableView;
	}
	/**
	 * @return predicate (boolean-valued function) of if a receipt is approved
	 */
	static Predicate<Receipt> getApproved() {
		return p -> p.getStatus().equals("Approved");
	}
	/**
	 * @return predicate (boolean-valued function) of if a receipt is pending
	 */
	static Predicate<Receipt> getPending() {
		return p -> p.getStatus().equals("Pending");
	}
	/**
	 * @return predicate (boolean-valued function) of if a receipt is fraud
	 */
	static Predicate<Receipt> getDeclined() {
		return p -> p.getStatus().equals("Fraud");
	}
}
