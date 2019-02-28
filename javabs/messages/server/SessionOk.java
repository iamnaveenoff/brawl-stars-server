package paulmodz.javabs.messages.server;

import paulmodz.javabs.messages.Packet;

public class SessionOk extends Packet
{
  public SessionOk() {
    id = 20100;
  }
  
  public void process() throws java.io.IOException
  {
    data = new byte[] { 0, 0, 0, 24, 99, 104, 101, 99, 107, 32, 111, 107, 32, 99, 104, 101, 99, 107, 32, 111, 107, 32, 107, 101, 101, 101, 101, 107 };
  }
}
