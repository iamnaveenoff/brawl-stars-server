package paulmodz.javabs.messages;

import java.io.DataOutputStream;

public class MessageOutputStream
{
  public DataOutputStream is;
  public paulmodz.javabs.encryption2v.Crypt cr;
  
  public MessageOutputStream(java.io.OutputStream is, paulmodz.javabs.encryption2v.Crypt cr) {
    this.is = new DataOutputStream(is);
    this.cr = cr;
  }
  
  public void write(Packet p) throws java.io.IOException {
    p.process();
    
    data = cr.encrypt(data, id);
    
    is.writeChar((char)id);
    is.write(data.length >>> 16);
    is.write(data.length >>> 8);
    is.write(data.length >>> 0);
    is.writeChar(version);
    is.write(data);
  }
  
  public void close() throws java.io.IOException {
    is.close();
  }
}
