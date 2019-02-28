package paulmodz.javabs.messages.client;

import daniillnull.javacr.messages.Packet;
import java.math.BigInteger;

public class GetAllianceData extends Packet
{
  public int cid;
  
  public GetAllianceData() {}
  
  public void process() throws java.io.IOException
  {
    cid = new BigInteger(java.util.Arrays.copyOf(data, 8)).intValue();
  }
}
