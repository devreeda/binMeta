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

        Roach[] roaches = new Roach[na];

        for (int t = 0; t<tmax; ++t) {
            //Initialisation of roaches
            for (int i = 0; i < na; ++i)
                roaches[i] = new Roach(0, 0, 20, 20, i);


        }

        for (int t = 0; t<tmax; ++t) {
            // M = [Mjk] = [||⃗xj − ⃗xk||2]
            // dg =median{Mjk ∈M:1≤j<k≤Na}
            for (int i = 0; i<na; ++i) {
                //if F(⃗xi) < F(p⃗i) then p⃗i = ⃗xi

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