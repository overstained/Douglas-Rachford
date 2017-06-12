
package pkg8.queens;

public class DougRachUtils {
    private final int dimension;
    private final SquareMatrixOperations matrixOps;
    public DougRachUtils(int dimension) {
        this.dimension=dimension;
        matrixOps = new SquareMatrixOperations(dimension);
    }
    
    public SquareMatrix projectionC1(SquareMatrix matrix) {
        SquareMatrix projection = new SquareMatrix(dimension);
        for(int i=0;i<dimension;i++) {
            int maxPosition = 0;
            for(int j=0;j<dimension;j++) {
                 if(matrix.get(i, maxPosition)<matrix.get(i, j)){
                     maxPosition = j;
                 }   
            }
            projection.set(i, maxPosition, 1d);
        }
        return projection;
    }
    
    public SquareMatrix projectionC2(SquareMatrix matrix) {
        SquareMatrix projection = new SquareMatrix(dimension);
        for(int j=0;j<dimension;j++) {
            int maxPosition = 0;
            for(int i=0;i<dimension;i++) {
                 if(matrix.get(maxPosition, j)<matrix.get(i, j)){
                     maxPosition = i;
                 }   
            }
            projection.set(maxPosition, j, 1d);
        }
        return projection;
    }
    
    public SquareMatrix projectionC3(SquareMatrix matrix) {
        SquareMatrix projection = new SquareMatrix(dimension);
        if(matrix.get(0, 0)>=0.5) {
            projection.set(0, 0, 1.0);
        } else {
            projection.set(0, 0, 0.0);
        }
        if(matrix.get(dimension-1, dimension-1)>=0.5) {
            projection.set(dimension-1, dimension-1, 1.0);
        } else {
            projection.set(dimension-1, dimension-1, 0.0);
        }
        
        for(int k=0;k<dimension;k++) {
            int i=k,j=0;
            int maxPositionI = i, maxPositionJ=j;
            while(i>=0) {
                if(matrix.get(maxPositionI,maxPositionJ)<matrix.get(i, j)){
                    maxPositionI=i;maxPositionJ=j;
                }
                --i;
                ++j;
            }
            projection.set(maxPositionI, maxPositionJ, 1d);
            
            
            i=k;
            j=0;
            Double leftSum = 0.0, rightSum = 0.0; 
            while(i>=0) {
                leftSum += Math.pow(matrix.get(i, j), 2.0);
                rightSum +=Math.pow(matrix.get(i, j)-projection.get(i, j), 2.0);
                --i;
                ++j;
            }
            
            if(Math.sqrt(leftSum)<Math.sqrt(rightSum)) {
                projection.set(maxPositionI, maxPositionJ, 0.0);
            }
        }
        
        for(int k=1;k<dimension;k++) {
            int i=dimension-1,j=k;
            int maxPositionI = i, maxPositionJ=j;
            while(j<dimension) {
                if(matrix.get(maxPositionI,maxPositionJ)<matrix.get(i, j)){
                    maxPositionI=i;maxPositionJ=j;
                }
                --i;
                ++j;
            }
            projection.set(maxPositionI, maxPositionJ, 1d);
            
            i=dimension-1;
            j=k;
            Double leftSum = 0.0, rightSum = 0.0; 
            while(j<dimension) {
                leftSum += Math.pow(matrix.get(i, j), 2.0);
                rightSum +=Math.pow(matrix.get(i, j)-projection.get(i, j), 2.0);
                --i;
                ++j;
            }
            if(Math.sqrt(leftSum)<Math.sqrt(rightSum)) {
                projection.set(maxPositionI, maxPositionJ, 0.0);
            }
        }
        
        return projection;
    }
    
    public SquareMatrix projectionC4(SquareMatrix matrix) {
        SquareMatrix projection = new SquareMatrix(dimension);
        if(matrix.get(0, dimension-1)>=0.5) {
            projection.set(0, dimension-1, 1.0);
        } else {
            projection.set(0, dimension-1, 0.0);
        }
        if(matrix.get(dimension-1, 0)>=0.5) {
            projection.set(dimension-1, 0, 1.0);
        } else {
            projection.set(dimension-1, 0, 0.0);
        }
        for(int k=dimension-1;k>=0;k--) {
            int i=k,j=0;
            int maxPositionI = i, maxPositionJ=j;
            while(i<dimension) {
                if(matrix.get(maxPositionI,maxPositionJ)<matrix.get(i, j)){
                    maxPositionI=i;maxPositionJ=j;
                }
                ++i;
                ++j;
            }
            projection.set(maxPositionI, maxPositionJ, 1d);
            i=k;
            j=0;
            Double leftSum = 0.0, rightSum = 0.0; 
            while(i<dimension) {
               leftSum += Math.pow(matrix.get(i, j), 2.0);
               rightSum +=Math.pow(matrix.get(i, j)-projection.get(i, j), 2.0);
               ++i;
               ++j;
            }
            if(Math.sqrt(leftSum)<Math.sqrt(rightSum)) {
                projection.set(maxPositionI, maxPositionJ, 0.0);
            }
        }
        
        for(int k=1;k<dimension;k++) {
            int i=0,j=k;
            int maxPositionI = i, maxPositionJ=j;
            while(j<dimension) {
                if(matrix.get(maxPositionI,maxPositionJ)<matrix.get(i, j)){
                    maxPositionI=i;maxPositionJ=j;
                }
                ++i;
                ++j;
            }
            projection.set(maxPositionI, maxPositionJ, 1.0);
            i=0;
            j=k;
            Double leftSum = 0.0, rightSum = 0.0; 
            while(j<dimension) {
                leftSum += Math.pow(matrix.get(i, j), 2.0);
                rightSum +=Math.pow(matrix.get(i, j)-projection.get(i, j), 2.0);
                ++i;
                ++j;
            }
            if(Math.sqrt(leftSum)<Math.sqrt(rightSum)) {
                projection.set(maxPositionI, maxPositionJ, 0.0);
            }
        }
        
        return projection;
    }
   
    
    public SquareMatrix projectionDelta(SquareMatrix firstMatrix,
                                        SquareMatrix secondMatrix,
                                        SquareMatrix thirdMatrix,
                                        SquareMatrix fourthMatrix) {
        SquareMatrix result = matrixOps.addMatrix(firstMatrix, secondMatrix);
        SquareMatrix result2 = matrixOps.addMatrix(thirdMatrix, fourthMatrix);
        result = matrixOps.addMatrix(result, result2);
        return matrixOps.scalarMultiplication(0.25, result);
    }
    
    public SquareMatrix firstIterateComponent(SquareMatrix firstMatrix,
                                        SquareMatrix secondMatrix,
                                        SquareMatrix thirdMatrix,
                                        SquareMatrix fourthMatrix) {
        SquareMatrix diagProjection = projectionDelta(firstMatrix, 
                                                      secondMatrix, 
                                                      thirdMatrix, 
                                                      fourthMatrix);
        SquareMatrix temp = matrixOps.scalarMultiplication(2.0, diagProjection);
        matrixOps.printMatrix(temp);
        SquareMatrix temp2 = matrixOps.scalarMultiplication(-1.0, firstMatrix);
        matrixOps.printMatrix(temp2);
        matrixOps.printMatrix(matrixOps.addMatrix(temp, temp2));
        temp = projectionC2(matrixOps.addMatrix(temp, temp2));
        matrixOps.printMatrix(temp);
        temp2 = matrixOps.addMatrix(firstMatrix, matrixOps.scalarMultiplication(-1.0, diagProjection));
        return matrixOps.addMatrix(temp, temp2);
    }
    
    public SquareMatrix secondIterateComponent(SquareMatrix firstMatrix,
                                               SquareMatrix secondMatrix,
                                               SquareMatrix thirdMatrix,
                                               SquareMatrix fourthMatrix) {
        SquareMatrix diagProjection = projectionDelta(firstMatrix, 
                                                      secondMatrix, 
                                                      thirdMatrix, 
                                                      fourthMatrix);
        SquareMatrix temp = matrixOps.scalarMultiplication(2.0, diagProjection);
        SquareMatrix temp2 = matrixOps.scalarMultiplication(-1.0, secondMatrix);
        temp = projectionC1(matrixOps.addMatrix(temp, temp2));
        temp2 = matrixOps.addMatrix(secondMatrix, matrixOps.scalarMultiplication(-1.0, diagProjection));
        return matrixOps.addMatrix(temp, temp2);
    }
    
    public SquareMatrix thirdIterateComponent(SquareMatrix firstMatrix,
                                              SquareMatrix secondMatrix,
                                              SquareMatrix thirdMatrix,
                                              SquareMatrix fourthMatrix) {
        SquareMatrix diagProjection = projectionDelta(firstMatrix, 
                                                      secondMatrix, 
                                                      thirdMatrix, 
                                                      fourthMatrix);
        SquareMatrix temp = matrixOps.scalarMultiplication(2.0, diagProjection);
        SquareMatrix temp2 = matrixOps.scalarMultiplication(-1.0, thirdMatrix);
        temp = projectionC3(matrixOps.addMatrix(temp, temp2));
        temp2 = matrixOps.addMatrix(thirdMatrix, matrixOps.scalarMultiplication(-1.0, diagProjection));
        return matrixOps.addMatrix(temp, temp2);
    }
    
    public SquareMatrix fourthIterateComponent(SquareMatrix firstMatrix,
                                               SquareMatrix secondMatrix,
                                               SquareMatrix thirdMatrix,
                                               SquareMatrix fourthMatrix) {
        SquareMatrix diagProjection = projectionDelta(firstMatrix, 
                                                      secondMatrix, 
                                                      thirdMatrix, 
                                                      fourthMatrix);
        SquareMatrix temp = matrixOps.scalarMultiplication(2.0, diagProjection);
        SquareMatrix temp2 = matrixOps.scalarMultiplication(-1.0, fourthMatrix);
        temp = projectionC4(matrixOps.addMatrix(temp, temp2));
        temp2 = matrixOps.addMatrix(fourthMatrix, matrixOps.scalarMultiplication(-1.0, diagProjection));
        return matrixOps.addMatrix(temp, temp2);
    }
    
    public boolean checkSolution(SquareMatrix matrix) {
        
        SquareMatrix roundedMatrix = matrixOps.roundMatrix(matrix);
        //Horizontal
        for(int i=0;i<dimension;i++) {
            Double sum = 0d;
            for(int j=0;j<dimension;j++) {
                sum+=roundedMatrix.get(i, j);
            }
            if(sum>1d) {
                return false;
            }
        }    
        //Vertical
        for(int j=0;j<dimension;j++) {
            Double sum = 0d;
            for(int i=0;i<dimension;i++) {
                sum+=roundedMatrix.get(i, j);
            }
            if(sum>1d) {
                return false;
            }
        }    
        
        //Positive slope
        for(int k=0;k<dimension;k++) {
            int i=k,j=0;
            Double sum = 0.0;
            while(i>=0) {
                sum+=matrix.get(i, j);
                --i;
                ++j;
            }
            if(sum>1) {
              return false;
            }
        }
        for(int k=1;k<dimension;k++) {
            int i=dimension-1,j=k;
            Double sum = 0.0;
            while(j<dimension) {
                sum+=matrix.get(i, j);
                --i;
                ++j;
            }
            if(sum>1) {
              return false;
            }
        }
        //Negative slope
        for(int k=dimension-1;k>=0;k--) {
            int i=k,j=0;
            Double sum = 0.0;
            while(i<dimension) {
                sum+=matrix.get(i, j);
                ++i;
                ++j;
            }
            if(sum>1) {
              return false;
            }
        }
        for(int k=dimension-1;k>=0;k--) {
            int i=k,j=0;
            Double sum = 0.0;
            while(i<dimension) {
                sum+=matrix.get(i, j);
                ++i;
                ++j;
            }
            if(sum>1) {
              return false;
            }
        }
        return true;
    }
}
