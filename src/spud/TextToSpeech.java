package spud;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class TextToSpeech extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	private TextField input;

	private Button[] mostRecentButtons = new Button[3];

	private static final String[] COMMAND_HEADER = {"/bin/bash", "-c"};

	@Override
	public void start(Stage primaryStage) {

		// Create an object to store the parent.
		VBox parent;

		// Load the view from the FXML file.
		try {
			parent = FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("Dialog.fxml")));
		} catch (IOException | NullPointerException e) {
			e.printStackTrace();
			return;
		}

		// Get the elements in each row of the parent.
		ObservableList<Node> rows = parent.getChildrenUnmodifiable();

		// Setup the input text field.
		this.input = (TextField) rows.get(0);

		// Setup the button rows.
		//noinspection SuspiciousToArrayCall
		this.mostRecentButtons = ((HBox) rows.get(4)).getChildrenUnmodifiable().toArray(new Button[3]);

		((Button) ((HBox) rows.get(2)).getChildrenUnmodifiable().get(0)).setOnAction(event -> {

			// Execute the speech command.
			TextToSpeech.executeCommand(new String[]{"say", this.input.getText()});

			// TODO Shift input.

			// Clear the input.
			this.input.clear();
		});

		// Set the scene.
		Scene scene = new Scene(parent);

		// Set the title.
		primaryStage.setTitle("Text to Speech");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * TODO Documentation
	 */
	@FXML
	public void speak() {

		// Execute the speech command.
		TextToSpeech.executeCommand(new String[]{"say", this.input.getText()});

		// TODO Shift input.

		// Clear the input.
		this.input.clear();
	}

	/**
	 * Close the application.
	 */
	@FXML
	public void exit() {
		System.exit(0);
	}

	/**
	 * TODO Documentation
	 */
	@FXML
	public void openSettings() {
		TextToSpeech.executeCommand(new String[]{"open \"x-apple.systempreferences:com.apple.preference.universalaccess?TextToSpeech\""});
	}

	/**
	 * TODO Documentation
	 * @param command
	 */
	private static void executeCommand(String[] command) {
		String[] FINAL_COMMAND = new String[TextToSpeech.COMMAND_HEADER.length + command.length];

		System.arraycopy(TextToSpeech.COMMAND_HEADER, 0, FINAL_COMMAND, 0, TextToSpeech.COMMAND_HEADER.length);
		System.arraycopy(command, 0, FINAL_COMMAND, TextToSpeech.COMMAND_HEADER.length, command.length);

		try {
			Runtime.getRuntime().exec(FINAL_COMMAND);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
