
package tetravex;


public class SquareMatrix {
    private final int        dimension;
    private final double[][] matrix;
    public SquareMatrix(int dimension) {
        this.dimension = dimension;
        matrix = new double [dimension][dimension];
        for(int i=0;i<dimension;i++) {
            for(int j=0;j<dimension;j++) {
                matrix[i][j]=0d;
            }
        }
    }
    public double get(int i, int j) {
        if(i>=0 && i<dimension && j>=0 && j<dimension)
           return matrix[i][j];
        return Double.POSITIVE_INFINITY;
    }
    
    public double[] get(int i) {
        double [] v = new double[dimension];
        for(int j=0;j<v.length;j++) {
            v[j] = matrix[i][j];
        }
        return v;
    }
    
    public boolean set(int i, int j, double entry) {
        if(i>=0 && i<dimension && j>=0 && j<dimension) {
            matrix[i][j] = entry;
            return true;
        }
        return false;
    }
    
    public void print() {
        for(int i=0;i<dimension;i++) {
            for(int j=0;j<dimension;j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}
