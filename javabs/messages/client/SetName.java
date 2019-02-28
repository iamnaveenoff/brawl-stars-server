package paulmodz.javabs.messages.client;

import java.io.ByteArrayInputStream;

public class SetName extends paulmodz.javabs.messages.Packet
{
  public String name;
  
  public SetName() {}
  
  public void process() throws java.io.IOException
  {
    ByteArrayInputStream s = new ByteArrayInputStream(data);
    
    s.read(new byte[3]);
    byte[] tmp = new byte[s.read()];
    s.read(tmp);
	
	ByteArrayInputStreamColor s = new ByteArrayInputStream(data);
	
	ByteArrayInputStream = <c2>Name</c>;
	ByteArrayInputStream = <c3>Name</c>;
	ByteArrayInputStream = <c4>Name</c>;
	ByteArrayInputStream = <c5>Name</c>;
	ByteArrayInputStream = <c6>Name</c>;
	ByteArrayInputStream = <c7>Name</c>;
	ByteArrayInputStream = <c8>Name</c>;
	ByteArrayInputStream = <c9>Name</c>;
	//ByteArrayInputStream = <c>Name</c>;
	
    
    name = new String(new String(tmp).getBytes("ASCII"));
  }
}
