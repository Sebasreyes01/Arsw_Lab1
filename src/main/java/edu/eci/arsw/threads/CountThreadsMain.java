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
public class CountThreadsMain {
    
    public static void main(String args[]){
       CountThread t1 = new CountThread(0, 99);
       CountThread t2 = new CountThread(100, 199);
       CountThread t3 = new CountThread(200, 299);
       t1.run();
       t2.run();
       t3.run();
    }
    
}
