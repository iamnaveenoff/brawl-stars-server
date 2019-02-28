package paulmodz.javabs.messages;

import paulmodz.javabs.encryption2v.Crypt;
import java.io.DataInputStream;
import java.io.PrintStream;

public class MessageInputStream
{
  public DataInputStream is;
  public Crypt cr;
  
  public MessageInputStream(java.io.InputStream is, Crypt cr)
  {
    this.is = new DataInputStream(is);
    this.cr = cr;
  }
  
  public Packet read() throws java.io.IOException {
    int type = is.readChar();
    int length = is.read() << 16 | is.read() << 8 | is.read();
    char version = is.readChar();
    byte[] data = new byte[length];
    is.read(data);
    

    data = cr.decrypt(data, type);
    

    Packet p = null;
    
    switch (type) {
    case 10100: 
      p = new paulmodz.javabs.messages.client.SessionReq();
      break;
    
    case 10101: 
      p = new paulmodz.javabs.messages.client.Login();
      break;
    
    case 10108: 
      p = new paulmodz.javabs.messages.client.KeepAlive();
      break;
    
    case 14102: 
      p = new paulmodz.javabs.messages.client.ExecuteCmd();
      break;
    
    case 14104: 
      p = new paulmodz.javabs.messages.client.StartMission();
      break;
    
    case 10212: 
    case 14600: 
      p = new paulmodz.javabs.messages.client.SetName();
      break;
    
    case 14101: 
      p = new paulmodz.javabs.messages.client.GetHomeData();
      break;
    
    case 14113: 
      p = new paulmodz.javabs.messages.client.GetProfileData();
      break;
    
    case 14301: 
      p = new paulmodz.javabs.messages.client.CreateAlliance();
      break;
    
    case 14316: 
      p = new paulmodz.javabs.messages.client.ChangeAlliance();
      break;
    
    case 14302: 
      p = new paulmodz.javabs.messages.client.GetAllianceData();
      break;
    
    case 14308: 
      p = new paulmodz.javabs.messages.client.LeaveAlliance();
      break;
    
    case 14305: 
      p = new paulmodz.javabs.messages.client.JoinAlliance();
      break;
    
    case 14303: 
      p = new paulmodz.javabs.messages.client.GetJoinableAlliances();
      break;
    
    case 14315: 
      p = new paulmodz.javabs.messages.client.SendAllianceMessage();
      break;
    
    case 14306: 
      p = new paulmodz.javabs.messages.client.PromoteAllianceMember();
      break;
    
    case 10107: 
    case 10905: 
      return read();
    
    default: 
      System.out.println("Unhandled: " + type);
      System.out.println(java.util.Arrays.toString(data));
      return read();
    }
    
    id = type;
    version = version;
    data = data;
    p.process();
    
    return p;
  }
  
  public void close() throws java.io.IOException {
    is.close();
  }
}
