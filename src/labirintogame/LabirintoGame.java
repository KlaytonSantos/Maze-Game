/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package labirintogame;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

public class LabirintoGame extends Canvas implements Runnable, KeyListener {

    public static JFrame frame;
    public boolean isRunning = true;
    public Thread thread;
    public static int largura= 240;
    public static int altura = 135;
    public static int escala = 4;
    
    
    public static BufferedImage image;
    public int x,y;
    public boolean direita, esquerda, cima, baixo;
    
    
    public LabirintoGame(){
        addKeyListener(this);
        this.setPreferredSize(new Dimension(largura*escala, altura*escala));
        image = new BufferedImage(largura*escala, altura*escala, BufferedImage.TYPE_INT_RGB);
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
        if(direita){
            x++;
        }else if(esquerda){
            x--;
        }
        if(baixo){
            y++;
        }else if(cima){
            y--;
        }
    }
    public void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            this.createBufferStrategy(3);
            return;
        }
        requestFocus();
        Graphics g = bs.getDrawGraphics();
        
        g.setColor(Color.BLACK);
        g.fillRect(0, 0,largura*escala, altura*escala);
        g.setColor(Color.red);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        g.drawString("Olá", 150, 50);
        g.setColor(Color.white);
        g.fillRect(x, y, 64, 64);
        
        bs.show();
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

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
       if(e.getKeyCode() == KeyEvent.VK_D)
           direita = true;
     else if(e.getKeyCode() == KeyEvent.VK_A)
           esquerda = true;
       if(e.getKeyCode() == KeyEvent.VK_W)
           cima = true;
      else if(e.getKeyCode() == KeyEvent.VK_S)
           baixo = true;
           
    }

    @Override
    public void keyReleased(KeyEvent e) {
       if(e.getKeyCode() == KeyEvent.VK_D)
           direita = false;
     else if(e.getKeyCode() == KeyEvent.VK_A)
           esquerda = false;
       if(e.getKeyCode() == KeyEvent.VK_W)
           cima = false;
      else if(e.getKeyCode() == KeyEvent.VK_S)
           baixo = false;
    }

  
    
    
}
