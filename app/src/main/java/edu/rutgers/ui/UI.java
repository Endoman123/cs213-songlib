package edu.rutgers.ui;

import edu.rutgers.library.*;

import javafx.event.ActionEvent;

import javafx.fxml.*;
import javafx.application.Platform;
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
    //@FXML ListView lstSongs;

    private Library lib;
    private ObservableList<Song> olLibrary;

    private State curState;

    // Elements
    @FXML
    private ListView lstSongs;

	@FXML TextField inputName;
	@FXML TextField inputArtist;
	@FXML TextField inputAlbum;
	@FXML TextField inputYear;
    
    public void start() {
    	lib = new Library();
        olLibrary = FXCollections.observableList(lib);

        lib.read("library.txt");

        System.out.println("Library app started");
        
        lstSongs.setItems(olLibrary);

        lstSongs.getSelectionModel().select(0);
        lstSongs.getSelectionModel().selectedIndexProperty().addListener( (ov, old_val, new_val) -> {
            if ((int)new_val == -1)
                curState = State.ADD;
            else
                curState = State.VIEW;
        });
    }
    
    /*
    @FXML
    public void initialize() {
        lib = new Library();
        olLibrary = FXCollections.observableList(lib);

        lib.read("library.txt");

        System.out.println("Library app started");

        lstSongs.setItems(olLibrary);

        lstSongs.getSelectionModel().select(0);
        lstSongs.getSelectionModel().selectedIndexProperty().addListener( (ov, old_val, new_val) -> {
            if ((int)new_val == -1)
                curState = State.ADD;
            else
                curState = State.VIEW;
        });
    }
    */

    @FXML
    public void addSong() {
    	String name = inputName.getText();
		System.out.println("Name: " + name);
		if (name.equals("")) {
			System.out.println("No Name");

			inputName.setText("");
			inputArtist.setText("");
			inputAlbum.setText("");
			inputYear.setText("");
			System.out.println();
			return;
		}
		
		String artist = inputArtist.getText();
		System.out.println("Artist: " + artist);
		if (artist.equals("")) {
			System.out.println("No Artist");

			inputName.setText("");
			inputArtist.setText("");
			inputAlbum.setText("");
			inputYear.setText("");
			System.out.println();
			return;
		}
		
		String album = inputAlbum.getText();
		System.out.println("Album: " + album);
		
		String year = inputYear.getText();
		int numOfYear = 0;
		try {
			numOfYear = Integer.parseInt(year);
			if (numOfYear < 0) {
				// THROW ERROR AND DO NOT ADD SONG
				System.out.println("Negative year");
				
				inputName.setText("");
				inputArtist.setText("");
				inputAlbum.setText("");
				inputYear.setText("");
				System.out.println();
				return;
			} else {
				System.out.println("Year: " + numOfYear);
			}
		} catch (NumberFormatException nfe) {
			//e1.printStackTrace();
			
		}
		
		Song temp = new Song(name, artist, album, numOfYear);
		
		olLibrary.add(temp);
		FXCollections.sort(olLibrary);

		inputName.setText("");
		inputArtist.setText("");
		inputAlbum.setText("");
		inputYear.setText("");
		
    }
    
    @FXML
    public void editSong() {
    	
    }

    @FXML
    public void deleteSong() {
    	
    }
    
    public void close() {
        lib.write("library.txt");
        System.out.println("Saved to library.txt");
    }

    @FXML
    public void exitApplication(ActionEvent event) {
        Platform.exit();
    }
}