/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Sort;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.font.FontRenderContext;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 * @author andrey
 */
public class Element extends JPanel{
    public static final int rad=40;
    BufferedImage bi;
    private int value;
    private int x;
    private int y;
    private Color background;
    public static int sleeptime=10; 
    
    public Element(int cx,int cy,int val,Color background){
        value = val;
        x=cx;
        y=cy;
        this.background = background;
        bi = new BufferedImage(rad*2,rad*2,BufferedImage.TYPE_4BYTE_ABGR);
    }
    
    public static Shape createStar(int arms, Point center, double rOuter, double rInner) {
        double angle = Math.PI / arms;

        GeneralPath path = new GeneralPath();

        for (int i = 0; i < 2 * arms; i++) {
            double r = (i & 1) == 0 ? rOuter : rInner;
            Point2D.Double p = new Point2D.Double(center.x + Math.cos(i * angle) * r, center.y + Math.sin(i * angle) * r);
            if (i == 0) {
                path.moveTo(p.getX(), p.getY());
            } else {
                path.lineTo(p.getX(), p.getY());
            }
        }
        path.closePath();
        return path;
    }
    //рисование звездочек на буфере
    public void paint(Graphics g){
        Graphics2D big = (Graphics2D)bi.getGraphics();
        big.setColor(background);
        big.fillRect(0, 0, rad*2, rad*2);
        big.setColor(g.getColor());      
        //толщина линий
        Stroke newStroke = new BasicStroke ( 2f );        
        big.setStroke (newStroke);
        //сглаживание
        big.setRenderingHint (RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );       
        Point pnt = new Point(rad, rad);
        //отрисовка звездочек
        Shape s = createStar(5, pnt, rad-4, (rad-4) / 2);
        big.draw(s);
        //Получение размеров строки в пикселях
        String tmp = Integer.toString(value);
        FontRenderContext context = big.getFontRenderContext();
        Font f = big.getFont();
        Rectangle2D bounds = f.getStringBounds(tmp, context);
        int stringWidth = (int) bounds.getWidth(); // Ширина строки
        int stringHeight = (int) bounds.getHeight(); // Высота
        //Отрисовка строки посередине звезды
        big.drawString(tmp, rad - stringWidth / 2, rad + stringHeight / 2);
    }

    @Override
    public void paintComponent(Graphics g) {
        paint(g);       
        //рисование буфера на форме
        g.drawImage(bi, x-rad, y-rad, this);
    }
    
    public void setColor(Graphics g,Color c){
        g.setColor(c);
        this.paintComponent(g);
    }
    
    public void move(Graphics g, int dx, int dy) throws InterruptedException {
        x += dx;
        y += dy;
        this.paintComponent(g);
        Thread.sleep(sleeptime);

    }

    public int getValue() {
        return value;
    }
}
