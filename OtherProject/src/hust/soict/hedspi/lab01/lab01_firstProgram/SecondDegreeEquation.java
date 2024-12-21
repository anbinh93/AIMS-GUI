public class SecondDegreeEquation   {
    public static void main(String[] args) {
        // Create a Scanner object
        java.util.Scanner scanner = new java.util.Scanner(System.in);

        // Get the coefficients from the user
        System.out.print("Enter a, b, c: ");
        double a = scanner.nextDouble();
        double b = scanner.nextDouble();
        double c = scanner.nextDouble();

        // Calculate the discriminant
        double dis = b * b - 4 * a * c;

        // Calculate the roots
        if (dis > 0) {
            double root1 = (-b + Math.sqrt(dis)) / (2 * a);
            double root2 = (-b - Math.sqrt(dis)) / (2 * a);
            System.out.println("The roots are " + root1 + " and " + root2);
        } else if (dis == 0) {
            double root = -b / (2 * a);
            System.out.println("The root is " + root);
        } else {
            System.out.println("The equation has no real roots");
        }

        // Close the scanner
        scanner.close();
    }
}
