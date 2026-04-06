package practice.basics;
import java.util.*;

class PriceAnalysis {
    public static double getTotal(double[] prices, int size) {
        double total = 0.0;
        for(int i = 0; i < size; i++) {
            total += prices[i];
        }

        return total;
    }
    public static double getAverage(double[] prices, int size) {
        double average = 0.0;
        average = getTotal(prices, size) / size;

        return average;
    }
    public static double getMax(double[] prices, int size) {
        return Arrays.stream(prices).max().getAsDouble();
    }
    public static double getMin(double[] prices, int size) {
        return Arrays.stream(prices).min().getAsDouble();
    }
    public static int getCount(double[] prices, int size) {
        int count = 0;
        double avg = getAverage(prices, size);

        for(int i = 0; i < size; i++) {
            if(prices[i] > avg) {
                count++;
            }
        }

        return count;
    }
}

class priceTest{
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        double[] prices = new double[5];

        System.out.print("Enter 5 Numbers: ");
        for(int i = 0; i < 5; i++) {
            prices[i] = input.nextDouble();
        }

        int size = prices.length;

        double total = PriceAnalysis.getTotal(prices, size);
        double average = PriceAnalysis.getAverage(prices, size);
        double max = PriceAnalysis.getMax(prices, size);
        double min = PriceAnalysis.getMin(prices, size);
        int count = PriceAnalysis.getCount(prices, size);

        System.out.printf("%.2f$ total price.\n", total);
        System.out.printf("%.2f$ average.\n", average);
        System.out.printf("%.2f$ max price.\n", max);
        System.out.printf("%.2f$ min price.\n", min);
        System.out.printf("%d count of prices above average.\n", count);

        input.close();
    }
}