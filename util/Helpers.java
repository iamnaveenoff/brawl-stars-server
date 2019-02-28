package paulmodz.util;

import javax.xml.bind.DatatypeConverter;

public class Helpers { public Helpers() {}
  
  public static byte[] concat(byte[] one, byte[] two) { byte[] all = new byte[one.length + two.length];
    System.arraycopy(one, 0, all, 0, one.length);
    System.arraycopy(two, 0, all, one.length, two.length);
    return all;
  }
  
  public static byte[] concat(byte[] one, byte[] two, byte[] three) {
    byte[] all = new byte[one.length + two.length + three.length];
    System.arraycopy(one, 0, all, 0, one.length);
    System.arraycopy(two, 0, all, one.length, two.length);
    System.arraycopy(three, 0, all, one.length + two.length, three.length);
    return all;
  }
  
  public static byte[] parseHexBinary(String s) {
    return DatatypeConverter.parseHexBinary(s);
  }
}
