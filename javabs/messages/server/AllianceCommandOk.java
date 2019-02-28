package paulmodz.javabs.messages.server;

import paulmodz.javabs.game.Alliance;
import paulmodz.javabs.messages.Packet;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

public class AllianceCommandOk extends Packet
{
  Alliance all;
  int type;
  boolean adv;
  
  public AllianceCommandOk(Alliance all, int type, boolean adv)
  {
    id = 24111;
    this.all = all;
    this.type = type;
    this.adv = adv;
  }
  



  public void process()
    throws java.io.IOException
  {
    ByteArrayOutputStream b = new ByteArrayOutputStream();
    DataOutputStream d = new DataOutputStream(b);
    
    d.write(type);
    d.write(3);
    d.writeLong(all.id);
    
    if (type != 141) {
      d.writeInt(all.name.length());
      d.write(all.name.getBytes());
      d.write(16);
      d.write(encodeVInt(all.badge));
    }
    d.writeBoolean(adv);
    
    data = b.toByteArray();
  }
}
