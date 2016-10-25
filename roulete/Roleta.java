package roulete;
import java.util.*;
import java.util.Date;
import java.util.Random;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import org.apache.commons.lang.SerializationUtils;
import java.io.*;
import java.net.*;


public class Roleta extends JFrame {
  private int first=0;
  private int sortudo=0;
  private int sortudo1=0;
  User player=new User();
  PlayerSong musica=new PlayerSong();
 JPanel rodar = new JPanel();
  JButton  roda= new JButton("Saiu o numero:"+sortudo);
  JTextArea insuficiente = new JTextArea("Fundos esgotados, por favor reinicie o jogo!",1,2);
  JTextArea nobet = new JTextArea("Não inseriu uma aposta",1,2);
  Header cabecalho=new Header(player);
    Board numerosort = new Board();
  public ObjectInputStream in;
  public Socket client;
  public ObjectOutputStream out;
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
      filetolog.arg4=(String) "Roleta";

        try{
         filetolog.envia(outfile);
        } catch (Exception er) {
          System.err.println(er.getMessage());
          System.exit(1);
        }
        try{
      fileclient.close();
    } catch (IOException e) {
        System.err.println(e.getMessage());
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
        try{
      fileclient.close();
    } catch (IOException e) {
        System.err.println(e.getMessage());
        System.exit(1);
    }
  }
    MouseAdapter listentoserver=new MouseAdapter(){
        public void mouseClicked(MouseEvent e)
        {
            Thread one=new Thread(){
                public void run() {
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
                            try{
                              sortudo=in.read();
                            } catch (Exception er) {
                                System.err.println(er.getMessage());
                                System.exit(1);
                            }
                            numerosort.Bolarodando(sortudo);
                            int ganhos=0;
                              if(convertesorte(sortudo)==1)
                              {
                                ganhos=player.getBet()*(player.getOdds()+1);
                              }
                              if(convertesorte(sortudo)!=1){
                            roda.setText("Saiu o numero: "+sortudo1);
                                
                              }
                             try{

                            out.write(ganhos);
                            out.flush();
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

                            roda.setText("Saiu o numero: "+sortudo1+" Parabéns recebeu -"+player.getBet()*player.getOdds()+"$");
                            cabecalho.saldo.setText("Saldo:"+player.getSaldo()+"$");
                            player.resetOdds();
                          }
                      };

                  System.out.println("Começando thread");
                        one.start();
        }
    };

  public Roleta() {
  	super("Roleta Russa");
    ligaaoservidor();
    setSize(750, 725);
    player.setNome(JOptionPane.showInputDialog("Por favor insira o seu nome:"));
    if(player.getNome().equals(""))
      player.setNome("Convidado");
    setLayout(new GridLayout(4,1));
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
    JPanel userstatus = new JPanel(new GridLayout(3,0));
    JPanel numbers = new JPanel();   
    JPanel groups = new JPanel();
	Button button1[]=new Button[40];
	Button button2;
    groups.setLayout(new GridLayout(2,0));
    for(int j=1;j<=2;j++)
    {

      JPanel pane = new JPanel();
      if(j==1)
      pane.setLayout(new GridLayout(0,3));
      else
      pane.setLayout(new GridLayout(0,6));
      if(j==1)
      {
        for(int i=1;i<=3;i++)
        {
            button2 = new Button(i,j,2);
          JPanel button = new JPanel();
          button=button2.getButton();
            pane.add(button);
        }
      }
      else
      {
        for(int i=1;i<=6;i++)
        {
            button2 = new Button(i,j,2);
          JPanel button = new JPanel();
          button=button2.getButton();
            pane.add(button);
        }
      }
      groups.add(pane);
    }  
    numbers.setLayout(new GridLayout(0,14));
    JPanel column0 = new JPanel();
    column0.setLayout(new GridLayout(0,1));

    button1[0] = new Button(0,0,3);
    JPanel button0 = new JPanel();
    button0=button1[0].getButton();
    column0.add(button0);
    numbers.add(column0);
    for(int j=1;j<=13;j++)
    {

        JPanel column = new JPanel();
        column.setLayout(new GridLayout(0,1));

      for(int i=3;i>=1;i--)
      {
        
        button1[(i+(j-1)*3)] = new Button(i,j,1);

        JPanel button = new JPanel();
        button=button1[(i+(j-1)*3)].getButton();
        column.add(button);
      }
      numbers.add(column);
    }

    add(userstatus);
	add(numerosort);
    add(numbers);
    add(groups);
    
	cabecalho.bet.addMouseListener(new MouseAdapter() { 
    public void mouseClicked(MouseEvent e)
    {
      if(player.getSaldo() > 0 && player.getBet() > 0 && Button.getKind()!=-1)
		  {
          new Sound("./sounds/rou.wav");
        player.setOdds(Button.getBettingmult(Button.getKind()));
  			//player.setSaldo(player.getSaldo()-player.getBet());
  			cabecalho.saldo.setText("Saldo:"+player.getSaldo()+"$");
  			if(player.getSaldo()<player.getBet())
  			{
  				player.setBet(player.getSaldo());
  				cabecalho.bettingarea.setText("bet value: "+player.getSaldo()+"$");
  			}
      }

    }

    });
    userstatus.add(cabecalho);
    rodar.add(roda);
    userstatus.add(musica);
    userstatus.add(rodar);
  }
  public int convertesorte(int rand)
  {
    int a=-1,coluna=0,cor=-1;
    switch(rand)
    {
      case 1:
      a=32;coluna=2;cor=1;break;
      case 2:
      a=15;coluna=3;cor=0;break;
      case 3:
      a=19;coluna=1;cor=1;break;
      case 4:
      a=4;coluna=1;cor=0;break;
      case 5:
      a=21;coluna=3;cor=1;break;
      case 6:
      a=2;coluna=2;cor=0;break;
      case 7:
      a=25;coluna=1;cor=1;break;
      case 8:
      a=17;coluna=2;cor=0;break;
      case 9:
      a=34;coluna=1;cor=1;break;
      case 10:
      a=6;coluna=3;cor=0;break;
      case 11:
      a=27;coluna=3;cor=1;break;
      case 12:
      a=13;coluna=1;cor=0;break;
      case 13:
      a=36;coluna=3;cor=1;break;
      case 14:
      a=11;coluna=2;cor=0;break;
      case 15:
      a=30;coluna=3;cor=1;break;
      case 16:
      a=8;coluna=2;cor=0;break;
      case 17:
      a=23;coluna=2;cor=1;break;
      case 18:
      a=10;coluna=1;cor=0;break;
      case 19:
      a=5;coluna=2;cor=1;break;
      case 20:
      a=24;coluna=3;cor=0;break;
      case 21:
      a=16;coluna=1;cor=1;break;
      case 22:
      a=33;coluna=3;cor=0;break;
      case 23:
      a=1;coluna=1;cor=1;break;
      case 24:
      a=20;coluna=2;cor=0;break;
      case 25:
      a=14;coluna=2;cor=1;break;
      case 26:
      a=31;coluna=1;cor=0;break;
      case 27:
      a=9;coluna=3;cor=1;break;
      case 28:
      a=22;coluna=1;cor=0;break;
      case 29:
      a=18;coluna=3;cor=1;break;
      case 30:
      a=29;coluna=2;cor=0;break;
      case 31:
      a=7;coluna=1;cor=1;break;
      case 32:
      a=28;coluna=1;cor=0;break;
      case 33:
      a=12;coluna=3;cor=1;break;
      case 34:
      a=35;coluna=2;cor=0;break;
      case 35:
      a=3;coluna=3;cor=1;break;
      case 36:
      a=26;coluna=2;cor=0;break;
      case 0:
      a=0;cor=2;break;
    }
    sortudo1=a;
    if(cor==2)
    {
      if(Button.getKind()==a)
        return 1;
      else
        return 0;
    }
    if(Button.getKind()/100==a)
      return 1;
    if(Button.getKind()/100==37 && coluna==1)
      return 1; 
    if(Button.getKind()/100==38 && coluna==2)
      return 1;
    if(Button.getKind()/100==39 && coluna==3)
      return 1;
    if(Button.getKind()==22 && a%2==0)
      return 1;
    if(Button.getKind()==52 && a%2!=0)
      return 1;
    if(Button.getKind()==32 && cor==1)
      return 1;
    if(Button.getKind()==42 && cor==0)
      return 1;
    if(Button.getKind()==62 && a>18)
      return 1;
    if(Button.getKind()==12 && a<18)
      if(a!=0)
      return 1;
    if(Button.getKind()==11 && a>0 && a<=12)
      return 1;
    if(Button.getKind()==21 && a>12 && a<=24)
      return 1;
    if(Button.getKind()==31 && a>24 && a<=36)
      return 1;
    return 0;
  }
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
           out.write(1);
           out.flush();
          } catch (Exception er) {
            System.err.println(er.getMessage());
            System.exit(1);
          }
      cabecalho.bet.addMouseListener(listentoserver);

  }
   public static void main(String[] args) {
    new Casino().setVisible(true);
  }
}
