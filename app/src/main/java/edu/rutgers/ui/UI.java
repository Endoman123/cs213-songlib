package edu.rutgers.ui;

import edu.rutgers.library.*;

import javafx.event.ActionEvent;
import javafx.util.converter.IntegerStringConverter;

import javafx.fxml.*;
import javafx.application.Platform;
import javafx.collections.ModifiableObservableListBase;
import javafx.collections.FXCollections;

import javafx.scene.control.*;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;

/**
 * The UI Controller class for the application.
 */
public class UI {
    public class ObservableLibrary extends ModifiableObservableListBase<Song> {
        private final Library DELEGATE;

        protected ObservableLibrary(Library l) {
            DELEGATE = l;
        }
        
        public Song get(int index) { 
            return DELEGATE.get(index);
        }

        public int size() {
            return DELEGATE.size();
        }

        protected void doAdd(int index, Song element) {
            DELEGATE.add(element);
        }

        protected Song doSet(int index, Song element) {
            return DELEGATE.set(index, element);
        }

        protected Song doRemove(int index) {
            return DELEGATE.remove(index);
        }
    }

    private Library lib;
    private ObservableLibrary obsLib;

    private int curSelected;

    // Elements
    @FXML
    private ListView 
        lstSongs;

    @FXML Label 
        lblName,
        lblArtist,
        lblAlbum,
        lblYear;

    @FXML TextField 
        txtName,
        txtArtist,
        txtAlbum;
    
    @FXML Spinner<Integer>
        txtYear;

    @FXML Button 
        btnAdd,
        btnEdit,
        btnDelete;

    /**
     * Sets the library to use with the song list.
     * 
     * @param l the {@code Library} object to bind to the {@code ListView} items.
     */
    public void initLibrary(Library l) {
        lib = l;
        obsLib = new ObservableLibrary(lib);

        lstSongs.setItems(obsLib);
        lstSongs.getSelectionModel().select(0);
    }

    /**
     * Initialization of the UI. Hooked into the event where the parent stage becomes visible.
     * <p>
     * Used to initialize our listeners, filters, etc. in here.
     */
    @FXML
    public void initialize() {
        // Limit the txtYear box to only digits.
        txtYear.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 2021, 2021));
        txtYear.getEditor().setText("");
        txtYear.getEditor().setTextFormatter(new TextFormatter<>(change -> {
            String t = change.getControlNewText();

            return t.matches("([1-9][0-9]{0,3})?") ? change : null;
        }));

        // Update the info display for the music
        lstSongs.getSelectionModel().selectedIndexProperty().addListener( (ov, old_val, new_val) -> {
            Song s;

            // We need this in other places, may as well track it globally.
            curSelected = new_val.intValue();

            s = lib.get(curSelected);

            lblName.setText(s.getName());
            lblArtist.setText(s.getArtist());
            lblAlbum.setText(s.getAlbum().isEmpty() ? "" : s.getAlbum());
            lblYear.setText("" + (s.getYear() > 0 ? s.getYear() : ""));
        });
    }

    @FXML
    public void addSong() {
        Song s;
        String 
            name = txtName.getText(),
            artist = txtArtist.getText(),
            album = txtAlbum.getText(),
            year = txtYear.getEditor().getText();

        // Step 1: Validate input
        if (!name.isEmpty() && !artist.isEmpty()) {
            if (!album.isEmpty() || !year.isEmpty()){
                s = new Song(name, artist, album, Integer.parseInt(year));
            } else {
                s = new Song(name, artist);
            }

            // Step 2: Attempt to add the song
            if (!obsLib.add(s)) {
                // TODO: Add a status label to display this
                System.out.println("Sorry, repeat!");
            }
        }
    }
    
    @FXML
    public void editSong() {
    	
    }

    @FXML
    public void deleteSong() {
        obsLib.remove(curSelected);       
    }
}