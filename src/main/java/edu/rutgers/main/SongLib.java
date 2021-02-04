package edu.rutgers.main;

import edu.rutgers.library.*;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Main runner class
 */
public class SongLib extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane root = FXMLLoader.load(getClass().getResource("/main.fxml"));

        root.autosize();
        primaryStage.setScene(new Scene(root));
        primaryStage.sizeToScene();
        primaryStage.setTitle("SongLib");

        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
