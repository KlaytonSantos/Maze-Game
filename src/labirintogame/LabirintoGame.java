/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package labirintogame;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

public class LabirintoGame extends Canvas implements Runnable {

    public static JFrame frame;
    public boolean isRunning = true;
    public Thread thread;
    public static int largura= 240;
    public static int altura = 135;
    public static int escala = 4;
    
    public static BufferedImage image;
    
    public LabirintoGame(){
        this.setPreferredSize(new Dimension(240*3, 160*3));
       
        iniFrame();
    
    
    }   
    
    public void iniFrame(){
        frame = new JFrame("Labirinto");
        frame.add(this);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
    }
    
    public synchronized void start(){
        thread = new Thread(this);
        isRunning = true;
        thread.start();
    }
    public synchronized void stop(){
        try {
            isRunning = false;
            thread.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(LabirintoGame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String[] args) {
        LabirintoGame jogo = new LabirintoGame();
        jogo.start();
        
    }
    public void tick(){
        
    }
    public void render(){
        
    }
    
    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double amountofTicks = 60.0;
        double ms = 1000000000 / amountofTicks;
        double delta = 0;
        int frames = 0;
        double timer = System.currentTimeMillis();
        while(isRunning){
            long now = System.nanoTime();
            delta +=(now - lastTime)/ms;
            lastTime = now;
            if(delta >= 1){
                tick();
                render();
                frames++;
                delta--;
            }
            if(System.currentTimeMillis() - timer > 1000){
                System.out.println("FPS: "+ frames);
                frames = 0;
                timer+=1000;
                
            }
        }
        stop();
    }

  
    
    
}
