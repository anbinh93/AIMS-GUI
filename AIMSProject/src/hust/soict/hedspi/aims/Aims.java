package hust.soict.hedspi.aims;

import java.util.*;
import hust.soict.hedspi.aims.cart.Cart;
import hust.soict.hedspi.aims.menu.Menu;
import hust.soict.hedspi.aims.store.Store;
import hust.soict.hedspi.aims.media.*;

import javax.naming.LimitExceededException;

public class Aims {

    private static final Scanner scanner = new Scanner(System.in);

    public static Media getMediaInfo() {
        int mediaType;
        int id;
        String title;
        String category;
        float cost;

        Menu.mediaTypeMenu();
        mediaType = getValidInput("Choose media type (1-3):", 1, 3);

        System.out.println("Enter the media id: ");
        id = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter the media title: ");
        title = scanner.nextLine();
        System.out.println("Enter the media category: ");
        category = scanner.nextLine();
        System.out.println("Enter the media cost: ");
        cost = scanner.nextFloat();
        scanner.nextLine();

        if (mediaType == 1) {
            List<String> authors = getAuthors();
            Book book = new Book(id, title, category, cost);
            authors.forEach(book::addAuthor);
            return book;
        } else if (mediaType == 2) {
            System.out.println("Enter the media length: ");
            int length = scanner.nextInt();
            scanner.nextLine();
            System.out.println("Enter the media director: ");
            String director = scanner.nextLine();
            return new DigitalVideoDisc(id, title, category, director, length, cost);
        } else {
            System.out.println("Enter the media director: ");
            String director = scanner.nextLine();
            System.out.println("Enter the media artist: ");
            String artist = scanner.nextLine();
            CompactDisc cd = new CompactDisc(id, title, artist, category, director, cost);

            while (true) {
                System.out.println("Enter the track title (Enter q to quit): ");
                String trackTitle = scanner.nextLine();
                if (trackTitle.equalsIgnoreCase("q")) break;
                System.out.println("Enter the track length: ");
                int trackLength = scanner.nextInt();
                scanner.nextLine();
                cd.addTrack(new Track(trackTitle, trackLength));
            }
            return cd;
        }
    }

    private static List<String> getAuthors() {
        List<String> authors = new ArrayList<>();
        while (true) {
            System.out.println("Enter the author name (Press q to stop entering): ");
            String author = scanner.nextLine();
            if (author.equalsIgnoreCase("q")) break;
            authors.add(author);
        }
        return authors;
    }

    private static int getValidInput(String message, int min, int max) {
        int input;
        do {
            System.out.println(message);
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }
            input = scanner.nextInt();
            scanner.nextLine();
        } while (input < min || input > max);
        return input;
    }

    public static void main(String[] args) throws LimitExceededException {
        Store store = new Store();
        Cart cart = new Cart();

        while (true) {
            Menu.mainMenu();
            int option = getValidInput("Choose an option (0-3):", 0, 3);

            switch (option) {
                case 1:
                    storeMenu(store, cart);
                    break;
                case 2:
                    updateStoreMenu(store);
                    break;
                case 3:
                    cartMenu(cart);
                    break;
                case 0:
                    System.out.println("Exiting the application.");
                    scanner.close();
                    System.exit(0);
            }
        }
    }

    private static void storeMenu(Store store, Cart cart) throws LimitExceededException {
        while (true) {
            Menu.storeMenu();
            int option = getValidInput("Choose an option (0-4):", 0, 4);

            switch (option) {
                case 1:
                    System.out.println("Enter the title of the media: ");
                    String title = scanner.nextLine();
                    Media media = store.findMediaByTitle(title);
                    if (media != null) {
                        if (media instanceof Book) {
                            Menu.bookDetailsMenu();
                            int choice = getValidInput("Choose an option (0-1):", 0, 1);
                            if (choice == 1) cart.addMedia(media);
                        } else {
                            Menu.playableDetailsMenu();
                            int choice = getValidInput("Choose an option (0-2):", 0, 2);
                            if (choice == 1) cart.addMedia(media);
                            else if (choice == 2) playMedia(media);
                        }
                    } else System.out.println("Media not found.");
                    break;
                case 2:
                    store.showStore();
                    System.out.println("Enter the title of the media to add to the cart: ");
                    title = scanner.nextLine();
                    media = store.findMediaByTitle(title);
                    if (media != null) cart.addMedia(media);
                    else System.out.println("Media not found.");
                    break;
                case 3:
                    store.showStore();
                    System.out.println("Enter the title of the media to play: ");
                    title = scanner.nextLine();
                    media = store.findMediaByTitle(title);
                    if (media != null) playMedia(media);
                    else System.out.println("Media not found.");
                    break;
                case 4:
                    cart.printCart();
                    break;
                case 0:
                    return;
            }
        }
    }

    private static void updateStoreMenu(Store store) {
        Menu.updateStoreOptionMenu();
        int option = getValidInput("Choose an option (0-2):", 0, 2);

        switch (option) {
            case 1:
                Media media = getMediaInfo();
                store.addMedia(media);
                break;
            case 2:
                System.out.println("Enter the media title to remove from the store: ");
                String title = scanner.nextLine();
                Media found = store.findMediaByTitle(title);
                if (found != null) store.removeMedia(found);
                else System.out.println("Media not found.");
                break;
            case 0:
                System.out.println("Canceling update store....");
                break;
        }
    }

    private static void cartMenu(Cart cart) {
        while (true) {
            cart.printCart();
            Menu.cartMenu();
            int option = getValidInput("Choose an option (0-5):", 0, 5);

            switch (option) {
                case 1:
                    Menu.filterOptionMenu();
                    int filterOption = getValidInput("Choose an option (1-2):", 1, 2);
                    if (filterOption == 1) {
                        System.out.println("Enter media's ID to filter: ");
                        int id = scanner.nextInt();
                        scanner.nextLine();
                        cart.filterById(id);
                    } else {
                        System.out.println("Enter media's title to filter: ");
                        String title = scanner.nextLine();
                        cart.filterByTitle(title);
                    }
                    break;
                case 2:
                    Menu.sortOptionMenu();
                    int sortOption = getValidInput("Choose an option (1-2):", 1, 2);
                    if (sortOption == 1) cart.sortMediaByTitle();
                    else cart.sortMediaByCost();
                    break;
                case 3:
                    System.out.println("Enter the title of the media to remove: ");
                    String title = scanner.nextLine();
                    Media found = cart.searchMediaByTitle(title);
                    if (found != null) cart.removeMedia(found);
                    else System.out.println("Media not found.");
                    break;
                case 4:
                    System.out.println("Enter the title of the media to play: ");
                    title = scanner.nextLine();
                    Media media = cart.searchMediaByTitle(title);
                    if (media != null) playMedia(media);
                    else System.out.println("Media not found.");
                    break;
                case 5:
                    System.out.println("Order created successfully!");
                    cart.emptyCart();
                    break;
                case 0:
                    return;
            }
        }
    }

    private static void playMedia(Media media) {
        if (media instanceof Playable) {
            try {
                ((Playable) media).play();
            } catch (Exception e) {
                System.out.println("Failed to play media: " + e.getMessage());
            }
        } else {
            System.out.println("This media type cannot be played.");
        }
    }
}
