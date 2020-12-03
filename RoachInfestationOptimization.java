import java.util.Random;

class RoachInfestationOptimization extends binMeta {

    @Override
    public void optimize() {
        //number of roaches
        int na = 20;
        int tmax = 1000;
        double C0 = 0.7;
        double CMAX = 1.43;
        double A1 = 0.49;
        double A2 = 0.63;
        double A3 = 0.65;
        int thunger = 100;

        Random random = new Random();

        Roach[] roaches = new Roach[na];

        for (int t = 0; t<tmax; ++t) {
            //Initialisation of roaches
            int[] bestPosition = new int[na];
            for (int i = 0; i < na; ++i)
                roaches[i] = new Roach(0, 0, 20, 20, i);
            //initialisation of the distance between roaches
            int[][] distances = new int[na][];
            for (int i = 0; i < na; ++i)
                distances[i] = new int[na];
            for (int i = 0; i < na - 1; ++i) {
                for (int j = i + 1; j < na; ++j) {
                    //distance between roaches
                    int d = (int)(Math.sqrt(Math.pow(roaches[i].getPosition()[0] - roaches[j].getPosition()[0], 2) +
                            Math.pow(roaches[i].getPosition()[1] - roaches[j].getPosition()[1], 2)));
                    distances[i][j] = distances[j][i] = d;
                }
            }
            //TODO: calculate the median
            for (int i = 0; i < na; ++i) {
                Roach current = roaches[i];
                if (roaches[i].getHunger() < thunger) {
                    int vx, vy;
                    vx = (int)(C0*current.getVelocity()[0] +
                            (CMAX * random.nextInt() * (current.getPersonalBestPosition()[0] -
                                    current.getPosition()[0])) +
                            (CMAX * random.nextInt() * (current.getGroupBestPosition()[0] -
                                    current.getPosition()[0])));
                    vy = (int)(C0*current.getVelocity()[1] +
                            (CMAX * random.nextInt() * (current.getPersonalBestPosition()[1] -
                                    current.getPosition()[1])) +
                            (CMAX * random.nextInt() * (current.getGroupBestPosition()[1] -
                                    current.getPosition()[1])));
                    current.setVelocity(new int[]{vx, vy});

                    // BAAAAAAD
                    current.getPosition()[0] += vx;
                    current.getPosition()[1] += vy;

                } else {
                    roaches[i] = new Roach(0, 0, 20, 20, i);
                }
                //TODO: add the new position for food
            }

        }
    }
}