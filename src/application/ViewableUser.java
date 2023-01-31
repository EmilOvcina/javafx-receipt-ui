package application;

import javafx.scene.control.Button;

public class ViewableUser extends User
{
	private Button button;
	private String role;
	private User user;
	/**
	 * Extends User, and is used for easy displaying in table views.
	 * @param id - ID of the user
	 * @param name - Name of the user
	 * @param role - the role of the user
	 * @param button - the view button which opens the pop-up window for viewing a user
	 * @param user - the user object
	 */
	public ViewableUser(int id, String name, String role, Button button, User user) {
		super(id, name);
		this.button = button;
		this.role = role;
		this.user = user;
		
		button.getStyleClass().add("viewReceiptButton");
		button.setMinWidth(125);
		
		button.setOnAction(e-> {
			new ViewUserStage(this);
		});
	}
	/**
	 * @return the original user object
	 */
	public User getUser() {
		return user;
	}
	/**
	 * @return the button used for pop-up window
	 */
	public Button getButton() {
		return button;
	}
	/**
	 * @return the role of the user
	 */
	public String getRole() {
		return role;
	}
}
