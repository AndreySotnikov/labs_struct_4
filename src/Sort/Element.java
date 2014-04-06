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
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.font.FontRenderContext;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import javax.swing.DebugGraphics;
import javax.swing.JPanel;
import javax.swing.RepaintManager;

/**
 *
 * @author andrey
 */
public class Element extends JPanel{
    public static final int rad=40;
    BufferedImage bi = new BufferedImage(rad*2,rad*2,BufferedImage.TYPE_4BYTE_ABGR);
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
    
    public void paint(Graphics g){
        Graphics2D big = (Graphics2D)bi.getGraphics();
        big.setColor(background);
        big.fillRect(0, 0, rad*2, rad*2);
        big.setColor(g.getColor());      
        Stroke newStroke = new BasicStroke ( 2f );        
        big.setStroke (newStroke);
        big.setRenderingHint (RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );       
        Point pnt = new Point(rad, rad);
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
        //g.clearRect(x - rad, y - rad-2, 2 * rad+4, 2 * rad+4);
        paint(g);       
        g.drawImage(bi, x-rad, y-rad, this);
        
       /* String tmp = Integer.toString(value);
        Graphics2D g2d = (Graphics2D) g;
        FontRenderContext context = g2d.getFontRenderContext();
        Font f = g.getFont();
        Rectangle2D bounds = f.getStringBounds(tmp, context);
        int stringWidth = (int) bounds.getWidth(); // Ширина строки
        int stringHeight = (int) bounds.getHeight(); // Высота
        //Отрисовка строки посередине звезды
        g.drawString(tmp, x - stringWidth / 2, y + stringHeight / 2);*/
        
    /*    g.clearRect(x - rad - 1, y - rad - 2, 2 * rad + 5, 2 * rad + 4);       
        Point pnt = new Point(x, y);
        Shape s = createStar(5, pnt, rad, rad / 2);
        Graphics2D g2d = (Graphics2D) g;
        g2d.draw(s);
        //Получение размеров строки в пикселях
        String tmp = Integer.toString(value);
        FontRenderContext context = g2d.getFontRenderContext();
        Font f = g.getFont();
        Rectangle2D bounds = f.getStringBounds(tmp, context);
        int stringWidth = (int) bounds.getWidth(); // Ширина строки
        int stringHeight = (int) bounds.getHeight(); // Высота
        //Отрисовка строки посередине звезды
        g.drawString(tmp, x - stringWidth / 2, y + stringHeight / 2);*/
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

/*    public Element(int cx,int cy,int val){
        value = val;
        x=cx;
        y=cy;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    public static Shape createStar(int arms, Point center, double rOuter, double rInner){
    double angle = Math.PI / arms;

    GeneralPath path = new GeneralPath();

    for (int i = 0; i < 2 * arms; i++)
    {
        double r = (i & 1) == 0 ? rOuter : rInner;
        Point2D.Double p = new Point2D.Double(center.x + Math.cos(i * angle) * r, center.y + Math.sin(i * angle) * r);
        if (i == 0) 
            path.moveTo(p.getX(), p.getY());
        else path.lineTo(p.getX(), p.getY());
    }
    path.closePath();
    return path;
}
    public void paint(Graphics g){
        g.clearRect(x-rad-1, y-rad-2, 2*rad+5, 2*rad+4);
        Point pnt = new Point(x,y);
        //Point pnt = new Point(x,y);
        Shape s = createStar(5,pnt,rad,rad/2);
        //Graphics2D g2d = ( Graphics2D ) gr;

        
        Graphics2D g2d = ( Graphics2D ) g;
        g2d.draw(s);
        //g.drawOval((int)pnt.getX()-rad, (int)pnt.getY()-rad, rad*2, rad*2);
            //Получение размеров строки в пикселях
        String tmp = Integer.toString(value);
        FontRenderContext context = g2d.getFontRenderContext();
        Font f = g.getFont(); 
        Rectangle2D bounds = f.getStringBounds(tmp,context);        
        int stringWidth = (int)bounds.getWidth(); // Ширина строки
        int stringHeight = (int)bounds.getHeight(); // Высота
            //Отрисовка строки посередине звезды
        g.drawString(tmp,x- stringWidth/2, y + stringHeight/2);
        
        
    }

    public int getValue() {
        return value;
    }
    public void setColor(Graphics g,Color c){
        g.setColor(c);
        this.paint(g);
    }
    
    public void move(Graphics g,int dx, int dy) throws InterruptedException{  
        //this.paint(g);
        //g.clearRect(x-rad-1, y-rad-2, 2*rad+5, 2*rad+4);
        //this.paint(g);

       // try {
                x += dx;
                y += dy;
            
            //g.clearRect(x-rad, y-rad-3, 2*rad+7, 2*rad+6);
            this.paint(g);
            Thread.sleep(5);
       // } catch (InterruptedException ex) {
            //Logger.getLogger(Element.class.getName()).log(Level.SEVERE, null, ex);
       // }

    } */
}
