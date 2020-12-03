import java.util.Random;

public class Roach {
    /**
     * Attributes of a Roach
     */
    private int[] position;
    private int[] velocity;
    private int[] personalBestPosition;
    private int[] groupBestPosition;
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
    public Roach(int minX, int minY, int maxX, int maxY, int randomSeed) {
        this.random = new Random(randomSeed);
        this.hunger = this.random.nextInt(THUNGER);
        position = new int[2];
        velocity = new int[2];
        personalBestPosition = new int[2];
        groupBestPosition = new int[2];

        position[0] = (int)((maxX - minX) * random.nextInt() + minX);
        position[1] = (int)((maxY - minY) * random.nextInt() + minY);
        personalBestPosition[0] = this.position[0];
        personalBestPosition[1] = this.position[1];

        groupBestPosition[0] = this.position[0];
        groupBestPosition[1] = this.position[1];
    }

    //GETTERS AND SETTERS

    public int[] getPosition() {
        return position;
    }

    public void setPosition(int[] position) {
        this.position = position;
    }

    public int[] getVelocity() {
        return velocity;
    }

    public void setVelocity(int[] velocity) {
        this.velocity = velocity;
    }

    public int[] getPersonalBestPosition() {
        return personalBestPosition;
    }

    public void setPersonalBestPosition(int[] personalBestPosition) {
        this.personalBestPosition = personalBestPosition;
    }

    public int[] getGroupBestPosition() {
        return groupBestPosition;
    }

    public void setGroupBestPosition(int[] groupBestPosition) {
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
