package edu.rutgers.library;

import java.io.*;
import java.util.ArrayList;

/**
 * A collection of songs.
 * <p>
 * This will handle the organization of the library,
 * as well the file i/o of libraries.
 * <p>
 * This is an extension of an {@code ModifiableObservableList}, so all those functions apply,
 * as well as some niceties.
 * 
 * @see Song
 * @see ModifiableObservableList
 */
public class Library extends ArrayList<Song> {
    public Library() {
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
        int l = 0, r = size() - 1, m;

        // Yes, we construct a new song just to use the compareTo method.
        // No, I don't regret anything.
        Song s = new Song(name, artist);

        while (l <= r) {
            m = (int)((l + r) / 2);
            int comp = get(m).compareTo(s);

            if (comp < 0) { // Song must come before, move the right pointer
                r = m - 1;
            } else if (comp > 0) { // Song must come after, move the left pointer
                l = m + 1;
            } else // Song exists, return
                return get(m);
        }

        // Song does not exist
        return null;
    }

    /**
     * Adds a song to the library, maintaining list order.
     * 
     * @param s the song to add to the library
     */
    @Override
    public boolean add(Song s) {
        int l = 0, r = size() - 1, m;

        while (l <= r) {
            m = (int)((l + r) / 2);
            int comp = get(m).compareTo(s);

            if (comp < 0) { // Song must come before, move the right pointer
                r = m - 1;
            } else if (comp > 0) { // Song must come after, move the left pointer
                l = m + 1;
            } else // Song exists, exit
                return false;
        }

        // Insert the song into the rightmost pointer
        add((int)((l + r + 1) / 2), s);
        return true;
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
                    s.getArtist(), 
                    s.getAlbum(), 
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