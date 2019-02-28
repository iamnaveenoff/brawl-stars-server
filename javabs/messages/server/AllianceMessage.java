package paulmodz.javabs.messages.server;

import paulmodz.javabs.game.AllianceChatMessage;
import paulmodz.javabs.messages.Packet;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

public class AllianceMessage extends Packet
{
  AllianceChatMessage message;
  
  public AllianceMessage(AllianceChatMessage message)
  {
    id = 24312;
    
    this.message = message;
  }
  
  public void process() throws java.io.IOException
  {
    ByteArrayOutputStream b = new ByteArrayOutputStream();
    DataOutputStream d = new DataOutputStream(b);
    
    message.encode(d);
    
    data = b.toByteArray();
  }
}
