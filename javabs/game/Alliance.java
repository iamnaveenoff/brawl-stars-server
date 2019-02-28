package paulmodz.javabs.game;

import paulmodz.javabs.messages.Packet;
import paulmodz.javabs.messages.server.AllianceMessage;
import paulmodz.javabs.server.Session;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class Alliance
{
  private static HashMap<Integer, Alliance> alliances = new HashMap();
  public int id;
  
  static { load0descr = "Official clan"; }
  
  public int badge;
  public int reqsc;
  public int orig;
  public int score;
  
  private Alliance(int id)
  {
    this.id = id;
    players = new CopyOnWriteArrayList();
    messages = new CopyOnWriteArrayList();
    name = "DCS Team";
    descr = ""; }
  
  public String name;
  
  public static Alliance load(int id) { if (id == 0) {
      id = 1;
      while (alliances.containsKey(Integer.valueOf(id))) id++;
      alliances.put(Integer.valueOf(id), new Alliance(id));
    }
    
    return (Alliance)alliances.get(Integer.valueOf(id)); }
  
  public String descr;
  
  public static void loadSavedData() throws IOException { if (!new File("alliances.dat").exists()) {
      return;
    }
    
    DataInputStream d = new DataInputStream(new FileInputStream("alliances.dat"));
    int count = d.readInt();
    for (int i = 0; i < count; i++) {
      Alliance all = new Alliance(d.readInt());
      badge = d.readShort();
      orig = d.readShort();
      reqsc = d.readShort();
      name = Packet.readString(d);
      descr = Packet.readString(d);
      int pls = d.readInt();
      for (int q = 0; q < pls; q++) {
        Player pl = Player.load(d.readInt());
        all = all;
        players.add(pl);
      }
      d.skip(10L);
      alliances.put(Integer.valueOf(id), all);
    }
    d.close(); }
  
  public CopyOnWriteArrayList<Player> players;
  public transient CopyOnWriteArrayList<AllianceChatMessage> messages;
  public static void saveData() throws IOException { DataOutputStream d = new DataOutputStream(new FileOutputStream("alliances.dat"));
    d.writeInt(alliances.size());
    
    for (Alliance all : alliances.values()) {
      d.writeInt(id);
      d.writeShort(badge);
      d.writeShort(orig);
      d.writeShort(reqsc);
      d.writeInt(name.length());
      d.write(name.getBytes());
      d.writeInt(descr.length());
      d.write(descr.getBytes());
      d.writeInt(players.size());
      int tmpscore = 0;
      for (Player pl : players) {
        d.writeInt(id);
        tmpscore += score;
      }
      score = tmpscore;
      d.write(new byte[10]);
    }
    d.close();
  }
  
  public static java.util.List<Alliance> joinable() {
    int alls = alliances.size();
    int rets = alls > 20 ? 20 : alls;
    int offset = alls > rets ? new Random().nextInt(alls - rets) : 0;
    
    return new ArrayList(alliances.values()).subList(offset, offset + rets);
  }
  
  public void encode(DataOutput d) throws IOException {
    d.writeLong(id);
    d.writeInt(name.length());
    d.write(name.getBytes());
    d.write(16);
    d.write(Packet.encodeVInt(badge));
    d.write(1);
    d.write(players.size());
    d.write(Packet.encodeVInt(score));
    d.write(Packet.encodeVInt(reqsc));
    d.writeLong(57L);
    d.write(Packet.encodeVInt(orig));
  }
  
  public void addMessage(AllianceChatMessage message) {
    messages.add(message);
    
    if (messages.size() > 50) {
      messages.remove(0);
    }
    
    for (Player pl : players) {
      if (current != null) {
        try {
          current.os.write(new AllianceMessage(message));
        }
        catch (Exception localException) {}
      }
    }
  }
}
