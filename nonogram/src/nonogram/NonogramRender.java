
package nonogram;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;

class Renderer extends JPanel{
    private final Nonogram nonogram;
    private final MatrixOperations matrixOps;
    private final DouglasRachfordUtils dougRachUtils;
    private Matrix toDraw;
    
    public Renderer(String fileName) {
        nonogram = new Nonogram(fileName); //test.txt
        matrixOps = new MatrixOperations(nonogram.getDimensionR(),
                                         nonogram.getDimensionC());
        dougRachUtils = new DouglasRachfordUtils(nonogram.getRConstraints(),
                                                 nonogram.getCConstraints());
    }
    
    private int findMaxSize(List<List<Integer>> list) {
        int maxSize = 0;
        for(List<Integer> subList: list) {
            if(subList.size()>maxSize) {
                maxSize = subList.size();
            }
        }
        return maxSize;
    }
    
    private void buildFrame(Graphics g) {
        Graphics2D g2d=(Graphics2D) g;
        int mMax = findMaxSize(nonogram.getCConstraints()),
            nMax = findMaxSize(nonogram.getRConstraints()),
            unitSize = Math.min(600/(nMax+nonogram.getDimensionC()), 
                                600/(mMax+nonogram.getDimensionR()));
        
        g2d.setColor(Color.BLUE);
        g2d.setStroke(new BasicStroke(1));
        for(int i=nMax*unitSize;i+unitSize<=600;i+=unitSize) {
            g2d.drawLine(i, 0, i, 600);
        }
        for(int i=unitSize;(i+unitSize)<=(mMax*unitSize);i+=unitSize) {
            g2d.drawLine(nMax*unitSize, i, 600, i);
        }
        for(int i=unitSize;i+unitSize<=nMax*unitSize;i+=unitSize) {
            g2d.drawLine(i, mMax*unitSize, i, 600);
        }
        for(int i=mMax*unitSize;i+unitSize<=600;i+=unitSize) {
            g2d.drawLine(0, i, 600, i);
        }
        
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRect(nMax*unitSize, 0, 600-nMax*unitSize, mMax*unitSize);
        g2d.drawRect(0, mMax*unitSize, nMax*unitSize, 600-mMax*unitSize);
        g2d.drawRect(nMax*unitSize, mMax*unitSize, 600-nMax*unitSize, 600-mMax*unitSize);
        
        g.setFont(new Font("TimesRoman", Font.PLAIN, unitSize/2));
        List<List<Integer>> r_constraints = nonogram.getRConstraints();
        for(int i=0;i<r_constraints.size();i++) {
            List<Integer> currentRow = r_constraints.get(i);
            for(int j=currentRow.size()-1;j>=0;j--) {
                g2d.drawString(currentRow.get(j).toString(), (nMax-j-1)*unitSize+unitSize/2, 
                              (i+mMax+1)*unitSize-unitSize/3);
            }
        }
        
        List<List<Integer>> c_constraints = nonogram.getCConstraints();
        for(int i=0;i<c_constraints.size();i++) {
            List<Integer> currentColumn = c_constraints.get(i);
            for(int j=currentColumn.size()-1;j>=0;j--) {
                g2d.drawString(currentColumn.get(j).toString(), (nMax+i)*unitSize+unitSize/2,
                               (mMax-j)*unitSize-unitSize/3);
            }
        }
    }
    
    private void drawMatrix(Graphics g) {
        int mMax = findMaxSize(nonogram.getCConstraints()),
            nMax = findMaxSize(nonogram.getRConstraints()),
            unitSize = Math.min(600/(nMax+nonogram.getDimensionC()), 
                                600/(mMax+nonogram.getDimensionR()));
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 800, 800);
        g.setColor(Color.BLACK);
        for(int i=0;i<nonogram.getDimensionR();i++) {
            for(int j=0;j<nonogram.getDimensionC();j++) {
                if(toDraw.get(i, j)==1.0) {
                    g.fillRect((nMax+j)*unitSize, (mMax+i)*unitSize, unitSize, unitSize);
                }
            }
        }
    }
    
    public void solveNonogram() {
        int iterations = 0;
        Matrix iterate = matrixOps.someMatrix();
        toDraw = iterate;
        Matrix projectionC1old = dougRachUtils.projectionC1(iterate);
        
        while(!nonogram.checkNonogram(projectionC1old)) {
            Matrix temp = matrixOps.scalarMultiplication(2.0, projectionC1old);
            Matrix temp2 = matrixOps.scalarMultiplication(-1.0, iterate);
            temp = dougRachUtils.projectionC2(matrixOps.addition(temp, temp2));
            temp2 = matrixOps.addition(iterate, 
                    matrixOps.scalarMultiplication(-1.0, projectionC1old));
            iterate = matrixOps.addition(temp, temp2);
            projectionC1old = dougRachUtils.projectionC1(iterate);
            try {
                Thread.sleep(200);
            } catch(InterruptedException ie) {
                ie.printStackTrace();
            }
            toDraw=projectionC1old;
            this.repaint();
            ++iterations;
        }   
        
        System.out.println("Solved in " +
                            iterations + 
                           " iterations! ");
       
        
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(toDraw!=null) {
            drawMatrix(g);
        }
        buildFrame(g);   
    }
}


public class NonogramRender {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Nonogram");
        Renderer render=new Renderer("test2.txt");
        frame.add(render);
        frame.setSize(618, 640);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        render.solveNonogram();
    }
}