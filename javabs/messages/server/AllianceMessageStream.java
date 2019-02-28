package paulmodz.javabs.messages.server;

import paulmodz.javabs.game.AllianceChatMessage;
import paulmodz.javabs.messages.Packet;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.List;

public class AllianceMessageStream extends Packet
{
  public List<AllianceChatMessage> messages;
  
  public AllianceMessageStream(List<AllianceChatMessage> messages)
  {
    id = 24311;
    this.messages = messages;
  }
  
  public void process() throws java.io.IOException
  {
    ByteArrayOutputStream b = new ByteArrayOutputStream();
    DataOutputStream d = new DataOutputStream(b);
    
    d.write(messages.size());
    for (AllianceChatMessage message : messages) {
      message.encode(d);
    }
    
    data = b.toByteArray();
  }
}
