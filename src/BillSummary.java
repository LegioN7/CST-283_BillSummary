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

    // Method to display the menu
    // This method will display the menu for the user to choose from
    // It will display the options to display the energy data or calculate the average cost
    // It will also allow the user to exit the program
    private static void choiceMenu() {
        Scanner input = new Scanner(System.in);
        do {
            // Create a menu
            System.out.println("Energy Bill Summary");
            System.out.println("1. Display Energy.txt Data");
            System.out.println("2. Calculate Energy Cost per Month");
            System.out.println("3. Exit");
            System.out.println("Enter your choice: ");

            // Create a scanner object

            int choice = input.nextInt();

            // Create a switch statement to handle the menu
            switch (choice) {
                case 1:
                    System.out.println("Display Energy.txt Data");
                    displayEnergyData(Energy.readEnergyData());
                    break;
                case 2:
                    System.out.println("Calculate Energy Cost per Month");
                    calculateAverageCost(Energy.readEnergyData());
                    break;
                case 3:
                    System.out.println("Exiting Program");
                    input.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please select 1, 2, or 3.");
                    break;
            }
        } while (true);
    }


    // Method to display the energy data
    // This method will display the energy data from the file
    // It will display the year, month, and energy used
    private static void displayEnergyData(Energy[] energyRecords) {
        if (energyRecords.length == 0) {
            System.out.println("No energy records found.");
            return;
        }

        System.out.println("Year  Month  Energy Used");
        for (Energy record : energyRecords) {
            System.out.println(record);
        }
    }

    // Method to calculate the energy cost per month
    public static double calcElecBill(int month, int energy) {
        double bill;
        if (month > 0 && month <= 3) {
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

    // Method to calculate the average cost per month
    // This method will calculate the average cost per month
    // It will display the total cost and average cost per month
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

        // Display the total cost per month and average cost per month
        System.out.println("Month  Total Cost  Average Cost");
        for (int month = 0; month < 12; month++) {
            if (countPerMonth[month] > 0) {
                double averageCost = totalCostPerMonth[month] / countPerMonth[month];
                System.out.printf("%-6d $%-11.2f $%-12.2f\n", month + 1, totalCostPerMonth[month], averageCost);
            }
        }


    }
}


