import javax.swing.JOptionPane;
public class HelloNameDialog {
    public static void main(String[] args) {
        String res;
        res = JOptionPane.showInputDialog("Enter your name?");
        JOptionPane.showMessageDialog(null, "Hello, " + res + "!");
        System.exit(0);

    }
}
