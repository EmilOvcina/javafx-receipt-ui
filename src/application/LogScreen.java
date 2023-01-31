package application;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class LogScreen extends GridPane
{
	//List of log entries.
	private static List<String> log = new ArrayList<>();
	private static TextArea logPanel;
	
	public LogScreen()
	{
		super();
		logPanel = new TextArea();
		
		this.setMaxHeight(600 - Main.NAVBAR_HEIGHT);
		this.setMaxWidth(800 - Main.SIDEBAR_WIDTH);
		
		VBox vFiller = new VBox();
		vFiller.setMinSize(50, 600);
		
		HBox hFiller = new HBox();
		hFiller.getChildren().add(TextLabel.createTextLabel("Application Log", 16, Color.BLACK, "title"));
		hFiller.getStyleClass().add("title");
		
		logPanel.setMaxWidth(800 - (Main.SIDEBAR_WIDTH + 100));
		logPanel.setMinWidth(800 - (Main.SIDEBAR_WIDTH + 100));
		logPanel.setMaxHeight(600 - (Main.NAVBAR_HEIGHT + 100));
		logPanel.setMinHeight(600 - (Main.NAVBAR_HEIGHT + 100));
		logPanel.setEditable(false);
		logPanel.setFocusTraversable(false);
		logPanel.getStyleClass().add("logPanel");
		
		this.add(vFiller, 0, 1);
		this.add(hFiller, 1, 1);
		this.add(logPanel, 1, 1);
		
		Platform.runLater(()-> {
			updateLog();
		});
	}
	/**
	 * Puts all the elements from the list of log entries into the text area on the screen.
	 */
	private static void updateLog()
	{
		logPanel.setText("");
		for(String s : log) 
			logPanel.setText(s + "\n" +logPanel.getText());
	}
	/**
	 * Universal method of adding something to the log.
	 * @param entry - String of event to add to the log.
	 */
	public static void addToLog(String entry)
	{
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		String entryTime = dtf.format(now);
		log.add(entryTime + " - " + Main.ROLE + ": " + Main.NAME + " - " + entry);
		updateLog();
	}
}
