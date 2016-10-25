package roulete;
import java.util.*;
import java.util.Random;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class Casino extends JFrame{

  private static int saldo=1000,score=0,bet=5,odds=0;

  String a;
  JLabel label;
  Blackjack b;
  SlotMachine s;
  Roleta r;

  public Casino() {
    super("Casino Lucky-Shot");
    setSize(500, 500);
    setLayout(new GridLayout(2,0));
    JPanel Casinotheme = new JPanel(new GridLayout(2,0));
    JPanel music =new JPanel();
    PlayerSong musica=new PlayerSong();
    Casinotheme.add(new Jogoimg("./img/casino.jpeg",10,-40));
    Casinotheme.add(musica);
    add(Casinotheme);
    JPanel button = new JPanel(new GridLayout(1,3)); 
    JPanel button2 = new JPanel(); 
    JTextArea rou=new JTextArea("Roleta!");
    JTextArea slo=new JTextArea("Slot Machine!");
    JTextArea black=new JTextArea("Blackjack!");
    JPanel aux=new JPanel();
    aux.add(rou);
    button2.add(aux);
    aux.add(slo);
    button2.add(aux);
    aux.add(black);
    button2.add(aux);
   

    for(int i=1;i<=3;i++)
    {
    
      if(i==1)
      {
        a= new String("./img/roul/rou.jpeg");
        Icon image=new ImageIcon(a);
        label = new JLabel(image);
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK,2)); 
       label.addMouseListener(new MouseAdapter() {
        public void mouseClicked(MouseEvent e){
            musica.backtheme.Stopsound();
            musica.flag=1;
            setVisible(false);
            r=new Roleta();
            r.setVisible(true);  
          }

        });
        button.add(label); 
      }
      if(i==2)
      {
        a= new String("./img/slot/luck.jpeg");
        Icon image=new ImageIcon(a);
        label = new JLabel(image);
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK,2)); 
       label.addMouseListener(new MouseAdapter() {
        public void mouseClicked(MouseEvent e){
            musica.backtheme.Stopsound();
            musica.flag=1;
            setVisible(false);
            s=new SlotMachine();
            s.setVisible(true);
          }

        }); 
        button.add(label); 
      }
      if(i==3)
      {
        a= new String("./img/Deck/bj.png");
        Icon image=new ImageIcon(a);
        label = new JLabel(image);
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK,2)); 
       label.addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent e){
            musica.backtheme.Stopsound();
            musica.flag=1;
            setVisible(false); 
            b=new Blackjack();
            b.setVisible(true);
        }

        });

        button.add(label);  
      }
    }
   button2.add(button);
    add(button2);
  }
  public static void main(String[] args) {
    new Casino().setVisible(true);
  }
}