package edu.rutgers.main;

import edu.rutgers.library.*;

/**
 * Main runner class
 */
public class SongLib {
    public static void main(String[] args) {
        Library lib = new Library();

        lib.add(new Song("Sun Veins", "Landmark", "Hippo Campus", 2017));
        lib.add(new Song("Way it Goes", "Landmark", "Hippo Campus", 2017));
        lib.add(new Song("Vines", "Landmark", "Hippo Campus", 2017));

        System.out.println(lib.toString());
    }
}
