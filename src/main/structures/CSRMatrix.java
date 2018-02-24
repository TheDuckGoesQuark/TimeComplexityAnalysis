package main.structures;

import java.util.ArrayList;

public class CSRMatrix {

    private int[] A;
    private int[] IA;
    private int[] JA;

    /**
     * Creates a square matrix with the given dimension.
     */
    public CSRMatrix(IntMatrix a) {
        ArrayList<Integer> NNZs = new ArrayList<>();
        for (int i = 0; i < a.getDim(); i++) {
            for (int j = 0; j < a.getDim(); j++) {
                
            }
        }
    }

    /**
     * Creates a matrix containing the given values.
     */
    public IntMatrix(int[][] values) {
        this.values = values;
    }

    /**
     * Returns the dimension of the matrix
     */
    public int getDim() {
        return values.length;
    }

    /**
     * Returns the values stored in the matrix.
     */
    public int[][] getValues() {
        return values;
    }

    /**
     * Update the value stored at the specified location.
     */
    public void set(int i, int j, int val) {
        values[i][j] = val;
    }

    /**
     * Get the value stored at the specified location.
     */
    public int get(int i, int j) {
        return values[i][j];
    }

    /**
     * Return a (square) slice of the matrix, starting from
     * the given coordinates with the given dimension.
     */
    public main.structures.IntMatrix getSlice(int x, int y, int dim) {
        int[][] values = new int[dim][dim];
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                values[i][j] = this.values[x + i][y + j];
            }
        }
        return new main.structures.IntMatrix(values);
    }

    /**
     * Update the values of a slice of the matrix, starting
     * from the given coordinates.
     */
    public void setSlice(int x, int y, main.structures.IntMatrix m) {
        int dim = m.getDim();
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                this.set(x + i, y + j, m.get(i, j));
            }
        }
    }


    /**
     * Matrix addition.
     */
    public static main.structures.IntMatrix add(main.structures.IntMatrix a, main.structures.IntMatrix b) {
        int dim = a.getDim();

        main.structures.IntMatrix c = new main.structures.IntMatrix(dim);

        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                c.set(i, j, a.get(i, j) + b.get(i, j));
            }
        }

        return c;
    }

    /**
     * Matrix subtraction.
     */
    public static main.structures.IntMatrix subtract(main.structures.IntMatrix a, main.structures.IntMatrix b) {
        int dim = a.getDim();

        main.structures.IntMatrix c = new main.structures.IntMatrix(dim);

        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                c.set(i, j, a.get(i, j) - b.get(i, j));
            }
        }

        return c;
    }

    /**
     * A string representation of the matrix.
     */
    public String toString() {
        int dim = values.length;
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                b.append(values[i][j]);
                b.append(" ");
            }
            b.append("\n");
        }
        return b.toString();
    }
}


