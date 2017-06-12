package sudoku;

public class CubeMatrixOperations {

    private final int dimension;

    CubeMatrixOperations(int dimension) {
        this.dimension = dimension;
    }

    public CubeMatrix scalarMultiplication(Double scalar, CubeMatrix matrix) {
        CubeMatrix result = new CubeMatrix(dimension);
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                for (int k = 0; k < dimension; k++) {
                    result.set(i, j, k, scalar * matrix.get(i, j, k));
                }
            }
        }
        return result;
    }

    public CubeMatrix addCubeMatrix(CubeMatrix firstMatrix, CubeMatrix secondMatrix) {
        CubeMatrix result = new CubeMatrix(dimension);
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                for (int k = 0; k < dimension; k++) {
                    result.set(i, j, k, firstMatrix.get(i, j, k) + secondMatrix.get(i, j, k));
                }
            }
        }
        return result;
    }

    public CubeMatrix cloneCubeMatrix(CubeMatrix matrix) {
        CubeMatrix clone = new CubeMatrix(dimension);
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                for (int k = 0; k < dimension; k++) {
                    clone.set(i, j, k, matrix.get(i, j, k));
                }
            }
        }
        return clone;
    }

    public void printMatrix(CubeMatrix matrix) {
        for (int k = 0; k < dimension; k++) {
            System.out.println("Floor " + k + " :");
            for (int i = 0; i < dimension; i++) {
                for (int j = 0; j < dimension; j++) {
                    System.out.print(matrix.get(i, j, k) + " ");
                }
                System.out.println();
            }
            System.out.println();
        }
    }
}
