package spud;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TextToSpeech extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {

		// Load the view from the FXML file.
		try {
			Scene scene = new Scene(FXMLLoader.load(this.getClass().getResource("Dialog.fxml")));

			primaryStage.setTitle("Text to Speech");
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
