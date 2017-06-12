package sudoku;


public class Tuple {
    private final CubeMatrix firstMatrix;
    private final CubeMatrix secondMatrix;
    private final CubeMatrix thirdMatrix;
    private final CubeMatrix fourthMatrix;
    
    public Tuple(CubeMatrix firstMatrix,
                 CubeMatrix secondMatrix,
                 CubeMatrix thirdMatrix,
                 CubeMatrix fourthMatrix) {
        this.firstMatrix = firstMatrix;
        this.secondMatrix = secondMatrix;
        this.thirdMatrix = thirdMatrix;
        this.fourthMatrix=fourthMatrix;
    }
    
    public CubeMatrix getFirst() {
        return firstMatrix;
    }
    
    public CubeMatrix getSecond() {
        return secondMatrix;
    }
    
    public CubeMatrix getThird() {
        return thirdMatrix;
    }
    
    public CubeMatrix getFourth() {
        return fourthMatrix;
    }
}
