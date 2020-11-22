import java.util.Random;

public class Roach {
    /**
     * Attributes of a Roach
     */
    private double[] position;
    private double[] velocity;
    private double[] personalBestPosition;
    private double[] groupBestPosition;
    private int hunger;
    private Random random;
    private final int THUNGER = 100;

    /**
     * We intialize in a 2D dimension
     * @param minX
     * @param minY
     * @param maxX
     * @param maxY
     * @param randomSeed
     */
    public Roach(double minX, double minY, double maxX, double maxY, int randomSeed) {
        this.random = new Random(randomSeed);
        this.hunger = this.random.nextInt(THUNGER);
        position = new double[2];
        velocity = new double[2];
        personalBestPosition = new double[2];
        groupBestPosition = new double[2];

        position[0] = (maxX - minX) * random.nextDouble() + minX;
        position[1] = (maxY - minY) * random.nextDouble() + minY;
        personalBestPosition[0] = this.position[0];
        personalBestPosition[1] = this.position[1];

        groupBestPosition[0] = this.position[0];
        groupBestPosition[1] = this.position[1];
    }

    //GETTERS AND SETTERS

    public double[] getPosition() {
        return position;
    }

    public void setPosition(double[] position) {
        this.position = position;
    }

    public double[] getVelocity() {
        return velocity;
    }

    public void setVelocity(double[] velocity) {
        this.velocity = velocity;
    }

    public double[] getPersonalBestPosition() {
        return personalBestPosition;
    }

    public void setPersonalBestPosition(double[] personalBestPosition) {
        this.personalBestPosition = personalBestPosition;
    }

    public double[] getGroupBestPosition() {
        return groupBestPosition;
    }

    public void setGroupBestPosition(double[] groupBestPosition) {
        this.groupBestPosition = groupBestPosition;
    }

    public int getHunger() {
        return hunger;
    }

    public void setHunger(int hunger) {
        this.hunger = hunger;
    }

    public Random getRandom() {
        return random;
    }

    public void setRandom(Random random) {
        this.random = random;
    }

    public int getTHUNGER() {
        return THUNGER;
    }
}
