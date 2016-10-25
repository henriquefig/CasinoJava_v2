// 2 º Semestre 2016 Trabalho 1
// Henrique Figueiredo
package roulete;
import java.awt.EventQueue;
import javax.swing.JFrame;

import java.util.Random;
import java.util.ArrayList;
import java.awt.event.KeyEvent;

// classe que cria um baralho de cartas e o baralha
public class Deck {
	private ArrayList<Card> cards;
	public Card teste;
	 Deck()
	{
		cards = new ArrayList<Card>();
		int index_1, index_2;
		Random generator = new Random();
		Card temp;
		for (short a=0; a<=3; a++)
		{
			for (short b=0; b<=12; b++)
			 {
			   cards.add( new Card(a,b) );
			 }
		}
		int size = cards.size() -1;
		
		// baralhar cartas
		for (short i=0; i<100; i++)
		{
			index_1 = generator.nextInt( size );
			index_2 = generator.nextInt( size );

			temp = (Card) cards.get( index_2 );
			cards.set( index_2 , cards.get( index_1 ) );
			cards.set( index_1, temp );
		}
	}
	// tirar carta do baralho
	public Card drawFromDeck()
	{	   
		return cards.remove( cards.size()-2 );
	}
	// retorna tamanho do baralho
	public int getTotalCards()
	{
		return cards.size(); 
	}
	 
} 
