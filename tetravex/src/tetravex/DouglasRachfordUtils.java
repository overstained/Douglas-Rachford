package tetravex;

import java.util.LinkedList;
import java.util.List;


public class DouglasRachfordUtils {
    private final int tetraDimension;
    private final int reprDimension;   
    private final List<Tile> tiles;
    private final List<Pair<Integer,Integer>> horizontallyCompatible;
    private final List<Pair<Integer,Integer>> verticallyCompatible;
    private final SquareMatrixOperations matrixOps;
    DouglasRachfordUtils(String fileName) {
        Reader reader = new Reader(fileName);
        tiles = reader.readTiles();
        tetraDimension = (int)Math.sqrt(tiles.size());
        reprDimension = tiles.size();
        horizontallyCompatible = horizontallyCompatible();
        verticallyCompatible = verticallyCompatible();
        matrixOps = new SquareMatrixOperations(reprDimension);
    }
    
    public int getTetraDimension() {
        return tetraDimension;
    }
    
    public int getReprDimension() {
        return reprDimension;
    }
    
    public List<Tile> getTiles() {
        return tiles;
    }
    
    //Constructs an identity matrix of size "reprDimension"
    public SquareMatrix identity() {
        SquareMatrix identity = new SquareMatrix(reprDimension);
        for(int i=0;i<reprDimension;i++) {
            identity.set(i, i, 1.0);
        }
        return identity;
    }
    
    //Computes the distance between two vectors
    public double distance(double [] v, double [] w) {
        if(v.length != w.length) {
            return Double.POSITIVE_INFINITY;
        }
        double sum = 0.0;
        for(int i=0;i<v.length;i++) {
            sum+=Math.pow((v[i]-w[i]), 2);
        }
        return Math.sqrt(sum);
    }
    
    //Computes the distance between a vector "v"
    //and a unit vector with the i-th entry 1 
    public double unitDistance(double[] v, int i) {
        double [] e = new double[v.length];
        e[i]=1.0;
        double sum = 0.0;
        for(int k=0;k<v.length;k++) {
            sum+=Math.pow((v[k]-e[k]), 2);
        }
        return Math.sqrt(sum);
    }
    
    //Returnes true if "tile1" can be placed left
    //of "tile2", false otherwise
    public boolean allowedHorizontal(Tile tile1, Tile tile2) {
        return tile1.getEast() == tile2.getWest();
    }
    
    //Returns true if "tile1" can be places on top of
    //"tile2", false otherwise
    public boolean allowedVertical(Tile tile1, Tile tile2) {
        return tile1.getSouth() == tile2.getNorth();
    }
    
    
    /*A pair of tiles are said to be horizontally compatible
      if the first tile can be places left of the second tile.
      A pair of tiles are said to be vertically compatible
      if the first tile can be places on top of the second tile.*/
    
    //Creates a list of all horizontally compatible pairs
    //of tiles
    public List<Pair<Integer,Integer>> horizontallyCompatible() {
        List<Pair<Integer,Integer>> horTiles = new LinkedList<>();
        for(int i=0;i<tiles.size();i++) {
            for(int j=0;j<tiles.size();j++) {
                if(i!=j && allowedHorizontal(tiles.get(i),tiles.get(j))) {
                    horTiles.add(new Pair(i+1,j+1));
                }
            }
        }
        return horTiles;
    }
    
    //Creates a list of all vertically compatible pairs
    //of tiles
    public List<Pair<Integer,Integer>> verticallyCompatible() {
        List<Pair<Integer,Integer>> verTiles = new LinkedList<>();
        for(int i=0;i<tiles.size();i++) {
            for(int j=0;j<tiles.size();j++) {
                if(i!=j && allowedVertical(tiles.get(i),tiles.get(j))) {
                    verTiles.add(new Pair(i+1,j+1));
                }
            }
        }
        return verTiles;
    }
    
    //Computes the projection of a matrix on one of the horizontal constraints
    public SquareMatrix horizontalProjection(int i1, int i2, SquareMatrix matrix) {
        int minPoz = 0;
        double min = unitDistance(matrix.get(i1),horizontallyCompatible.get(0).getFirst()-1) +
                     unitDistance(matrix.get(i2),horizontallyCompatible.get(0).getSecond()-1);
        for(int i=1;i<horizontallyCompatible.size();i++) {
            double candidate = unitDistance(matrix.get(i1),horizontallyCompatible.get(i).getFirst()-1) +
                               unitDistance(matrix.get(i2),horizontallyCompatible.get(i).getSecond()-1);
            if(min>candidate) {
                minPoz = i;
                min = candidate;
            }
        }
        for(int j=0;j<reprDimension;j++) {
            matrix.set(i1, j, 0.0);
            matrix.set(i2, j, 0.0);
        }
        matrix.set(i1, horizontallyCompatible.get(minPoz).getFirst()-1, 1.0);
        matrix.set(i2, horizontallyCompatible.get(minPoz).getSecond()-1, 1.0);
        return matrix;
    }
    
    //Computes the projection of a matrix on one of the vertical constraints
    public SquareMatrix verticalProjection(int i1, int i2, SquareMatrix matrix) {
        int minPoz = 0;
        double min = unitDistance(matrix.get(i1),verticallyCompatible.get(0).getFirst()-1) +
                     unitDistance(matrix.get(i2),verticallyCompatible.get(0).getSecond()-1);
        for(int i=1;i<verticallyCompatible.size();i++) {
            double candidate = unitDistance(matrix.get(i1),verticallyCompatible.get(i).getFirst()-1) +
                               unitDistance(matrix.get(i2),verticallyCompatible.get(i).getSecond()-1);
            if(min>candidate) {
                minPoz = i;
                min = candidate;
            }
        }
        for(int j=0;j<reprDimension;j++) {
            matrix.set(i1, j, 0.0);
            matrix.set(i2, j, 0.0);
        }
        matrix.set(i1, verticallyCompatible.get(minPoz).getFirst()-1, 1.0);
        matrix.set(i2, verticallyCompatible.get(minPoz).getSecond()-1, 1.0);
        return matrix;
    }
    
    
    //Creates the tetravex matrix from the representation matrix
    public SquareMatrix toOrderN(SquareMatrix reprMatrix) {
        SquareMatrix tetravex = new SquareMatrix(tetraDimension);
        for(int i=0;i<reprDimension;i++) {
            for(int j=0;j<reprDimension;j++) {
                if(reprMatrix.get(i, j) == 1.0) {
                    tetravex.set((int)Math.floor(i/tetraDimension), 
                                 i%tetraDimension, 
                                 j+1);
                }
            }
        }
        return tetravex;
    }
    
    //Rounds the matrix to 0-1
    public SquareMatrix roundMatrix(SquareMatrix matrix) {
        SquareMatrix rounded = new SquareMatrix(reprDimension);
        for(int i=0;i<reprDimension;i++) {
            for(int j=0;j<reprDimension;j++) {
                if(matrix.get(i, j)>=0.5) {
                    rounded.set(i, j, 1d);
                } 
            }
        }
        return rounded;
    }
    
    public SquareMatrix goodForm(SquareMatrix matrix) {
        return toOrderN(roundMatrix(matrix));
    }
    
    //Checks if the tetravex matrix satisfies all constraints
    public boolean checkTetravex(SquareMatrix iterate) {
        SquareMatrix tetravex = toOrderN(roundMatrix(iterate));
        
        //Check if there is every position is occupied by a tile
        for(int i=0;i<tetraDimension;i++) {
            for(int j=0;j<tetraDimension;j++) {
                if(tetravex.get(i, j) == 0.0) {
                    return false;
                }
            }
        }
        //Check if every tile is used exactly once
        int frequence[] = new int[reprDimension+1];
        for(int i=0;i<tetraDimension;i++) {
            for(int j=0;j<tetraDimension;j++) {
                ++frequence[(int)tetravex.get(i, j)];
                if(frequence[(int)tetravex.get(i, j)]>1) {
                    return false;
                }
            }
        }
        
        //Check if every horizontal constraint is satisfied
        for(int i=0;i<tetraDimension;i++) {
            for(int j=1;j<tetraDimension;j++) {
                if(!allowedHorizontal(tiles.get((int)(tetravex.get(i,j-1)-1)),
                                     tiles.get((int)(tetravex.get(i, j)-1)))) {
                    return false;
                }
            }
        }
        
        //Check if every vertical constraint is satisfied
        for(int j=0;j<tetraDimension;j++) {
            for(int i=1;i<tetraDimension;i++) {
                if(!allowedVertical(tiles.get((int)(tetravex.get(i-1,j)-1)),
                                    tiles.get((int)(tetravex.get(i, j)-1)))) {
                    return false;
                }
            }
        }
        
        return true;
    }
    
    //Computes the projection on the diagonal space
    public SquareMatrix projectionDelta(Tuple tuple) {
        SquareMatrix projectionDelta = matrixOps.addMatrix(tuple.getFirst(), 
                                                           tuple.getSecond());
        for(int i=0;i<tuple.getThird().size();i++) {
            projectionDelta = matrixOps.addMatrix(projectionDelta, 
                                                  tuple.getThird().get(i));
        }
        double nominator = 1.0/(2.0*(Math.pow(tetraDimension,2)-tetraDimension+1.0));
        return matrixOps.scalarMultiplication(nominator, projectionDelta);
    }
    
    public SquareMatrix projectionC1(SquareMatrix matrix) {
        SquareMatrix projection = new SquareMatrix(reprDimension);
        for(int i=0;i<reprDimension;i++) {
            int maxPoz = 0;
            for(int j=0;j<reprDimension;j++) {
                if(matrix.get(i, maxPoz)<matrix.get(i, j)){
                    maxPoz = j;
                }
            }
            projection.set(i, maxPoz, 1.0);
        }
        return projection;
    }
    
    public SquareMatrix projectionC2(SquareMatrix matrix) {
        SquareMatrix projection = new SquareMatrix(reprDimension);
        for(int j=0;j<reprDimension;j++) {
            int maxPoz = 0;
            for(int i=0;i<reprDimension;i++) {
                if(matrix.get(maxPoz, j)<matrix.get(i, j)){
                    maxPoz = i;
                }
            }
            projection.set(maxPoz, j, 1.0);
        }
        return projection;
    }
    
    public List<SquareMatrix> projectionC3(List<SquareMatrix> matrices) {
        //Horizontal constraints projection
        int counter = 0;
        for(int i=0;i<reprDimension;i++) {
            if((i+1)%tetraDimension != 0) {
                SquareMatrix projection = horizontalProjection(i,i+1,matrices.get(counter));
                matrices.set(counter, projection);
                ++counter;
            }
        }
        
        //Vetrtical constraints projection
        for(int i=0;i<tetraDimension*(tetraDimension-1);i++) {
            SquareMatrix projection = verticalProjection(i,i+tetraDimension,matrices.get(counter));
            matrices.set(counter, projection);
            ++counter;
        }
        return matrices;
    }
    
    public SquareMatrix firstIterate(Tuple tuple) {
        SquareMatrix diagProjection = projectionDelta(tuple);
        SquareMatrix temp = matrixOps.scalarMultiplication(2.0, diagProjection);
        SquareMatrix temp2 = matrixOps.scalarMultiplication(-1.0, tuple.getFirst());
        temp = projectionC1(matrixOps.addMatrix(temp, temp2));
        temp2 = matrixOps.addMatrix(tuple.getFirst(), 
                matrixOps.scalarMultiplication(-1.0, diagProjection));
        return matrixOps.addMatrix(temp, temp2);
    }
    
    public SquareMatrix secondIterate(Tuple tuple) {
        SquareMatrix diagProjection = projectionDelta(tuple);
        SquareMatrix temp = matrixOps.scalarMultiplication(2.0, diagProjection);
        SquareMatrix temp2 = matrixOps.scalarMultiplication(-1.0, tuple.getSecond());
        temp = projectionC2(matrixOps.addMatrix(temp, temp2));
        temp2 = matrixOps.addMatrix(tuple.getSecond(), 
                matrixOps.scalarMultiplication(-1.0, diagProjection));
        return matrixOps.addMatrix(temp, temp2);
    }
    
    public List<SquareMatrix> thirdIterate(Tuple tuple) {
        SquareMatrix diagProjection = projectionDelta(tuple);
        SquareMatrix temp = matrixOps.scalarMultiplication(2.0, diagProjection);
        List<SquareMatrix> temp2 = matrixOps.scalarMultiplication(-1.0, tuple.getThird());
        temp2 = projectionC3(matrixOps.addMatrix(temp, temp2));
        List<SquareMatrix> temp3 = matrixOps.addMatrix(matrixOps.scalarMultiplication(-1.0, diagProjection),
                                                       tuple.getThird());
        
        return matrixOps.addMatrix(temp2, temp3);
    }
    
}
