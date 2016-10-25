package roulete;

import java.util.*;
import java.awt.*;


public class User{

  private Card blackjack[]= new Card[10];
  private int saldo=1000,score=0,bet=5,odds=0,blackscore=0;
  public User() {

  }
  public int getSaldo()
  {
    return  saldo;
  }
  public Card getBlackcards(int i)
  {
    return blackjack[i];
  }
  public void setBlackcards(Card um, int i)
  {
    blackjack[i]=um;
  }
  public int getBet()
  {
    return  bet;
  }
  public int getOdds()
  {
    return  odds;
  } 
  public int getBlackscore()
  {
    return  blackscore;
  }
  public void setBlackscore(int b)
  {
    blackscore=b;
  }
  public void setSaldo(int s)
  {
    saldo=s;
  }
  public void setBet(int b)
  {
    bet=b;
  }
  public void setOdds(int o)
  {
    odds=odds+o;
  }  
  public void resetOdds()
  {
    odds=0;
  }
  public boolean hasAce(Card a)
  {
     if(a.getRank()==0)
      return true;
    else
    return false;
  }
}