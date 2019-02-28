package paulmodz.javabs.messages.server;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

public class LoginOk extends paulmodz.javabs.messages.Packet
{
  int vid;
  String timestamp1 = Long.toString(System.currentTimeMillis());
  
  public LoginOk(int vid) {
    id = 20104;
    this.vid = vid;
  }
  
  public void process() throws java.io.IOException
  {
    ByteArrayOutputStream b = new ByteArrayOutputStream();
    DataOutputStream d = new DataOutputStream(b);
    
    d.writeLong(vid);
    d.writeLong(vid);
    d.write(new byte[] { 0, 0, 0, 4, 112, 97, 115, 115 });
    d.writeInt(-1);
    d.writeInt(-1);
    d.writeInt(0);
    d.write(0);
    d.write(0);
    d.write(new byte[] { 0, 0, 0, 4, 112, 114, 111, 100 });
    d.writeInt(0);
    d.write(0);
    d.writeInt(timestamp1.length());
    d.write(timestamp1.getBytes());
    d.writeInt(-1);
    d.writeInt(-1);
    d.write(0);
    d.writeInt(-1);
    d.writeInt(0);
    d.writeInt(0);
    
    data = b.toByteArray();
  }
}
