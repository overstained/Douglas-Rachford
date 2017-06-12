package nonogram;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;


public class Reader {
    private String         fileName;
    private BufferedReader bufferedReader;
    Reader(String fileName) {
        this.fileName = fileName;
        try {
           bufferedReader = new BufferedReader(new FileReader(fileName));
        } catch(FileNotFoundException fnf) {
            System.out.println("Eroare");
        }
    }
    
    public Pair<List<List<Integer>>,List<List<Integer>>> getConstraints() {
        try {
            List<List<Integer>> listOfRConstraints = new LinkedList<>();
            int dimensionR = Integer.valueOf(bufferedReader.readLine());
            StringTokenizer tokens;
            for(int i=0;i<dimensionR;i++) {
                tokens = new StringTokenizer(bufferedReader.readLine());
                List<Integer> listTuple = new LinkedList<>();
                while(tokens.hasMoreTokens()) {
                    listTuple.add(Integer.valueOf(tokens.nextToken()));
                }
                listOfRConstraints.add(listTuple);
            }
            
            List<List<Integer>> listOfCConstraints = new LinkedList<>();
            int dimensionC = Integer.valueOf(bufferedReader.readLine());
            for(int i=0;i<dimensionC;i++) {
                tokens = new StringTokenizer(bufferedReader.readLine());
                List<Integer> listTuple = new LinkedList<>();
                while(tokens.hasMoreTokens()) {
                    listTuple.add(Integer.valueOf(tokens.nextToken()));
                }
                listOfCConstraints.add(listTuple);
            }
            Pair<List<List<Integer>>,List<List<Integer>>> pair = 
                    new Pair<>(listOfRConstraints,
                               listOfCConstraints);
            return pair;
        } catch(IOException ioe) {
            System.out.println(ioe.getMessage());
        }
        return null;
    }
}
