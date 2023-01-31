package application;

import javafx.scene.control.Button;
import javafx.scene.text.Font;

public class SidebarButton extends Button
{
	/**
	 * Used for the side bar in the application
	 * @param text - text to be displayed inside the button.
	 */
	public SidebarButton(String text)
	{
		super(text);
		
		this.setFocusTraversable(false);
		this.setMinWidth(Main.SIDEBAR_WIDTH);
		this.setMaxWidth(Main.SIDEBAR_WIDTH);
		this.getStyleClass().add("sidebarButton");
		this.setFont(new Font("Coda", 14));
	}
}
