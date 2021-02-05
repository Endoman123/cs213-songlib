package edu.rutgers.ui;

import edu.rutgers.library.*;

import javafx.fxml.*;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

import javafx.scene.control.*;

/**
 * The UI Controller class for the application.
 */
public class UI {
    private enum State {
        ADD,
        VIEW,
        EDIT
    }

    private Library lib;
    private ObservableList<Song> olLibrary;

    private State curState;

    // Elements
    @FXML
    private ListView lstSongs;

    @FXML
    public void initialize() {
        lib = new Library();
        olLibrary = FXCollections.observableList(lib);

        lib.read("library.txt");

        System.out.println("Library app started");

        lstSongs.setItems(olLibrary);

        lstSongs.getSelectionModel().select(0);        
        lstSongs.getSelectionModel().selectedItemProperty().addListener( (ov, old_val, new_val) -> {
            if ((int)new_val == -1)
                curState = State.ADD;
            else
                curState = State.VIEW;
        });
    }
}