package src;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AES {
    private SecretKey key;
    private final int KEY_SIZE = 64;
    private final int T_LEN = 64;

    private final String password = "holaholaholaholaholaholaholahola";
    private Cipher encryptionCipher;

// private String prueba="Variable no válida.\n";


    public void init() throws Exception {

        key = new SecretKeySpec(password.getBytes(),"AES");

        encryptionCipher = Cipher.getInstance("AES");
        encryptionCipher.init(Cipher.ENCRYPT_MODE, key);

    }

    public String encrypt(String message) throws Exception {
        byte[] messageInBytes = message.getBytes();
        System.out.println(message);
        System.out.println("LM: "+messageInBytes.length);
        byte[] encryptedBytes = encryptionCipher.doFinal(messageInBytes);
        init();
        return encode(encryptedBytes);
    }

    public String decrypt(String encryptedMessage) throws Exception {
        byte[] messageInBytes = decode(encryptedMessage);
        encryptionCipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedBytes = encryptionCipher.doFinal(messageInBytes);
        init();
        return new String(decryptedBytes);
    }

    private String encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    private byte[] decode(String data) {
        return Base64.getDecoder().decode(data);
    }
    
}
