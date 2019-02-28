package paulmodz.javabs.messages.server;

import paulmodz.javabs.game.Alliance;
import paulmodz.javabs.game.Player;
import paulmodz.javabs.Packet;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

public class AllianceData extends Packet
{
  Alliance all;
  
  public AllianceData(Alliance all)
  {
    id = 24301;
    this.all = all;
  }
  
  public void process() throws java.io.IOException
  {
    ByteArrayOutputStream b = new ByteArrayOutputStream();
    DataOutputStream d = new DataOutputStream(b);
    
    all.encode(d);
    d.writeInt(all.descr.length());
    d.write(all.descr.getBytes());
    d.write(all.players.size());
    for (Player player : all.players) {
      player.encodeAsMember(d);
    }
    
    d.write(1);
    d.write(0);
    d.write(encodeVInt(259200));
    d.write(encodeVInt(3250));
    d.writeInt(0);
    
    data = b.toByteArray();
  }
}
