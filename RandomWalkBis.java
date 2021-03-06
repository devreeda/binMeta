import java.awt.*;
import java.util.Random;

public class RandomWalkBis extends binMeta {

    public RandomWalkBis(Data startPoint, Objective obj, long maxTime) {
        try {
          String msg = "Impossible to create RandomWalk object: ";
          if (maxTime <= 0) throw new Exception(msg + "the maximum execution time is 0 or even negative");
          this.maxTime = maxTime;
          if (startPoint == null) throw new Exception(msg+ "the reference to the starting point is null");
          this.solution = startPoint;
          if (obj == null) throw new Exception(msg + "the reference to the objective function is null");
          this.obj = obj;
          this.objValue = this.obj.value(this.solution);
          this.metaName = "RandomWalk";
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public void optimize() {
        Random R = new Random();
        Data D = new Data(this.solution);
        long startTime = System.currentTimeMillis();

        // main loop
        while (System.currentTimeMillis() - startTime < this.maxTime) {
            // the random walker can walk in a neighbourhood of D
            // (Hamming distance is randomly selected among 1, 2 and 3)
            int h = 1 + R.nextInt(3);

            // generating a new solution in the neighbour of D with Hamming distance h
            Data newD = D.randomSelectInNeighbour(h);

            // evaluating the quality of the generated solution
            double value = obj.value(newD);
            if (this.objValue > value) {
                this.objValue = value;
                this.solution = new Data(newD);
            }

            // the walk continues from the new generated solution
            D = newD;
        }
    }

    // main
    public static void main(String[] args) {
        int ITMAX = 10_000; // number of iterations

        // BitCounter
        int n = 50;
        Objective obj = new BitCounter(n);
        Data D = obj.solutionSample();
        RandomWalkBis rw = new RandomWalkBis(D, obj, ITMAX);
        System.out.println(rw);
        System.out.println("starting point : " + rw.getSolution());
        System.out.println("optimizing ...");
        rw.optimize();
        System.out.println(rw);
        System.out.println("solution : " + rw.getSolution());
        System.out.println();

        // Fermat
        int exp = 2;
        int ndigits = 10;
        obj = new Fermat(exp, ndigits);
        D = obj.solutionSample();
        rw = new RandomWalkBis(D, obj, ITMAX);
        System.out.println(rw);
        System.out.println("stating point : " + rw.getSolution());
        System.out.println("optimizing ...");
        rw.optimize();
        System.out.println(rw);
        System.out.println("solution : " + rw.getSolution());
        Data x = new Data(rw.solution, 0, ndigits-1);
        Data y = new Data(rw.solution, ndigits, 2*ndigits-1);
        Data z = new Data(rw.solution, 2*ndigits, 3*ndigits-1);
        System.out.println("equivalent to the equation : " + x.posLongValue() + "^" + exp + " + " + y.posLongValue());
        if (rw.objValue == 0.0)
            System.out.println(" == ");
        else
            System.out.println(" ?= ");
        System.out.println(z.posLongValue() + "^" + exp);
        System.out.println();

        // ColorPartition
        n = 4; int m = 14;
        ColorPartition cp = new ColorPartition(n, m);
        D = cp.solutionSample();
        rw = new RandomWalkBis(D, cp, ITMAX);
        System.out.println(rw);
        System.out.println("starting point : " + rw.getSolution());
        System.out.println("optimizing ...");
        rw.optimize();
        System.out.println(rw);
        System.out.println("solution : " + rw.getSolution());
        cp.value(rw.solution);
        System.out.println("corresponding to the matrix : \n" + cp.show());
    }
}
