
package tetravex;

import java.util.List;



public class Tuple{
    private final SquareMatrix firstMatrix;
    private final SquareMatrix secondMatrix;
    private final List<SquareMatrix> thirdMatrix;
    
    public Tuple(SquareMatrix firstMatrix,
                 SquareMatrix secondMatrix,
                 List<SquareMatrix> thirdMatrix) {
        this.firstMatrix = firstMatrix;
        this.secondMatrix = secondMatrix;
        this.thirdMatrix = thirdMatrix;
    }
    
    public SquareMatrix getFirst() {
        return firstMatrix;
    }
    
    public SquareMatrix getSecond() {
        return secondMatrix;
    }
    
    public List<SquareMatrix> getThird() {
        return thirdMatrix;
    }
    
}
