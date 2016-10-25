package roulete;
import java.util.*;
import java.util.Random;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import org.apache.commons.lang.SerializationUtils;
import java.io.*;
import java.net.*;



public class Blackjack extends JFrame{

  private static int saldo=1000,score=0,botscore=0,bet=0,counter=0,counter1=0,score2=0,botcounter=0,acecounter=0,botacecounter=0,segundamao=0;
   User player=new User();
   User computer=new User();
   Deck baralho=new Deck();
  public ObjectInputStream in;
  public Socket client;
  public ObjectOutputStream out;
    JTextArea computerhand = new JTextArea("Cartas computador: Score :-");
    JTextArea mao = new JTextArea();
    JPanel bot= new JPanel();
    String back="./img/Deck/back.jpeg";
    Icon image=new ImageIcon(back);
    JLabel label[] = new JLabel[4];
    JPanel tuamao=new JPanel();
    PlayerSong musica=new PlayerSong();
    JTextArea scoremao = new JTextArea("Score=-");
    JButton hit =new JButton("Hit Me!");
    JButton split =new JButton("Split");
    JButton stand =new JButton("Stand");
    JButton segunda =new JButton("Click-me!");
    JTextArea resultado = new JTextArea();
    JTextArea ganhou = new JTextArea("Blackjack! Ganhou!");
    JPanel action=new JPanel();
    JPanel header= new JPanel();
    JPanel headerbot= new JPanel();
    JPanel Cartscor= new JPanel();
    Header cabeçalho = new Header(player);
   JPanel cenas=new JPanel(new GridLayout(7,0));
   Thread one;
    ObjectOutputStream outfile;
     ObjectInputStream infile;

      Socket fileclient;
  public void writetolog()
  {
     try{
     fileclient = new Socket("localhost", 4000);
    infile =new ObjectInputStream(fileclient.getInputStream());
   outfile=new ObjectOutputStream(fileclient.getOutputStream());
    } catch (IOException e) {
        System.err.println(e.getMessage());
        System.exit(1);
    }
      Protocolo filetolog=new Protocolo();
      filetolog.arg1=(String) "Recieve HighScores";
      filetolog.arg2=(String) player.getNome();
      filetolog.arg3=(User) player;
      filetolog.arg4=(String) "Blackjack";

        try{
         filetolog.envia(outfile);
        } catch (Exception er) {
          System.err.println(er.getMessage());
          System.exit(1);
        }
    }

   public void readfromlog()
  {
     try{
   fileclient = new Socket("localhost", 4000);
    infile =new ObjectInputStream(fileclient.getInputStream());
   outfile=new ObjectOutputStream(fileclient.getOutputStream());
    } catch (IOException e) {
        System.err.println(e.getMessage());
        System.exit(1);
    }
      Protocolo filetolog=new Protocolo();
      filetolog.arg1=(String) "Get HighScores";

        try{
         filetolog.envia(outfile);
        } catch (Exception er) {
          System.err.println(er.getMessage());
          System.exit(1);
        }
        JFrame High= new JFrame("HighScores");
        High.setSize(400,600);
        High.setLayout(new GridLayout(10,0));
         try{

          filetolog=filetolog.recebe(infile);
          String [] maxscores=(String []) filetolog.arg1;
          for(int i=0;i<9;i++)
          {
            if(maxscores[i]!=null)
            High.add(new JTextArea(i+1+" - "+maxscores[i]));
          }
          High.setVisible(true);
          } catch (Exception er) {
              System.err.println(er.getMessage());
              System.exit(1);
          }
  }
   MouseAdapter listentoserver=new MouseAdapter(){
        public void mouseClicked(MouseEvent e)
        {
          cabeçalho.bet.setEnabled(false);
          one=new Thread(){
                public void run() {
                          if(player.getSaldo()>=player.getBet() && player.getBet()!=0)
                          {
                            Protocolo a=new Protocolo();
                            try{
                                a.arg1= (User) player;
                                a.envia(out);
                            } catch (Exception er) {
                                System.err.println(er.getMessage());
                                System.exit(1);
                            }

                            try{

                            a=a.recebe(in);
                            player=(User) a.arg1;
                            } catch (Exception er) {
                                System.err.println(er.getMessage());
                                System.exit(1);
                            }
                            baralho=new Deck();
                            acecounter=0;
                            botacecounter=0;
                            counter1=0;
                            counter=0;
                              score=0;
                              score2=0;
                            hit.setVisible(true);
                            stand.setVisible(true);
                            split.setVisible(true);
                            segunda.setVisible(false);
                            label[0].setVisible(true);

                            resultado.setVisible(false);
                            tuamao.removeAll();
                            bot.removeAll();
                            repaint();
                            revalidate();
                            botcounter=0;
                            player.setBlackcards(baralho.drawFromDeck(),0);
                            player.setBlackcards(baralho.drawFromDeck(),1);
                            computer.setBlackcards(baralho.drawFromDeck(),0);
                            computer.setBlackcards(baralho.drawFromDeck(),1);
                            for(int i=0;i<=1;i++)
                            {
                              if(player.hasAce(player.getBlackcards(i))==true)
                              {
                                  acecounter++;
                              }
                            }
                            for(int i=0;i<=1;i++)
                            {
                              if(computer.hasAce(computer.getBlackcards(i))==true)
                              {
                                  botacecounter++;
                              }
                            }
                            computerhand.setText("Cartas computador: Score :"+getCardsScore(computer.getBlackcards(0),0));
                            score=getCardsScore(player.getBlackcards(0),0);
                            score=getCardsScore(player.getBlackcards(1),score);
                            botscore=getCardsScore(computer.getBlackcards(0),0);
                            botscore=getCardsScore(computer.getBlackcards(1),botscore);
                             if(botacecounter==2)
                              {
                                botscore=botscore-10;
                                botacecounter--;
                              }
                             if(acecounter==2)
                             {
                                score=score-10;
                                acecounter--;
                             }
                              cabeçalho.saldo.setText("Saldo:"+player.getSaldo()+"$");
                              for(int i=1;i<4;i++)
                                label[i].setVisible(false);

                              tuamao.add(player.getBlackcards(0).imgcard);
                              tuamao.add(player.getBlackcards(1).imgcard);
                              bot.add(computer.getBlackcards(0).imgcard);
                              bot.add(label[0]);
                                scoremao.setText("Score="+score);
                              }
                              try{
                              one.join();

                              }catch (Exception e) {
                              System.err.println(e.getMessage());
                              System.exit(1);
                            }
                          }
                      };
                if(!one.isAlive())
                one.start();

        }
    };


  public void ligaaoservidor()
  {
    try{
    client = new Socket("localhost", 5000);
    in=new ObjectInputStream(client.getInputStream());
       out=new ObjectOutputStream(client.getOutputStream());
      } catch (IOException e) {
          System.err.println(e.getMessage());
          System.exit(1);
      }
          try{
           out.write(2);
           out.flush();
          } catch (Exception er) {
            System.err.println(er.getMessage());
            System.exit(1);
          }
      cabeçalho.bet.addMouseListener(listentoserver);
  }
  
  public Blackjack() {
    super("Blackjack 21 - Lucks");
    ligaaoservidor();
    setSize(670, 770);
    player.setNome(JOptionPane.showInputDialog("Por favor insira o seu nome:"));
    if(player.getNome().equals(""))
      player.setNome("Convidado");
     JMenuBar menuBar = new JMenuBar();
      setJMenuBar(menuBar);
      JMenu jogoMenu = new JMenu("Jogo");
      menuBar.add(jogoMenu);
        JMenuItem scoresAction = new JMenuItem("HighScores");
        jogoMenu.add(scoresAction);
      scoresAction.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent event) {
              readfromlog();
          }
      });
      JMenuItem exitAction = new JMenuItem("Sair");
      jogoMenu.add(exitAction);
  exitAction.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent event) {
              writetolog();
              System.exit(0);
          }
      });
  
    hit.setVisible(false);
    stand.setVisible(false);
    split.setVisible(false);
    resultado.setVisible(false);
    ganhou.setVisible(false);
    segunda.setVisible(false);
    segunda.addMouseListener(new MouseAdapter(){
    	public void mouseClicked(MouseEvent e)
    	{
          resultado.setVisible(false);
          hit.setVisible(true);
          stand.setVisible(true);
          split.setVisible(true);
     	 segunda.setVisible(false);
        tuamao.removeAll();
          player.getBlackcards(1).imgcard.setVisible(true);
          player.getBlackcards(3).imgcard.setVisible(true);
          tuamao.add(player.getBlackcards(1).imgcard);
          tuamao.add(player.getBlackcards(3).imgcard);
          score=0;
          score2=getCardsScore(player.getBlackcards(1),0);
          score2=getCardsScore(player.getBlackcards(3),score2);
          scoremao.setText("Score2="+score2);
          segundamao++;
          acecounter=0;
    	}
    });
   
    player.setBlackcards(baralho.drawFromDeck(),0);
    player.setBlackcards(baralho.drawFromDeck(),1);
    computer.setBlackcards(baralho.drawFromDeck(),0);
    computer.setBlackcards(baralho.drawFromDeck(),1);

    

    for(int i=0;i<4;i++)
    label[i]=new JLabel(image);
    split.addMouseListener(new MouseAdapter() { 
          public void mouseClicked(MouseEvent e){
              if (player.getBlackcards(0).getRank()==player.getBlackcards(1).getRank() && player.getSaldo()>=player.getBet() && counter==0) {
                 player.setBlackcards(baralho.drawFromDeck(),2);  
                  player.setBlackcards(baralho.drawFromDeck(),3);
                  score=getCardsScore(player.getBlackcards(0),0);
                  score=getCardsScore(player.getBlackcards(2),score);
                  score2=getCardsScore(player.getBlackcards(1),0);
                  score2=getCardsScore(player.getBlackcards(3),score2);
                  scoremao.setText("Split! Score1="+score);
                  System.out.println("As merdas ja existem!!!");
                  player.getBlackcards(1).imgcard.setVisible(false);
                  player.getBlackcards(3).imgcard.setVisible(false);

                  tuamao.add(player.getBlackcards(2).imgcard);
                  tuamao.add(player.getBlackcards(3).imgcard);
                  }
                }

              });
          stand.addMouseListener(new MouseAdapter() { 
          public void mouseClicked(MouseEvent e){
              hit.setVisible(false);
              stand.setVisible(false);
              split.setVisible(false);
              label[0].setVisible(false);
              bot.add(computer.getBlackcards(1).imgcard);
               while(botscore<17)
                {
                  botcounter++;
                  computer.setBlackcards(baralho.drawFromDeck(),botcounter+1);
                  botscore=getCardsScore(computer.getBlackcards(botcounter+1),botscore);
                  computer.getBlackcards(botcounter+1).imgcard.setVisible(true);
                  bot.add(computer.getBlackcards(botcounter+1).imgcard);
                
                  if(computer.hasAce(computer.getBlackcards(botcounter+1))==true)
                    {
                        botacecounter++;
                     } 
                   if(botscore>21)
                   {
                       if(botacecounter>0)
                        {

                         while(botacecounter>0)
                          {
                            botscore=botscore-10;
                            botacecounter--;
                          }
                        }
                    }
                }

          		if (score2==0) 
          		{
                  Protocolo a=new Protocolo();
                    try{
                        a.arg1 = (String) "No-split";
                        a.arg2= (int) score;
                        a.arg3= (int) botscore;
                        a.arg4 = (User) player;
                        a.envia(out);
                    } catch (Exception er) {
                        System.err.println(er.getMessage());
                        System.exit(1);
                    }
                    String result=null;

                    System.out.println("kaskfaskdas");
                    try{

                    a=a.recebe(in);
                    result=(String) a.arg1;
                    } catch (Exception er) {
                        System.err.println(er.getMessage());
                        System.exit(1);
                    }

                      if(result.equals("Winner"))
                      {
                        resultado.setText("Vencedor! Recebe -"+player.getBet()+"$");
                        resultado.setVisible(true);
                        player=(User) a.arg2;
                      }
                      if(result.equals("Tie"))
                      {
                        resultado.setText("Empate! A sua aposta foi retornada!");
                        resultado.setVisible(true);
                        player=(User) a.arg2;
                      }
                      if(result.equals("Loser"))
                      {
                        resultado.setText("Perdedor! Tenta outra vez (Dica, aposta mais)!");
                        resultado.setVisible(true);
                        if(player.getBet()>=player.getSaldo())
                        {
                          player.setBet(player.getSaldo());
                          cabeçalho.bettingarea.setText("bet value: "+player.getBet()+"$");
                        }
                      }
                    cabeçalho.saldo.setText("Saldo:"+player.getSaldo()+"$");
                    computerhand.setText("Cartas computador: Score :"+botscore);
             }
            else
            {
            	if(segundamao==0)
            	{


                  Protocolo a=new Protocolo();
                    try{
                        a.arg1 = (String) "Split";
                        a.arg2= (int) score;
                        a.arg3= (int) botscore;
                        a.arg4=(User) player;
                        a.envia(out);
                    } catch (Exception er) {
                        System.err.println(er.getMessage());
                        System.exit(1);
                    }
                    String result=null;

                    try{

                    a=a.recebe(in);
                    result=(String) a.arg1;
                    } catch (Exception er) {
                        System.err.println(er.getMessage());
                        System.exit(1);
                    }

                      if(result.equals("Winner"))
                      {
                        resultado.setText("Vencedor! Recebe -"+player.getBet()+"$");
                        resultado.setVisible(true);
                        player=(User) a.arg2;
                      }
                      if(result.equals("Tie"))
                      {
                        resultado.setText("Empate! A sua aposta foi retornada!");
                        resultado.setVisible(true);
                        player=(User) a.arg2;
                      }
                      if(result.equals("Loser"))
                      {
                        resultado.setText("Perdedor! Tenta outra vez (Dica, aposta mais)!");
                        resultado.setVisible(true);
                        if(player.getBet()>=player.getSaldo())
                        {
                          player.setBet(player.getSaldo());
                          cabeçalho.bettingarea.setText("bet value: "+player.getBet()+"$");
                        }
                      }


                        a=new Protocolo();
                        try{
                            a.arg1 = (User) player;
                            a.envia(out);
                        } catch (Exception er) {
                            System.err.println(er.getMessage());
                            System.exit(1);
                        }
                       a=new Protocolo();
                        try{
                            a=a.recebe(in);
                        } catch (Exception er) {
                            System.err.println(er.getMessage());
                            System.exit(1);
                        }
                        player =(User) a.arg1;
                    cabeçalho.saldo.setText("Saldo:"+player.getSaldo()+"$");
                    computerhand.setText("Cartas computador: Score :"+botscore);


                      segunda.setVisible(true);

            	}
            	else
            	{
                Protocolo a=new Protocolo();

					           String result=null;

                    try{

                    a=a.recebe(in);
                    result=(String) a.arg1;
                    } catch (Exception er) {
                        System.err.println(er.getMessage());
                        System.exit(1);
                    }

                      if(result.equals("Winner"))
                      {
                        resultado.setText("Vencedor! Recebe -"+player.getBet()+"$");
                        resultado.setVisible(true);
                        player=(User) a.arg2;
                      }
                      if(result.equals("Tie"))
                      {
                        resultado.setText("Empate! A sua aposta foi retornada!");
                        resultado.setVisible(true);
                        player=(User) a.arg2;
                      }
                      if(result.equals("Loser"))
                      {
                        resultado.setText("Perdedor! Tenta outra vez (Dica, aposta mais)!");
                        resultado.setVisible(true);
                        if(player.getBet()>=player.getSaldo())
                        {
                          player.setBet(player.getSaldo());
                          cabeçalho.bettingarea.setText("bet value: "+player.getBet()+"$");
                        }
                      }
                    cabeçalho.saldo.setText("Saldo:"+player.getSaldo()+"$");
                    computerhand.setText("Cartas computador: Score :"+botscore);
              }
            }
          cabeçalho.bet.setEnabled(true);
          }

        });
        hit.addMouseListener(new MouseAdapter() { 
          public void mouseClicked(MouseEvent e){
                if (score2==0)
                {
                  if (score<21 && counter<3) 
                  {
                     counter++;
                        player.setBlackcards(baralho.drawFromDeck(),counter+1);
                        score=getCardsScore(player.getBlackcards(counter+1),score);
                        if(player.hasAce(player.getBlackcards(counter+1))==true)
                        {
                            acecounter++;
                         } 
                       if(score>21)
                       {
                           if(acecounter>0)
                            {

                             while(acecounter>0)
                              {
                                score=score-10;
                                acecounter--;
                              }
                            }
                        }
                        scoremao.setText("Score="+score);
                        tuamao.add(player.getBlackcards(counter+1).imgcard);
                        if(score>21)
                        {
                          hit.setVisible(false);
                          stand.setVisible(false);
                          split.setVisible(false);
                          resultado.setText("Bust!");
                          resultado.setVisible(true);
                          label[0].setVisible(false);
                          bot.add(computer.getBlackcards(1).imgcard);
                          computerhand.setText("Cartas computador: Score :"+botscore);
                           cabeçalho.bet.setEnabled(true);
                        }
                        if(score==21)
                        {
                          ganhou.setVisible(true);
                        }
                    }
                }
                else
                {
	              if(segundamao==0)	
	              {
	                  if (score<21 && counter<3) 
	                  {
	                     
	                    counter++;
	                    player.setBlackcards(baralho.drawFromDeck(),counter+3);  
	                    score=getCardsScore(player.getBlackcards(counter+3),score);
	                    if(player.hasAce(player.getBlackcards(counter+3))==true)
	                    {
	                        acecounter++;
	                     } 
	                     if(score>21)
	                     {
	                         if(acecounter>0)
	                          {

	                           while(acecounter>0)
	                            {
	                              score=score-10;
	                              acecounter--;
	                            }
	                          }
	                      }
	                    scoremao.setText("Score1="+score);
	                    tuamao.add(player.getBlackcards(counter+3).imgcard);
	                    if(score>21)
	                    {
	                      resultado.setText("Bust! Clique para jogar a segunda mao!");
	                      resultado.setVisible(true);
	                      hit.setVisible(false);
	                      stand.setVisible(false);
	                      split.setVisible(false);
	                      segunda.setVisible(true);
	                    }
	              	}
	              }
	              else
	              {
	              	if (score2<21 && counter1<3) 
	                {
	                     
	                    counter1++;
	                    player.setBlackcards(baralho.drawFromDeck(),counter+3+counter1);  
	                    score2=getCardsScore(player.getBlackcards(counter+3+counter1),score2);
	                    if(player.hasAce(player.getBlackcards(counter+3+counter1))==true)
	                    {
	                        acecounter++;
	                     } 
	                     if(score2>21)
	                     {
	                         if(acecounter>0)
	                          {

	                           while(acecounter>0)
	                            {
	                              score2=score2-10;
	                              acecounter--;
	                            }
	                          }
	                      }
	                    scoremao.setText("Score2="+score2);
	                    tuamao.add(player.getBlackcards(counter+3+counter1).imgcard);
	                    if(score2>21)
	                    {
	                      resultado.setText("Bust! Tenta novamente!");
	                      resultado.setVisible(true);
	                      hit.setVisible(false);
	                      stand.setVisible(false);
	                      split.setVisible(false);
	              			segundamao=0;
	                    }
                     cabeçalho.bet.setEnabled(true);
	                  
	                 }
                }
            }
           }
          });
  
  cenas.add(cabeçalho);
  headerbot.add(computerhand);
  cenas.add(musica);
   cenas.add(headerbot);
   tuamao.add(label[2]);
   tuamao.add(label[3]);
   bot.add(label[0]);
   bot.add(label[1]);
   cenas.add(bot);
   mao = new JTextArea("As suas cartas:");
   Cartscor.add(mao);
   Cartscor.add(scoremao);
   cenas.add(Cartscor);
   action.add(hit);
   action.add(stand);
   action.add(split);
   action.add(resultado);
    action.add(segunda);
   cenas.add(tuamao);
   cenas.add(action);
   add(cenas);
  }
  public int getCardsScore(Card a,int score)
  {
    switch(a.getRank())
    {
      case 1:
      return score+2;
      case 2:
      return score+3;
      case 3:
      return score+4;
      case 4:
      return score+5;
      case 5:
      return score+6;
      case 6:
      return score+7;
      case 7:
      return score+8;
      case 8:
      return score+9;
      case 9:
      return score+10;
      case 10:
      return score+10;
      case 11:
      return score+10;
      case 12:
      return score+10;
      case 0:
      {
        return score+11;
      }
    }
    return score+0;
  }
  public static void main(String[] args) {
    new Blackjack().setVisible(true);
  }
}