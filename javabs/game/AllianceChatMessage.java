package paulmodz.javabs.game;

import java.io.DataOutput;

public class AllianceChatMessage { public byte[] message;
  public byte[] pname;
  public byte[] mname;
  public int type;
  public int pid; public int prole;
  public AllianceChatMessage() {}
  public long time = System.currentTimeMillis();
  
  public static AllianceChatMessage createText(byte[] message, Player sender) {
    AllianceChatMessage m = new AllianceChatMessage();
    message = message;
    pname = name.getBytes();
    pid = id;
    prole = role;
    return m;
  }
  
  public static AllianceChatMessage createEvent(int type, Player sender, String mname) {
    AllianceChatMessage m = new AllianceChatMessage();
    mname = mname.getBytes();
    pid = id;
    pname = name.getBytes();
    type = type;
    return m;
  }
      d.write(0);
      d.write(0);
      d.writeInt(mname.length);
      d.write(mname);
    }
  }
}
