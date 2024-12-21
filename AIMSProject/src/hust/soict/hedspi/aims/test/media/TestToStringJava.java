package hust.soict.hedspi.test.media;

import java.util.ArrayList;
import java.util.List;
import hust.soict.hedspi.aims.media.Book;
import hust.soict.hedspi.aims.media.CompactDisc;
import hust.soict.hedspi.aims.media.DigitalVideoDisc;
import hust.soict.hedspi.aims.media.Media;
import hust.soict.hedspi.aims.media.Track;

public class TestToStringJava {

    public static void main(String[] args) {
        List<Media> items = createMediaItems();
        printMediaItems(items);
    }

    private static List<Media> createMediaItems() {
        List<Media> items = new ArrayList<>();

        Book book1 = new Book(1, "Harry Potter", "Fantasy", 20f);
        book1.addAuthor("J.K.Rowling");

        DigitalVideoDisc dvd1 = new DigitalVideoDisc(2, "The Lion King", "Animation", 19.95f);

        CompactDisc cd1 = new CompactDisc(123, "Thriller", "Micheal Jackson", "Pop", "Micheal Jackson", 25.65f);
        addTracksToCD(cd1);

        items.add(book1);
        items.add(cd1);
        items.add(dvd1);

        return items;
    }

    private static void addTracksToCD(CompactDisc cd) {
        Track track1 = new Track("Wanna Be Startin' Somethin", 363);
        Track track2 = new Track("Baby Be Mine", 260);
        Track track3 = new Track("The Girl Is Mine", 222);

        cd.addTrack(track1);
        cd.addTrack(track2);
        cd.addTrack(track3);
    }

    private static void printMediaItems(List<Media> items) {
        for (Media item : items) {
            System.out.println(item.toString());
        }
    }
}