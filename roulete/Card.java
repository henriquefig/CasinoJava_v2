// 2 º Semestre 2016 Trabalho 1
// Henrique Figueiredo
package roulete;
import java.util.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

// classe com a imagem e informação de uma carta de um baralho normal de cartas
public class Card
{
    private short rank, suit;
    private String a= new String();
    private static String[] suits = { "hearts", "spades", "diamonds", "clubs"};
    private static String[] ranks  = { "Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
     JPanel imgcard = new JPanel();
   
    public static String rankAsString( int __rank ) {
        return ranks[__rank];
    }

    Card(short suit, short rank)
    {
        
        a="./img/Deck/"+suit+"/"+rank+".png";
         Icon image=new ImageIcon(a);


        JLabel label = new JLabel(image);
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
        imgcard.add(label); 
        this.rank=rank;
        this.suit=suit;
    }

    public @Override String toString()
    {
          return ranks[rank] + " of " + suits[suit];
    }
    //retorna o numero da carta
    public short getRank() {
         return rank;
    }

    public short getSuit() {
        return suit;
    }
}
