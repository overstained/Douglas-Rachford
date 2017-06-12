
package tetravex;


public class Pair<T1,T2>{
    private T1 firstComponent;
    private T2 secondComponent;
    public Pair(T1 firstComponent,
                T2 secondComponent) {
        this.firstComponent = firstComponent;
        this.secondComponent = secondComponent;
    }
    
    public T1 getFirst() {
        return firstComponent;
    }
    
    public T2 getSecond() {
        return secondComponent;
    }
    
    @Override
    public String toString() {
        return "(" + firstComponent + " " + secondComponent + ")";
    }
}
