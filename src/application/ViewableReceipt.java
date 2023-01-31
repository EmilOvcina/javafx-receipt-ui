package application;

import java.io.FileNotFoundException;

import javafx.scene.control.Button;

public class ViewableReceipt extends Receipt 
{
	private Button viewButton;
	private String name;
	private User user;
	/**
	 * Extends Receipt, and is used for easy displaying in table views.
	 * @param id - ID of receipt
	 * @param value - the amount which was used on the receipt.
	 * @param status - status in the system. "approved", "fraud" or "pending"
	 * @param filePath - File path of the image of the receipt
	 * @param viewButton - button used to open pop up window
	 * @param name - name of the user owning the receipt
	 * @param user - user object of the owner
	 */
	public ViewableReceipt(int id, double value, String status, String filePath, Button viewButton, String name, User user) {
		super(id, value, status, filePath);
		this.viewButton = viewButton;
		this.user = user;
		this.name = name;
		viewButton.getStyleClass().add("viewReceiptButton");
		viewButton.setMinWidth(125);
		
		//Opens the pop-up to view the receipt
		viewButton.setOnAction(e -> {
			try {
				new ViewReceiptStage(this);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} 
		});
	}
	/**
	 * @return the view button to open pop-up
	 */
	public Button getViewButton() {
		return viewButton;
	}
	/**
	 * @return gets name of user
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return gets user object owning receipt
	 */
	public User getUser() {
		return user;
	}
}