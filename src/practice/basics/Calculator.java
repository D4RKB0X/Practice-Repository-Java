package practice.basics;
import java.util.Scanner;

class CalculatorMenu {
    public static void menuStart(int fn, int sn) {
        Scanner input = new Scanner(System.in);

        System.out.print("Choose Operator (+, -, *, /): ");
        char operator = input.next().charAt(0);

        switch(operator) {
            case '+': {
                int result = CalculatorFunctions.additionFunction(fn, sn);
                System.out.printf("Result: %d", result);
                break;
            }
            case '-': {
                int result = CalculatorFunctions.subtractionFunction(fn, sn);
                System.out.printf("Result: %d", result);
                break;
            }
            case '*': {
                int result = CalculatorFunctions.multiplicationFunction(fn, sn);
                System.out.printf("Result: %d", result);
                break;
            }
            case '/': {
                if(sn == 0) {
                    System.out.print("Cannot Divide by Zero!\n");
                    return;
                }
                else {
                    double result = CalculatorFunctions.divisionFunction(fn, sn);
                    System.out.printf("Result: %f", result);
                }

                break;
            }
            default: {
                System.out.println("Invalid Input!\n");
            }
        }
    }
}

class CalculatorFunctions {
    public static int additionFunction(int fn, int sn) { return fn + sn; }
    public static int subtractionFunction(int fn, int sn) { return fn - sn; }
    public static int multiplicationFunction(int fn, int sn) { return fn * sn; }
    public static double divisionFunction(double fn, double sn) { return fn / sn; }
}

class CalcTest {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter First Number: ");
        int fn = input.nextInt();

        System.out.print("Enter Second Number: ");
        int sn = input.nextInt();

        CalculatorMenu.menuStart(fn, sn);

        input.close();
    }
}
