package edu.rutgers.library;

import java.io.*;
import java.util.ArrayList;

/**
 * A collection of songs.
 * <p>
 * This will handle the organization of the library,
 * as well the file i/o of libraries.
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
     * Read a file and import the contents into this library.
     * <p>
     * This will overwrite the contents of this library.
     * 
     * @param path the path to the file to read from
     */
    public void read(String path) {
        try { 
            BufferedReader r = new BufferedReader(new FileReader(path));
    
            for (String s = r.readLine(); s != null; s = r.readLine()) { 
                String[] fields = s.split("\\|");

                add(new Song(
                    fields[0].trim(),
                    fields[1].trim(),
                    fields[2].trim(),
                    Integer.parseInt(fields[3].trim())
                ));
            }

            r.close();
        } catch (Exception e) { 
            e.printStackTrace();
        }
    }

    /**
     * Write the library out to a file.
     * <p>
     * Assumes that you want to overwrite the file if it exists.
     * 
     * @param path the path to write the library out to
     */
    public void write(String path) {
        File f = new File(path);
        BufferedWriter w;

        // We're going to start with making sure that the
        // file exists and can be written to
        if (!f.exists()) {
            File dir = f.getParentFile(); 

            if (dir != null && !dir.exists() && dir.isDirectory()) {
                if (!dir.mkdirs())
                    return;
            }                
        } else
            f.delete();


        // Now we write to the file
        try {
            f.createNewFile();
            w = new BufferedWriter(new FileWriter(f));

            for (Song s : this) {
                w.write(String.format(
                    "%s | %s | %s | %s" + System.lineSeparator(), 
                    s.getName(), 
                    s.getAlbum(), 
                    s.getArtist(), 
                    "" + s.getYear()
                ));
            }

            // Close the stream
            w.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
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