/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.threads;

/**
 *
 * @author Sebastián Reyes
 */
public class CountThread extends Thread {
    
    private int a;
    private int b;
    
    /**
     * 
     * @param a It is the beginning of the range.
     * @param b It is the end of the range.
     */
    public CountThread(int a,int b) {
        this.a = a;
        this.b = b;
    }
    
    @Override
    public void run() {
        for(int i = a;i <= b;i++) {
            System.out.println(i);
        }
    }
    
}
