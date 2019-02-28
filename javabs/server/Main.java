package daniillnull.javacr.server;

import daniillnull.javacr.encryption2v.Crypt;
import daniillnull.javacr.game.Alliance;
import daniillnull.javacr.game.Player;
import daniillnull.javacr.messages.MessageInputStream;
import java.io.FileInputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

public class Main
{
  public static LinkedList<Session> sessions;
  public static byte[] fingerPrint = new byte[80000];
  
  public Main() {}
  
  public static void main(String[] args) throws Exception { fingerPrintUrl = args.length > 1 ? args[0] : "";
    fingerPrint = args.length > 1 ? Arrays.copyOf(fingerPrint, new FileInputStream(args[1]).read(fingerPrint)) : null;
    fingerPrintHash = new String(Arrays.copyOfRange(fingerPrint, new String(fingerPrint).indexOf("],\"sha\":") + 9, fingerPrint.length)).substring(0, 40);
    
    System.out.println("Server starting...");
    
    sessions = new LinkedList();
    
    Player.loadSavedData();
    Alliance.loadSavedData();
    
    new Timer(true).schedule(new TimerTask()
    {
      public void run() {
        try {
          Player.saveData();
          Alliance.saveData();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }, 1000L, 20000L);
    
    ServerSocket ss = new ServerSocket(9339);
    while (ss.isBound()) {
      Socket s = ss.accept();
      
      Session z = new Session();
      curr = new Thread(z);
      cr = new Crypt();
      is = new MessageInputStream(s.getInputStream(), cr);
      os = new daniillnull.javacr.messages.MessageOutputStream(s.getOutputStream(), cr);
      curr.start();
      
      sessions.add(z);
    }
    
    ss.close();
  }
  
  public static String fingerPrintUrl;
  public static String fingerPrintHash;
}
