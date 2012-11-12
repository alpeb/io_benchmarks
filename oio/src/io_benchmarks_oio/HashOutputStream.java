package io_benchmarks_oio;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashOutputStream extends OutputStream {
    
    private MessageDigest md;
    
    public HashOutputStream() {
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void write(int b) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public void write(byte[] buffer, int offset, int length) {
        md.update(buffer, offset, length);
    }
    
    public String getHash() {
        BigInteger bigInt = new BigInteger(1,md.digest());
        String hashText = bigInt.toString(16);
        while(hashText.length() < 32 ){
            hashText = "0"+hashText;
        }
        return hashText;
    }
    
}
