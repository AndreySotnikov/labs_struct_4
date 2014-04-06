/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Sort;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author andrey
 */
public class Array {
    private Element[] ar;
    private Graphics g;
    public static final int size=10;
    public static final int startx=50;
    public static final int starty=220;
    

    public Array(Graphics g){
        ar = new Element[size];
        this.g = g;
        Random rand = new Random();
        for (int i = 0;i < size; i++){
            Element tmp = new Element(startx+2*Element.rad*i,starty,rand.nextInt(100));
            ar[i] = tmp;
            ar[i].paintComponent(g);
        }
    }

    public Element get(int i){
        return ar[i];
    }
    protected ArrayList<Integer> fib(int n){
        ArrayList<Integer> dp = new ArrayList();
        dp.add(0, 0);
        dp.add(1, 1);
        for (int i = 2; dp.get(i-1) + + dp.get(i-2) < n/2; i++){
            dp.add(i,dp.get(i - 1) + dp.get(i-2));
        }
        return dp;
    }
    public void sort_shell() throws InterruptedException{
        int N = ar.length;
        int d,step;
        ArrayList<Integer> f = fib(N);
        for(d = f.size()-1; d > 1; d--){
            step = f.get(d);
            for (int i=0;i<N;i++)
                ar[i].setColor(g, Color.black);
            for(int p = 0;p<step;p++){
                group_sort(N,step,p);
            }
        }
    }
    private void move_group(int N, int step, int p, int dy) throws InterruptedException {
        int j = p;
        for (int k = 0; k < 2*Element.rad; k++) {
            j = p;
            while (j < N) {
                ar[j].move(g, 0, dy);
                j = j + step;
            }
        }
        Thread.sleep(200);
    }
    
    private void move_horizontal(Element el1,Element el2,int step) throws InterruptedException{
                for (int t = 0; t < 2*Element.rad*step; t++){
                    el1.move(g, -1, 0);
                    el2.move(g, 1, 0);
                }        
    }
    
    private void move_vertikal(Element elem,int val) throws InterruptedException{
            for (int t = 0; t < 2*Element.rad; t++){
                elem.move(g, 0, val);
            }       
    }
    
    public void group_sort(int N, int step, int p) throws InterruptedException {
        move_group(N, step, p, 1);
        ar[p].setColor(g, Color.red);
        int i = step + p;
        while (i < N) {
            Element elem = ar[i];
            elem.setColor(g, Color.blue);
            move_vertikal(elem,1);
            int l = i - step;
            while (l >= 0 && ar[l].getValue()>elem.getValue()) {
                move_horizontal(elem,ar[l],step);
                ar[l].setColor(g, Color.red);
                ar[l + step] = ar[l];
                l = l - step;
            }
            ar[l + step] = elem;
            move_vertikal(elem,-1);
            elem.setColor(g, Color.red);
            i = i + step;
        }
        move_group(N,step,p,-1);
    }


}
