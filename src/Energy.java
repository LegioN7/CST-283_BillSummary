public class Energy {

    private final int month;
    private final int year;
    private final int energy;

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

}
