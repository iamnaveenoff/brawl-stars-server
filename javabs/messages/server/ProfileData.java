package paulmodz.javabs.messages.server;

import paulmodz.javabs.game.Player;
import paulmodz.javabs.messages.Packet;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

public class ProfileData extends Packet
{
  Player own;
  
  public ProfileData(Player own)
  {
    id = 24113;
    this.own = own;
  }
  
  public void process() throws java.io.IOException
  {
    ByteArrayOutputStream b = new ByteArrayOutputStream();
    DataOutputStream d = new DataOutputStream(b);
    
    d.write(3);
    d.write(255);
    for (int i = 0; i < 8; i++) {
      d.write(own.deck[i][0]);
      d.write(own.deck[i][1]);
      d.write(own.deck[i][2]);
      d.write(0);
      d.write(1);
      d.writeShort(0);
      d.write(0);
    }
    d.writeLong(own.id);
    own.encodeEntry(d);
    d.write(new byte[] { 0, 0, 0, 0, 0, 1 });
    
    data = b.toByteArray();
  }
}
