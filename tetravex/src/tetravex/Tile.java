package tetravex;


public class Tile {
    private final int north;
    private final int east;
    private final int south;
    private final int west;
    public Tile(int north, 
                int east, 
                int south, 
                int west) {
        this.north = north;
        this.east = east;
        this.south = south;
        this.west = west;
    }
    
    public int getNorth() {
        return north;
    }
    
    public int getEast() {
        return east;
    }
    
    public int getSouth() {
        return south;
    }
    
    public int getWest() {
        return west;
    }
}
