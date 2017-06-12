package sudoku;

import java.util.Random;

public class SquareMatrixOperations {
    private final int dimension;
    SquareMatrixOperations(int dimension) {
        this.dimension = dimension;
    }
    
    public SquareMatrix queensMatrix() {
        SquareMatrix matrix = new SquareMatrix(dimension);
        matrix.set(0, 0, 1d);
        matrix.set(0, 1, 1d);
        matrix.set(0, 2, 1d);
        matrix.set(1, 0, 1d);
        matrix.set(1, 1, 1d);
        matrix.set(1, 2, 1d);
        matrix.set(2, 0, 1d);
        matrix.set(2, 1, 1d);
        return matrix;
    }
    public SquareMatrix initRandomMatrix() {
        Random rand = new Random();
        SquareMatrix matrix = new SquareMatrix(dimension);
        for(int i=0;i<dimension;i++) {
            for(int j=0;j<dimension;j++) {
              matrix.set(i, j, rand.nextDouble());
            }
        }
        return matrix;
    }
    
    public SquareMatrix scalarMultiplication(Double scalar ,SquareMatrix matrix) {
        SquareMatrix result = new SquareMatrix(dimension);
         for(int i=0;i<dimension;i++) {
            for(int j=0;j<dimension;j++) {
                result.set(i, j, scalar * matrix.get(i, j));
            }
        }
         return result;
    }
    
    public SquareMatrix addMatrix(SquareMatrix firstMatrix, SquareMatrix secondMatrix) {
        SquareMatrix result = new SquareMatrix(dimension);
        for(int i=0;i<dimension;i++) {
            for(int j=0;j<dimension;j++) {
                result.set(i, j, firstMatrix.get(i, j)+secondMatrix.get(i, j));
            }
        }
        return result;
    }
    
    public SquareMatrix roundMatrix(SquareMatrix matrix) {
        SquareMatrix rounded = new SquareMatrix(dimension);
        for(int i=0;i<dimension;i++) {
            for(int j=0;j<dimension;j++) {
                if(matrix.get(i, j)>=0.5) {
                    rounded.set(i, j, 1d);
                } 
            }
        }
        return rounded;
    }
    
    public SquareMatrix cloneMatrix(SquareMatrix matrix) {
        SquareMatrix clone = new SquareMatrix(dimension);
        for(int i=0;i<dimension;i++) {
            for(int j=0;j<dimension;j++) {
                clone.set(i, j, matrix.get(i, j));
            }
        }
        return clone;
    }
    
    public void printMatrix(SquareMatrix matrix) {
        for(int i=0;i<dimension;i++) {
            for(int j=0;j<dimension;j++) {
                System.out.print(matrix.get(i, j) + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
