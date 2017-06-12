
package nonogram;


public class Matrix {
    private final int        dimensionR;
    private final int        dimensionC;
    private final Double[][] matrix;
    public Matrix(int dimensionR, int dimensionC) {
        this.dimensionR = dimensionR;
        this.dimensionC = dimensionC;
        matrix = new Double [dimensionR][dimensionC];
        for(int i=0;i<dimensionR;i++) {
            for(int j=0;j<dimensionC;j++) {
                matrix[i][j]=0d;
            }
        }
    }
    public Double get(int i, int j) {
        if(i>=0 && i<dimensionR && 
           j>=0 && j<dimensionC)
           return matrix[i][j];
        return null;
    }
    
    public boolean set(int i, int j, Double entry) {
        if(i>=0 && i<dimensionR &&
           j>=0 && j<dimensionC) {
            matrix[i][j] = entry;
            return true;
        }
        return false;
    }
    
    public void print() {
        for(int i=0;i<dimensionR;i++) {
            for(int j=0;j<dimensionC;j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
