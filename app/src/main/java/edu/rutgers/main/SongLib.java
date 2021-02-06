package edu.rutgers.main;

import edu.rutgers.ui.UI;

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
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/songLib.fxml"));
        Pane root = (Pane)loader.load();

        //loader.setLocation(getClass().getResource("/main.fxml"));
        
        UI ui = loader.getController();
        ui.start();

        root.autosize();

        primaryStage.setScene(new Scene(root));
        primaryStage.sizeToScene();
        primaryStage.setTitle("SongLib");

        primaryStage.setOnHidden(e -> ui.close());
        primaryStage.setResizable(true);
        primaryStage.show();
    }
}
