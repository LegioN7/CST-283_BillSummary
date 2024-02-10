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
    final static int MAX_RECORDS = 100;

    public static void main(String[] args) {
        Energy[] energyRecords = readEnergyData();

        // Display a Menu for the user to choose from
        choiceMenu();


    }

    // Method to read the energy data from the file
    public static Energy[] readEnergyData() {
        Energy[] energyRecords = new Energy[MAX_RECORDS];
        int count = 0;

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
                    int year = Integer.parseInt(parts[1]);
                    int energy = Integer.parseInt(parts[2]);
                    energyRecords[count] = new Energy(month, year, energy);
                    count++;
                } else {
                    System.out.println("Invalid Data Format: " + line);
                }
            }
            inputFile.close();

            // Error Catching
            // Exits if there is an issue with the file.
        } catch (IOException e) {
            System.out.println("File error");
            System.exit(0);
        }

        return energyRecords;
    }

    private static void choiceMenu() {

        do {
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
                    displayEnergyData(readEnergyData());
                    break;
                case 2:
                    System.out.println("Calculate Energy Cost per Month");
                    calculateAverageCost(readEnergyData());
                    break;
                case 3:
                    System.out.println("Exiting Program");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please select 1, 2, or 3.");
                    break;
            }
        } while (true);
    }


    // This is a method to extract the data from the file
    // I've aligned the text for the data, so I know the data is extracting correctly, and can be displayed to the user
    // I've created a menu to display the data so the user can confirm that the data is correct.
    private static void displayEnergyData(Energy[] energyRecords) {
        if (energyRecords.length == 0) {
            System.out.println("No data found");
            return;
        }


        System.out.println("Year  Month  Energy");
        for (Energy energyRecord : energyRecords) {
            if (energyRecord != null) {
                System.out.printf("%-6d %-5d %-12d\n", energyRecord.getYear(), energyRecord.getMonth(), energyRecord.getEnergy());
            }
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

    public static void calculateAverageCost(Energy[] energyRecords) {
        if (energyRecords.length == 0) {
            System.out.println("No data found");
            return;
        }

        // Arrays to store the total cost per month
        double[] totalCostPerMonth = new double[12];
        int[] countPerMonth = new int[12];

        for (Energy energyRecord : energyRecords) {
            if (energyRecord != null) {

                int month = energyRecord.getMonth() - 1;
                int energy = energyRecord.getEnergy();
                double cost = calcElecBill(month + 1, energy);


                totalCostPerMonth[month] += cost;
                countPerMonth[month]++;
            }
        }

        // Display the total cost per month
        System.out.println("Month  Total Cost  Average Cost");
        for (int month = 0; month < 12; month++) {
            if (countPerMonth[month] > 0) {
                double averageCost = totalCostPerMonth[month] / countPerMonth[month];
                System.out.printf("%-6d %-11.2f %-12.2f\n", month + 1, totalCostPerMonth[month], averageCost);
            }
        }


    }
}


