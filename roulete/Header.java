// 2º Semestre 2016 Trabalho 1
// Henrique Figueiredo

package roulete;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

// esta classe implementa o painel comum aos três jogos onde se apresentam as opções de aposta ("Bet,subir, descer,duplicar")
public class Header extends JPanel{
   User player;
    JButton bet= new JButton("Bet!");
    JButton up= new JButton(">");
    JButton down= new JButton("<");
    JButton exit = new JButton("x");
    JButton twotimes= new JButton("x2");
    JTextArea bettingarea;
    JTextArea saldo;
    public Header(User a) {

    player=a;
    bettingarea=new JTextArea("bet value: "+player.getBet()+"$",1,2);
    saldo = new JTextArea("Saldo: "+player.getSaldo()+"$");
    up.setPreferredSize(new Dimension(20, 20));
    down.setPreferredSize(new Dimension(20, 20));
    twotimes.setPreferredSize(new Dimension(40, 20));
    exit.setPreferredSize(new Dimension(15, 15));
       // cada botão influencia o valor da aposta
    up.addMouseListener(new MouseAdapter() { 
    public void mouseClicked(MouseEvent e){
        if(player.getSaldo()>player.getBet())
           player.setBet(player.getBet()+5);
         bettingarea.setText("bet value: "+player.getBet()+"$");
          }

        });
  down.addMouseListener(new MouseAdapter() { 
    public void mouseClicked(MouseEvent e){
        if(player.getBet()>0)
           player.setBet(player.getBet()-5);
           bettingarea.setText("bet value: "+player.getBet()+"$");

          }

        });
  twotimes.addMouseListener(new MouseAdapter() { 
    public void mouseClicked(MouseEvent e){
        if(player.getSaldo()>2*player.getBet())
           player.setBet(player.getBet()*2);
           bettingarea.setText("bet value: "+player.getBet()+"$");

          }

        });
  
    add(exit);
    add(saldo);
    add(bettingarea);
    add(down);
    add(up);
    add(twotimes);
       // é modificado posteriormente em cada jogo
    add(bet);
    }
}
