package sudoku;


public class DouglasRachfordUtils {
    private final int dimension;
    private final CubeMatrixOperations matrixOperations;
    DouglasRachfordUtils(int dimension) {
        this.dimension = dimension;
        this.matrixOperations = new CubeMatrixOperations(dimension);
    }
    
    //An initial sudoku constraint
    public SquareMatrix initialConstraint() {
        SquareMatrix initialProblem = new SquareMatrix(9);
        initialProblem.set(0, 0, 4.0);
        initialProblem.set(0, 1, 2.0);
        initialProblem.set(0, 5, 7.0);
        initialProblem.set(1, 0, 1.0);
        initialProblem.set(2, 1, 3.0);
        initialProblem.set(2, 2, 7.0);
        initialProblem.set(2, 3, 1.0);
        initialProblem.set(2, 4, 6.0);
        initialProblem.set(2, 8, 9.0);
        initialProblem.set(3, 2, 2.0);
        initialProblem.set(3, 4, 5.0);
        initialProblem.set(3, 7, 8.0);
        initialProblem.set(4, 0, 9.0);
        initialProblem.set(4, 1, 8.0);
        initialProblem.set(4, 3, 6.0);
        initialProblem.set(6, 3, 4.0);
        initialProblem.set(6, 7, 9.0);
        initialProblem.set(6, 8, 1.0);
        initialProblem.set(7, 0, 7.0);
        initialProblem.set(7, 2, 1.0);
        initialProblem.set(7, 3, 9.0);
        initialProblem.set(7, 8, 2.0);
        initialProblem.set(8, 0, 5.0);
        initialProblem.set(8, 4, 2.0);
        initialProblem.set(8, 7, 3.0);
        initialProblem.set(8, 8, 6.0);
        return initialProblem;
    }
    
    //Turn the cube sudoku matrix into a square one, i.e. turn each 
    //"elevator shaft" into it's corresponding floor number
    public SquareMatrix flattenCube(CubeMatrix cubeSudoku) {
        SquareMatrix squareSudoku = new SquareMatrix(9);
        for(int i=0;i<9;i++) {
            for(int j=0;j<9;j++) {
                int maxPoz = 0;
                for(int k=0;k<9;k++) {
                    if(cubeSudoku.get(i, j, maxPoz)
                     < cubeSudoku.get(i, j, k)) {
                        maxPoz = k;
                    }
                }
                squareSudoku.set(i, j, (maxPoz+1)*1d);
            }
        }
        return squareSudoku;
    }
    
    //Initialize a vector with 0
    public void initFrequenceVector(int [] vector) {
        for(int i=1;i<vector.length;i++) {
            vector[i]=0;
        }
    }
    
    //Check if the sudoku square matrix satisfies the initial constraints
    public boolean checkSudoku(SquareMatrix sudokuToTest, SquareMatrix initialProblem) {
         int [] frequenceVector = new int[10];
         //Check if each row has all the digits from 1-9
         for(int i=0;i<9;i++) {
             initFrequenceVector(frequenceVector);
             for(int j=0;j<9;j++) {
                 int componentValue = sudokuToTest.get(i, j).intValue();
                 ++frequenceVector[componentValue];
                 if(frequenceVector[componentValue]>1) {
                     return false;
                 }
             }
         }
         //Check if each column has all the digits from 1-9
         for(int i=0;i<9;i++) {
             initFrequenceVector(frequenceVector);
             for(int j=0;j<9;j++) {
                 int componentValue = sudokuToTest.get(j, i).intValue();
                 ++frequenceVector[componentValue];
                 if(frequenceVector[componentValue]>1) {
                     return false;
                 }
             }
         }
         //Check if each 3x3 square has all the digits from 1-9
         for(int squareRow=0;squareRow<9;squareRow+=3) {
             for(int squareColumn=0;squareColumn<9;squareColumn+=3) {
                 initFrequenceVector(frequenceVector);
                 for(int i=squareRow;i<squareRow+3;i++) {
                     for(int j=squareColumn;j<squareColumn+3;j++) {
                         int componentValue = sudokuToTest.get(i, j).intValue();
                         ++frequenceVector[componentValue];
                         if(frequenceVector[componentValue]>1) {
                             return false;
                         }
                     }
                 }
             }
         }
         
         //Check if it respects the initial problem
         for(int i=0;i<9;i++) {
             for(int j=0;j<9;j++) {
                 if(initialProblem.get(i, j)!=0.0) {
                     if(sudokuToTest.get(i, j).intValue()!=
                        initialProblem.get(i, j).intValue()) {
                         return false;
                     }
                 }
             }
         }
         return true;
    }
    
    //Turn the initial sudoku square matrix into a cube matrix
    //by setting each entry to it's corresponding unit vector
    public CubeMatrix startingMatrix(SquareMatrix initialProblem) {
        CubeMatrix startingMatrix = new CubeMatrix(dimension);
        for(int i=0;i<dimension;i++) {
            for(int j=0;j<dimension;j++) {
                if(initialProblem.get(i, j).intValue() > 0) {
                    startingMatrix.set(i, j, initialProblem.get(i, j).intValue()-1, 1.0);
                }
            }
        }
        return startingMatrix;
    }
    
    //The unit vector E corresponding to some vector V, is constructed by
    //setting E[i]=1 if the index i corresponds to index of the maximal entry 
    //of V and E[i]=0 otherwise
    
    //The projection on C1 of the cube matrix is the unit vector corresponding 
    //to the row vectors on each floor of the matrix
    public CubeMatrix projectionC1(CubeMatrix matrixToProject) {
        CubeMatrix projection = new CubeMatrix(dimension);
        for(int i=0;i<dimension;i++) {
            for(int k=0;k<dimension;k++) {
                int maxPoz = 0;
                for(int j=0;j<dimension;j++) {
                    if(matrixToProject.get(i, maxPoz, k)
                     < matrixToProject.get(i, j, k)){
                        maxPoz = j;
                    }
                }
                projection.set(i, maxPoz, k, 1.0);
            }
        }
        return projection;
    }
    
    //The projection on C2 of the cube matrix is the unit vector corresponding 
    //to the column vectors on each floor of the matrix
    public CubeMatrix projectionC2(CubeMatrix matrixToProject) {
        CubeMatrix projection = new CubeMatrix(dimension);
        for(int j=0;j<dimension;j++) {
            for(int k=0;k<dimension;k++) {
                int maxPoz = 0;
                for(int i=0;i<dimension;i++) {
                    if(matrixToProject.get(maxPoz, j, k)
                     < matrixToProject.get(i, j, k)){
                        maxPoz = i;
                    }
                }
                projection.set(maxPoz, j, k, 1.0);
            }
        }
        return projection;
    }
    //The projection on C3 of the cube matrix is the unit vector corresponding 
    //to every 3x3 square matrix on each floor of the cube matrix 
    public CubeMatrix projectionC3(CubeMatrix matrixToProject) {
        CubeMatrix projection = new CubeMatrix(dimension);
        for(int k=0;k<dimension;k++) {
            for(int squareRow=0;squareRow<9;squareRow+=3) {
                 for(int squareColumn=0;squareColumn<9;squareColumn+=3) {
                     int maxPozX = squareRow, maxPozY = squareColumn; 
                     for(int i=squareRow;i<squareRow+3;i++) {
                         for(int j=squareColumn;j<squareColumn+3;j++) {
                            if(matrixToProject.get(maxPozX, maxPozY, k)
                             < matrixToProject.get(i, j, k)) {
                                maxPozX = i;
                                maxPozY = j;
                            }
                         }
                     }
                     projection.set(maxPozX, maxPozY, k, 1.0);
                 }
             }
        }
        return projection;
    }
    //The projection on C4 of the cube matrix is the unit vector corresponding 
    //to each "elevator shaft" of the matrix if the corresponding entry of the
    //initial sudoku square matrix is null or the only unit vector
    //corresponding to the entry of the initial sudoku square matrix otherwise
    public CubeMatrix projectionC4(CubeMatrix matrixToProject, 
                                   SquareMatrix initialProblem) {
        CubeMatrix projection = new CubeMatrix(dimension);
        for(int i=0;i<dimension;i++) {
            for(int j=0;j<dimension;j++) {
                if(initialProblem.get(i, j).intValue() > 0) {
                    projection.set(i, j, initialProblem.get(i, j).intValue()-1, 1.0);
                } else if(initialProblem.get(i, j).intValue() == 0) {
                    int maxPoz = 0;
                    for(int k=0;k<dimension;k++) {
                        if(matrixToProject.get(i, j, maxPoz)
                         < matrixToProject.get(i, j, k)) {
                            maxPoz = k;
                        }
                    }
                    projection.set(i, j, maxPoz, 1.0);
                } 
            }
        }
        return projection;
    }
    //Projection onto the diagonal of the product space is the mean of each 
    //iterate components
    public CubeMatrix diagonalProjection(Tuple tuple) {
        CubeMatrix result = matrixOperations.addCubeMatrix(tuple.getFirst(), 
                                                           tuple.getSecond());
        result = matrixOperations.addCubeMatrix(result, tuple.getThird());
        result = matrixOperations.addCubeMatrix(result, tuple.getFourth());
        return matrixOperations.scalarMultiplication(0.25, result);
    }
    
    //First iterate component
    public CubeMatrix firstIterateComponent(Tuple tuple) {
        CubeMatrix diagonalProjection = diagonalProjection(tuple);
        CubeMatrix temp = matrixOperations.scalarMultiplication(2.0, diagonalProjection);
        CubeMatrix temp2 = matrixOperations.scalarMultiplication(-1.0, tuple.getFirst());
        temp = projectionC1(matrixOperations.addCubeMatrix(temp, temp2));
        temp2 = matrixOperations.addCubeMatrix(tuple.getFirst(), 
                matrixOperations.scalarMultiplication(-1.0, diagonalProjection));
        return matrixOperations.addCubeMatrix(temp, temp2);
        
    }
    
    //Second iterate component
    public CubeMatrix secondIterateComponent(Tuple tuple) {
        CubeMatrix diagonalProjection = diagonalProjection(tuple);
        CubeMatrix temp = matrixOperations.scalarMultiplication(2.0, diagonalProjection);
        CubeMatrix temp2 = matrixOperations.scalarMultiplication(-1.0, tuple.getSecond());
        temp = projectionC2(matrixOperations.addCubeMatrix(temp, temp2));
        temp2 = matrixOperations.addCubeMatrix(tuple.getSecond(), 
                matrixOperations.scalarMultiplication(-1.0, diagonalProjection));
        return matrixOperations.addCubeMatrix(temp, temp2);
    }
    
    //Third iterate component
    public CubeMatrix thirdIterateComponent(Tuple tuple) {
        CubeMatrix diagonalProjection = diagonalProjection(tuple);
        CubeMatrix temp = matrixOperations.scalarMultiplication(2.0, diagonalProjection);
        CubeMatrix temp2 = matrixOperations.scalarMultiplication(-1.0, tuple.getThird());
        temp = projectionC3(matrixOperations.addCubeMatrix(temp, temp2));
        temp2 = matrixOperations.addCubeMatrix(tuple.getThird(), 
                matrixOperations.scalarMultiplication(-1.0, diagonalProjection));
        return matrixOperations.addCubeMatrix(temp, temp2);
    }
    
    //Fourth iterate component
    public CubeMatrix fourthIterateComponent(Tuple tuple,
                                             SquareMatrix initialProblem) {
        CubeMatrix diagonalProjection = diagonalProjection(tuple);
        CubeMatrix temp = matrixOperations.scalarMultiplication(2.0, diagonalProjection);
        CubeMatrix temp2 = matrixOperations.scalarMultiplication(-1.0, tuple.getFourth());
        temp = projectionC4(matrixOperations.addCubeMatrix(temp, temp2),initialProblem);
        temp2 = matrixOperations.addCubeMatrix(tuple.getFourth(), 
                matrixOperations.scalarMultiplication(-1.0, diagonalProjection));
        return matrixOperations.addCubeMatrix(temp, temp2);
    }
    
}
