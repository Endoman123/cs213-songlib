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
        Library test = new Library();

        test.add(new Song("test", "", "c", 2000));
        test.add(new Song("test", "", "a", 2000));
        test.add(new Song("test", "", "b", 2000));

        System.out.println(test.toString());
        // Pane root = FXMLLoader.load(getClass().getResource("/main.fxml"));

        // root.autosize();
        // primaryStage.setScene(new Scene(root));
        // primaryStage.sizeToScene();
        // primaryStage.setTitle("SongLib");

        // primaryStage.setResizable(false);
        // primaryStage.show();
    }
}
