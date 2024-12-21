package hust.soict.hedspi.aims.screen.manager;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

import hust.soict.hedspi.aims.media.Media;
import hust.soict.hedspi.aims.store.Store;

public class StoreAddItemScreen extends JFrame {
    private static final long serialVersionUID = 1L;
    private final Store store;

    public StoreAddItemScreen(Store store) {
        this.store = store;
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());

        cp.add(createNorthPanel(), BorderLayout.NORTH);
        cp.add(createCenterPanel(), BorderLayout.CENTER);

        setTitle("Store Add Item");
        setSize(1024, 768);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private JPanel createNorthPanel() {
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS));
        northPanel.add(createMenuBar());
        northPanel.add(createHeader());
        return northPanel;
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu menu = new JMenu("Options");
        JMenuItem viewStore = new JMenuItem("View Store");
        menu.add(viewStore);

        JMenu updateStoreMenu = new JMenu("Update Store");
        JMenuItem addBook = new JMenuItem("Add a Book");
        JMenuItem addCD = new JMenuItem("Add a CD");
        JMenuItem addDVD = new JMenuItem("Add a DVD");

        updateStoreMenu.add(addBook);
        updateStoreMenu.add(addCD);
        updateStoreMenu.add(addDVD);
        menu.add(updateStoreMenu);

        menuBar.add(menu);
        return menuBar;
    }

    private JPanel createHeader() {
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.X_AXIS));

        JLabel titleLabel = new JLabel("AIMS");
        titleLabel.setFont(new Font(titleLabel.getFont().getName(), Font.PLAIN, 50));
        titleLabel.setForeground(Color.CYAN);

        header.add(Box.createRigidArea(new Dimension(10, 10)));
        header.add(titleLabel);
        header.add(Box.createHorizontalGlue());
        header.add(Box.createRigidArea(new Dimension(10, 10)));

        return header;
    }

    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(3, 3, 10, 10));

        ArrayList<Media> mediaInStore = store.getItemsInStore();
        for (int i = 0; i < Math.min(9, mediaInStore.size()); i++) {
            MediaStore cell = new MediaStore(mediaInStore.get(i));
            centerPanel.add(cell);
        }

        return centerPanel;
    }

    public static void main(String[] args) {
        Store store = new Store();
        new StoreAddItemScreen(store);
    }
}
