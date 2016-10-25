package roulete;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.sound.sampled.*;
import javax.sound.sampled.LineEvent.*;

public class PlayerSong extends JPanel{

  static Sound backtheme= new Sound();
  static int song=0,flag=0;

   public PlayerSong() {

    backtheme=new Sound("./sounds/"+song+".wav");

    JButton play = new JButton("|>");
    JButton stop = new JButton("||");
    JButton next = new JButton("=>");
    JButton last = new JButton("<=");
    JButton mute = new JButton("MUTE");
    next.setPreferredSize(new Dimension(30, 20));
    last.setPreferredSize(new Dimension(30, 20));
    play.setPreferredSize(new Dimension(20, 20));
    stop.setPreferredSize(new Dimension(20, 20));
    mute.setPreferredSize(new Dimension(40, 20));

    LineListener listener = new LineListener() {
           public void update(LineEvent event) {
                  if (event.getType() != Type.STOP) {
                     flag=0;
                     return;
                  }
                  if(flag==0)
                  {
                     song++;
                     backtheme=new Sound("./sounds/"+song+".wav");
                  }
              }
             };
        backtheme.clip.addLineListener(listener);
    play.addMouseListener(new MouseAdapter(){
      public void mouseClicked(MouseEvent e)
      {
        backtheme.Startsound();
      }
    });
    stop.addMouseListener(new MouseAdapter(){
      public void mouseClicked(MouseEvent e)
      {
         flag=1;
        backtheme.Stopsound();
      }
    }); 
    next.addMouseListener(new MouseAdapter(){
      public void mouseClicked(MouseEvent e)
      {
         flag=1;
         backtheme.Close();
        song++;
        backtheme=new Sound("./sounds/"+song+".wav");
        backtheme.clip.addLineListener(listener);
      }
    }); 
    last.addMouseListener(new MouseAdapter(){
      public void mouseClicked(MouseEvent e)
      {
         if (song>0)
         {
            flag=1;
            backtheme.Close();
           song--;
           backtheme=new Sound("./sounds/"+song+".wav");
        backtheme.clip.addLineListener(listener);
          }
      }
    });
    mute.addMouseListener(new MouseAdapter(){
      public void mouseClicked(MouseEvent e)
      {
        backtheme.ToggleMute();
      }
    });
      add(last);
      add(play);
      add(stop);
      add(next);
      add(mute);
   }
  
}