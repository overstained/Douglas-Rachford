
package pkg8.queens;


public class SquareMatrix {
    private final int        dimension;
    private final Double[][] matrix;
    public SquareMatrix(int dimension) {
        this.dimension = dimension;
        matrix = new Double [dimension][dimension];
        for(int i=0;i<dimension;i++) {
            for(int j=0;j<dimension;j++) {
                matrix[i][j]=0d;
            }
        }
    }
    public Double get(int i, int j) {
        if(i>=0 && i<dimension && j>=0 && j<dimension)
           return matrix[i][j];
        return null;
    }
    
    public boolean set(int i, int j, Double entry) {
        if(i>=0 && i<dimension && j>=0 && j<dimension) {
            matrix[i][j] = entry;
            return true;
        }
        return false;
    }
}
