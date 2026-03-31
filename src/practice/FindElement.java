package practice;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class FindElement {
    public static int findNumber(List<Integer> arr, int find) {
        for(int i = 0; i < arr.size(); i++) {
            if(arr.get(i) == find) {
                return i;
            }
        }
        return -1;
    }
}

class findTest {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        List<Integer> arr = new ArrayList<>();
        arr.add(1);
        arr.add(2);
        arr.add(3);
        arr.add(4);
        arr.add(5);

        System.out.print("Enter the element you want to find from (1 - 5): ");
        int find = input.nextInt();

        int found = FindElement.findNumber(arr, find);

        if (found != -1) {
            System.out.println("Element found at index: " + found);
        }
        else {
            System.out.println("Element not found!");
        }

        input.close();
    }
}
