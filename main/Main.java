package az.developia.audioplayer.main;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application{

	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage1) throws Exception {
		try {
		AnchorPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("az/developia/audioplayer/view/AudioPlayer.fxml"));
		Scene scene = new Scene(pane);
		stage1.setScene(scene);
		stage1.setTitle("Audio Player");
		stage1.setResizable(false);
		stage1.show();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

}
