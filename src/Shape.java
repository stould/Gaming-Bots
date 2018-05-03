import java.util.Comparator;

public abstract class Shape<T> implements Comparable{

    private int n, m;
    private T shapeMatrix[][];

    public Shape() {
    }

    public Shape(int n, int m, T[][] shapeMatrix) {
        this.n = n;
        this.m = m;
        this.shapeMatrix = shapeMatrix;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public int getM() {
        return m;
    }

    public void setM(int m) {
        this.m = m;
    }

    public T[][] getShapeMatrix() {
        return shapeMatrix;
    }

    public void setShapeMatrix(T[][] shapeMatrix) {
        this.shapeMatrix = shapeMatrix;
    }

}
