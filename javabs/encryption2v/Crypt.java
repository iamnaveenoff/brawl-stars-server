package paulmodz.javabs.encryption2v;

import paulmodz.javabs.Sodium;

public class Crypt
{
  public Crypt() {}
  
  public static final byte[] sk = daniillnull.util.Helpers.parseHexBinary("1891d401fadb51d25d3a9174d472a9f691a45b974285d47729c45c6538070d85");
  public static final byte[] pk = daniillnull.util.Helpers.parseHexBinary("72f1a4a4c48e44da0c42310f800e96624e6dc6a641a9d41c3b5039d8dfadc27e");
  
  public byte[] clientKey = null;
  public byte[] sharedKey = null;
  public byte[] decryptNonce;
  
  public byte[] encryptNonce = Sodium.sodium.genericHash("hashedpass".getBytes());
  
  public byte[] decrypt(byte[] packet, int id) {
    if (id == 10100) {
      return packet;
    }
    if (id == 10101) {
      clientKey = java.util.Arrays.copyOf(packet, 32);
      sharedKey = java.util.Arrays.copyOf(packet, 32);
      byte[] nonce = Sodium.sodium.genericHash(paulmodz.javabs.util.Helpers.concat(clientKey, pk));
      
      byte[] chipherText = java.util.Arrays.copyOfRange(packet, clientKey.length, packet.length);
      byte[] message = Sodium.sodium.openPublicBox(chipherText, nonce, clientKey, sk);
      
      decryptNonce = java.util.Arrays.copyOfRange(message, 24, 48);
      return java.util.Arrays.copyOfRange(message, 48, message.length);
    }
    
    Sodium.sodium.incr2x(decryptNonce);
    return Sodium.sodium.openBox(packet, decryptNonce, sharedKey);
  }
  
  public byte[] encrypt(byte[] packet, int id)
  {
    if ((id == 20100) || ((id == 20103) && (clientKey == null))) {
      return packet;
    }
    if ((id == 20103) || (id == 20104)) {
      byte[] nonce = Sodium.sodium.genericHash(paulmodz.javabs.util.Helpers.concat(decryptNonce, clientKey, pk));
      byte[] message = daniillnull.util.Helpers.concat(encryptNonce, sharedKey, packet);
      byte[] chipherText = Sodium.sodium.createPublicBox(message, nonce, clientKey, sk);
      return chipherText;
    }
    
    Sodium.sodium.incr2x(encryptNonce);
    byte[] ret = Sodium.sodium.createBox(packet, encryptNonce, sharedKey);
    return ret;
  }
}
