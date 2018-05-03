import Exceptions.ShapeEmptyException;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Serializer {
    public static void serialize(Shape<Integer> shape, String fileName) throws ShapeEmptyException, IOException {
        int n = shape.getN();
        if (n == 0) throw new ShapeEmptyException("Shape is empty.");
        int m = shape.getM();
        if (m == 0) throw new ShapeEmptyException("Shape is empty.");

        File file = new File(fileName);
        file.createNewFile();
        PrintWriter writer = new PrintWriter(file);

        writer.write(Integer.toString(n));
        writer.write(" ");
        writer.write(Integer.toString(m));
        writer.write("\r");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                writer.write(Integer.toString(shape.getShapeMatrix()[i][j]));
                if (j < m - 1) {
                    writer.write(" ");
                }
            }
            if (i < n - 1) {
                writer.write("\n");
            }
        }
        writer.flush();
        writer.close();
    }

    public static Shape<Integer> deserialize(String fileName) throws Exception {
        Scanner fr = new Scanner(new File(fileName));
        int n = fr.nextInt();
        int m = fr.nextInt();
        Integer[][] matrix = new Integer[n][m];
        Shape<Integer> ans = ShapeFactory.getComparableShape();
        ans.setN(n);
        ans.setM(m);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                matrix[i][j] = fr.nextInt();
            }
        }
        ans.setShapeMatrix(matrix);
        fr.close();
        return ans;
    }
}
