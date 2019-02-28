package paulmodz.javabs.messages.client;

import java.io.DataInputStream;

public class CreateAlliance extends paulmodz.javabs.messages.Packet {
  public String name;
  public String descr;
  public int badge;
  public int reqsc;
  public int orig;
  
  public CreateAlliance() {}
  
  public void process() throws java.io.IOException {
    DataInputStream s = new DataInputStream(new java.io.ByteArrayInputStream(data));
    
    name = readString(s);
    descr = readString(s);
    s.read();
    badge = readVInt(s);
    s.read();
    reqsc = readVInt(s);
    s.read();
    orig = readVInt(s);
  }
}
