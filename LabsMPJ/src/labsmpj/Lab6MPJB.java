
import mpi.*;

public class Lab6MPJB {

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
            long startTime = System.nanoTime();

            averow = NRA / numworkers;
            extra = NRA % numworkers;
            offset = 0;
            for (dest = 1; dest <= numworkers; dest++) {
                rows = (dest <= extra) ? averow + 1 : averow;
                a_arr = Functions.ToArr(a);
                b_arr = Functions.ToArr(b);
                MPI.COMM_WORLD.Send(new int[]{offset}, 0, 1, MPI.INT, dest, FROM_MASTER);
                MPI.COMM_WORLD.Send(new int[]{rows}, 0, 1, MPI.INT, dest, FROM_MASTER);
                MPI.COMM_WORLD.Send(a_arr, offset * NCA, rows * NCA, MPI.DOUBLE, dest, FROM_MASTER);
                MPI.COMM_WORLD.Send(b_arr, 0, NCA * NCB, MPI.DOUBLE, dest, FROM_MASTER);
                offset += rows;
            }
            /* Receive results from worker tasks */
            for (source = 1; source <= numworkers; source++) {
                MPI.COMM_WORLD.Recv(offset_mass, 0, 1, MPI.INT, source, FROM_WORKER);
                offset = offset_mass[0];
                MPI.COMM_WORLD.Recv(rows_mass, 0, 1, MPI.INT, source, FROM_WORKER);
                rows = rows_mass[0];
                MPI.COMM_WORLD.Recv(c_arr, offset * NCB, rows * NCB, MPI.DOUBLE, source, FROM_WORKER);
            }
            // End time
            long endTime = System.nanoTime();
            /* Print results */
            //System.out.println("********");
            c = Functions.ToMatr(c_arr, NRA, NCB, 0);
            //Functions.PrintMatr(c, NRA, NCB, "The result matrix:");
            System.out.println("********");

            long totalTime = endTime - startTime;
            System.out.println("Time: " + totalTime + " nanoseconds.");

            System.out.println("Done.");
        } else {
            /* if (taskid > MASTER)*/
            MPI.COMM_WORLD.Recv(offset_mass, 0, 1, MPI.INT, MASTER, FROM_MASTER);
            offset = offset_mass[0];
            MPI.COMM_WORLD.Recv(rows_mass, 0, 1, MPI.INT, MASTER, FROM_MASTER);
            rows = rows_mass[0];

            MPI.COMM_WORLD.Recv(a_arr, 0, rows * NCA, MPI.DOUBLE, MASTER, FROM_MASTER);
            a = Functions.ToMatr(a_arr, rows, NCA, 0);
            MPI.COMM_WORLD.Recv(b_arr, 0, NCA * NCB, MPI.DOUBLE, MASTER, FROM_MASTER);
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
            MPI.COMM_WORLD.Send(new int[]{offset}, 0, 1, MPI.INT, MASTER, FROM_WORKER);
            MPI.COMM_WORLD.Send(new int[]{rows}, 0, 1, MPI.INT, MASTER, FROM_WORKER);
            MPI.COMM_WORLD.Send(Functions.ToArr(c), 0, rows * NCB, MPI.DOUBLE, MASTER, FROM_WORKER);
        }

        MPI.Finalize();
    }
}
