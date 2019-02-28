package daniillnull.javacr.game;

import java.io.DataOutput;

public class AllianceChatMessage { public byte[] message;
  public byte[] pname;
  public byte[] mname;
  public int type;
  public int pid; public int prole;
  public AllianceChatMessage() {}
  public long time = System.currentTimeMillis();
  
  public static AllianceChatMessage createText(byte[] message, Player sender) {
    AllianceChatMessage m = new AllianceChatMessage();
    message = message;
    pname = name.getBytes();
    pid = id;
    prole = role;
    return m;
  }
  
  public static AllianceChatMessage createEvent(int type, Player sender, String mname) {
    AllianceChatMessage m = new AllianceChatMessage();
    mname = mname.getBytes();
    pid = id;
    pname = name.getBytes();
    type = type;
    return m;
  }
  
  public void encode(DataOutput d) throws java.io.IOException {
    d.write(type == 0 ? 2 : 4);
    d.write(0);
    d.write(daniillnull.javacr.messages.Packet.encodeVInt(hashCode() & 0xFFFFF));
    d.write(0);
    d.write(daniillnull.javacr.messages.Packet.encodeVInt(pid));
    d.write(0);
    d.write(daniillnull.javacr.messages.Packet.encodeVInt(pid));
    d.writeInt(pname.length);
    d.write(pname);
    d.write(new byte[] { 10, (byte)prole });
    d.write(daniillnull.javacr.messages.Packet.encodeVInt((int)(System.currentTimeMillis() / 1000L - time / 1000L)));
    d.write(0);
    
    if (type == 0) {
      d.writeInt(message.length);
      d.write(message);
    } else {
      d.write(type);
      d.write(0);
      d.write(0);
      d.writeInt(mname.length);
      d.write(mname);
    }
  }
}
