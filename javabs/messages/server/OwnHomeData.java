package paulmodz.javabs.messages.server;

import paulmodz.javabs.game.Player;
import paulmodz.javabs.messages.Packet;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

public class OwnHomeData extends Packet
{
  Player own;
  
  public OwnHomeData(Player own)
  {
    id = 24101;
    this.own = own;
  }
  
  public void process() throws java.io.IOException
  {
    ByteArrayOutputStream b = new ByteArrayOutputStream();
    DataOutputStream d = new DataOutputStream(b);
    
    own.encode(d);
    
    data = b.toByteArray();
  }
}
