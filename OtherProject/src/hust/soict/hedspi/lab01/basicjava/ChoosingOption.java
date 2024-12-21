import javax.swing.*;
import javax.swing.JOptionPane;
public class ChoosingOption {
    public static void main(String[] args) {
        int option = javax.swing.JOptionPane.showConfirmDialog(null, "Do you want to change to the first class ticket?");
        javax.swing.JOptionPane.showMessageDialog(null, "You've chosen: " + (option == JOptionPane.YES_OPTION ? "Yes" : "No"));
        System.exit(0);
    }
}

