package paulmodz;

public class Sodium {
  public static Sodium sodium = new Sodium();
  
  private Sodium() {
    System.loadLibrary("lib");
  }
  
  public native byte[] genericHash(byte[] paramArrayOfByte);
  
  public native byte[] openPublicBox(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3, byte[] paramArrayOfByte4);
  
  public native byte[] createPublicBox(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3, byte[] paramArrayOfByte4);
  
  public native byte[] openBox(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3);
  
  public native byte[] createBox(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3);
  
  public native void incr2x(byte[] paramArrayOfByte);
}
