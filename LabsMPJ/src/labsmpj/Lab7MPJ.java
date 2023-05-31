
import mpi.*;

public class Lab7MPJ {

    static final int N = 2000;
    static final int NRA = N;
    /* number of rows in matrix A */
    static final int NCA = N;
    /* number of columns in matrix A */
    static final int NCB = N;
    /* number of columns in matrix B */
    static final int MASTER = 0;

    /* setting a message type */
    public static void main(String[] args) throws Exception {
        int numtasks, taskid, rows, /* rows of matrix A sent to each worker */
                averow, extra, offset, i, j, k;
        double[][] a = new double[NRA][NCA], /* matrix A to be multiplied */
                b = new double[NCA][NCB], /* matrix B to be multiplied */
                c = new double[NRA][NCB];
        double[] a_arr, /* matrix A to be multiplied */
                b_arr, /* matrix B to be multiplied */
                c_arr;
        boolean isOneToMany = true;
        /* result matrix C */
        int[] portions, offsets;
        int portionSize;
        double[] portionArray;
        long startTime = System.nanoTime(), endTime;

        MPI.Init(args);
        numtasks = MPI.COMM_WORLD.Size();
        taskid = MPI.COMM_WORLD.Rank();
        portions = new int[numtasks];
        offsets = new int[numtasks];

        averow = NRA / numtasks;
        extra = NRA % numtasks;
        offset = 0;
        for (i = 0; i < numtasks; i++) {
            rows = (i < extra) ? averow + 1 : averow;
            portions[i] = rows * NCA;
            offsets[i] = offset * NCA;
            offset += rows;
        }

        if (taskid == MASTER) {
            for (i = 0; i < NRA; i++) {
                for (j = 0; j < NCA; j++) {
                    a[i][j] = 1;//i * 10 + j;//10;
                }
            }
            for (i = 0; i < NCA; i++) {
                for (j = 0; j < NCB; j++) {
                    b[i][j] = 1;//-(i * 10 + j);//10;
                }
            }

            //Functions.PrintMatr(a, NRA, NCA, "The A matrix: ");
            //Functions.PrintMatr(b, NCA, NCB, "The B matrix: ");
            // Start time
            if (isOneToMany) {
                System.out.println("Collective (1:M). Size: " + N);
            } else {
                System.out.println("Collective (M:M). Size: " + N);
            }
            startTime = System.nanoTime();

        }

        portionSize = portions[taskid];
        portionArray = new double[portionSize];

        //System.out.println("portionSize - " + portionSize / NCA + " in " + taskid);
        a_arr = Functions.ToArr(a);
        b_arr = Functions.ToArr(b);

        MPI.COMM_WORLD.Scatterv(a_arr, 0, portions, offsets, MPI.DOUBLE,
                portionArray, 0, portionSize, MPI.DOUBLE, MASTER);
        MPI.COMM_WORLD.Bcast(b_arr, 0, b_arr.length, MPI.DOUBLE, MASTER);

        a = Functions.ToMatr(portionArray, portionSize / NCA, NCA, 0);
        b = Functions.ToMatr(b_arr, NCA, NCB, 0);

        //Functions.PrintMatr(a, portionSize / NCA, NCA, ("portion in " + taskid));
        //Functions.PrintMatr(b, NCA, NCB, ("b arr in " + taskid));
        for (k = 0; k < NCB; k++) {
            for (i = 0; i < portionSize / NCA; i++) {
                c[i][k] = 0.0;
                for (j = 0; j < NCA; j++) {
                    c[i][k] = c[i][k] + a[i][j] * b[j][k];
                }
            }
        }

        //Functions.PrintMatr(c, portionSize / NCA, NCB, ("res mat in " + taskid));
        c_arr = Functions.ToArr(c);

        if (isOneToMany) {
            MPI.COMM_WORLD.Gatherv(c_arr, 0, portionSize, MPI.DOUBLE,
                    c_arr, 0, portions, offsets, MPI.DOUBLE, MASTER);
        } else {
            MPI.COMM_WORLD.Allgatherv(c_arr, 0, portionSize, MPI.DOUBLE,
                    c_arr, 0, portions, offsets, MPI.DOUBLE);
        }

        if (taskid == MASTER) {
            // End time
            endTime = System.nanoTime();
            /* Print results */
            /*System.out.println("********");
            c = Functions.ToMatr(c_arr, NRA, NCB, 0);
            Functions.PrintMatr(c, NRA, NCB, "The result matrix:");
            System.out.println("********");*/

            long totalTime = endTime - startTime;
            System.out.println("Time: " + totalTime + " nanoseconds.");

            System.out.println("Done.");
        }
        MPI.Finalize();
    }
}
