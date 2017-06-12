package sudoku;

public class CubeMatrix {
    private final int        dimension;
    private final Double[][][] matrix;
    public CubeMatrix(int dimension) {
        this.dimension = dimension;
        matrix = new Double [dimension][dimension][dimension];
        for(int i=0;i<dimension;i++) {
            for(int j=0;j<dimension;j++) {
                for(int k=0;k<dimension;k++) {
                    matrix[i][j][k]=0.0;
                }
            }
        }
    }
    public Double get(int i, int j, int k) {
        if(i>=0 && i<dimension && 
           j>=0 && j<dimension && 
           k>=0 && k<dimension) {
                   return matrix[i][j][k];
        }
        return null;
    }
    
    public boolean set(int i, int j, int k, Double entry) {
        if(i>=0 && i<dimension && 
           j>=0 && j<dimension &&
           k>=0 && k<dimension) {
                  matrix[i][j][k] = entry;
            return true;
        }
        return false;
    }
}