/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Sort;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * @author andrey
 */
public class Array {
    private Element[] ar;
    public static final int startx=50;
    public static final int starty=220;
    public Array(int len){
        ar = new Element[len];
    }
    public Array(ArrayList<Integer> a,Graphics g){
        ar = new Element[a.size()];
        for (int i = 0; i<a.size();i++){
            Element tmp = new Element(startx+2*Element.rad*i,starty,a.get(i));
            ar[i] = tmp;
            ar[i].paint(g);
        }
    }
    public Element get(int i){
        return ar[i];
    }
    protected ArrayList<Integer> fib(int n){
        ArrayList<Integer> dp = new ArrayList();
        dp.add(0, 0);
        dp.add(1, 1);
        for (int i = 2; dp.get(i-1) + + dp.get(i-2) < n; i++){
            dp.add(i,dp.get(i - 1) + dp.get(i-2));
        }
        return dp;
    }
    public void sort_shell(Graphics g) throws InterruptedException{
        int N = ar.length;
        int d,step;
        ArrayList<Integer> f = fib(N);
        for(d = f.size()-1; d > 1; d--){
            step = f.get(d);
            for (int i=0;i<N;i++)
                ar[i].setColor(g, Color.black);
            for(int p = 0;p<step;p++){
                group_sort(N,step,p,g);
            }
        }
    }
    private void move_group(int N, int step, int p, Graphics g, int dy) throws InterruptedException {
        int j = p;
        while (j < N) {
            for (int k = 0; k < Element.rad; k++) {
                ar[j].move(g, 0, dy);
            }
            j = j + step;
        }
        Thread.sleep(200);
    }
    
    private void move_horizontal(Element el1,Element el2,Graphics g,int step){
                for (int t = 0; t < Element.rad*step; t++){
                    el1.move(g, -2, 0);
                    el2.move(g, 2, 0);
                }        
    }
    
    private void move_vertikal(Element elem,Graphics g,int val){
            elem.setColor(g, Color.green);
            for (int t = 0; t < Element.rad; t++){
                elem.move(g, 0, val);
            }       
    }
    
    public void group_sort(int N, int step, int p, Graphics g) throws InterruptedException {
        move_group(N, step, p, g,2);
        ar[p].setColor(g, Color.red);
        int i = step + p;
        while (i < N) {
            Element elem = ar[i];
            elem.setColor(g, Color.green);
            move_vertikal(elem,g,2);
            int l = i - step;
            while (l >= 0 && ar[l].getValue()>elem.getValue()) {
                move_horizontal(elem,ar[l],g,step);
                ar[l + step] = ar[l];
                l = l - step;
            }
            ar[l + step] = elem;
            move_vertikal(elem,g,-2);
            elem.setColor(g, Color.red);
            i = i + step;
        }
        move_group(N,step,p,g,-2);
    }


}
