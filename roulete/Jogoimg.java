 
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

public class Jogoimg extends JPanel{

  private int x,y;  
  private static String a;  
  private BufferedImage image;
  
  public Jogoimg(String url,int cordx,int cordy) {
      a=url;
      x=cordx;
      y=cordy;
  }

 @Override
        public void paintComponent(Graphics g) {

            super.paintComponent(g);
            try {                
              image = ImageIO.read(new File(a));
              g.drawImage(image, x, y, null);
           } catch (IOException ex) {
                // handle exception...
           }
            
            	}
  }