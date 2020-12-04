import java.util.Arrays;
import java.util.Random;

public class ROIBis extends binMeta{

    // LES ATTRIBUTS DE binMeta
    /*
        String metaName // meta-heuristic name
        Objective obj // objective function
        Double objValue // objective function value in solution
        Data solution // Data object containing solution
        long maxTime // maximum execution time (ms)
     */

    // TODO : AJOTUER DATA DANS LA CLASSE ROACH
    public ROIBis(Data startPoint, Objective obj, long maxTime) {
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
        int na = 20; // number of roaches
        int tmax = 10000; // nombre maximal d'itérations
        double C0 = 0.7, CMAX  = 1.43;
        int thunger = 100;

        Random random = new Random();

        RoachBis[] roaches = new RoachBis[na];
        // TODO - changer le bound en ITMAX
        long startime = System.currentTimeMillis();

        //for (int t = 0; t<tmax; ++t)
        while (System.currentTimeMillis() - startime < this.maxTime){
            // initialisation of roaches
            Data[] bestPosition = new Data[na];
            for (int i = 0; i<na; ++i)
                roaches[i] = new RoachBis(new Data(this.solution.numberOfBits(), 0.5),random.nextInt(i+1),i);
            // initialisation of the distance between roaches
            //TODO - à voir, comparer avec les méthodes de Data/Objective
            int[][] distances = new int[na][];
            for (int i = 0; i<na; ++i)
                distances[i] = new int[na];
            // initialisation of the distances matrix
            for (int i = 0; i<na; ++i) {
                for (int j = 0; j<na; ++j) {
                    // distance between roaches
                    int dBis = roaches[i].getPosition().hammingDistanceTo(roaches[j].getPosition());
                    //int d = (int)(Math.sqrt(Math.pow(roaches[i].getPosition()[0] - roaches[j].getPosition()[0], 2) +
                    //        Math.pow(roaches[i].getPosition()[1] - roaches[j].getPosition()[1], 2)));
                    distances[i][j] = distances[j][i] = dBis;
                }
            }

            // FORMULE MATHS
            for (int i = 0; i < na; ++i) {
                RoachBis current = roaches[i];
                // DONE - AJOUTER A COMPARAISON AVEC LA SOLUTION GLOBALE
                double value = obj.value(current.getPosition());
                //System.out.println("value : "+value);//
                //System.out.println("objValue : "+this.objValue);//
                //System.out.println("random Value : "+obj.value(new Data(this.solution.numberOfBits(), 0.5)));//
                if (this.objValue > value) {
                    this.objValue = value;
                    this.solution = new Data(current.getPosition());
                }

                // TALK TO THE NEIGHBOURS
                for (int j = 0; j<na; ++j) {
                    if (this.objValue > value) {
                        this.objValue = value;
                        this.solution = new Data(current.getPosition());
                    }
                    if (distances[i][j] <= 1) {
                        double valueN = obj.value(roaches[j].getPosition());
                        if (value > valueN)
                            current.setGroupBestPosition(roaches[j].getPosition());
                    }
                }


                if (roaches[i].getHunger() < thunger) {
                    int v;
                    v = (int)(C0*current.getVelocity() +
                            (CMAX * random.nextInt() * (current.getPersonalBestPosition().longValue() -
                                    current.getPosition().longValue())) +
                            (CMAX * random.nextInt() * (current.getGroupBestPosition().longValue() -
                                    current.getPosition().longValue())));

                    //System.out.println(v);
                    current.setVelocity(v);

                    Data minPosition = current.getGroupBestPosition();

                    for (int j = 0; j<10; ++j) {
                        Data position = current.getGroupBestPosition().randomSelectInNeighbour(5);
                        if (obj.value(position) < obj.value(minPosition)) {
                            minPosition = position;
                        }
                    }


                    // TODO - PEUT-ÊTRE QUE L'ON DEVRAIT SÉLÉCTIONNER SELON LA DISTANCE LA PLUS PROCHE
                    // TODO - COMPARER LES BEST SOLUTIONS DE CHACUN
                    current.setPosition(minPosition);

                    roaches[i] = current;

                } else {
                    roaches[i] = new RoachBis(obj.solutionSample(),random.nextInt(i+1),i);
                }
            }
            // ON RECALCUL LES DISTANCES DE HAMMING ENTRE AGENTS
            for (int i = 0; i<na; ++i) {
                for (int j = 0; j<na; ++j) {
                    // distance between roaches
                    // DONE : calculer les distances de Hamming entre i et j
                    int dBis = roaches[i].getPosition().hammingDistanceTo(roaches[j].getPosition());
                    //int d = (int)(Math.sqrt(Math.pow(roaches[i].getPosition()[0] - roaches[j].getPosition()[0], 2) +
                    //        Math.pow(roaches[i].getPosition()[1] - roaches[j].getPosition()[1], 2)));
                    distances[i][j] = distances[j][i] = dBis;
                }
            }
            //if (t%100 == 0)
              //  System.out.println(Arrays.toString(roaches));//
            //System.out.println(Arrays.deepToString(distances));//

        }

    }

    public void optimizeRandom() {
        Random R = new Random();
        Data D = new Data(this.solution);
        long startTime = System.currentTimeMillis();

        // main loop
        while (System.currentTimeMillis()-startTime < this.maxTime) {
            // le marcheur aléatoire peut avancer dans un voisinage de D
            // (la distance de Hamming est selectionnée de manière aléatoire entre 1 et 3)
            int h = 1 + R.nextInt(3);

            // on génère une nouvelle solution dans le voisin de D avec une distance de Hamming de h
            Data newD = D.randomSelectInNeighbour(h);

            // on évalue la qualité de la solution générée
            double value = obj.value(newD);
            if (this.objValue > value) {
                this.objValue = value;
                this.solution = new Data(newD);
            }

            // la marche continue depuis la nouvelle solution générée
            D = newD;
        }
    }

    public static void main(String[] args) {
        int ITMAX = 10000;

        // bit counter
        int n = 50;
        Objective obj = new BitCounter(n);
        Data D = obj.solutionSample();
        ROIBis roi = new ROIBis(D,obj,10000);
        System.out.println(roi);
        System.out.println();
        roi.optimize();
        System.out.println(roi);

        // Fermat
        int exp = 2;
        int ndigits = 10;
        obj = new Fermat(exp,ndigits);
        D = obj.solutionSample();
        roi = new ROIBis(D,obj,ITMAX);
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
        roi = new ROIBis(D,cp,ITMAX);
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
