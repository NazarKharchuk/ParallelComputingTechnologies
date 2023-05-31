
import mpi.*;

public class multipleMatrix {

    static final int N = 2000;
    static final int NRA = N;
    /* number of rows in matrix A */
    static final int NCA = N;
    /* number of columns in matrix A */
    static final int NCB = N;

    /* setting a message type */
    public static void main(String[] args) throws Exception {
        int i, j, k;
        double[][] a = new double[NRA][NCA], /* matrix A to be multiplied */
                b = new double[NCA][NCB], /* matrix B to be multiplied */
                c = new double[NRA][NCB];
        long startTime, endTime;

        MPI.Init(args);
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
        System.out.println("One task. Size: " + N);
        startTime = System.nanoTime();

        for (k = 0; k < NCB; k++) {
            for (i = 0; i < NCA; i++) {
                c[i][k] = 0.0;
                for (j = 0; j < NCA; j++) {
                    c[i][k] = c[i][k] + a[i][j] * b[j][k];
                }
            }
        }

        // End time
        endTime = System.nanoTime();
        /* Print results */
        /*System.out.println("********");
        Functions.PrintMatr(c, NRA, NCB, "The result matrix:");
        System.out.println("********");*/

        long totalTime = endTime - startTime;
        System.out.println("Time: " + totalTime + " nanoseconds.");

        System.out.println("Done.");
        MPI.Finalize();
    }
}
