package roulete;
import java.util.Random;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import org.apache.commons.lang.SerializationUtils;
import java.io.*;
import java.net.*;

public class SlotMachine extends JFrame {
   User player=new User();
   Header cabecalho=new Header(player);
   PlayerSong musica=new PlayerSong();
   JPanel linhas=new JPanel(new GridLayout(0,3));
   JPanel board=new JPanel();
   JPanel preco=new JPanel();
   JLabel inliber,liber,boardi;
   JPanel alavanc=new JPanel();
   SlotLinha um=new SlotLinha();
   SlotLinha dois=new SlotLinha();
   SlotLinha tres=new SlotLinha();
   JTextArea ganhos=new JTextArea("Parabéns recebeu -");
    public ObjectInputStream in;
    public Socket client;
    public ObjectOutputStream out;
    private Timer spining,spining2;
    CardLayout c1 = (CardLayout) (um.getLayout());
    CardLayout c2 = (CardLayout) (dois.getLayout());
    CardLayout c3 = (CardLayout) (tres.getLayout());
    private int rand,rand1,rand2,value=0,value1=0,value2=0,selector=0,flagstop=1,wining=0;

   Thread one;

      Socket fileclient;
    ObjectOutputStream outfile;
	 ObjectInputStream infile;
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
	      filetolog.arg4=(String) "SlotMachine";

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
          one=new Thread(){
                public void run() {
                          if(player.getSaldo() > 0 && player.getBet() > 0 && flagstop==1)
                          {
                           ganhos.setVisible(false);
                            Protocolo a=new Protocolo();
                            alavanc.remove(liber); 
                             alavanc.add(inliber); 
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

                            //player.setSaldo(player.getSaldo()-player.getBet());
                            cabecalho.saldo.setText("Saldo: "+player.getSaldo());
                            selector=1;
                            rand=(int) a.arg2;
                            rand1=(int) a.arg3;
                            rand2=(int) a.arg4;
                            flagstop=0;
                            spining2=new Timer(30,new Flipping());
                            spining2.start();  

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
           out.write(3);
           out.flush();
          } catch (Exception er) {
            System.err.println(er.getMessage());
            System.exit(1);
          }
      alavanc.addMouseListener(listentoserver);
  }

    public SlotMachine() {
    super("Lucky BAR");
    ligaaoservidor();
    setSize(790, 500);
    player.setNome(JOptionPane.showInputDialog("Por favor insira o seu nome:"));
    if(player.getNome().equals(""))
      player.setNome("Convidado");
    setLayout(new BorderLayout());
    board.setLayout(new OverlayLayout(board));
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
    c1.first(um);
    c2.first(dois);
    c3.first(tres);
    um.linha.get(0).imgcard.setVisible(false);
    dois.linha.get(0).imgcard.setVisible(false);
    tres.linha.get(0).imgcard.setVisible(false);

    Icon inleaver=new ImageIcon("./img/slot/alav3.png");
    inliber = new JLabel(inleaver);
    Icon precario=new ImageIcon("./img/slot/precario.png");
    JLabel libel = new JLabel(precario);
    Icon base=new ImageIcon("./img/slot/base.png");
    boardi = new JLabel(base);
    board.add(boardi);
    libel.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
    preco.setVisible(true);
    preco.add(libel); 
    Icon leaver=new ImageIcon("./img/slot/alav.png");
    liber = new JLabel(leaver);
    liber.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
    alavanc.setVisible(true);
    alavanc.add(liber); 
    cabecalho.bet.setVisible(false);
    add(cabecalho,BorderLayout.NORTH);
    add(preco,BorderLayout.WEST);
    linhas.add(um);
    linhas.add(dois);
    linhas.add(tres);
    JPanel centro=new JPanel(new GridLayout(3,0));
    centro.add(board);
    centro.add(linhas);
    JPanel ganhador=new JPanel();
    ganhos.setEditable(false);
    ganhos.setVisible(false);
    ganhador.add(ganhos);
    centro.add(ganhador);
    add(centro,BorderLayout.CENTER);
    add(alavanc,BorderLayout.EAST);
    add(musica,BorderLayout.SOUTH);


    }
    public class Flipping implements ActionListener { 
      public void actionPerformed(ActionEvent e) 
      {
        if(selector==1)
        {
          new movingPanel(um,selector);
           selector=2;
        }
        if(selector==2)
        {
            new movingPanel(dois,selector);
           selector=3;
        }
        if(selector==3)
        {
            new movingPanel(tres,selector);
           selector=1;
        }
      }
    }
  public class movingPanel
  {
     movingPanel(SlotLinha a,int i)
      {
        if(i == 1 && value<rand)
        {
          c1.next(a);
          value++;
        }
        if(i == 2 && value1<rand1)
        {
          c2.next(a);
          value1++;

        }
        if(i == 3 && value2<rand2)
        {
          c3.next(a);
          value2++;
        }
        if(value==rand && value1==rand1 && value2==rand2)
        {
          alavanc.remove(inliber);
          alavanc.add(liber);
          wining=getWinning();
          spining2.stop();
           Protocolo sa=new Protocolo();
            try{
                sa.arg1= (int) wining;
                sa.envia(out);
            } catch (Exception er) {
                System.err.println(er.getMessage());
                System.exit(1);
            }
            try{

            sa=sa.recebe(in);
            player=(User) sa.arg1;
            } catch (Exception er) {
                System.err.println(er.getMessage());
                System.exit(1);
            }

            if(wining>0)
            {
	            ganhos.setText("Parabens recebeu - "+player.getBet()*(wining)+"$");
	            ganhos.setVisible(true);
            }
          cabecalho.saldo.setText("Saldo: "+player.getSaldo());
          flagstop=1;
        }
      }
  }
  public int getWinning() {
      int a=-1,b=0,cont=0,c=0,d=0;
      cont=value/316;
        b=value-316*cont;
      cont= value1/316;
        c=value1-316*cont;
      cont= value2/316;
        d=value2-316*cont;
      if(um.linha.get(b).getRank() == dois.linha.get(c).getRank() && dois.linha.get(c).getRank()==tres.linha.get(d).getRank())
      {
        switch(um.linha.get(b).getRank())
        {
          case 0:
          a=50;break;
          case 1:
          a=100;break;
          case 2:
          a=500;break;
          case 3:
          a=1;break;
          case 4:
          a=5;break;
          case 5:
          a=2;break;
          case 6:
          a=10;break;
          case 7:
          a=200;break;
          case 8:
          a=1000;break;
        }
      }
      value=0;
      value1=0;
      value2=0;
      return a;
    }
   public static void main(String[] args) {
    new Casino().setVisible(true);
  }
}
