// 2 º Semestre 2016 Trabalho 1
// Henrique Figueiredo
package roulete;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

// classe que implementa painel onde a bola da roleta vai rodar
public class Board extends JPanel implements ActionListener{

	private java.awt.Color color = Color.BLUE;   // The color of the ball
	private java.awt.Point loc = new java.awt.Point(375, 0);  // the location of the center of the ball
	private java.awt.Point circlepos = new java.awt.Point(0, 0);  // the location of the center of the ball
	private int radius = 3;   // the radius of the ball
	private int tim = 50;
	private double theta = -Math.PI/2+2*Math.PI/37;
	private double step= 2*Math.PI/37;
    private BufferedImage image;
	 Timer spining;
  boolean over=false;

  public Board() {
    }
	// desenha o circulo da roleta
  public void paintBoard(Graphics2D g2D)
  {
  	g2D.setColor(Color.BLACK);
  	g2D.setStroke(new BasicStroke(2F));
  	g2D.drawOval (295, 0, 160, 160);
    for(double i=-Math.PI/2;i<=3*Math.PI/2;i=i+step)
    {

      Polygon p = new Polygon();

      g2D.setColor(Color.BLACK);
      p.addPoint((int) Math.round(375 + 75*Math.cos(i)),5+(int) Math.round(75+75*Math.sin(i)));
      if(i<3*Math.PI/2)
       p.addPoint((int) Math.round(375 + 75*Math.cos(i)+step),5+(int) Math.round(75+75*Math.sin(i)+step));
      else
        p.addPoint((int) Math.round(375 + 75*Math.cos(i)+step),5+(int) Math.round(75+75*Math.sin(i)+step));
      p.addPoint(375,75);
      g2D.drawPolygon(p);
        
    }
    g2D.setStroke(new BasicStroke(2F));
    g2D.drawOval (375-11, 77-11, 21, 21);
    g2D.setColor(new Color(102,51,0));
    g2D.fillOval (375-10, 77-10, 20, 20);
  }
	// pinta a bola que vai rodar
  public void paintBall(Graphics2D g2D,Color c)
  {
  //	g2D.setColor(c);
	g2D.setStroke(new BasicStroke(2F));
	circlepos.x=loc.x-radius;
	circlepos.y=loc.y-radius;
	//g2D.drawOval (circlepos.x, circlepos.y, 2*radius, 2*radius);
	g2D.setColor (Color.WHITE);  // set the color to paint with
	g2D.fillOval (circlepos.x, circlepos.y, 2*radius, 2*radius); 
 
  }


  @Override
        public void paintComponent(Graphics g) {

            super.paintComponent(g);
            try {                
		// carrega imagem de fundo da roleta
              image = ImageIO.read(new File("./img/roul/board.png"));
              g.drawImage(image, 300, 0, null);
           } catch (IOException ex) {
                // handle exception...
           }
            
            
             	Graphics2D g2D = (Graphics2D) g;   
             	paintBall(g2D,Color.BLACK);

                theta=theta+step;
             		loc.x=(int) Math.round(375 + 75*Math.cos(theta));
             		loc.y=(int) Math.round(75+75*Math.sin(theta));
             		if(theta >= 7*Math.PI/2+2*Math.PI*(tim+1)/37)
             		{
                  theta=-Math.PI/2;
             			spining.stop();
             		}

            	}
	// metodo que incia timer que roda a bola "rand" vezes
  public void Bolarodando(int rand)
  {
  		tim=rand;
  		//Draw a random colored circle in random place 
		//for every 100 milliseconds 
		spining=new Timer(145,this);
		spining.start();	
  }
  public void actionPerformed(ActionEvent e) 
  {
    repaint();
  }
    
}
