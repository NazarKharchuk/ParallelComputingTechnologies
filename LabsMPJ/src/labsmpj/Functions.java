
import java.util.Arrays;
import mpi.*;

public class Functions {

    public static void PrintArr(double[] buf, int row, String str0) {

        String str = str0 + "\n";
        for (int i = 0; i < row; i++) {
            str += "(" + i + ") - " + buf[i] + " ; ";
        }
        str += "\n";
        System.out.println(str);
    }

    public static void PrintMatr(double[][] buf, int row, int col, String str0) {

        String str = str0 + "\n";
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                str += "(" + i + "," + j + ") - " + buf[i][j] + " ; ";
            }
            str += "\n";
        }
        System.out.println(str);
    }

    public static double[] ToArr(double[][] buf) {
        return Arrays.stream(buf)
                .flatMapToDouble(Arrays::stream)
                .toArray();
    }

    public static double[][] ToMatr(double[] buf, int row, int col, int offset) {
        double[][] matr = new double[row][col];
        int miss = offset * col;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (miss-- <= 0) {
                    matr[i][j] = buf[i * col + j];
                }
            }
        }
        return matr;
    }
}
