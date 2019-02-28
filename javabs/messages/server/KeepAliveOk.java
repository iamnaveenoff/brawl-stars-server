package paulmodz.javabs.messages.server;

import paulmodz.javabs.messages.Packet;

public class KeepAliveOk extends Packet
{
  public KeepAliveOk()
  {
    id = 20108;
  }
  
  public void process()
    throws java.io.IOException
  {}
}
