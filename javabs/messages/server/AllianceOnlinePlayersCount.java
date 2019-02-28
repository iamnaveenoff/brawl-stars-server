package paulmodz.javabs.messages.server;

import paulmodz.javabs.game.Alliance;
import paulmodz.javabs.game.Player;
import paulmodz.javabs.messages.Packet;

public class AllianceOnlinePlayersCount extends Packet
{
  Alliance all;
  
  public AllianceOnlinePlayersCount(Alliance all)
  {
    id = 20207;
    this.all = all;
  }
  
  public void process() throws java.io.IOException
  {
    data = new byte[1];
    
    for (Player pl : all.players) {
      if (current != null) {
        int tmp43_42 = 0; byte[] tmp43_39 = data;tmp43_39[tmp43_42] = ((byte)(tmp43_39[tmp43_42] + 1));
      }
    }
  }
}
