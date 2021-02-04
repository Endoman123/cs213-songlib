package edu.rutgers.library;

import java.util.ArrayList;

/**
 * A collection of songs.
 * <p>
 * This class handles organization of songs ONLY. This class does NOT handle
 * writing out the songs to a file, nor does this class handle displaying the
 * list in a GUI (though it should print the song list as text just fine).
 * <p>
 * This is an extension of an {@code ArrayList}, so all those functions apply,
 * as well as some niceties.
 * 
 * @see Song
 */
public class Library extends ArrayList<Song> {
    /**
     * Add a song to the collection, so long as it is not a duplicate.
     * 
     * @param s the song to add to the library
     * @return  1 if successful,
     *          0 otherwise
     */
    public boolean add(Song s) {
       if (get(s.getName(), s.getArtist()) == null) {
           super.add(s);
           return true;
       }

       return false;
    }

    /**
     * Finds a song by name and artist.
     * 
     * @param name   the name of the song to find
     * @param artist the artist of the song to find
     * @return       the {@code Song} with the same name and artist,
     *               or {@code null} if nonexistent
     */
    public Song get(String name, String artist) {
        for (Song s : this) {
            if (s.getName().equals(name) && s.getArtist().equals(artist))
                return s;
        }

        return null;
    }

    /**
     * Gets a string representing the full list of songs.
     * <p>
     * Really meant as a form of debug without GUI,
     * or if you were to interface with this via CLI.
     */
    public String toString() {
        // I use a StringBuilder to avoid constant re-allocation
        // of a string object
        StringBuilder b = new StringBuilder();
        
        for (Song s : this)
            b.append("" + s + System.lineSeparator());
        
        return b.toString();
    }
}