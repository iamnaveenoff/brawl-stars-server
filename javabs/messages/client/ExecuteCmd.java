package paulmodz.javabs.messages.client;

import paulmodz.javabs.game.Alliance;
import paulmodz.javabs.game.Player;
import paulmodz.javabs.messages.MessageOutputStream;
import paulmodz.javabs.messages.server.ChestData;
import paulmodz.javabs.server.Session;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;

public class ExecuteCmd extends paulmodz.javabs.messages.Packet
{
  public ExecuteCmd() {}
  
  public void process() throws IOException
  {}
  
  public void executeAll(Session s) throws IOException
  {
    DataInputStream d = new DataInputStream(new java.io.ByteArrayInputStream(data));
    
    readVInt(d);
    readVInt(d);
    int count = d.read();
    for (int i = 0; i < count; i++) {
      execute(d, s);
    }
  }
  
  protected void execute(DataInputStream d, Session s) throws IOException {
    int type = readVInt(d);
    
    if (type < 500) {
      skipS(d, type);
      return;
    }
    
    readVInt(d);
    readVInt(d);
    readVInt(d);
    readVInt(d);
    
    switch (type) {
    case 516: 
      d.read();
      int chestid = d.read();
      if (chestid == 60) {
        os.write(new ChestData(2));
      } else if (chestid == 61) {
        os.write(new ChestData(3));
      } else {
        os.write(new ChestData(1));
      }
      return;
    
    case 504: 
      short id = d.readShort();
      player.upgradeCard(id);
      return;
    
    case 500: 
      int f = d.read();int t = d.read();int n = d.read();
      if (n == 127) {
        int[] w = player.deck[t];
        player.deck[t] = player.unlockedCards[f];
        player.unlockedCards[f] = w;
      } else if (f == 127) {
        int[] w = player.deck[t];
        player.deck[t] = player.deck[n];
        player.deck[n] = w;
      }
      return;
    
    case 501: 
      d.read();
      throw new RuntimeException();
    

    case 550: 
      os.write(new ChestData(5));
      return;
    
    case 509: 
      os.write(new ChestData(4));
      return;
    
    case 551: 
      d.read();
      d.read();
      readVInt(d);
      readVInt(d);
      d.readInt();
      readVInt(d);
      readVInt(d);
      return;
    
    case 525: 
      os.write(new daniillnull.javacr.messages.server.SectorState(player, 11));
      d.skip(2L);
      return;
    
    case 514: 
      readString(d);
      Player tt = Player.load((int)d.readLong());
      if (player.all == all) {
        try {
          current.os.write(new daniillnull.javacr.messages.server.AllianceCommandOk(all, 141, true));
        } catch (Exception e) {
          kickedfrom = player.all.id;
        }
        
        player.all.players.remove(tt);
        player.all.addMessage(daniillnull.javacr.game.AllianceChatMessage.createEvent(1, tt, player.name));
        all = null;
      }
      return;
    
    case 539: 
      d.skip(1L);
      return;
    
    case 523: 
    case 526: 
    case 545: 
      return;
    }
    
    System.out.println("Unhandled: type: " + type + ", data: " + Arrays.toString(data) + ", av: " + d.available());
  }
  
  protected void skipS(DataInputStream d, int type) throws IOException
  {
    switch (type) {
    case 201: 
      readString(d);
      d.skip(6L);
      break;
    
    case 205: 
      d.skip(10L);
      break;
    
    case 206: 
      d.skip(8L);
      readString(d);
      d.skip(1L);
      readVInt(d);
      d.skip(2L);
      break;
    
    case 210: 
      d.skip(1L);
      if (d.read() != 0) throw new RuntimeException("This command cant contain cards if it received form client");
      d.skip(5L);
      break;
    case 202: case 203: case 204: case 207: 
    case 208: case 209: default: 
      System.out.println("Unhandled: type: " + type + ", data: " + Arrays.toString(data) + ", av: " + d.available());
    }
    
    readVInt(d);
    readVInt(d);
    readVInt(d);
    readVInt(d);
  }
}
