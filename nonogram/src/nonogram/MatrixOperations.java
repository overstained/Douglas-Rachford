package nonogram;

import java.util.Random;


public class MatrixOperations {
    private final int dimensionR;
    private final int dimensionC;
    public MatrixOperations(int dimensionR,
                            int dimensionC) {
        this.dimensionR = dimensionR;
        this.dimensionC = dimensionC;
    }
    public Matrix addition(Matrix firstMatrix,
                           Matrix secondMatrix) {
        Matrix result = new Matrix(dimensionR,
                                   dimensionC);
        for(int i=0;i<dimensionR;i++) {
            for(int j=0;j<dimensionC;j++) {
                result.set(i, j, firstMatrix.get(i, j) +
                                 secondMatrix.get(i, j));
            }
        }
        return result;
    }
    
    public Matrix scalarMultiplication(double scalar,
                                       Matrix matrix) {
        Matrix result = new Matrix(dimensionR,
                                   dimensionC);
        for(int i=0;i<dimensionR;i++) {
            for(int j=0;j<dimensionC;j++) {
                result.set(i, j, scalar *
                                 matrix.get(i, j));
            }
        }
        return result;
    }
    
    public Matrix randomMatrix() {
        Random random = new Random();
        Matrix randomMatrix = new Matrix(dimensionR,
                                         dimensionC);
        for(int i=0;i<dimensionR;i++) {
            for(int j=0;j<dimensionC;j++) {
                if(random.nextDouble()<=0.5) {
                    randomMatrix.set(i, j, 1.0);
                } else {
                    randomMatrix.set(i, j, 0.0);
                }
            }
        }
        return randomMatrix;
    }
    
    
    public Matrix someMatrix() {
        Matrix randomMatrix = new Matrix(dimensionR,
                                         dimensionC);
        for(int i=0;i<dimensionR;i++) {
            for(int j=0;j<dimensionC;j++) {
                if((i+j)%2==0) {
                    randomMatrix.set(i, j, 1.0);
                } else {
                    randomMatrix.set(i, j, 0.0);
                }
            }
        }
        return randomMatrix;
    }
}
