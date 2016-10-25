package roulete;
import java.io.*;
import java.net.*;
import java.util.Random;
import java.util.Date;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import org.apache.commons.lang.SerializationUtils;

public class ServeSorte{
  protected final int DIM = 4;
  protected ArrayList<ObjectOutputStream>  oos=new ArrayList<ObjectOutputStream>();
  protected ArrayList<ObjectInputStream>  in=new ArrayList<ObjectInputStream>();
  protected ServerSocket SocketEscuta;
  protected ArrayList<Socket> meias=new ArrayList<Socket>();
  int n = 0; 
  static Thread escreficheiros;
  ObjectOutputStream writestream;
  ObjectInputStream readstream;

  public ServeSorte (int listenPort)
  throws IOException   {
    SocketEscuta = new ServerSocket(listenPort, DIM);
   }
   public ServeSorte ()  {

   }
  public void waitforwriting()
  {

    escreficheiros=new Thread(){
      public void run(){
      while(true){
         try{
        Socket supermeia=SocketEscuta.accept();
         writestream=new ObjectOutputStream(supermeia.getOutputStream());
         readstream=new ObjectInputStream(supermeia.getInputStream());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
          Protocolo filesprot=new Protocolo();
          try {
                filesprot=filesprot.recebe(readstream);
            
            }catch (Exception er) {
           try{
            escreficheiros.join();

            } catch (Exception erro) {
                System.err.println(erro.getMessage());
                System.exit(1);
            }
              System.err.println(er.getMessage());
          }
            String option= (String) filesprot.arg1;
          if(option.equals("Get HighScores"))
          {

                String  thisLine = null;
                String [] scorezinho=new String[10];
              try{
                 // open input stream test.txt for reading purpose.
                 BufferedReader br = new BufferedReader(new FileReader ("highscores.txt"));
                 int counter=0;
                 while ((thisLine = br.readLine()) != null) {
                  counter++;

                      scorezinho[counter-1]=thisLine;    
                 }      
                 

              }catch(Exception e){
                 e.printStackTrace();
                }
              filesprot=new Protocolo();
                try{
                    filesprot.arg1= (String []) scorezinho;
                    filesprot.envia(writestream);
                } catch (Exception er) {
                     try{
                      escreficheiros.join();

                      } catch (Exception erro) {
                          System.err.println(erro.getMessage());
                          System.exit(1);
                      }
                    System.err.println(er.getMessage());
                    System.exit(1);
                }

          }
          if(option.equals("Recieve HighScores"))
          {
                String omeunome=(String) filesprot.arg2;
                User omeuplayer=(User) filesprot.arg3;
                String typeofgame=(String) filesprot.arg4;
                Date date=new Date();
                String s =date.toString() + " - " +  omeunome + "|" + omeuplayer.getSaldo() + "|" + typeofgame;

                try(FileWriter fw = new FileWriter("scores.txt", true);
                  BufferedWriter bw = new BufferedWriter(fw);
                  PrintWriter out = new PrintWriter(bw))
                {
                  out.println(s);
                } catch (IOException e) {
                  e.printStackTrace();
                }
                String  thisLine = null;
                String [] scorezinho=null;
                int minscore=0;
                try{
                   // open input stream test.txt for reading purpose.
                   BufferedReader br = new BufferedReader(new FileReader ("highscores.txt"));
                   int counter=0;
                   while ((thisLine = br.readLine()) != null) {
                    counter++;

                        scorezinho=thisLine.split("\\|");
                        if(minscore>Integer.parseInt(scorezinho[1]) && counter<=10)
                        minscore=Integer.parseInt(scorezinho[1]);
                       
                   }      
                   

                }catch(Exception e){
                   e.printStackTrace();
                }
                 if(omeuplayer.getSaldo()>minscore)
                 {
                   s =omeunome + "|" + omeuplayer.getSaldo() + "|" + typeofgame;

                  try(FileWriter fw = new FileWriter("highscores.txt", true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    PrintWriter out = new PrintWriter(bw))
                  {
                    out.println(s);
                  } catch (IOException e) {
                    e.printStackTrace();
                  }

                 }

          }
        } 

      }
    };
    escreficheiros.start();


  }
  public void waitForClients()
  {
    
    try {
      while ( n < DIM ){
      
        Socket supermeia=SocketEscuta.accept();
		    meias.add(supermeia);
		    oos.add(new ObjectOutputStream(meias.get(n).getOutputStream()));
		    in.add(new ObjectInputStream(meias.get(n).getInputStream()));
	        System.out.println(n);
          n=meias.size();
         System.out.println("Conexao estabelicidada - "+n);

	    Thread one=new Thread(){
	    	public void run(){
        Protocolo jogo = new Protocolo();
          User player=new User();
	    		int my=meias.size()-1;
	    		System.out.println(my);
           int selector=-1;
           Socket minhameia=supermeia;
              try{
               selector=in.get(my).read();
              } catch (IOException er) {
                   meias.remove(minhameia);
                   in.remove(minhameia);
                   oos.remove(minhameia);
                   n=meias.size();
                     try{
                      join();

                      } catch (Exception erro) {
                          System.err.println(erro.getMessage());
                          System.exit(1);
                      }
                System.err.println(er.getMessage());
                System.exit(1);
              }
          switch(selector)
          {
            case 1:
            {
              for(;;)
              {
               try {
                jogo=jogo.recebe(in.get(my));
            
                    }catch (Exception er) {
                 meias.remove(minhameia);
                 in.remove(minhameia);
                 oos.remove(minhameia);
                 n=meias.size();
                   try{
                    join();

                    } catch (Exception erro) {
                        System.err.println(erro.getMessage());
                        System.exit(1);
                    }
                      System.err.println(er.getMessage());
                  }
               player= (User) jogo.arg1;
               System.out.println(player.getOdds());
               player.setSaldo(player.getSaldo()-player.getBet());
                jogo=new Protocolo();
                try{
                    jogo.arg1= (User) player;
                    jogo.envia(oos.get(my));
                } catch (Exception er) {
                   meias.remove(minhameia);
                   in.remove(minhameia);
                   oos.remove(minhameia);
                   n=meias.size();
                     try{
                      join();

                      } catch (Exception erro) {
                          System.err.println(erro.getMessage());
                          System.exit(1);
                      }
                    System.err.println(er.getMessage());
                    System.exit(1);
                }
                try {
                Random r=new Random();
                oos.get(my).write(r.nextInt(37));
                oos.get(my).flush();
            
                    } catch (IOException er) {
                   meias.remove(minhameia);
                   in.remove(minhameia);
                   oos.remove(minhameia);
                   n=meias.size();
                     try{
                      join();

                      } catch (Exception erro) {
                          System.err.println(erro.getMessage());
                          System.exit(1);
                      }
                        System.err.println(er.getMessage());
                    }

                int aux=0;
                  try{
                   aux=in.get(my).read();
                  } catch (IOException er) {
                   meias.remove(minhameia);
                   in.remove(minhameia);
                   oos.remove(minhameia);
                   n=meias.size();
                     try{
                      join();

                      } catch (Exception erro) {
                          System.err.println(erro.getMessage());
                          System.exit(1);
                      }
                    System.err.println(er.getMessage());
                    System.exit(1);
                  }
                  player.setSaldo(player.getSaldo()+aux);
                System.out.println("Acrescentou - "+aux);
                  try{
                    jogo.arg1= (User) player;
                    jogo.envia(oos.get(my));
                  } catch (Exception er) {
                   meias.remove(minhameia);
                   in.remove(minhameia);
                   oos.remove(minhameia);
                   n=meias.size();
                     try{
                      join();

                      } catch (Exception erro) {
                          System.err.println(erro.getMessage());
                          System.exit(1);
                      }
                    System.err.println(er.getMessage());
                    System.exit(1);
                }
                System.out.println("Saldo - "+player.getSaldo()+"$ Bet -"+player.getBet()+"$");

              }
            }
            case 2:
            {
              for(;;)
              {
                try {
                jogo=jogo.recebe(in.get(my));
            
                    }catch (Exception er) {
                 meias.remove(minhameia);
                 in.remove(minhameia);
                 oos.remove(minhameia);
                 n=meias.size();
                   try{
                    join();

                    } catch (Exception erro) {
                        System.err.println(erro.getMessage());
                        System.exit(1);
                    }
                      System.err.println(er.getMessage());
                  }
               player= (User) jogo.arg1;
               player.setSaldo(player.getSaldo()-player.getBet());
                jogo=new Protocolo();
                try{
                    jogo.arg1= (User) player;
                    jogo.envia(oos.get(my));
                } catch (Exception er) {
                   meias.remove(minhameia);
                   in.remove(minhameia);
                   oos.remove(minhameia);
                   n=meias.size();
                     try{
                      join();

                      } catch (Exception erro) {
                          System.err.println(erro.getMessage());
                          System.exit(1);
                      }
                    System.err.println(er.getMessage());
                    System.exit(1);
                }

               System.out.println(player.getSaldo());
                try {
                jogo=jogo.recebe(in.get(my));
            
                    } catch (IOException er) {
                   meias.remove(minhameia);
                   in.remove(minhameia);
                   oos.remove(minhameia);
                   n=meias.size();
                     try{
                      join();

                      } catch (Exception erro) {
                          System.err.println(erro.getMessage());
                          System.exit(1);
                      }
                        System.err.println(er.getMessage());
                    }catch (Exception er) {
                   meias.remove(minhameia);
                   in.remove(minhameia);
                   oos.remove(minhameia);
                   n=meias.size();
                     try{
                      join();

                      } catch (Exception erro) {
                          System.err.println(erro.getMessage());
                          System.exit(1);
                      }
                        System.err.println(er.getMessage());
                    }
                
                  String resultado=(String) jogo.arg1;
                  int score=(int) jogo.arg2;
                  int botscore=(int) jogo.arg3;
                  player=(User) jogo.arg4;
                  jogo=new Protocolo();
                  if(resultado.equals("No-split"))
                  {
                    System.out.println("Entrei no no-split");
                    if(score<=21)
                    {
                      if(botscore>21 || score>botscore)
                      {
                        jogo.arg1="Winner";
                        System.out.println("Winer\n");
                        player.setSaldo(player.getSaldo()+2*player.getBet());
                        jogo.arg2=player;

                      }
                      if(botscore<21 && botscore>score || botscore==21)
                      {
                        jogo.arg1="Loser";
                        System.out.println("Loser\n");
                        jogo.arg2=player;

                      }
                      if(botscore==score)
                      {
                        jogo.arg1="Tie";

                        System.out.println("tie\n");
                        player.setSaldo(player.getSaldo()+player.getBet());
                        jogo.arg2=player;

                      }
                    
                      try{
                        jogo.envia(oos.get(my));
                      } catch (Exception er) {
                   meias.remove(minhameia);
                   in.remove(minhameia);
                   oos.remove(minhameia);
                   n=meias.size();
                     try{
                      join();

                      } catch (Exception erro) {
                          System.err.println(erro.getMessage());
                          System.exit(1);
                      }
                        System.err.println(er.getMessage());
                        System.exit(1);
                     }

                    }

                  }
                  if(resultado.equals("Split"))
                  {
                    System.out.println("Entrei no split");
                    for(int i=0;i<2;i++)
                    {
                      if(score<=21)
                    {
                      if(botscore>21 || score>botscore)
                      {
                        jogo.arg1="Winner";
                        System.out.println("Winer\n");
                        player.setSaldo(player.getSaldo()+2*player.getBet());
                        jogo.arg2=player;

                      }
                      if(botscore<21 && botscore>score || botscore==21)
                      {
                        jogo.arg1="Loser";
                        System.out.println("Loser\n");
                        jogo.arg2=player;

                      }
                      if(botscore==score)
                      {
                        jogo.arg1="Tie";

                        System.out.println("tie\n");
                        player.setSaldo(player.getSaldo()+player.getBet());
                        jogo.arg2=player;

                      }
                      
                        try{
                          jogo.envia(oos.get(my));
                        } catch (Exception er) {
                   meias.remove(minhameia);
                   in.remove(minhameia);
                   oos.remove(minhameia);
                   n=meias.size();
                     try{
                      join();

                      } catch (Exception erro) {
                          System.err.println(erro.getMessage());
                          System.exit(1);
                      }
                          System.err.println(er.getMessage());
                          System.exit(1);
                       }

                      }
                     if(i==0)
                     {
                    System.out.println("Enviando dados de palyer");

                       Protocolo a=new Protocolo();
                        try{
                           a= a.recebe(in.get(my));
                        } catch (Exception er) {
                         meias.remove(minhameia);
                         in.remove(minhameia);
                         oos.remove(minhameia);
                           try{
                            join();

                            } catch (Exception erro) {
                                System.err.println(erro.getMessage());
                                System.exit(1);
                            }
                            System.err.println(er.getMessage());
                            System.exit(1);
                        }
                        player=(User) a.arg1;
                        player.setSaldo(player.getSaldo()-player.getBet());
                        a=new Protocolo();
                        try{
                            a.arg1 = (User) player;
                            a.envia(oos.get(my));
                        } catch (Exception er) {
                   meias.remove(minhameia);
                   in.remove(minhameia);
                   oos.remove(minhameia);
                   n=meias.size();
                     try{
                      join();

                      } catch (Exception erro) {
                          System.err.println(erro.getMessage());
                          System.exit(1);
                      }
                            System.err.println(er.getMessage());
                            System.exit(1);
                        }
                     }

                    } 
                 }
                  
                System.out.println("Saldo - "+player.getSaldo()+"$ Bet -"+player.getBet()+"$");

              }
            }
            case 3:
            {
              for(;;)
              {
                try {
                jogo=jogo.recebe(in.get(my));
            
                    }catch (Exception er) {
                 meias.remove(minhameia);
                 in.remove(minhameia);
                 oos.remove(minhameia);
                 n=meias.size();
                   try{
                    join();

                    } catch (Exception erro) {
                        System.err.println(erro.getMessage());
                        System.exit(1);
                    }
                      System.err.println(er.getMessage());
                  }
               player= (User) jogo.arg1;
               player.setSaldo(player.getSaldo()-player.getBet());
                jogo=new Protocolo();
                try{
                  int rand=0,rand1=0,rand2=0;
                    jogo.arg1= (User) player;
                    rand=rand+new Random().nextInt(80);
                    rand=rand+50;
                    jogo.arg2= (int) rand;
                    rand1=rand+new Random().nextInt(60);
                    jogo.arg3= (int) rand1;
                    rand2=rand1+new Random().nextInt(50);
                    jogo.arg4= (int) rand2;
                    jogo.envia(oos.get(my));
                } catch (Exception er) {
                   meias.remove(minhameia);
                   in.remove(minhameia);
                   oos.remove(minhameia);
                   n=meias.size();
                     try{
                      join();

                      } catch (Exception erro) {
                          System.err.println(erro.getMessage());
                          System.exit(1);
                      }
                    System.err.println(er.getMessage());
                    System.exit(1);
                }
                 Protocolo a=new Protocolo();
                 int wining=0;
                  try{

                  a=a.recebe(in.get(my));
                  wining=(int) a.arg1;
                  } catch (Exception er) {
                      System.err.println(er.getMessage());
                      System.exit(1);
                  }
                  player.setSaldo(player.getSaldo()+player.getBet()*(wining+1));
                  try{
                      a.arg1= (User) player;
                      a.envia(oos.get(my));
                  } catch (Exception er) {
                      System.err.println(er.getMessage());
                      System.exit(1);
                  }

                System.out.println("Saldo - "+player.getSaldo()+"$ Bet -"+player.getBet()+"$");

              }

            }
          }

        }
	    };
	    if(!one.isAlive())
	    one.start();
     }
      } catch (Exception e) {e.printStackTrace();}
  }
    public static void main(String [] args){
    try {
    ServeSorte servidor = new ServeSorte(5000);

        try{
        ServeSorte servescore = new ServeSorte(4000);
        servescore.waitforwriting();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
     servidor.waitForClients();
    } catch (Exception e) {
      System.out.println("Erro:");
     e.printStackTrace();
    }
  }
}