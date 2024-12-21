package hust.soict.hedspi.aims.media;

import java.util.ArrayList;
import java.util.List;

import hust.soict.hedspi.aims.except.MediaPlayerException;

public class CompactDisc extends Disc implements Playable {
    private String artist;
    private List<Track> tracks = new ArrayList<>();

    public CompactDisc(int id, String title, String artist) {
        super(id, title);
        this.artist = artist;
    }

    public CompactDisc(int id, String title, String artist, String category, float cost) {
        super(id, title, category, cost);
        this.artist = artist;
    }

    public CompactDisc(int id, String title, String artist, String category, String director, float cost) {
        super(id, title, category, director, cost);
        this.artist = artist;
    }

    public String getArtist() {
        return artist;
    }

    public void addTrack(Track track) {
        if (tracks.contains(track)) {
            System.out.println("Track is already in the list");
        } else {
            tracks.add(track);
            System.out.println("Add track successfully");
        }
    }

    public void removeTrack(Track track) {
        if (tracks.contains(track)) {
            tracks.remove(track);
            System.out.println("Remove track successfully");
        } else {
            System.out.println("Track is not in the list");
        }
    }

    @Override
    public String toString() {
        StringBuilder tracksInfo = new StringBuilder();
        for (Track track : tracks) {
            tracksInfo.append(track.getTitle()).append(", ");
        }
        if (tracksInfo.length() > 0) {
            tracksInfo.setLength(tracksInfo.length() - 2); // Remove the last ", "
        }
        return String.format("%d. CD - %s - %s - %s - %s - %d: %.2f$",
                             getId(), getTitle(), getCategory(), artist, tracksInfo, getLength(), getCost());
    }

    public int getLength() {
        return tracks.stream().mapToInt(Track::getLength).sum();
    }

    @Override
    public void play() throws MediaPlayerException {
        if (getLength() > 0) {
            System.out.println("Playing CD: " + getTitle());
            for (Track track : tracks) {
                track.play();
            }
        } else {
            throw new MediaPlayerException("ERROR: CD length is non-positive");
        }
    }

    public String playMedia() throws MediaPlayerException {
        if (getLength() > 0) {
            StringBuilder output = new StringBuilder("Playing CD: ").append(getTitle()).append("\n");
            for (Track track : tracks) {
                output.append(track.playMedia()).append("\n");
            }
            return output.toString();
        } else {
            throw new MediaPlayerException("ERROR: CD length is non-positive");
        }
    }
}