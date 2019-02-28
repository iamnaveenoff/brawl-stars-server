package paulmodz.javabs.messages.server;

import paulmodz.javabs.messages.Packet;
import paulmodz.javabs.server.Main;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

public class UpdateResources extends Packet
{
  public UpdateResources()
  {
    id = 20103;
  }
  
  public void process() throws java.io.IOException
  {
    ByteArrayOutputStream b = new ByteArrayOutputStream();
    DataOutputStream d = new DataOutputStream(b);
    
    d.write(7);
    d.writeInt(Main.fingerPrint.length);
    d.write(Main.fingerPrint);
    d.writeInt(-1);
    d.writeInt(Main.fingerPrintUrl.length());
    d.write(Main.fingerPrintUrl.getBytes());
    
    data = b.toByteArray();
  }
}
