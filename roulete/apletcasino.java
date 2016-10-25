package roulete;
import java.awt.*;
import javax.swing.JApplet;

public class apletcasino extends JApplet{
Casino cenas;
 public static void main(String args[]) {
        apletcasino app = new apletcasino();
        app.init();
    }
  public void init()
  { 
    cenas=new Casino();
    cenas.setVisible(true);
  }
}