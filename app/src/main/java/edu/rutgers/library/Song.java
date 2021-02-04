package edu.rutgers.library;

/**
 * Representation of a long in the song library.
 * <p> 
 * This will maintain a way of storing a song's fields,
 * as well as provide a standard of writing out a song's info. 
 * 
 * @see Library
 */
public class Song {
    private String name;
    private String album;
    private String artist;
    private int year;

    /**
     * Constructs a new Song with the provided fields.
     * 
     * @param n  the name of the song
     * @param al the album the song is a part of
     * @param ar the artist of the song
     * @param y  the year the song was written
     */
    public Song(String n, String al, String ar, int y) {
        name = n;
        album = al;
        artist = ar;

        if (y < 0) {
            throw new IllegalArgumentException("Song year cannot be less than 0!");
        }

        year = y;
    }

    public void setName(String v) {
        name = v;
    }

    public void setAlbum(String v) {
        album = v;
    }

    public void setArtist(String v) {
        artist = v;
    }

    public void setYear(int v) {
        if (v < 0) {
            throw new IllegalArgumentException("Song year cannot be less than 0!");
        }

        year = v;
    }

    public String getName() {
        return name;
    }
    
    public String getAlbum() {
        return album;
    }

    public String getArtist() {
        return artist;
    }

    public int getYear() {
        return year;
    }

    /**
     * Displays the song as a string.
     * 
     * The format is as follows:
     * <pre>
     * "ARTIST - NAME [ALBUM, YEAR]"
     * </pre>
     * For instance, Hippo Campus' "Way it Goes" would display as
     * <pre>
     * "Hippo Campus - Way it Goes [Landmark, 2017]"
     * </pre>
     */
    public String toString() {
        return String.format(
                    "%s - %s [%s, %s]", 
                    artist,
                    name,
                    album,
                    year == 0 ? "????" : "" + year
                );
    }
}
