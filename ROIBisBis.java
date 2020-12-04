public class ROIBisBis extends binMeta {

    public ROIBisBis(Data startPoint, Objective obj, long maxTime) {
        try {
            String msg = "Impossible to create Roach Infestation Optimization object: ";
            if (maxTime <= 0) throw new Exception(msg + "the maximum execution time is 0 or even negative");
            this.maxTime = maxTime;
            if (startPoint == null) throw new Exception(msg+ "the reference to the starting point is null");
            this.solution = startPoint;
            if (obj == null) throw new Exception(msg + "the reference to the objective function is null");
            this.obj = obj;
            this.objValue = this.obj.value(this.solution);
            this.metaName = "Roach Infestation Optimization";
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public void optimize() {
        int na = 20;
        int thunger = 100;

        RoachBis[] roaches = new RoachBis[na];
        for (int i = 0; i<na; ++i) {
            roaches[i] = new RoachBis(obj.solutionSample(), 0, i);
        }

    }

    public static void main(String[] args) {
        int ITMAX = 10000;

        // bit counter
        int n = 50;
        Objective obj = new BitCounter(n);
        Data D = obj.solutionSample();
        ROIBisBis roi = new ROIBisBis(D,obj,10000);
        System.out.println(roi);
        System.out.println();
        roi.optimize();
        System.out.println(roi);

        // Fermat
        int exp = 2;
        int ndigits = 10;
        obj = new Fermat(exp,ndigits);
        D = obj.solutionSample();
        roi = new ROIBisBis(D,obj,ITMAX);
        System.out.println(roi);
        System.out.println("starting point : " + roi.getSolution());
        System.out.println("optimizing ...");
        roi.optimize();
        System.out.println(roi);
        System.out.println("solution : " + roi.getSolution());
        Data x = new Data(roi.solution,0,ndigits-1);
        Data y = new Data(roi.solution,ndigits,2*ndigits-1);
        Data z = new Data(roi.solution,2*ndigits,3*ndigits-1);
        System.out.print("equivalent to the equation : " + x.posLongValue() + "^" + exp + " + " + y.posLongValue() + "^" + exp);
        if (roi.objValue == 0.0)
            System.out.print(" == ");
        else
            System.out.print(" ?= ");
        System.out.println(z.posLongValue() + "^" + exp);
        System.out.println();

        // ColorPartition
        n = 4;  int m = 14;
        ColorPartition cp = new ColorPartition(n,m);
        D = cp.solutionSample();
        roi = new ROIBisBis(D,cp,ITMAX);
        System.out.println(roi);
        System.out.println("starting point : " + roi.getSolution());
        System.out.println("optimizing ...");
        roi.optimize();
        System.out.println(roi);
        System.out.println("solution : " + roi.getSolution());
        cp.value(roi.solution);
        System.out.println("corresponding to the matrix :\n" + cp.show());
    }
}
