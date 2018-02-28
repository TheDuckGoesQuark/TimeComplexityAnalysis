package main.algorithms;

import main.structures.IntMatrix;


/**
 * refactored from https://stackoverflow.com/questions/29041193/implementing-strassens-algorithm
 * DIMENSIONS MUST BE POWER OF 2
 */
public class StrassenMultiplier extends BaseMultiplier<IntMatrix> {

    private static final BasicMultiplier basicMultiplier = new BasicMultiplier();
    private static final int LEAFSIZE = 256;

    @Override
    public String toString() {
        return "StrassenMultiplier";
    }

    @Override
    public IntMatrix multiply(IntMatrix a, IntMatrix b) {

        int dim = a.getDim();

        if (dim <= LEAFSIZE) {
            return basicMultiplier.multiply(a, b);
        }

        // make new sub-matrices
        int newDim = (dim + 1) / 2;
        IntMatrix a11 = a.getSlice(0, 0, newDim); // top left
        IntMatrix a12 = a.getSlice(0, newDim, newDim); // top right
        IntMatrix a21 = a.getSlice(newDim, 0, newDim); // bottom left
        IntMatrix a22 = a.getSlice(newDim, newDim, newDim); // bottom right

        IntMatrix b11 = b.getSlice(0, 0, newDim);
        IntMatrix b12 = b.getSlice(0, newDim, newDim);
        IntMatrix b21 = b.getSlice(newDim, 0, newDim);
        IntMatrix b22 = b.getSlice(newDim, newDim, newDim);

        IntMatrix aResult;
        IntMatrix bResult;

        aResult = IntMatrix.add(a11, a22);
        bResult = IntMatrix.add(b11, b22);
        IntMatrix p1 = this.multiply(aResult, bResult);

        aResult = IntMatrix.add(a21, a22);
        IntMatrix p2 = this.multiply(aResult, b11);

        bResult = IntMatrix.subtract(b12, b22); // b12 - b22
        IntMatrix p3 = this.multiply(a11, bResult);

        bResult = IntMatrix.subtract(b21, b11); // b21 - b11
        IntMatrix p4 = this.multiply(a22, bResult);

        aResult = IntMatrix.add(a11, a12); // a11 + a12
        IntMatrix p5 = this.multiply(aResult, b22);

        aResult = IntMatrix.subtract(a21, a11); // a21 - a11
        bResult = IntMatrix.add(b11, b12); // b11 + b12
        IntMatrix p6 = this.multiply(aResult, bResult);

        aResult = IntMatrix.subtract(a12, a22); // a12 - a22
        bResult = IntMatrix.add(b21, b22); // b21 + b22
        IntMatrix p7 = this.multiply(aResult, bResult);

        IntMatrix c12 = IntMatrix.add(p3, p5); // c12 = p3 + p5
        IntMatrix c21 = IntMatrix.add(p2, p4); // c21 = p2 + p4

        aResult = IntMatrix.add(p1, p4); // p1 + p4
        bResult = IntMatrix.add(aResult, p7); // p1 + p4 + p7
        IntMatrix c11 = IntMatrix.subtract(bResult, p5);

        aResult = IntMatrix.add(p1, p3); // p1 + p3
        bResult = IntMatrix.add(aResult, p6); // p1 + p3 + p6
        IntMatrix c22 = IntMatrix.subtract(bResult, p2);

        // Grouping the results obtained in a single matrix:
        int rows = c11.getDim();
        int cols = c11.getDim();

        IntMatrix C = new IntMatrix(dim);
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                int el;
                if (i < rows) {
                    if (j < cols) {
                        el = c11.get(i, j);
                    } else {
                        el = c12.get(i, j - cols);
                    }
                } else {
                    if (j < cols) {
                        el = c21.get(i - rows, j);
                    } else {
                        el = c22.get(i - rows, j - rows);
                    }
                }
                C.set(i, j, el);
            }
        }

        return C;
    }
}
