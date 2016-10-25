// 2 º Semestre 2016 Trabalho 1
// Henrique Figueiredo
package roulete;

import java.util.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;


// classe que implementa os botoes de aposta para a roleta
public class Button extends JPanel {
  private int first=0;
  private JPanel button = new JPanel(new GridLayout(1,1));
  private String a= new String();
  private static int kind=-1;
  //carregar imagem de acordo com o tipo de botao
  public Button(int i, int j,int b) {
    if(b==2)
    a = new String("./img/roul/"+i+j+"g.png");
    if(b==3)
    a = new String("./img/roul/0.png");

    if(b==1)
    a = new String("./img/roul/"+(i+(j-1)*3)+".png");
    Icon image=new ImageIcon(a);


    JLabel label = new JLabel(image);
    label.setBorder(BorderFactory.createLineBorder(Color.BLACK,2)); 
    // selecionador de aposta (fica rodeado a vermelho)
    label.addMouseListener(new MouseAdapter() { 
    public void mouseClicked(MouseEvent e){
            
            if (e.getButton() == MouseEvent.BUTTON1) {
              if(first==0)
              {
                if(b==1)
                kind=(i+(j-1)*3)*100;
                if(b==2)
                kind=i*10+j;
               if(b==3)
                kind=0;
               
                   label.setBorder(BorderFactory.createLineBorder(Color.RED,2)); 
                  first++;
               }
               else
               {
                kind=-1;
                first=0;
                 label.setBorder(BorderFactory.createLineBorder(Color.BLACK,2)); 
                }

            }
          }

        });

    button.add(label);

  }
  // retorna o botao
  public JPanel getButton()
  {
    return  button;
  }
  // retorna o tipo de botao
   public static int getKind()
  { 
    return  kind;
  }
  // retorna o valor do multiplicador de aposta para esse botão
  public static int getBettingmult(int kind)
  {
    int value=0;
   
    switch(kind)
    {
      case 12: case 22: case 32: case 42: case 52: case 62:
      value=1;
      break;
      case 0: case 100: case 200: case 300: case 400: case 500: case 600: case 700: case 800: case 900: case 1000: case 1100: case 1200: case 1300: case 1400: case 1500: case 1600: case 1700: case 1800: case 1900: case 2000: case 2100: case 2200: case 2300: case 2400: case 2500: case 2600: case 2700: case 2800: case 2900: case 3000: case 3100: case 3200: case 3300: case 3400: case 3500: case 3600:
      value=35;
      break;
      case 11: case 21: case 31: case 3700: case 3800: case 3900:
      value=2;
      break;
    }
    return value;
  }
}
