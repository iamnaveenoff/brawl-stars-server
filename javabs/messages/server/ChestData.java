package paulmodz.javabs.messages.server;

import paulmodz.javabs.messages.Packet;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class ChestData extends Packet
{
// Still in development
  

  public ArrayList<byte[]> was = new ArrayList();
  public int type = 0;
  
  public ChestData(int type) {
    id = 24111;
    this.type = type;
  }
  





  public void process()
    throws IOException
  {
    ByteArrayOutputStream b = new ByteArrayOutputStream();
    DataOutputStream d = new DataOutputStream(b);
    Random r = new Random();
    
    d.write(146);
    d.write(3);
    d.write(1);
    
    // nothing
    
    
    d.write(0);
    d.write(0);
    d.write(encodeVInt(634));
    d.write(type == 5 ? 9 : type == 4 ? 2 : 8);
    
    data = b.toByteArray();
  }
}
