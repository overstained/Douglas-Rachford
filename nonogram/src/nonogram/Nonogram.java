package nonogram;

import java.util.List;


public class Nonogram {
    private int dimensionR;
    private int dimensionC;
    private List<List<Integer>> r_constraints;
    private List<List<Integer>> c_constraints;
    private DouglasRachfordUtils dRUtils;
    Nonogram(String fileName) {
        Reader reader = new Reader(fileName);
        Pair<List<List<Integer>>,List<List<Integer>>> pair = reader.getConstraints();
        r_constraints = pair.getFirst();
        c_constraints = pair.getSecond();
        dimensionR = r_constraints.size();
        dimensionC = c_constraints.size();
        dRUtils = new DouglasRachfordUtils(r_constraints,
                                           c_constraints);
    }
    
    public int getDimensionR() {
        return dimensionR;
    }
    
    public int getDimensionC() {
        return dimensionC;
    }
    
    public List<List<Integer>> getRConstraints() {
        return r_constraints;
    }
    
    public List<List<Integer>> getCConstraints() {
        return c_constraints;
    }
    
    //Returns "true" if the given nonogram respects the
    //inital constraints, "false" otherwise
    public boolean checkNonogram(Matrix nonogram) {
        
        //Check row constraints
        for(int i=0;i<dimensionR;i++) {
            int [] v = new int[dimensionC+1];
            List<Integer> list = r_constraints.get(i);
            for(int groupDimension: list) {
                ++v[groupDimension];
            }
            boolean inGroup = false;
            boolean outOfGroup = true;
            int counter = 0;
            for(int j=0;j<dimensionC;j++) {
                
                if(nonogram.get(i, j) == 1.0 && outOfGroup == true) {
                    inGroup = true;
                    outOfGroup = false;
                }
                
                if(inGroup == true && nonogram.get(i, j) == 0.0) {
                    outOfGroup = true;
                    inGroup = false;
                    --v[counter];
                    if(v[counter]<0) {
                        return false;
                    }
                    counter = 0;
                }
                if(inGroup) {
                    ++counter;
                }
            }
            --v[counter];
            for(int j=0;j<dimensionC;j++) {
                if(v[j]>0) {
                    
                    return false;
                }
            }
        }
        
        //Check column constraints
        for(int j=0;j<dimensionC;j++) {
            int [] v = new int[dimensionR+1];
            List<Integer> list = c_constraints.get(j);
            for(int groupDimension: list) {
                ++v[groupDimension];
            }
            boolean inGroup = false;
            boolean outOfGroup = true;
            int counter = 0;
            for(int i=0;i<dimensionR;i++) {
                
                if(nonogram.get(i, j) == 1.0 && outOfGroup == true) {
                    inGroup = true;
                    outOfGroup = false;
                }
                if(inGroup == true && nonogram.get(i, j) == 0.0) {
                    outOfGroup = true;
                    inGroup = false;
                    --v[counter];
                    if(v[counter]<0) {
                        return false;
                    }
                    counter = 0;
                }
                
                if(inGroup) {
                    ++counter;
                }
                
            }
            --v[counter];
            for(int i=0;i<dimensionR;i++) {
                if(v[i]>0) {
                    return false;
                }
            }
        }
        
        return true;
    }
     
}
