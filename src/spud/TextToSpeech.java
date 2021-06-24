package spud;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.intellij.lang.annotations.RegExp;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextToSpeech extends Application {

	public static void main(String[] args) {
		TextToSpeech.launch(args);
	}

	private static TextField input;

	private static final Button[] mostRecentButtons = new Button[3];

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
	protected void exit() {
		System.exit(0);
	}

	/**
	 * TODO Documentation
	 */
	@FXML
	protected void speak() {

		String text = TextToSpeech.input.getText();

		// Check if the entered text has any speakable characters.
		Pattern pattern = Pattern.compile("[\\d|\\w]");
		Matcher matcher = pattern.matcher(text);

		// If the entered text is just whitespace just return.
		if (!matcher.find()) {
			return;
		}

		// Shift input.
		TextToSpeech.mostRecentButtons[2].setText(TextToSpeech.mostRecentButtons[1].getText());
		TextToSpeech.mostRecentButtons[1].setText(TextToSpeech.mostRecentButtons[0].getText());
		TextToSpeech.mostRecentButtons[0].setText(text);

		// Execute the speech command.
		TextToSpeech.executeCommand(new String[]{"say", text});

		// Clear the input.
		TextToSpeech.input.clear();
	}

	/**
	 * TODO Documentation
	 * @param event
	 */
	@FXML
	protected void mostRecent(@NotNull ActionEvent event) {

		Button button = (Button) event.getTarget();

		TextToSpeech.input.setText(button.getText());

		this.speak();
	}

	/**
	 * TODO Documentation
	 */
	@FXML
	protected void openSettings() {
		TextToSpeech.executeCommand(new String[]{"/bin/zsh", "-c",
				"open \"x-apple.systempreferences:com.apple.preference.universalaccess?TextToSpeech\""});
	}

	/**
	 * TODO Documentation
	 *
	 * @param command
	 */
	private static void executeCommand(@NotNull String[] command) {

		try {
			Process process = Runtime.getRuntime().exec(command);
			process.waitFor();

			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			@SuppressWarnings("MismatchedQueryAndUpdateOfStringBuilder")
			StringBuilder output = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				output.append(line).append("\n");
			}
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}
