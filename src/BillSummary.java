// Delta College - CST 283 - Homework 1
// Name: Aaron Pelto
// Winter 2024

import java.io.*;
import java.util.Scanner;


// Bill Summary Homework
// Write a program that summarizes the energy bill for this home.
// The file energy.txt has month, year, and energy usage

public class BillSummary {
    // Final statements for all the electrical costs required

    static final String FILENAME = "energy.txt";
    final static double WINTER_RATE = 0.223;
    final static double WINTER_RATE_OVER = 0.315;
    final static double SUMMER_RATE = 0.184;
    final static double SUMMER_RATE_OVER = 0.293;
    final static int WINTER_OVER_RATE = 800;
    final static int SUMMER_OVER_RATE = 700;

    public static void main(String[] args) {

        // Display a Menu for the user to choose from
        choiceMenu();


    }

    private static void choiceMenu() {

        while (true) {
            // Create a menu
            System.out.println("Energy Bill Summary");
            System.out.println("1. Display Energy.txt Data");
            System.out.println("2. Calculate Energy Cost per Month");
            System.out.println("3. Exit");
            System.out.println("Enter your choice: ");

            // Create a scanner object
            Scanner input = new Scanner(System.in);
            int choice = input.nextInt();

            // Create a switch statement to handle the menu
            switch (choice) {
                case 1:
                    System.out.println("Display Energy.txt Data");
                    dataExtract();
                    break;
                case 2:
                    System.out.println("Calculate Energy Cost per Month");
                    calculateAverageCost();
                    break;
                case 3:
                    exitProgram();
                    break;
                default:
                    System.out.println("Invalid choice. Please select 1, 2, or 3.");
                    break;
            }
        }
    }


    private static void exitProgram() {
        System.out.println("Exiting Program");
        System.exit(0);
    }

    // This is a method to extract the data from the file
    // I've aligned the text for the data, so I know the data is extracting correctly, and can be displayed to the user
    // I've created a menu to display the data so the user can confirm that the data is correct.
    private static void dataExtract() {
        try {
            File dataFileRef = new File(FILENAME);

            // Check for file existence.  If not found, display error and exit
            if (!dataFileRef.exists()) {
                System.out.println("File not found");
                System.exit(0);
            }

            Scanner inputFile = new Scanner(dataFileRef);

            System.out.println("Year  Month  Energy Used");
            // File processing loop.

            while (inputFile.hasNext()) {
                String line = inputFile.nextLine();
                String[] parts = line.split(",");

                if (parts.length == 3) {
                    int month = Integer.parseInt(parts[0]);
                    int year = Integer.parseInt(parts[1]);
                    int energy = Integer.parseInt(parts[2]);
                    System.out.printf("%-4d  %-5d  %-12d\n", year, month, energy);
                } else {
                    System.out.println("Invalid Data Format: " + line);
                }
            }
            inputFile.close();
            // If file error, display message and crash
        } catch (IOException e)
        {
            System.out.println("File error");
            System.exit(0);
        }
    }

    // Method to calculate the energy cost per month
    // Than summarize it for the 3-year period
    public static double calcElecBill(int month, int energy) {
        double bill;
        if (month == 1 || month == 2 || month == 3) {
            if (energy <= 800) {
                bill = WINTER_RATE * energy;
            } else {
                bill = WINTER_RATE * WINTER_OVER_RATE + ((energy - WINTER_OVER_RATE) * WINTER_RATE_OVER);
            }
        } else {
            if (energy <= 700) {
                bill = SUMMER_RATE * energy;
            } else {
                bill = SUMMER_RATE * SUMMER_OVER_RATE + ((energy - SUMMER_OVER_RATE) * SUMMER_RATE_OVER);
            }
        }
        return Double.parseDouble(String.format("%.2f", bill));
    }

    public static void calculateAverageCost() {
        // Array to store total cost for each month
        double[] totalCostPerMonth = new double[12];
        // Array to store count of months
        int[] countPerMonth = new int[12];

        try {
            File dataFileRef = new File(FILENAME);

            // Check for file existence.  If not found, display error and exit
            if (!dataFileRef.exists()) {
                System.out.println("File not found");
                System.exit(0);
            }

            Scanner inputFile = new Scanner(dataFileRef);

            // File processing loop
            while (inputFile.hasNext()) {
                String line = inputFile.nextLine();
                String[] parts = line.split(",");

                if (parts.length == 3) {
                    int month = Integer.parseInt(parts[0]);
                    // Because we are not using year, we do not loop through the year and store the year data
                    int energy = Integer.parseInt(parts[2]);

                    double cost = calcElecBill(month, energy);
                    totalCostPerMonth[month - 1] += cost;
                    countPerMonth[month - 1]++;
                } else {
                    System.out.println("Invalid Data Format: " + line);
                }
            }
            inputFile.close();

            // Calculate and display average cost for each month
            // Loop through the months and then calculate the average cost
            for (int i = 0; i < 12; i++) {
                if (countPerMonth[i] > 0) {
                    double averageCost = totalCostPerMonth[i] / countPerMonth[i];
                    // Print a statement that explains the electric cost for each month
                    // Format the output to 2 decimal places
                    // Print the average cost for each month
                    // Print a statement if there is no data found for the month
                    System.out.printf("Average Electric Cost for Month %d: $%.2f\n", i + 1, averageCost);
                } else {
                    System.out.printf("No data found for Month %d\n", i + 1);
                }
            }

            // Error Catching
            // Exits if there is an issue with the file.
        } catch (IOException e) {
            System.out.println("File error");
            System.exit(0);
        }
    }
    }


