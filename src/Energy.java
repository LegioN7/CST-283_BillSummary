import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Energy {

    private final int month;
    private final int year;
    private final int energy;
    private final static String FILENAME = "energy.txt";

    public Energy(int month, int year, int energy) {
        this.month = month;
        this.year = year;
        this.energy = energy;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public int getEnergy() {
        return energy;
    }

    // To String for the energy data
    // This method will display the year, month, and energy used
    @Override
    public String toString() {
        return String.format("%-4d  %-5d  %-12d", year, month, energy);
    }

    // Method to read the energy data file
    // This method will read the energy data from the file
    // It will return an array of Energy objects

    public static Energy[] readEnergyData() {

        final int MAX_RECORDS = 36; // Maximum number of records in the file
        Energy[] energyRecords = new Energy[MAX_RECORDS];

        int count = 0;

        try {

            File energyFile = new File(FILENAME);
            Scanner scanner = new Scanner(energyFile);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    int month = Integer.parseInt(parts[0]);
                    int year = Integer.parseInt(parts[1]);
                    int energy = Integer.parseInt(parts[2]);
                    energyRecords[count] = new Energy(month, year, energy);
                    count++;
                } else {
                    System.err.println("Invalid data format: " + line);
                }
            }
            scanner.close();
        } catch (IOException e) {
            System.err.println("Error reading energy data: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error reading energy data: Invalid integer format.");
        }


        Energy[] result = new Energy[count];
        System.arraycopy(energyRecords, 0, result, 0, count);
        return result;
    }
}
