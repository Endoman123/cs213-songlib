package edu.rutgers.ui;

import edu.rutgers.library.Library;
import edu.rutgers.library.Song;

import javafx.collections.ModifiableObservableListBase;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

/**
 * The UI Controller class for the application.
 */
public class UI {
    /**
     * An {@code ObservableList} wrapper that allows the {@code Library} obect to be
     * observed by the {@code ListView} and be subsequently updated when the list is modified.
     * 
     * @see ObservableList
     */
    public class ObservableLibrary extends ModifiableObservableListBase<Song> {
        private final Library DELEGATE;

        protected ObservableLibrary(Library l) {
            DELEGATE = l;
        }
        
        public Song get(String name, String artist) {
            return DELEGATE.get(name, artist);
        }

        public Song get(int index) { 
            return DELEGATE.get(index);
        }

        @Override
        public boolean add(Song element) {
            boolean ret = false;

            beginChange();
            ret = DELEGATE.add(element);
            nextAdd(0, size() - 1);
            endChange();

            return ret;
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
    private boolean isEditing = false;

    // Elements
    @FXML
    private ListView<Song> 
        lstSongs;

    @FXML Label 
        lblName,
        lblArtist,
        lblAlbum,
        lblYear,
        lblDebug,
        lblFieldStatus;

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

            s = obsLib.get(curSelected);

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
                s = new Song(name, artist, album, year.isEmpty() ? 0 : Integer.parseInt(year));
            } else {
                s = new Song(name, artist);
            }

            // Step 2: Attempt to add the song
            if (!obsLib.add(s))
                debug("Couldn't add \"%s\", duplicate exists!", s.toString());
            else
                debug("Successfully added \"%s\"", s.toString());
        }

        txtName.setText("");
        txtArtist.setText("");
        txtAlbum.setText("");
        txtYear.getEditor().setText("");
    }
    
    @FXML
    public void editSong() {
        Song s;

        if (isEditing) {
            Song temp;
            String 
                name = txtName.getText(),
                artist = txtArtist.getText(),
                album = txtAlbum.getText(),
                year = txtYear.getEditor().getText();

            // Step 1: Validate input
            if (!name.isEmpty() && !artist.isEmpty()) {
                if (!album.isEmpty() || !year.isEmpty()) {
                    s = new Song(name, artist, album, year.isEmpty() ? 0 : Integer.parseInt(year));
                } else {
                    s = new Song(name, artist);
                }
                    
                // Step 2: Remove the song to be edited and replace it
                temp = obsLib.remove(curSelected);

                if (!obsLib.add(s)) {
                    obsLib.add(temp);

                    debug("Couldn't save \"%s\", duplicate exists!", s.toString());
                } else
                    debug("Saved \"%s\" as \"%s\"", temp.toString(), s.toString());
            } 

            txtName.setText("");
            txtArtist.setText("");
            txtAlbum.setText("");
            txtYear.getEditor().setText("");

            lblFieldStatus.setText("Add a song below:");
        } else {
            s = obsLib.get(curSelected);

            lblFieldStatus.setText("Editing " + s.toString());
            debug("Editing \"%s\"...", s.toString());

            txtName.setText(s.getName());
            txtArtist.setText(s.getArtist());
            txtAlbum.setText(s.getAlbum());
            txtYear.getEditor().setText("" + (s.getYear() > 0 ? s.getYear() : ""));
        }

        // Regardless, let's toggle some buttons and things
        isEditing = !isEditing;

        lstSongs.setDisable(isEditing);
        btnAdd.setDisable(isEditing);
        btnDelete.setDisable(isEditing);
    }

    @FXML
    public void deleteSong() {
        obsLib.remove(curSelected);       
    }

    /**
     * Sends a formatted debug message to the end user via the debug label.
     * 
     * @param format the format of the debug message
     * @param args   the arguments to pass to the format
     */
    private void debug(String format, Object... args) {
        String message = String.format(format, args);
        lblDebug.setText(message);
    }
}