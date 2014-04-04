/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Sort;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author andrey
 */
public class Element {
    public static final int rad=40;
    private int value;
    private int x;
    private int y;
    
    public Element(int cx,int cy,int val){
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
        Point pnt = new Point(x,y);
        Shape s = createStar(5,pnt,rad,rad/2);
        Graphics2D g2d = ( Graphics2D ) g;
        g2d.draw(s);
            //Получение размеров строки в пикселях
        String tmp = Integer.toString(value);
        FontRenderContext context = g2d.getFontRenderContext();
        Font f = g.getFont(); 
        Rectangle2D bounds = f.getStringBounds(tmp,context);        
        int stringWidth = (int)bounds.getWidth(); // Ширина строки
        int stringHeight = (int)bounds.getHeight(); // Высота
            //Отрисовка строки посередине звезды
        g.drawString(tmp,x- stringWidth/2, y + stringHeight/2);
        
        /*g.clearRect(x-4, y-4, rad+8, rad+8);
        g.drawOval(x, y, rad, rad);
        String tmp = Integer.toString(value);
        Graphics2D g2d = ( Graphics2D ) g;
        FontRenderContext context = g2d.getFontRenderContext();
        Font f = g.getFont(); 
        Rectangle2D bounds = f.getStringBounds(tmp,context);        
        int stringWidth = (int)bounds.getWidth(); // Ширина строки
        int stringHeight = (int)bounds.getHeight(); // Высота
        g.drawString(Integer.toString(value),(x+rad/2)- stringWidth/2, (y+rad/2) + stringHeight/2);*/
    }

    public int getValue() {
        return value;
    }
    public void setColor(Graphics g,Color c){
        g.setColor(c);
        this.paint(g);
    }
    public int compare(Element obj,Graphics g){
        this.setColor(g, Color.green);
        obj.setColor(g, Color.green);
        try {
            Thread.sleep(200);
        } catch (InterruptedException ex) {
            Logger.getLogger(Element.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setColor(g, Color.black);
        obj.setColor(g, Color.black);
        if (this.value>obj.value)
            return 1;
        else
            return -1;
    }
    public void move(Graphics g,int dx, int dy){  
        x += dx;
        y += dy;
        try {
            g.clearRect(x-rad-2, y-rad-3, 2*rad+6, 2*rad+6);
            this.paint(g);
            Thread.sleep(5);
        } catch (InterruptedException ex) {
            Logger.getLogger(Element.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
