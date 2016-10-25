// 2º Semestre 2016 Trabalho 1
// Henrique Figueiredo
package roulete;
import java.util.*;
import java.awt.*;
import javax.swing.*;

// classe que implementa cada linha da slot machine (3 linhas)
public class SlotLinha extends JPanel
{
    ArrayList<SlotChoice> linha;
    
    SlotLinha()
    {
        short a=-3;
        SlotChoice escolha;
        int index_1, index_2;
        Random generator = new Random();
        SlotChoice temp;
        setLayout(new CardLayout());
        linha = new ArrayList<SlotChoice>();
        //cerca de 300+ imagens em cada linha
        // cada quadradinho tem certa probabilidade de se repetir
       for(int j=0;j<=127;j++)
        {
            if(j==0)
            {
                a=8;
                SlotChoice Carta=new SlotChoice(a);
                Carta.imgcard.setVisible(true);
                linha.add(Carta);
            }
            if((j-1)%128==0 || (j-1)%128==128)
            {
                a=2;
                SlotChoice Carta=new SlotChoice(a);
                linha.add(Carta);
            }
            if((j-2)%64==0 || (j-2)%64==64)
            {
                a=7;
                SlotChoice Carta=new SlotChoice(a);
                linha.add(Carta);
            }
            if((j+3)%32==0 || (j-3)%32==32)
            {
                a=1;
                SlotChoice Carta=new SlotChoice(a);
                linha.add(Carta);
            }
            if((j+4)%16==0 || (j-4)%16==16)
            {
                a=0;
                SlotChoice Carta=new SlotChoice(a);
                linha.add(Carta);
            }
            if((j+5)%8==0 || (j-5)%8==8)
            {
                a=6;
                SlotChoice Carta=new SlotChoice(a);
                linha.add(Carta);
            }
            if((j+6)%4==0 || (j-6)%4==4)
            {
                a=4;
                SlotChoice Carta=new SlotChoice(a);
                linha.add(Carta);
            }
            if((j+7)%2==0 || (j-7)%2==1)
            {
                a=5;
                SlotChoice Carta=new SlotChoice(a);
                linha.add(Carta);
            }
            if((j+8)%1==0 || (j-8)%1==1)
            {
                a=3;
                SlotChoice Carta=new SlotChoice(a);
                linha.add(Carta);
            }


        }
            for(int h=0;h<=linha.size()-1;h++)
                add(linha.get(h).imgcard);
    }
}
