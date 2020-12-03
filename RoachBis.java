import java.util.Random;

public class RoachBis {

    private Data position;
    private int velocity;
    private Data personalBestPosition;
    private Data groupBestPosition;
    private int hunger;
    // TODO - peut-être à enlever car non utilisée
    private Random random;
    public final int THUNGER = 100;

    public RoachBis(Data position, int velocity, int randomSeed) {
        // TODO - à revoir
        this.random = new Random(randomSeed);
        this.hunger = this.random.nextInt(randomSeed+1);
        this.position = position;
        this.velocity = velocity;
        this.personalBestPosition = this.position;
        this.groupBestPosition = this.position;
    }

    // GETTERS AND SETTERS

    public Data getPosition() {
        return position;
    }

    public void setPosition(Data position) {
        this.position = position;
    }

    public int getVelocity() {
        return velocity;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    public Data getPersonalBestPosition() {
        return personalBestPosition;
    }

    public void setPersonalBestPosition(Data personalBestPosition) {
        this.personalBestPosition = personalBestPosition;
    }

    public Data getGroupBestPosition() {
        return groupBestPosition;
    }

    public void setGroupBestPosition(Data groupBestPosition) {
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

    @Override
    public String toString() {
        return "RoachBis{" +
                "position=" + position +
                '}';
    }
}
