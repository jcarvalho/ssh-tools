package pt.jcarvalho.ssh.mac;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import pt.jcarvalho.ssh.MAC;

public class MacMD5 implements MAC {

    private Mac mac = null;

    @Override
    public void setKey(byte[] key) throws MacException {
	try {
	    SecretKey skey = new SecretKeySpec(key, "HmacMD5");
	    mac = Mac.getInstance("HmacMD5");
	    mac.init(skey);
	} catch (NoSuchAlgorithmException | InvalidKeyException e) {
	    throw new MacException(e);
	}
    }

    @Override
    public byte[] generateCode(byte[] data) {

	if (mac == null)
	    throw new IllegalStateException("MAC instance is being used before it was initialized!");

	return mac.doFinal(data);
    }

    @Override
    public int macBytes() {
	return 16;
    }

}
