package paulmodz.javabs.messages.server;

import paulmodz.javabs.messages.Packet;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

public class ChangeNameOk extends Packet
{
  String newName;
  
  public ChangeNameOk(String newName)
  {
    id = 24111;
    this.newName = newName;
  }
  
  public void process() throws java.io.IOException
  {
    ByteArrayOutputStream b = new ByteArrayOutputStream();
    DataOutputStream d = new DataOutputStream(b);
    
    d.write(137);
    d.write(3);
    d.writeInt(newName.length());
    d.write(newName.getBytes());
    d.writeInt(0);
    d.write(1);
    d.write(2);
    
    data = b.toByteArray();
  }
}
