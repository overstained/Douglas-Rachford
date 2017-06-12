package pkg8.queens;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;


class ChessBoard extends JPanel{
    private SquareMatrixOperations matOps;
    private DougRachUtils drUtil;
    private SquareMatrix chessBoard;
    private int dimension;
    public void EightQueens() throws InterruptedException{
        dimension = 8;
        matOps = new SquareMatrixOperations(dimension);
        drUtil= new DougRachUtils(dimension);
        SquareMatrix matrixInit = matOps.initRandomMatrix();
        SquareMatrix firstComponentOld = matOps.cloneMatrix(matrixInit);
        SquareMatrix secondComponentOld = matOps.cloneMatrix(matrixInit);
        SquareMatrix thirdComponentOld = matOps.cloneMatrix(matrixInit);
        SquareMatrix fourthComponentOld = matOps.cloneMatrix(matrixInit);
       
        boolean isSolution = false;
        int iterations = 0;
        while(!isSolution){
        
            SquareMatrix firstComponent = drUtil.firstIterateComponent(firstComponentOld,
                                                                       secondComponentOld, 
                                                                       thirdComponentOld, 
                                                                       fourthComponentOld);
            SquareMatrix secondComponent = drUtil.secondIterateComponent(firstComponentOld,
                                                                        secondComponentOld, 
                                                                        thirdComponentOld, 
                                                                        fourthComponentOld);
            
            SquareMatrix thirdComponent = drUtil.thirdIterateComponent(firstComponentOld,
                                                                       secondComponentOld, 
                                                                       thirdComponentOld, 
                                                                       fourthComponentOld);
            SquareMatrix fourthComponent = drUtil.fourthIterateComponent(firstComponentOld,
                                                                       secondComponentOld, 
                                                                       thirdComponentOld, 
                                                                       fourthComponentOld);
            SquareMatrix diagonalProjection = drUtil.projectionDelta(firstComponent,
                                                                     secondComponent, 
                                                                     thirdComponent, 
                                                                     fourthComponent);
           
            isSolution = drUtil.checkSolution(diagonalProjection);
            firstComponentOld = firstComponent;
            secondComponentOld = secondComponent;
            thirdComponentOld = thirdComponent;
            fourthComponentOld = fourthComponent;
            
            
            
            chessBoard = matOps.roundMatrix(diagonalProjection);
            this.repaint();
            Thread.sleep(200);
            System.out.println("Iteration " + iterations);
            matOps.printMatrix(firstComponent);
            ++iterations;
            
        }
    }
    
    @Override
    public void paintComponent(Graphics g) {
      super.paintComponent(g);
      
      int color=0;
      for(int i=0;i<dimension;i++) {
          for(int j=0;j<dimension;j++) {
              if(color == 0) {
                  g.setColor(Color.WHITE);
              } else {
                  g.setColor(Color.BLACK);
              }
              g.fillRect(i*64, j*64, 64, 64);
              color = 1-color;
          }
          color = 1-color;
      }
      if(chessBoard!=null) {
          for(int i=0;i<dimension;i++) {
              for(int j=0;j<dimension;j++) {
                  if(chessBoard.get(i, j)==1) {
                      g.setColor(Color.BLUE);
                      g.fillOval(i*64+10, j*64+10, 44, 44);
                  }
              }
          }
      }
    }
}

public class Queens {

    
    
    public static void main(String[] args) throws InterruptedException {
        JFrame frame = new JFrame("Chess");
        ChessBoard c=new ChessBoard();
        frame.add(c);
        frame.setSize(530, 545);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        c.EightQueens();
        
       
    }
    
}
