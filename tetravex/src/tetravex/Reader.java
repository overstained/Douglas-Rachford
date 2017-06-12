package tetravex;

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
            fnf.printStackTrace();
        }
    }
    
    public List<Tile> readTiles() {
        List<Tile> tiles = new LinkedList<>();
        try {
           int noTiles = Integer.parseInt(bufferedReader.readLine());
           for(int i=0;i<noTiles;i++) {
               StringTokenizer tokens = new StringTokenizer(bufferedReader.readLine());
               if(tokens.countTokens() == 4) {
                   Tile tile = new Tile(Integer.parseInt(tokens.nextToken()),
                                        Integer.parseInt(tokens.nextToken()),
                                        Integer.parseInt(tokens.nextToken()),
                                        Integer.parseInt(tokens.nextToken()));
                   tiles.add(tile);
               }
           }
           if(tiles.size() != noTiles) {
               throw new IllegalArgumentException();
           }
        } catch (IOException ioe) {
        } catch (IllegalArgumentException iae) {
            return null;
        }
        return tiles;
    }
}
