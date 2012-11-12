package io_benchmarks_vertx;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.vertx.java.core.Handler;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.streams.WriteStream;

public class HashStream implements WriteStream {
    
    private MessageDigest md;
    
    public HashStream() {
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void writeBuffer(Buffer data) {
        md.update(data.getBytes());
    }

    @Override
    public void setWriteQueueMaxSize(int maxSize) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean writeQueueFull() {
        return false;
    }

    @Override
    public void drainHandler(Handler<Void> handler) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void exceptionHandler(Handler<Exception> handler) {
        throw new UnsupportedOperationException("Not supported yet.");
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
