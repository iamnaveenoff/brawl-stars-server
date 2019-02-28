package paulmodz.javabs.messages.server;

import paulmodz.javabs.game.Alliance;
import paulmodz.javabs.messages.Packet;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.List;

public class JoinableAlliances extends Packet
{
  public JoinableAlliances()
  {
    id = 24304;
  }
  
  public void process() throws java.io.IOException
  {
    ByteArrayOutputStream b = new ByteArrayOutputStream();
    DataOutputStream d = new DataOutputStream(b);
    
    List<Alliance> alls = Alliance.joinable();
    d.write(alls.size());
    for (Alliance all : alls) {
      all.encode(d);
    }
    

    data = b.toByteArray();
  }
}
