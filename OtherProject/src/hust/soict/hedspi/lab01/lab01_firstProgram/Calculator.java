import javax.swing.JOptionPane;

public class Calculator {
    public static void main(String[] args) {
        String strNum1 = JOptionPane.showInputDialog("Input the first number");
        String strNum2 = JOptionPane.showInputDialog("Input the second number");

        double num1 = Double.parseDouble(strNum1);
        double num2 = Double.parseDouble(strNum2);

        double sum = num1 + num2;
        double difference = num1 - num2;
        double product = num1 * num2;
        double quotient = num1 / num2;

        String result = "Sum: " + sum + "\n" +
                        "Difference: " + difference + "\n" +
                        "Product: " + product + "\n" +
                        "Quotient: " + quotient;

        JOptionPane.showMessageDialog(null, result,
                "Calculation Results", JOptionPane.INFORMATION_MESSAGE);

        System.exit(0);
    }
}