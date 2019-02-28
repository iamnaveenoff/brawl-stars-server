package daniillnull.javacr.game;

import daniillnull.javacr.messages.Packet;
import daniillnull.javacr.server.Session;
import daniillnull.util.Helpers;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class Player
{
  private static HashMap<Integer, Player> players = new HashMap();
  
  public int id;
  public String name = "";
  public byte lvl = 4; public byte arena = 7; public byte role = 1;
  public int score = 4000; public int gems = 1000000; public int gold = 1000000;
  public int kickedfrom;
  public Alliance all;
  public Session current; public int[][] deck = {
    { 26, 0, 12 }, { 26, 1, 12 }, { 26, 2, 12 }, { 26, 3, 10 }, { 26, 4, 7 }, { 26, 5, 12 }, { 26, 6, 7 }, { 26, 7, 7 } };
  
  public int[][] unlockedCards = {
    { 26, 8, 12 }, { 26, 9, 7 }, { 26, 10, 12 }, { 26, 11, 10 }, { 26, 12, 7 }, { 26, 13, 12 }, { 26, 14, 10 }, { 26, 15, 7 }, { 26, 16, 7 }, { 26, 17, 10 }, { 26, 18, 10 }, { 26, 19, 12 }, { 26, 20, 7 }, { 26, 21, 10 }, { 26, 22, 12 }, { 26, 23, 4 }, { 26, 24, 12 }, { 26, 25, 7 }, { 26, 26, 4 }, { 26, 27, 7 }, { 26, 28, 10 }, { 26, 29, 4 }, { 26, 30, 12 }, { 26, 31, 12 }, { 26, 32, 4 }, { 26, 33, 4 }, { 26, 34, 7 }, { 26, 35, 4 }, { 26, 36, 10 }, { 26, 37, 4 }, { 26, 38, 10 }, { 26, 39, 10 }, { 26, 40, 10 }, { 26, 41, 12 }, { 26, 42, 4 }, { 26, 43, 12 }, { 26, 45, 7 }, 
    { 27, 0, 12 }, { 27, 1, 10 }, { 27, 2, 12 }, { 27, 3, 10 }, { 27, 4, 10 }, { 27, 5, 10 }, { 27, 6, 12 }, { 27, 7, 10 }, { 27, 8, 7 }, { 27, 9, 10 }, { 27, 10, 10 }, 
    { 28, 0, 10 }, { 28, 1, 12 }, { 28, 2, 7, 10 }, { 28, 3, 10 }, { 28, 4, 7 }, { 28, 5, 7 }, { 28, 6, 7 }, { 28, 7, 7 }, { 28, 8, 12 }, { 28, 9, 7 }, { 28, 10, 4 }, { 28, 11, 4 }, { 28, 12, 7 }, { 28, 13, 7 } };
  
  private Player(int id)
  {
    this.id = id;
  }
  
  public static Player load(int id) {
    if (id == 0) {
      id = 1;
      while (players.containsKey(Integer.valueOf(id))) { id++;
      }
    }
    if (!players.containsKey(Integer.valueOf(id))) {
      players.put(Integer.valueOf(id), new Player(id));
    }
    
    return (Player)players.get(Integer.valueOf(id));
  }
  
  public static void loadSavedData() throws IOException {
    if (!new File("players.dat").exists()) {
      return;
    }
    
    DataInputStream d = new DataInputStream(new FileInputStream("players.dat"));
    int count = d.readInt();
    for (int i = 0; i < count; i++) {
      Player pl = new Player(d.readInt());
      name = Packet.readString(d);
      arena = ((byte)d.read());
      lvl = ((byte)d.read());
      role = ((byte)d.read());
      score = d.readInt();
      gems = d.readInt();
      gold = d.readInt();
      for (int q = 0; q < 8; q++) {
        deck[q][0] = d.read();
        deck[q][1] = d.read();
        deck[q][2] = d.read();
      }
      unlockedCards = new int[d.readInt()][];
      for (int q = 0; q < unlockedCards.length; q++) {
        unlockedCards[q] = {
          d.read(), 
          d.read(), 
          d.read() };
      }
      
      kickedfrom = d.readInt();
      d.skip(16L);
      players.put(Integer.valueOf(id), pl);
    }
    d.close();
  }
  
  public static void saveData() throws IOException {
    DataOutputStream d = new DataOutputStream(new FileOutputStream("players.dat"));
    d.writeInt(players.size());
    for (Player pl : players.values()) {
      d.writeInt(id);
      d.writeInt(name.length());
      d.write(name.getBytes());
      d.write(arena);
      d.write(lvl);
      d.write(role);
      d.writeInt(score);
      d.writeInt(gems);
      d.writeInt(gold);
      for (int q = 0; q < 8; q++) {
        d.write(deck[q][0]);
        d.write(deck[q][1]);
        d.write(deck[q][2]);
      }
      d.writeInt(unlockedCards.length);
      for (int q = 0; q < unlockedCards.length; q++) {
        d.write(unlockedCards[q][0]);
        d.write(unlockedCards[q][1]);
        d.write(unlockedCards[q][2]);
      }
      d.writeInt(kickedfrom);
      d.write(new byte[16]);
    }
    d.close();
  }
  
  public void encode(DataOutput d) throws IOException {
    d.writeLong(id);
    
    d.write(Helpers.parseHexBinary("0500A8E4D201A8F3D20181A5D1870B00030880EAE51881EAE5188DEAE51881FCD91A80FCD91A83EAE51800000880EAE51881EAE5188DEAE51881FCD91A80FCD91A83EAE51800000880EAE51881EAE5188DEAE51881FCD91A80FCD91A83EAE5180000FF"));
    for (int i = 0; i < 8; i++) {
      d.write(deck[i][0]);
      d.write(deck[i][1]);
      d.write(deck[i][2]);
      d.write(0);
      d.write(0);
      d.writeShort(0);
      d.write(0);
    }
    d.write(Packet.encodeVInt(unlockedCards.length));
    for (int i = 0; i < unlockedCards.length; i++) {
      d.write(unlockedCards[i][0]);
      d.write(unlockedCards[i][1]);
      d.write(unlockedCards[i][2]);
      d.write(0);
      d.write(0);
      d.writeShort(0);
      d.write(0);
    }
    
    d.write(Helpers.parseHexBinary("0000007F7F0000000000007F0113070102007F000000000000000000000000000000000000007F010000000000000002"));
    d.write(new byte[] { lvl, 54, arena });
    
    d.write(Helpers.parseHexBinary("0000007f00007f00007f130e9705000002b3200500011a27010900000000F807"));
    for (int i = 0; i < 8; i++) {
      d.write(deck[i][0]);
      d.write(deck[i][1]);
      d.write(deck[i][2]);
      d.write(0);
      d.write(0);
      d.writeShort(0);
      d.write(0);
    }
    d.write(Helpers.parseHexBinary("09A5EAE518A6EAE518A7EAE518A8EAE518AAEAE518ABEAE5188AFCD91A8CFCD91A8DFCD91A0000000000010000AEDFC6870B01000000000000007F000000"));
    d.write(Helpers.parseHexBinary("0113940301ba09007f94198e0404029c9f038019587c53c158804841adaf3c"));
    
    encodeEntry(d);
    
    d.write(new byte[] { 0, 0, 0, 0, 0, 6 });
    d.write(Helpers.parseHexBinary("FBFADD8003BFDFC6870B"));
  }
  
  public void encodeEntry(DataOutput d) throws IOException {
    d.write(0);
    d.write(Packet.encodeVInt(id));
    d.write(0);
    d.write(Packet.encodeVInt(id));
    d.write(0);
    d.write(Packet.encodeVInt(id));
    
    d.writeInt(name.length());
    d.write(name.getBytes());
    d.write(new byte[] { name.isEmpty() ? 127 : 0, 54, arena });
    
    d.write(Packet.encodeVInt(score));
    d.write(new byte[6]);
    d.write(Packet.encodeVInt(score));
    d.write(Packet.encodeVInt(score));
    
    d.write(7);
    d.write(1);
    d.writeShort(1281);
    d.write(Packet.encodeVInt(gold));
    d.write(0);
    d.write(0);
    d.write(0);
    d.write(0);
    d.write(0);
    d.write(0);
    
    d.write(Packet.encodeVInt(gems));
    d.write(Packet.encodeVInt(gems));
    d.write(new byte[] { 0, lvl });
    
    if (all != null) {
      d.write(1);
      d.write(name.isEmpty() ? 8 : 9);
      d.write(0);
      d.write(Packet.encodeVInt(all.id));
      d.writeInt(all.name.length());
      d.write(all.name.getBytes());
      d.write(16);
      d.write(Packet.encodeVInt(all.badge));
      d.write(role);
    } else {
      d.write(0);
      d.write(name.isEmpty() ? 0 : 1);
    }
  }
  
  public void encodeAsMember(DataOutput d) throws IOException {
    d.writeLong(id);
    d.writeInt(-1);
    d.writeInt(name.length());
    d.write(name.getBytes());
    d.write(new byte[] { 54, arena, role, lvl });
    d.write(Packet.encodeVInt(score));
    d.write(0);
    d.write(new byte[] { 20, 48, 49, 0, 0, Byte.MAX_VALUE, -83, -81, 60, 6 });
    d.writeLong(id);
  }
  
  public void upgradeCard(short id) {
    for (int[] card : unlockedCards) {
      if ((card[0] == id >> 8) && (card[1] == (id & 0xFF))) {
        card[2] += 1;
        
        break;
      }
    }
    for (int[] card : deck) {
      if ((card[0] == id >> 8) && (card[1] == (id & 0xFF))) {
        card[2] += 1;
        
        break;
      }
    }
  }
}
