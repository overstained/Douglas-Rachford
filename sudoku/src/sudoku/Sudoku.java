package sudoku;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JFrame;
import javax.swing.JPanel;

class SudokuRender extends JPanel {
    private SquareMatrix sudoku;
    private DouglasRachfordUtils dRUtils;
    private SquareMatrixOperations squareMatrixOps;
    private CubeMatrixOperations cubeMatrixOps;
    public void init(int dimension) {
        dRUtils = new DouglasRachfordUtils(dimension);
        squareMatrixOps = new SquareMatrixOperations(dimension);
        cubeMatrixOps = new CubeMatrixOperations(dimension);
        sudoku = dRUtils.initialConstraint();
    }
    
    public void solveSudoku() throws InterruptedException {
        SquareMatrix initialProblem = dRUtils.initialConstraint();
        CubeMatrix startingMatrix = dRUtils.startingMatrix(initialProblem);
        Tuple tupleOld = new Tuple(cubeMatrixOps.cloneCubeMatrix(startingMatrix),
                                   cubeMatrixOps.cloneCubeMatrix(startingMatrix),
                                   cubeMatrixOps.cloneCubeMatrix(startingMatrix),
                                   cubeMatrixOps.cloneCubeMatrix(startingMatrix));
        CubeMatrix diagonalProjectionOld = dRUtils.diagonalProjection(tupleOld);
        int iterations = 1;    
        boolean isSolution = false;
        while(!isSolution) {
            CubeMatrix firstIterateComponent  = 
                                   dRUtils.firstIterateComponent(tupleOld);
            CubeMatrix secondIterateComponent  = 
                                    dRUtils.secondIterateComponent(tupleOld);
            CubeMatrix thirdIterateComponent  = 
                                    dRUtils.thirdIterateComponent(tupleOld);
            CubeMatrix fourthIterateComponent  = 
                                    dRUtils.fourthIterateComponent(tupleOld,
                                                                  initialProblem);
            Tuple tuple = new Tuple(firstIterateComponent,
                                    secondIterateComponent,
                                    thirdIterateComponent,
                                    fourthIterateComponent);
            CubeMatrix diagonalProjection = dRUtils.diagonalProjection(tuple);
            
            isSolution = 
              dRUtils.checkSudoku(dRUtils.flattenCube(diagonalProjection),
                                  initialProblem);
            tupleOld=tuple;
            diagonalProjectionOld = diagonalProjection;
            ++iterations;
            sudoku = dRUtils.flattenCube(diagonalProjection);
            Thread.sleep(50);
            this.repaint();
        }
        
        System.out.println("Solved in " +
                            (iterations-1) +
                           " iterations.");
    }
    
    public void drawFrame(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, 576, 576);
        g2.setColor(Color.BLACK);
        for(int i=64;i<576;i+=64) {
            g2.drawLine(0, i, 576, i);
            g2.drawLine(i, 0, i, 576);
        }
        g2.setStroke(new BasicStroke(5));
        for(int i=192;i<576;i+=192) {
            g2.drawLine(0, i, 576, i);
            g2.drawLine(i, 0, i, 576);
        }
    }
    
    public void highlightCheck(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                                                    0.5f));
        int [] frequenceVector = new int[10];
        
        for(int i=0;i<9;i++) {
             dRUtils.initFrequenceVector(frequenceVector);
             boolean isGood = true;
             for(int j=0;j<9;j++) {
                 int componentValue = sudoku.get(i, j).intValue();
                 ++frequenceVector[componentValue];
                 if(frequenceVector[componentValue]>1) {
                     isGood=false;
                 }
             }
             if(isGood) {
                 g2d.setColor(Color.BLUE);
                 g2d.fillRect(0, 64*i, 576, 64*(i+1));
             } else {
                 g2d.setColor(Color.WHITE);
                 g2d.fillRect(0, 64*i, 576, 64*(i+1));
             }
        }
         
        for(int i=0;i<9;i++) {
             dRUtils.initFrequenceVector(frequenceVector);
             boolean isGood = true;
             for(int j=0;j<9;j++) {
                 int componentValue = sudoku.get(j, i).intValue();
                 ++frequenceVector[componentValue];
                 if(frequenceVector[componentValue]>1) {
                     isGood = false;
                 }
             }
             if(isGood) {
                 g2d.setColor(Color.BLUE);
                 g2d.fillRect(64*i, 0, 64*(i+1), 576);
             } else {
                 g2d.setColor(Color.WHITE);
                 g2d.fillRect(64*i, 0, 64*(i+1), 576);
             }
         }
         
         for(int squareRow=0;squareRow<9;squareRow+=3) {
             for(int squareColumn=0;squareColumn<9;squareColumn+=3) {
                 boolean isGood = true;
                 dRUtils.initFrequenceVector(frequenceVector);
                 for(int i=squareRow;i<squareRow+3;i++) {
                     for(int j=squareColumn;j<squareColumn+3;j++) {
                         int componentValue = sudoku.get(i, j).intValue();
                         ++frequenceVector[componentValue];
                         if(frequenceVector[componentValue]>1) {
                             isGood = false;
                         }
                     }
                 }
                 if(isGood) {
                     g2d.setColor(Color.BLUE);
                     g2d.fillRect(64*squareRow, 
                                  64*squareColumn, 
                                  64*(squareRow+3), 
                                  64*(squareColumn+3));
                 } else {
                     g2d.setColor(Color.WHITE);
                     g2d.fillRect(64*squareRow, 
                                  64*squareColumn, 
                                  64*(squareRow+3), 
                                  64*(squareColumn+3));
                 }
             }
         }
    }
    
    public void drawSudokuNumbers(Graphics g) {
        g.setFont(new Font("TimesRoman", Font.PLAIN, 60));
        for(int i=0;i<9;i++) {
            for(int j=0;j<9;j++) {
                if(sudoku.get(i, j) > 0d) {
                    g.drawString(String.valueOf(sudoku.get(i, j).intValue()), 
                                 i*64+16, 
                                 j*64+55);
                }
            }
        }
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        highlightCheck(g);
        drawFrame(g);
        drawSudokuNumbers(g);
    }
    
}


public class Sudoku {
    
    public static void main(String[] args) throws InterruptedException {
         JFrame frame = new JFrame("Sudoku");
        SudokuRender s=new SudokuRender();
        s.init(9);
        frame.add(s);
        frame.setSize(590, 615);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        s.solveSudoku();
    }
    
}
