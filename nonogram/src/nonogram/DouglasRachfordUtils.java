package nonogram;

import java.util.LinkedList;
import java.util.List;


public class DouglasRachfordUtils {
    private final int dimensionR;
    private final int dimensionC;
    private final List<List<Integer>> r_constraints;
    private final List<List<Integer>> c_constraints;
    private final MatrixOperations matrixOps;
    
    public DouglasRachfordUtils(List<List<Integer>> r_constraints,
                                List<List<Integer>> c_constraints) {
        this.r_constraints = r_constraints;
        this.c_constraints = c_constraints;
        this.dimensionR = r_constraints.size();
        this.dimensionC = c_constraints.size();
        matrixOps = new MatrixOperations(dimensionR,
                                         dimensionC);
    }
    
    //Clones a vector
    private Double[] clone(Double[] toClone) {
        Double[] result = new Double[toClone.length];
        for(int i=0;i<toClone.length;i++) {
            result[i] = toClone[i];
        }
        return result;
    }
    
    //Generates all possible vectors with a cluster of 1s of size
    //groupDimension
    public List<Double[]> generate(int groupDimension, 
                                    int vectorDimension) {
        if(groupDimension>vectorDimension) {
            return null;
        }
        List<Double[]> list = new LinkedList<>();
        Double [] result = new Double[vectorDimension];
        for(int i=0;i<vectorDimension;i++) {
            if(i<groupDimension) {
                result[i] = 1.0;    
            } else{
                result[i] = 0.0;
            }
        }
        list.add(result);
        for(int k=1;k<vectorDimension-groupDimension+1;k++) {
            result = clone(result);
            result[k-1]=0.0;
            result[k+groupDimension-1]=1.0;
            list.add(result);
        }
        return list;
    }
    
    
    //Combines vectors with different cluster sizes
    public  List<Double[]> combine(List<Double[]> first,
                                   List<Double[]> second) {
        List<Double[]> list = new LinkedList<>();
        for(Double[] v: first) {
            for(Double[] w: second) {
                Double[] result = new Double[v.length];
                for(int i=0;i<v.length;i++) {
                    result[i] = 0.0;
                }
                boolean goodResult = true;
                for(int i=0;i<v.length;i++) {
                    if(i==0) {
                        if(v[i]==1.0 && w[i+1] == 1.0) {
                            goodResult = false;
                        }
                    }
                    else if(i==v.length-1) {
                        if(v[i]==1.0 && w[i-1] == 1.0) {
                            goodResult = false;
                        }
                    } else {
                        if((v[i]==1.0 && w[i-1] ==1.0 )||
                           (v[i]==1.0 && w[i+1] == 1.0)) {
                            goodResult = false;
                        }
                    }
                    result[i]+=v[i]+w[i];
                    if(result[i]>1.0) {
                        goodResult = false;
                    }
                    if(!goodResult) {
                        break;
                    }
                }
                if(goodResult) {
                    list.add(result);
                }
            }
        }
        return list;
    }
    
    //Generates all possible vectors with given cluster-size sequence
    public List<Double[]> generate(List<Integer> list,
                                   int vectorDimension) {
        if(!list.isEmpty()) {
            List<Double[]> result = generate(list.get(0),
                                             vectorDimension);
            for(int i=1;i<list.size();i++) {
                result = combine (result,
                                  generate(list.get(i),
                                           vectorDimension));
            }
            return result;
        }
        return null;
    }
    
    //Computes the distance between two vectors
    public Double distance(Double [] X, Double [] Y) {
        Double sum = 0.0;
        for(int i=0;i<X.length;i++) {
            sum+=Math.pow((X[i]-Y[i]),2);
        }
        return Math.sqrt(sum);
    }
    
    //Returns the closest vector to X from a given set of vectors C
    public Double[] minDistance(Double [] X, List<Double[]> C) {
        if(!C.isEmpty()) {
            Double[] min = C.get(0);
            Double minDistance = distance(X,min);
            for(int i=1;i<C.size();i++) {
                Double distance = distance(X,C.get(i));
                if(distance < minDistance) {
                    minDistance = distance;
                    min = C.get(i);
                }
            }
            return min;
        }
        return null;
    }
    
    //Computes the projection of the current iterate on the C1 constraint set
    public Matrix projectionC1(Matrix matrix) {
       Matrix projection = new Matrix(dimensionR,dimensionC);
       for(int i=0;i<dimensionR;i++) {
           Double [] X = new Double[dimensionC];
           for(int j=0;j<dimensionC;j++) {
               X[j]=matrix.get(i, j);
           }
           Double [] minDistance = minDistance(X,generate(r_constraints.get(i)
                                                         ,dimensionC));
           for(int j=0;j<dimensionC;j++) {
               projection.set(i, j, minDistance[j]);
           }
           
       }
       return projection;
    }
    
    //Computes the projection of the current iterate on the C2 constraint set
    public Matrix projectionC2(Matrix matrix) {
       Matrix projection = new Matrix(dimensionR,dimensionC);
       for(int j=0;j<dimensionC;j++) {
           Double [] X = new Double[dimensionR];
           for(int i=0;i<dimensionR;i++) {
               X[i]=matrix.get(i, j);
           }
           Double [] minDistance = minDistance(X,generate(c_constraints.get(j)
                                                         ,dimensionR));
           for(int i=0;i<dimensionR;i++) {
               projection.set(i, j, minDistance[i]);
           }
       }
       return projection;
    }
    
}
