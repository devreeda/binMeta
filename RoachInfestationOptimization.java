import java.util.Random;

class RoachInfestationOptimization extends binMeta {

//public RoachInfestationOptimization(double[] pos, double[] velocity, double[] personalBestPos, double[] groupBestPos, int hunger, Random rnd) {
    //}

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
            double[] bestPosition = new double[na];
            for (int i = 0; i < na; ++i)
                roaches[i] = new Roach(0, 0, 20, 20, i);
            //initialisation of the distance between roaches
            double[][] distances = new double[na][];
            for (int i = 0; i < na; ++i)
                distances[i] = new double[na];
            for (int i = 0; i < na - 1; ++i) {
                for (int j = i + 1; j < na; ++j) {
                    double d = Math.sqrt(Math.pow(roaches[i].getPosition()[0] - roaches[j].getPosition()[0], 2) +
                            Math.pow(roaches[i].getPosition()[1] - roaches[j].getPosition()[1], 2));
                    distances[i][j] = distances[j][i] = d;
                }
            }
            for (int i = 0; i < na; ++i) {
                Roach current = roaches[i];
                if (roaches[i].getHunger() < thunger) {
                    double vx, vy;
                    vx = C0*current.getVelocity()[0] +
                            (CMAX * random.nextDouble() * (current.getPersonalBestPosition()[0] -
                                    current.getPosition()[0])) +
                            (CMAX * random.nextDouble() * (current.getGroupBestPosition()[0] -
                                    current.getPosition()[0]));
                    vy = C0*current.getVelocity()[1] +
                            (CMAX * random.nextDouble() * (current.getPersonalBestPosition()[1] -
                                    current.getPosition()[1])) +
                            (CMAX * random.nextDouble() * (current.getGroupBestPosition()[1] -
                                    current.getPosition()[1]));
                    current.setVelocity(new double[]{vx, vy});

                    current.getPosition()[0] += vx;
                    current.getPosition()[1] += vy;

                } else {
                    roaches[i] = new Roach(0, 0, 20, 20, i);
                }
            }

        }
    }
    /*
    * initialize n roaches to random positions
    loop tMax times
      compute distances between all roaches
      compute median distance
      for-each roach
        compute number neighbors
        exchange data with neighbors
        if not hungry
          compute new velocity
          compute new position
          check if new best position
        else if hungry
          relocate to new position
        end-if
      end-for
    end loop
    return best position found
* */
}