package tetravex;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class SquareMatrixOperations {
    private final int dimension;
    SquareMatrixOperations(int dimension) {
        this.dimension = dimension;
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
    
    public List<SquareMatrix> scalarMultiplication(Double scalar, List<SquareMatrix> matrices) {
        List<SquareMatrix> resultList = new LinkedList<>();
        for(int i=0;i<matrices.size();i++) {
            resultList.add(scalarMultiplication(scalar,matrices.get(i)));
        }
        return resultList;
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
    
    public List<SquareMatrix> addMatrix(SquareMatrix matrix, List<SquareMatrix> matrices) {
        List<SquareMatrix> resultList = new LinkedList<>();
        for(int i=0;i<matrices.size();i++) {
            resultList.add(addMatrix(matrix,matrices.get(i)));
        }
        return resultList;
    }
    
    public List<SquareMatrix> addMatrix(List<SquareMatrix> matrices1, List<SquareMatrix> matrices2) {
        List<SquareMatrix> resultList = new LinkedList<>();
        for(int i=0;i<matrices1.size();i++) {
            resultList.add(addMatrix(matrices1.get(i),matrices2.get(i)));
        }
        return resultList;
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
    
}
