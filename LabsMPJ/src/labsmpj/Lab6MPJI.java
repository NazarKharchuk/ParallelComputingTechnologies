
import mpi.*;

public class Lab6MPJI {

    static final int N = 10;
    static final int NRA = N;
    /* number of rows in matrix A */
    static final int NCA = N;
    /* number of columns in matrix A */
    static final int NCB = N;
    /* number of columns in matrix B */
    static final int MASTER = 0;
    /* taskid of first task */
    static final int FROM_MASTER = 1;
    /* setting a message type */
    static final int FROM_WORKER = 2;

    /* setting a message type */
    public static void main(String[] args) throws Exception {
        int numtasks, taskid, numworkers, source, dest, rows, /* rows of matrix A sent to each worker */
                averow, extra, offset, i, j, k, rc;
        double[][] a = new double[NRA][NCA], /* matrix A to be multiplied */
                b = new double[NCA][NCB], /* matrix B to be multiplied */
                c = new double[NRA][NCB];
        double[] a_arr = new double[NRA * NCA], /* matrix A to be multiplied */
                b_arr = new double[NCA * NCB], /* matrix B to be multiplied */
                c_arr = new double[NRA * NCB];
        int[] offset_mass = new int[1],
                rows_mass = new int[1];
        /* result matrix C */
        MPI.Init(args);
        numtasks = MPI.COMM_WORLD.Size();
        taskid = MPI.COMM_WORLD.Rank();
        if (numtasks < 2) {
            System.out.println("Need at least two MPI tasks. Quitting...");
            rc = 1;
            MPI.COMM_WORLD.Abort(rc);
            System.exit(1);
        }
        numworkers = numtasks - 1;
        if (taskid == MASTER) {
            //System.out.println("mpi_mm has started with " + numtasks + " tasks.");
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
            System.out.println("Non-blocking. Size: " + N);
            long startTime = System.nanoTime();

            averow = NRA / numworkers;
            extra = NRA % numworkers;
            offset = 0;
            a_arr = Functions.ToArr(a);
            b_arr = Functions.ToArr(b);
            for (dest = 1; dest <= numworkers; dest++) {
                var isend4 = MPI.COMM_WORLD.Isend(b_arr, 0, NCA * NCB, MPI.DOUBLE, dest, FROM_MASTER);
                rows = (dest <= extra) ? averow + 1 : averow;
                var isend2 = MPI.COMM_WORLD.Isend(new int[]{rows}, 0, 1, MPI.INT, dest, FROM_MASTER);
                var isend3 = MPI.COMM_WORLD.Isend(a_arr, offset * NCA, rows * NCA, MPI.DOUBLE, dest, FROM_MASTER);
                var isend1 = MPI.COMM_WORLD.Isend(new int[]{offset}, 0, 1, MPI.INT, dest, FROM_MASTER);
                offset += rows;
                isend4.Wait();
                isend3.Wait();
                isend2.Wait();
                isend1.Wait();
            }
            /* Receive results from worker tasks */
            for (source = 1; source <= numworkers; source++) {
                var irecv2 = MPI.COMM_WORLD.Irecv(rows_mass, 0, 1, MPI.INT, source, FROM_WORKER);
                var irecv1 = MPI.COMM_WORLD.Irecv(offset_mass, 0, 1, MPI.INT, source, FROM_WORKER);
                irecv2.Wait();
                rows = rows_mass[0];
                irecv1.Wait();
                offset = offset_mass[0];
                var irecv3 = MPI.COMM_WORLD.Irecv(c_arr, offset * NCB, rows * NCB, MPI.DOUBLE, source, FROM_WORKER);
                irecv3.Wait();
            }
            // End time
            long endTime = System.nanoTime();
            /* Print results */
            //System.out.println("********");
            //c = Functions.ToMatr(c_arr, NRA, NCB, 0);
            //Functions.PrintMatr(c, NRA, NCB, "The result matrix:");
            //System.out.println("********");

            long totalTime = endTime - startTime;
            System.out.println("Time: " + totalTime + " nanoseconds.");

            System.out.println("Done.");
        } else {
            /* if (taskid > MASTER)*/
            var irecv4 = MPI.COMM_WORLD.Irecv(b_arr, 0, NCA * NCB, MPI.DOUBLE, MASTER, FROM_MASTER);
            var irecv2 = MPI.COMM_WORLD.Irecv(rows_mass, 0, 1, MPI.INT, MASTER, FROM_MASTER);
            irecv2.Wait();
            rows = rows_mass[0];
            var irecv3 = MPI.COMM_WORLD.Irecv(a_arr, 0, rows * NCA, MPI.DOUBLE, MASTER, FROM_MASTER);
            var irecv1 = MPI.COMM_WORLD.Irecv(offset_mass, 0, 1, MPI.INT, MASTER, FROM_MASTER);

            irecv3.Wait();
            irecv4.Wait();
            a = Functions.ToMatr(a_arr, rows, NCA, 0);
            b = Functions.ToMatr(b_arr, NCA, NCB, 0);

            for (k = 0; k < NCB; k++) {
                for (i = 0; i < rows; i++) {
                    c[i][k] = 0.0;
                    for (j = 0; j < NCA; j++) {
                        c[i][k] = c[i][k] + a[i][j] * b[j][k];
                    }
                }
            }

            //Functions.PrintMatr(c, rows, NCB, taskid);
            var isend2 = MPI.COMM_WORLD.Isend(new int[]{rows}, 0, 1, MPI.INT, MASTER, FROM_WORKER);
            irecv1.Wait();
            offset = offset_mass[0];
            var isend1 = MPI.COMM_WORLD.Isend(new int[]{offset}, 0, 1, MPI.INT, MASTER, FROM_WORKER);
            var isend3 = MPI.COMM_WORLD.Isend(Functions.ToArr(c), 0, rows * NCB, MPI.DOUBLE, MASTER, FROM_WORKER);
            isend1.Wait();
            isend2.Wait();
            isend3.Wait();

        }

        MPI.Finalize();
    }
}
