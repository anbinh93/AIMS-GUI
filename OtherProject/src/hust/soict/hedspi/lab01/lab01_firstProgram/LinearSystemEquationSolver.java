import java.util.Scanner;

public class LinearSystemEquationSolver {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter parameters a1, b1, c1, a2, b2, c2: ");
        double a1 = scanner.nextDouble();
        double b1 = scanner.nextDouble();
        double c1 = scanner.nextDouble();
        double a2 = scanner.nextDouble();
        double b2 = scanner.nextDouble();
        double c2 = scanner.nextDouble();

        double det = a1 * b2 - a2 * b1;

        if (det == 0) {
            if (b1 * c2 == b2 * c1) {
                System.out.println("The system has infinitely many solution.");
            } else {
                System.out.println("The system has no solution.");
            }
        } else {
            double x = (c1 * b2 - c2 * b1) / det;
            double y = (a1 * c2 - a2 * c1) / det;
            System.out.print("The solution is x = " + x + ", y = " + y);
        }
        scanner.close();
    }
}
