package hust.soict.hedspi.aims.media;

import hust.soict.hedspi.aims.except.MediaPlayerException;

public class Track implements Playable {
    private String title;
    private int length;

    // Constructor
    public Track(String title, int length) {
        this.title = title;
        this.length = length;
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public int getLength() {
        return length;
    }

    // Equals method
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Track track = (Track) obj;
        return length == track.length && title.equals(track.title);
    }

    // Play method
    @Override
    public void play() throws MediaPlayerException {
        if (length <= 0) {
            throw new MediaPlayerException("ERROR: Track length is non-positive!");
        }
        System.out.printf("Playing track: %s%nTrack length: %d%n", title, length);
    }

    // Play media as a string
    public String playMedia() throws MediaPlayerException {
        if (length <= 0) {
            throw new MediaPlayerException("ERROR: Track length is non-positive!");
        }
        return String.format("Playing track: %s\nTrack length: %d", title, length);
    }
}
