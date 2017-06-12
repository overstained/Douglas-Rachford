package tetravex;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;


class Render extends JPanel{
    private final int WIDTH;
    private final int HEIGHT;
    private final DouglasRachfordUtils dougRachUtils;
    private final List<Color> colors;
    private SquareMatrix toDraw;
    public Render(String fileName, int WIDTH, int HEIGHT) {
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        dougRachUtils = new DouglasRachfordUtils(fileName);
        colors = generateRandomColors();
    }
    
    private List<Color> generateRandomColors() {
        List<Color> colors = new LinkedList<>();
        Random random = new Random();
        for(int i=0;i<dougRachUtils.getReprDimension();i++) {
            colors.add(new Color(random.nextFloat(),
                                 random.nextFloat(),
                                 random.nextFloat()));
        }
        return colors;
    }
    
    public void solveTetravex() {
        SquareMatrix firstIterateOld = dougRachUtils.identity();
        SquareMatrix secondIterateOld = dougRachUtils.identity();
        List<SquareMatrix> thirdIterateOld = new LinkedList<>();
        int size = dougRachUtils.getTetraDimension();
        for(int i=0;i<(2*size*(size-1));i++) {
            thirdIterateOld.add(dougRachUtils.identity());
        }
        Tuple tupleOld = new Tuple(firstIterateOld,
                                   secondIterateOld,
                                   thirdIterateOld);
        SquareMatrix projectionDeltaOld = dougRachUtils.projectionDelta(tupleOld);
        toDraw = dougRachUtils.goodForm(projectionDeltaOld);
        int iterations = 0;
        while(!dougRachUtils.checkTetravex(projectionDeltaOld)) {
            SquareMatrix firstIterate = dougRachUtils.firstIterate(tupleOld);
            SquareMatrix secondIterate = dougRachUtils.secondIterate(tupleOld);
            List<SquareMatrix> thirdIterate = dougRachUtils.thirdIterate(tupleOld);
            SquareMatrix projectionDelta = dougRachUtils.projectionDelta(tupleOld);
            tupleOld=new Tuple(firstIterate,
                               secondIterate,
                               thirdIterate);
            projectionDeltaOld = projectionDelta;
            try {
                Thread.sleep(200);
            } catch(InterruptedException ie) {
            }
            toDraw = dougRachUtils.goodForm(projectionDeltaOld);
            this.repaint();
            ++iterations;
        }
        System.out.println("Solved in " +
                            iterations  +
                          " iterations!");
    }
    
    private void drawTiles(Graphics g) {
        if(toDraw == null) {
            return;
        }
        Graphics2D g2D = (Graphics2D) g;
        g2D.setColor(Color.WHITE);
        g2D.fillRect(0, 0, WIDTH, HEIGHT);
        List<Tile> tiles = dougRachUtils.getTiles();
        int unitSize = WIDTH/dougRachUtils.getTetraDimension();
        int x[] = new int[3];
        int y[] = new int[3];
        for(int i=0;i<dougRachUtils.getTetraDimension();i++) {
            for(int j=0;j<dougRachUtils.getTetraDimension();j++) {
                int xTranslation = j*unitSize;
                int yTranslation = i*unitSize;
                if(toDraw.get(i,j)!=0.0) {
                Tile tile = tiles.get((int)(toDraw.get(i, j)-1));
                g2D.setFont(new Font("TimesRoman", Font.PLAIN, unitSize/4));
                g2D.setColor(colors.get(tile.getNorth()-1));
                x[0]=xTranslation;
                x[1]=xTranslation+unitSize/2;
                x[2]=xTranslation+unitSize;
                y[0]=y[2]=yTranslation;
                y[1]=yTranslation+unitSize/2;
                g2D.fillPolygon(x, y, 3);
                g2D.setColor(Color.BLACK);
                g2D.setStroke(new BasicStroke(2));
                g2D.drawPolygon(x, y, 3);
                g2D.setColor(Color.WHITE);
                g2D.drawString(String.valueOf(tile.getNorth()), 
                               xTranslation+9*unitSize/20, 
                               yTranslation+unitSize/4);
                
                g2D.setColor(colors.get(tile.getEast()-1));
                x[0]=x[2]=xTranslation+unitSize;
                x[1]=xTranslation+unitSize/2;
                y[0]=yTranslation;
                y[1]=yTranslation+unitSize/2;
                y[2]=yTranslation+unitSize;
                g2D.fillPolygon(x, y, 3);
                g2D.setColor(Color.BLACK);
                g2D.setStroke(new BasicStroke(2));
                g2D.drawPolygon(x, y, 3);
                g2D.setColor(Color.WHITE);
                g2D.drawString(String.valueOf(tile.getEast()), 
                               xTranslation+3*unitSize/4, 
                               yTranslation+3*unitSize/5);
                
                g2D.setColor(colors.get(tile.getSouth()-1));
                x[0]=xTranslation+unitSize;
                x[1]=xTranslation+unitSize/2;
                x[2]=xTranslation;
                y[0]=y[2]=yTranslation+unitSize;
                y[1]=yTranslation+unitSize/2;
                g2D.fillPolygon(x, y, 3);
                g2D.setColor(Color.BLACK);
                g2D.setStroke(new BasicStroke(2));
                g2D.drawPolygon(x, y, 3);
                g2D.setColor(Color.WHITE);
                g2D.drawString(String.valueOf(tile.getSouth()), 
                               xTranslation+9*unitSize/20, 
                               yTranslation+9*unitSize/10);
                
                g2D.setColor(colors.get(tile.getWest()-1));
                x[0]=xTranslation;
                x[1]=xTranslation+unitSize/2;
                x[2]=xTranslation;
                y[0]=yTranslation;
                y[1]=yTranslation+unitSize/2;
                y[2]=yTranslation+unitSize;
                g2D.fillPolygon(x, y, 3);
                g2D.setColor(Color.BLACK);
                g2D.setStroke(new BasicStroke(2));
                g2D.drawPolygon(x, y, 3);
                g2D.setColor(Color.WHITE);
                g2D.drawString(String.valueOf(tile.getWest()), 
                               xTranslation+unitSize/8, 
                               yTranslation+3*unitSize/5);
                }
            }
        }
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawTiles(g);
    }
}


public class Tetravex {

    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Tetravex");
        Render render = new Render("test.txt",600,600);
        frame.add(render);
        frame.setSize(600, 630);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        render.solveTetravex();
    }
    
}
