package edu.rutgers.main;

import edu.rutgers.ui.UI;
import edu.rutgers.library.Library;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Main runner class
 * 
 * @author Oscar Bartolo
 * @author Jared Tulayan
 */
public class SongLib extends Application {
    private final String LIBRARY_PATH = "./library.txt";

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Library lib;
        UI ui;
        Pane root;
        FXMLLoader loader = new FXMLLoader();

        //loader.setLocation(getClass().getResource("/songLib.fxml"));
        loader.setLocation(getClass().getResource("/songLib2.fxml"));

        root = loader.load();
        ui = loader.getController();
        lib = new Library();

        lib.read(LIBRARY_PATH);
        ui.initLibrary(lib);

        root.autosize();

        primaryStage.setScene(new Scene(root));
        primaryStage.sizeToScene();
        primaryStage.setTitle("SongLib");

        primaryStage.setOnHidden(e -> lib.write(LIBRARY_PATH));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
