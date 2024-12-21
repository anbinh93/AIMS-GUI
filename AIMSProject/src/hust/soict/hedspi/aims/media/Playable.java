package hust.soict.hedspi.aims.media;

import hust.soict.hedspi.aims.except.MediaPlayerException;

public interface Playable {
    public void play() throws MediaPlayerException;
    public String playMedia() throws MediaPlayerException;

}