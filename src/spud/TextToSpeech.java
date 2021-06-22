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

	//private native void execCommand(String[] command);

	public static void main(String[] args) {
		launch(args);
	}

	private static TextField input;

	private static final Button[] mostRecentButtons = new Button[3];

	private static final String[] COMMAND_HEADER = {"/bin/zsh", "-c"};

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
		ObservableList<Node> rows = parent.getChildren();

		// Setup the input text field.
		TextToSpeech.input = (TextField) rows.get(0);

		// Setup the button rows.
		ObservableList<Node> recentButtons = ((HBox) rows.get(4)).getChildren();
		for (int i = 0; i < 3; i++) {
			Button button = (Button) recentButtons.get(i);
			TextToSpeech.mostRecentButtons[i] = button;
		}

		// Set the scene.
		Scene scene = new Scene(parent);

		// Set the title.
		primaryStage.setTitle("Text to Speech");
		primaryStage.setScene(scene);
		primaryStage.show();
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
	public void speak() {

		// Execute the speech command.
		TextToSpeech.executeCommand(new String[]{"say", TextToSpeech.input.getText()});

		// TODO Shift input.

		// Clear the input.
		TextToSpeech.input.clear();
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
	 *
	 * @param command
	 */
	private static void executeCommand(String[] command) {

		if (command == null) {
			return;
		}

		String[] FINAL_COMMAND = new String[TextToSpeech.COMMAND_HEADER.length + command.length];

		System.arraycopy(TextToSpeech.COMMAND_HEADER, 0, FINAL_COMMAND, 0, TextToSpeech.COMMAND_HEADER.length);
		System.arraycopy(command, 0, FINAL_COMMAND, TextToSpeech.COMMAND_HEADER.length, command.length);

		//new ExecCommand(FINAL_COMMAND);
		/*
		Process process = null;
		try {
			process = Runtime.getRuntime().exec(FINAL_COMMAND);

			new ReadStream("stdin", process.getInputStream()).start();
			new ReadStream("stderr", process.getErrorStream()).start();

			process.waitFor();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		} finally {

			if (process != null) {
				process.destroy();
			}
		}
		 */
	}
}
