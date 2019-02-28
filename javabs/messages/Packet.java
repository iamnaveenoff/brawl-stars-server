package paulmodz.javabs.messages;

import java.io.DataInputStream;
import java.io.InputStream;

public abstract class Packet
{
  public int id;
  public byte[] data = new byte[0];
  
  public Packet() {}
  
  public abstract void process() throws java.io.IOException;
  
  public static byte[] encodeVInt(int in) throws java.io.IOException { if (in < 64) {
      return new byte[] { (byte)(in & 0x3F) };
    }
    
    if (in < 8192) {
      return new byte[] { (byte)(in & 0x3F | 0x80), (byte)(in >> 6) };
    }
    
    if (in < 1048576) {
      return new byte[] { (byte)(in & 0x3F | 0x80), (byte)(in >> 6 | 0x80), (byte)(in >> 13) };
    }
    
    return new byte[1];
  }
  
  public int version;
  public static int readVInt(InputStream br) throws java.io.IOException { int b = br.read();
    int num = b & 0x80;
    int num2 = b & 0x3F;
    if ((b & 0x40) != 0)
    {
      if (num != 0)
      {
        b = br.read();
        num = b << 6 & 0x1FC0 | num2;
        if ((b & 0x80) != 0)
        {
          b = br.read();
          num |= b << 13 & 0xFE000;
          if ((b & 0x80) != 0)
          {
            b = br.read();
            num |= b << 20 & 0x7F00000;
            if ((b & 0x80) != 0)
            {
              b = br.read();
              num2 = num | b << 27 | 0x80000000;
            }
            else
            {
              num2 = num | 0xF8000000;
            }
          }
          else
          {
            num2 = num | 0xFFF00000;
          }
        }
        else
        {
          num2 = num | 0xE000;
        }
      }
    }
    else if (num != 0)
    {
      b = br.read();
      num2 |= b << 6 & 0x1FC0;
      if ((b & 0x80) != 0)
      {
        b = br.read();
        num2 |= b << 13 & 0xFE000;
        if ((b & 0x80) != 0)
        {
          b = br.read();
          num2 |= b << 20 & 0x7F00000;
          if ((b & 0x80) != 0)
          {
            b = br.read();
            num2 |= b << 27;
          }
        }
      }
    }
    return num2;
  }
  
  public static String readString(DataInputStream is) throws java.io.IOException {
    int len = is.readInt();
    if (len < 1) {
      return "";
    }
    byte[] tmp = new byte[len];
    is.read(tmp);
    return new String(tmp);
  }
}
