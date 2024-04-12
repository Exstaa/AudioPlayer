package az.developia.audioplayer.controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AudioPlayerController {

	@FXML
	private Label audioNameLabel;

	@FXML
	private Slider durationChangerSlider;

	@FXML
	private Label endDurationLabel;

	@FXML
	private MenuItem openMenuItem;

	@FXML
	private Button pauseButton;

	@FXML
	private Button playButton;

	@FXML
	private Label startDurationLabel;

	@FXML
	private Slider volumeSlider;

	private MediaPlayer player;

	private Integer totalAudioLengthSeconds;

	@FXML
	void openMenuItemClicked(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		File selectedFile = fileChooser.showOpenDialog(null);
		if (selectedFile != null && selectedFile.toString().contains(".mp3")) {
			if (player != null) {
				player.stop();
			}
			Media media = new Media(selectedFile.toURI().toString());
			player = new MediaPlayer(media);
			player.play();
			player.setVolume(volumeSlider.getValue());
			audioNameLabel.setText(selectedFile.getName());
			Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.1), event1 -> {
				String duration = player.getTotalDuration().toString();
				endDurationLabel.setText(changeDuration(player.getTotalDuration().toString()));
				totalAudioLengthSeconds = Integer.parseInt(duration.substring(0, duration.indexOf("."))) / 1000;
				refreshDurationEverySecond();
			}));
			timeline.play();
		}
	}

	@FXML
	void volumeSliderDragged(MouseEvent event) {
		if (player != null) {
			player.setVolume(volumeSlider.getValue());
		}
	}

	@FXML
	void playButtonClicked(ActionEvent event) {
		if (player != null) {
			player.play();
		}
	}

	@FXML
	void pauseButtonClicked(ActionEvent event) {
		if (player != null) {
			player.pause();
		}
		
	}
	public String changeDuration(String duration) {
		Integer totalDurationMs = Integer.parseInt(duration.substring(0, duration.indexOf(".")));
		int totalSeconds = totalDurationMs / 1000;
		int seconds = totalSeconds % 60;
		int minutes = totalSeconds / 60;
		String minutesSt = String.valueOf(minutes);
		String secondsSt = String.valueOf(seconds);
		if (seconds < 10) {
			secondsSt = "0" + secondsSt;
		}
		return minutesSt + ":" + secondsSt;
	}

	public void refreshDurationEverySecond() {
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event1 -> {
			String duration = player.getCurrentTime().toString();
			Integer currentSeconds = Integer.parseInt(duration.substring(0, duration.indexOf(".")))/1000;
			startDurationLabel.setText(changeDuration(player.getCurrentTime().toString()));
            durationChangerSlider.setValue((currentSeconds / (double) totalAudioLengthSeconds)*100);		
		}));
		timeline.setCycleCount(totalAudioLengthSeconds);
		timeline.play();
	}
}
